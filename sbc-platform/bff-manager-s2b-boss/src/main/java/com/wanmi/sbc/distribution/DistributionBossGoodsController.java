package com.wanmi.sbc.distribution;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyDistributionGoodsAuditRequest;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.elastic.bean.vo.storeInformation.EsCompanyInfoVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.info.DistributionGoodsBatchCheckRequest;
import com.wanmi.sbc.goods.api.request.info.DistributionGoodsCheckRequest;
import com.wanmi.sbc.goods.api.request.info.DistributionGoodsForbidRequest;
import com.wanmi.sbc.goods.api.request.info.DistributionGoodsRefuseRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.SaleType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * S2B的BOSS分销商品服务
 * Created by CHENLI on 19/2/22.
 */
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name =  "S2B的BOSS分销商品服务" ,description = "DistributionBossGoodsController")
public class DistributionBossGoodsController {

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private EsStoreInformationQueryProvider esStoreInformationQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private GoodsBaseService goodsBaseService;

    /**
     * 分页查询分销商品
     *
     * @param request 商品 {@link EsSkuPageRequest}
     * @return 分销商品分页
     */
    @Operation(summary = "分页查询分销商品")
    @RequestMapping(value = "/distribution-sku", method = RequestMethod.POST)
    public BaseResponse<EsSkuPageResponse> page(@RequestBody EsSkuPageRequest request) {
        request.setDelFlag(DeleteFlag.NO.toValue());//可用状态
//        request.setAuditStatus(CheckStatus.CHECKED);//已审核状态
        request.setShowPointFlag(Boolean.TRUE);//购买积分填充
        request.setShowProviderInfoFlag(Boolean.TRUE);//填充供应商商品信息
        request.setFillLmInfoFlag(Boolean.TRUE);//LM库存填充
        request.setSaleType(SaleType.RETAIL.toValue());//零售
        if(Objects.nonNull(request.getCommissionRateFirst())){
            request.setCommissionRateFirst(request.getCommissionRateFirst().divide(BigDecimal.valueOf(100)));
        }
        if(Objects.nonNull(request.getCommissionRateLast())){
            request.setCommissionRateLast(request.getCommissionRateLast().divide(BigDecimal.valueOf(100)));
        }
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
                                companyInfoVO.setSupplierName(c.getSupplierName());
                                companyInfoVO.setCompanyCode(c.getCompanyCode());
                                return companyInfoVO;
                            })
                            .collect(Collectors.toList()));
                }
            }

            systemPointsConfigService.clearBuyPointsForGoodsInfoVO(voList);
        }

        //填充marketingGoodsStatus属性
        goodsBaseService.populateMarketingGoodsStatus(voList);

        return BaseResponse.success(response);
    }

    /**
     * 分销商品审核通过(单个)
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "分销商品审核通过(单个)")
    @RequestMapping(value = "/distribution-check", method = RequestMethod.POST)
    public BaseResponse checkDistributionGoods(@RequestBody DistributionGoodsCheckRequest request) {
        goodsInfoProvider.checkDistributionGoods(request);
        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "审核通过单个分销商品", "商品SKU编码:"+request.getGoodsInfoId());

        EsGoodsInfoModifyDistributionGoodsAuditRequest auditRequest = new EsGoodsInfoModifyDistributionGoodsAuditRequest();
        auditRequest.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED.toValue());
        auditRequest.setGoodsInfoIds(Collections.singletonList(request.getGoodsInfoId()));
        esGoodsInfoElasticProvider.modifyDistributionGoodsAudit(auditRequest);

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量审核分销商品
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量审核分销商品")
    @RequestMapping(value = "/distribution-batch-check", method = RequestMethod.POST)
    public BaseResponse batchCheckDistributionGoods(@RequestBody DistributionGoodsBatchCheckRequest request) {
        goodsInfoProvider.batchCheckDistributionGoods(request);

        EsGoodsInfoModifyDistributionGoodsAuditRequest auditRequest = new EsGoodsInfoModifyDistributionGoodsAuditRequest();
        auditRequest.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED.toValue());
        auditRequest.setGoodsInfoIds(request.getGoodsInfoIds());
        esGoodsInfoElasticProvider.modifyDistributionGoodsAudit(auditRequest);
        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "批量审核分销商品", "批量审核分销商品通过");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 驳回分销商品
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "驳回分销商品")
    @RequestMapping(value = "/distribution-refuse", method = RequestMethod.POST)
    public BaseResponse refuseCheckDistributionGoods(@RequestBody DistributionGoodsRefuseRequest request) {
        goodsInfoProvider.refuseCheckDistributionGoods(request);

        EsGoodsInfoModifyDistributionGoodsAuditRequest auditRequest = new EsGoodsInfoModifyDistributionGoodsAuditRequest();
        auditRequest.setDistributionGoodsAudit(DistributionGoodsAudit.NOT_PASS.toValue());
        auditRequest.setGoodsInfoIds(Collections.singletonList(request.getGoodsInfoId()));
        auditRequest.setDistributionGoodsAuditReason(request.getDistributionGoodsAuditReason());
        esGoodsInfoElasticProvider.modifyDistributionGoodsAudit(auditRequest);

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "驳回分销商品",
                "商品SKU编码:"+request.getGoodsInfoId()+"原因:"+request.getDistributionGoodsAuditReason());

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 禁止分销商品
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "禁止分销商品")
    @RequestMapping(value = "/distribution-forbid", method = RequestMethod.POST)
    public BaseResponse forbidCheckDistributionGoods(@RequestBody DistributionGoodsForbidRequest request) {
         goodsInfoProvider.forbidCheckDistributionGoods(request);

        EsGoodsInfoModifyDistributionGoodsAuditRequest auditRequest = new EsGoodsInfoModifyDistributionGoodsAuditRequest();
        auditRequest.setDistributionGoodsAudit(DistributionGoodsAudit.FORBID.toValue());
        auditRequest.setGoodsInfoIds(Collections.singletonList(request.getGoodsInfoId()));
        auditRequest.setDistributionGoodsAuditReason(request.getDistributionGoodsAuditReason());
        esGoodsInfoElasticProvider.modifyDistributionGoodsAudit(auditRequest);

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "禁止分销商品",
                "商品SKU编码:"+request.getGoodsInfoId()+"原因:"+request.getDistributionGoodsAuditReason());

        return BaseResponse.SUCCESSFUL();
    }
}
