package com.wanmi.sbc.goods;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoEnterpriseAuditRequest;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.elastic.bean.vo.storeInformation.EsCompanyInfoVO;
import com.wanmi.sbc.goods.api.provider.enterprise.EnterpriseGoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.enterprise.EnterpriseGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.enterprise.goods.EnterpriseAuditCheckRequest;
import com.wanmi.sbc.goods.api.request.enterprise.goods.EnterpriseAuditStatusBatchRequest;
import com.wanmi.sbc.goods.api.response.enterprise.EnterpriseGoodsAuditResponse;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author baijianzhong
 * @ClassName EnterpriseGoodsInfoController
 * @Date 2020-03-05 11:30
 * @Description TODO
 **/
@RestController
@Validated
@RequestMapping(value = "/enterprise/goodsInfo")
public class EnterpriseGoodsInfoController {

    @Autowired
    private EnterpriseGoodsInfoQueryProvider enterpriseGoodsInfoQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EnterpriseGoodsInfoProvider enterpriseGoodsInfoProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private EsStoreInformationQueryProvider esStoreInformationQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsBaseService goodsBaseService;


    @Operation(summary = "分页查询企业购商品")
    @PostMapping("/page")
    public BaseResponse<EsSkuPageResponse> pageEnterpriseGoodsInfo(@RequestBody @Valid EsSkuPageRequest request) {
        request.setDelFlag(DeleteFlag.NO.toValue());//可用
        request.setFillLmInfoFlag(Boolean.TRUE);
        EsSkuPageResponse response = esSkuQueryProvider.page(request).getContext();
        List<GoodsInfoVO> voList = response.getGoodsInfoPage().getContent();
        if (CollectionUtils.isNotEmpty(voList)) {
            EsCompanyPageRequest pageRequest = new EsCompanyPageRequest();
            pageRequest.setPageSize(request.getPageSize());
            pageRequest.setStoreIds(voList.stream().map(GoodsInfoVO::getStoreId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(pageRequest.getStoreIds())) {
                List<EsCompanyInfoVO> infoVOS = esStoreInformationQueryProvider.companyInfoPage(pageRequest).getContext().getEsCompanyAccountPage().getContent();
                if (CollectionUtils.isNotEmpty(infoVOS)) {
                    response.setCompanyInfoList(infoVOS.stream()
                            .filter(IteratorUtils.distinctByKey(EsCompanyInfoVO::getCompanyInfoId)) //去重复companyId
                            .map(c -> {
                                //封装简易companyVo
                                CompanyInfoVO companyInfoVO = new CompanyInfoVO();
                                companyInfoVO.setCompanyInfoId(c.getCompanyInfoId());
                                companyInfoVO.setSupplierName(c.getStoreName());
                                companyInfoVO.setCompanyCode(c.getCompanyCode());
                                return companyInfoVO;
                            })
                            .collect(Collectors.toList()));
                }
            }
        }
        //填充营销商品状态
        goodsBaseService.populateMarketingGoodsStatus(voList);

        return BaseResponse.success(response);
    }

    /**
     * 单个审核接口
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "单个审核接口")
    @PostMapping("/audit")
    public BaseResponse modifyEnterpriseGoodsInfo(@RequestBody @Valid EnterpriseAuditCheckRequest request){
        //验证是否购买了企业购服务
        commonUtil.getIepSettingInfo();
        String operateStr = EnterpriseAuditState.NOT_PASS.equals(request.getEnterpriseAuditState()) ? "未通过" : "通过";
        operateLogMQUtil.convertAndSend("应用", "审核"+operateStr+"单个企业购商品", "商品SKUId:"+request.getGoodsInfoId());
        EnterpriseGoodsAuditResponse response = enterpriseGoodsInfoProvider.auditEnterpriseGoods(request).getContext();
        if(CommonErrorCodeEnum.K000000.getCode().equals(response.getBackErrorCode())){
            //更新es
            esGoodsInfoElasticProvider.modifyEnterpriseAuditStatus(EsGoodsInfoEnterpriseAuditRequest.builder()
                    .enterPriseGoodsAuditReason(request.getEnterPriseGoodsAuditReason())
                    .enterPriseAuditStatus(request.getEnterpriseAuditState())
                    .goodsInfoIds(Collections.singletonList(request.getGoodsInfoId()))
                    .build());
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.info(GoodsErrorCodeEnum.K030143.getCode(),
                "提示该商品状态已发生变更请刷新列表");
    }


    /**
     * 批量审核接口
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量审核接口")
    @PostMapping("/batchAudit")
    public BaseResponse batchAuditEnterpriseGoods(@RequestBody @Valid EnterpriseAuditStatusBatchRequest request){
        //验证是否购买了企业购服务
        commonUtil.getIepSettingInfo();
        String operateStr = EnterpriseAuditState.NOT_PASS.equals(request.getEnterpriseGoodsAuditFlag()) ? "未通过" : "通过";
        operateLogMQUtil.convertAndSend("应用", "审核"+operateStr+"单个企业购商品",
                "商品SKUId:"+ JSONObject.toJSONString(request.getGoodsInfoIds()));
        BaseResponse response = enterpriseGoodsInfoProvider.batchAuditEnterpriseGoods(request);
        if(CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())){
            //更新es
            esGoodsInfoElasticProvider.modifyEnterpriseAuditStatus(EsGoodsInfoEnterpriseAuditRequest.builder()
                    .enterPriseGoodsAuditReason(request.getEnterPriseGoodsAuditReason())
                    .enterPriseAuditStatus(request.getEnterpriseGoodsAuditFlag())
                    .goodsInfoIds(request.getGoodsInfoIds())
                    .build());
        }
        return BaseResponse.SUCCESSFUL();
    }
}
