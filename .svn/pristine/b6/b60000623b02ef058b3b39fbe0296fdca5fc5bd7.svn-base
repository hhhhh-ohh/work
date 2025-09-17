package com.wanmi.sbc.enterprise;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoEnterpriseBatchAuditRequest;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.goods.api.provider.enterprise.EnterpriseGoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.enterprise.EnterpriseGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.enterprise.goods.*;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.enterprise.EnterpriseBatchDeleteResponse;
import com.wanmi.sbc.goods.api.response.enterprise.EnterpriseCheckResponse;
import com.wanmi.sbc.goods.api.response.enterprise.EnterpriseGoodsAddResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.bean.dto.BatchEnterPrisePriceDTO;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author baijianzhong
 * @ClassName EnterPriseGoodsInfoController
 * @Date 2020-03-03 16:19
 * @Description TODO
 **/
@Tag(name =  "企业购商品" ,description = "EnterpriseGoodsInfoController")
@RestController
@Validated
@RequestMapping("/enterprise")
public class EnterpriseGoodsInfoController {

    @Autowired
    private EnterpriseGoodsInfoQueryProvider enterpriseGoodsInfoQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private EnterpriseGoodsInfoProvider enterpriseGoodsInfoProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private GoodsBaseService goodsBaseService;


    @Operation(summary = "分页查询企业购商品")
    @PostMapping("/goodsInfo/page")
    public BaseResponse<EsSkuPageResponse> pageEnterpriseGoodsInfo(@RequestBody @Valid EsSkuPageRequest request) {
        request.setStoreId(commonUtil.getStoreId());
        request.setDelFlag(DeleteFlag.NO.toValue());//可用
        request.setFillLmInfoFlag(Boolean.TRUE);//填充LM库存信息
        request.setFillStoreCate(Boolean.TRUE);//填充店铺分类id
        BaseResponse<EsSkuPageResponse> page = esSkuQueryProvider.page(request);
        //填充供应商名称
        List<GoodsInfoVO> goodsInfoVo = page.getContext().getGoodsInfoPage().getContent();
        storeBaseService.populateProviderName(goodsInfoVo);

        //填充营销商品状态
        goodsBaseService.populateMarketingGoodsStatus(goodsInfoVo);

        return page;
    }

    /**
     * 批量新增企业购商品
     *
     * @param batchUpdateRequest
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量新增企业购商品")
    @PostMapping(value = "/batchAdd")
    public BaseResponse batchAddEnterpriseGoodsInfo(@RequestBody @Valid EnterprisePriceBatchUpdateRequest batchUpdateRequest) {
        //判断是否购买了企业购服务
        IepSettingVO iepSettingVO = commonUtil.getIepSettingInfo();
        //审核开关入参
        batchUpdateRequest.setEnterpriseGoodsAuditFlag(iepSettingVO.getEnterpriseGoodsAuditFlag());
        EnterpriseGoodsAddResponse response =
                enterpriseGoodsInfoProvider.batchUpdateEnterprisePrice(batchUpdateRequest).getContext();
        if (CollectionUtils.isNotEmpty(response.getGoodsInfoIds())) {
            return BaseResponse.info(GoodsErrorCodeEnum.K030081.getCode(), "存在失效的商品，请删除后再保存",
                    response.getGoodsInfoIds());
        }
        //入日志
        operateLogMQUtil.convertAndSend("应用", "添加企业购商品",
                "skuIds: " + Arrays.toString(batchUpdateRequest.getBatchEnterPrisePriceDTOS().stream().map(BatchEnterPrisePriceDTO::getGoodsInfoId).toArray()) +
                        "\n 审核状态：" + iepSettingVO.getEnterpriseGoodsAuditFlag());
        if (DefaultFlag.NO.equals(iepSettingVO.getEnterpriseGoodsAuditFlag())) {
            //更新es
            esGoodsInfoElasticProvider.updateEnterpriseGoodsInfo(EsGoodsInfoEnterpriseBatchAuditRequest.builder().
                    batchEnterPrisePriceDTOS(batchUpdateRequest.getBatchEnterPrisePriceDTOS()).
                    enterpriseAuditState(EnterpriseAuditState.CHECKED).build());
        } else {
            esGoodsInfoElasticProvider.updateEnterpriseGoodsInfo(EsGoodsInfoEnterpriseBatchAuditRequest.builder().
                    batchEnterPrisePriceDTOS(batchUpdateRequest.getBatchEnterPrisePriceDTOS()).
                    enterpriseAuditState(EnterpriseAuditState.WAIT_CHECK).build());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 单个修改企业购商品的价格
     *
     * @param request
     * @return
     */
    @MultiSubmit
    @Operation(summary = "单个修改企业购商品的价格")
    @PostMapping(value = "/modify")
    @GlobalTransactional
    public BaseResponse modifyEnterpriseGoodsInfoPrice(@RequestBody @Valid EnterprisePriceUpdateRequest request) {
        //判断是否购买了企业购服务
        commonUtil.getIepSettingInfo();
        GoodsInfoByIdResponse goodsInfoByIdResponse = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder()
                .goodsInfoId(request.getGoodsInfoId()).build()).getContext();
        if (Objects.isNull(goodsInfoByIdResponse)) {
            throw new RuntimeException(GoodsErrorCodeEnum.K030035.getCode());
        }
        //越权校验
        commonUtil.checkStoreId(goodsInfoByIdResponse.getStoreId());
        //判断如果是已审核的就更新为审核通过，其他的更新为待审核
        request.setEnterpriseGoodsAuditFlag(EnterpriseAuditState.CHECKED.equals(goodsInfoByIdResponse.getEnterPriseAuditState())
                ? DefaultFlag.YES : DefaultFlag.NO);
        //更新库
        enterpriseGoodsInfoProvider.updateEnterprisePrice(request);
        //更新es
        List<BatchEnterPrisePriceDTO> batchEnterPrisePriceDTOS = new ArrayList<>();
        batchEnterPrisePriceDTOS.add(new BatchEnterPrisePriceDTO(request.getGoodsInfoId(),
                request.getEnterPrisePrice()));
        //修改价格后更新es
        esGoodsInfoElasticProvider.updateEnterpriseGoodsInfo(EsGoodsInfoEnterpriseBatchAuditRequest.builder().
                batchEnterPrisePriceDTOS(batchEnterPrisePriceDTOS).
                enterpriseAuditState(EnterpriseAuditState.CHECKED.equals(goodsInfoByIdResponse.getEnterPriseAuditState())
                        ? EnterpriseAuditState.CHECKED : EnterpriseAuditState.WAIT_CHECK).build()
        );
        //入日志
        operateLogMQUtil.convertAndSend("应用", "修改企业专享价",
                "skuId: " + request.getGoodsInfoId() +
                        "\n 修改后的价格：" + request.getEnterPrisePrice());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除企业购商品
     *
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "单个删除企业购商品")
    @PostMapping(value = "/delete")
    public BaseResponse deleteEnterpriseGoodsInfoPrice(@RequestBody @Valid EnterpriseSkuDeleteRequest request) {
        GoodsInfoByIdResponse goodsInfoByIdResponse = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder()
                .goodsInfoId(request.getGoodsInfoId()).build()).getContext();
        if (Objects.isNull(goodsInfoByIdResponse)) {
            throw new RuntimeException(GoodsErrorCodeEnum.K030035.getCode());
        }
        //越权校验
        commonUtil.checkStoreId(goodsInfoByIdResponse.getStoreId());
        BaseResponse response = enterpriseGoodsInfoProvider.deleteEnterpriseGoods(request);
        //入日志
        operateLogMQUtil.convertAndSend("应用", "删除企业购商品",
                "skuId: " + request.getGoodsInfoId());
        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
            //更新es
            List<BatchEnterPrisePriceDTO> batchEnterPrisePriceDTOS = new ArrayList<>();
            batchEnterPrisePriceDTOS.add(new BatchEnterPrisePriceDTO(request.getGoodsInfoId(), BigDecimal.ZERO));
            esGoodsInfoElasticProvider.updateEnterpriseGoodsInfo(EsGoodsInfoEnterpriseBatchAuditRequest.builder().
                    batchEnterPrisePriceDTOS(batchEnterPrisePriceDTOS).
                    enterpriseAuditState(EnterpriseAuditState.INIT).build());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量删除企业购商品
     *
     * @param request
     * @return
     */
    @Operation(summary = "批量删除企业购商品")
    @PostMapping(value = "/batchDelete")
    public BaseResponse batchDeleteEnterpriseGoodsInfoPrice(@RequestBody @Valid EnterpriseSpuDeleteRequest request) {
        EnterpriseBatchDeleteResponse response =
                enterpriseGoodsInfoProvider.batchDeleteEnterpriseGoods(request).getContext();
        //入日志
        operateLogMQUtil.convertAndSend("应用", "批量删除企业购商品",
                String.format("skuId: %s", response.getGoodsInfoIds().toString()));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 检查商品中是否有企业购货品
     *
     * @param request
     * @return
     */
    @Operation(summary = "检查商品中是否有企业购货品")
    @PostMapping(value = "/enterprise-check")
    public BaseResponse<EnterpriseCheckResponse> checkEnterpriseInSku(@RequestBody @Valid EnterpriseGoodsChangeRequest request) {
        return enterpriseGoodsInfoQueryProvider.checkEnterpriseInSku(request);
    }


    /**
     * 分页查询商家的企业购商品——用于选择商品时的接口
     *
     * @param request 商品 {@link EsSkuPageRequest}
     * @return 企业购商品分页
     */
    @Operation(summary = "分页查询商家的企业购商品")
    @RequestMapping(value = "/enterprise-sku", method = RequestMethod.POST)
    public BaseResponse<EsSkuPageResponse> page(@RequestBody EsSkuPageRequest request) {
        request.setStoreId(commonUtil.getStoreId());
        request.setAuditStatus(CheckStatus.CHECKED);//已审核状态
        request.setDelFlag(DeleteFlag.NO.toValue());//可用状态
        request.setFillLmInfoFlag(Boolean.TRUE);//LM库存填充
        request.setShowPointFlag(Boolean.TRUE);//购买积分填充
        request.setShowProviderInfoFlag(Boolean.TRUE);//填充供应商商品信息
        request.setFillStoreCate(Boolean.TRUE);//填充店铺分类id
        request.setAddedFlags(Lists.newArrayList(1,2));
        //如果为null就查询正常商品
        if(Objects.isNull(request.getEnterPriseAuditState())) {
            request.setEnterPriseAuditState(EnterpriseAuditState.INIT);
        }
        request.putSort("goodsInfo.createTime", SortType.DESC.toValue());
        BaseResponse<EsSkuPageResponse> page = esSkuQueryProvider.page(request);
        storeBaseService.populateProviderName(page.getContext().getGoodsInfoPage().getContent());
        return page;
    }

}
