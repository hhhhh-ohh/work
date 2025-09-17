package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyAddedStatusRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardImportProvider;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditDelByIdListRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.request.info.*;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditListResponse;
import com.wanmi.sbc.goods.api.response.goodsrestrictedsale.GoodsRestrictedSalePurchaseResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoConsignIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewListResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedPurchaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedValidateVO;
import com.wanmi.sbc.goods.service.StoreGoodsInfoService;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingLevelPluginProvider;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingLevelGoodsListFilterRequest;
import com.wanmi.sbc.marketing.bean.vo.MarketingViewVO;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseQueryProvider;
import com.wanmi.sbc.order.api.request.purchase.PurchaseGetGoodsMarketingRequest;
import com.wanmi.sbc.order.api.response.purchase.PurchaseGetGoodsMarketingResponse;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品服务
 * Created by daiyitian on 17/4/12.
 */
@Tag(name = "StoreGoodsInfoController", description = "商品服务 API")
@RestController
@Validated
public class StoreGoodsInfoController {

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired
    private MarketingLevelPluginProvider marketingLevelPluginProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PurchaseQueryProvider purchaseQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private StandardImportProvider standardImportProvider;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;
    @Resource
    private StoreGoodsInfoService storeGoodsInfoService;

    @Autowired private GoodsAuditProvider goodsAuditProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    /**
     * 分页显示商品
     *
     * @param queryRequest
     * @return 商品详情
     */
    @Operation(summary = "分页显示商品")
    @RequestMapping(value = "/goods/skus", method = RequestMethod.POST)
    public BaseResponse<EsSkuPageResponse> list(@RequestBody EsSkuPageRequest queryRequest) {
        queryRequest.setAddedFlag(AddedFlag.YES.toValue());//上架
        queryRequest.setAuditStatus(CheckStatus.CHECKED);//已审核
        queryRequest.setVendibility(NumberUtils.INTEGER_ONE);//可售
        return this.skuList(queryRequest);
    }

    /**
     * 分页显示商品，比上面的状态更灵活
     *
     * @param queryRequest 商品
     * @return 商品详情
     */
    @Operation(summary = "分页显示商品，比上面的状态更灵活")
    @RequestMapping(value = "/goods/list/sku", method = RequestMethod.POST)
    public BaseResponse<EsSkuPageResponse> skuList(@RequestBody EsSkuPageRequest queryRequest) {
        //获取会员
        CustomerGetByIdResponse customer = null;
        if (StringUtils.isNotBlank(queryRequest.getCustomerId())) {
            customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(queryRequest.getCustomerId())
            ).getContext();
            if (Objects.isNull(customer)) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010001);
            }
        }
        queryRequest.setStoreId(commonUtil.getStoreId());
        //按创建时间倒序、ID升序
        if(StringUtils.isBlank(queryRequest.getSortColumn()) &&
                MapUtils.isEmpty(queryRequest.getSortMap())){
            queryRequest.putSort("addedTime", SortType.DESC.toValue());
        }else{
            if (StringUtils.isNotBlank(queryRequest.getSortColumn())){
                queryRequest.putSort(queryRequest.getSortColumn(), queryRequest.getSortRole());
            }
        }
        if(CheckStatus.WAIT_CHECK.equals(queryRequest.getAuditStatus())){
            BaseResponse<GoodsAuditListResponse> baseResponse =
                    goodsAuditQueryProvider.listByCondition(GoodsAuditQueryRequest.builder()
                            .companyInfoId(commonUtil.getCompanyInfoId())
                            .storeId(commonUtil.getStoreId())
                            .delFlag(DeleteFlag.NO.toValue())
                            .build());
            if(Objects.nonNull(baseResponse.getContext())
                    && CollectionUtils.isNotEmpty(baseResponse.getContext().getGoodsAuditVOList())){
                queryRequest.setGoodsIds(baseResponse.getContext().getGoodsAuditVOList().stream().map(GoodsAuditVO::getGoodsId).collect(Collectors.toList()));
            }
            queryRequest.setAuditStatus(null);
        }
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());//可用
        queryRequest.setShowPointFlag(Boolean.TRUE);
        queryRequest.setShowProviderInfoFlag(Boolean.TRUE);
        queryRequest.setFillLmInfoFlag(Boolean.TRUE);
        queryRequest.setShowPointFlag(Boolean.TRUE);
        EsSkuPageResponse response = esSkuQueryProvider.page(queryRequest).getContext();

        List<GoodsInfoVO> goodsInfoVOList = response.getGoodsInfoPage().getContent();
        Map<String, List<Long>> storeCateIdsMap = new HashMap<>();
        for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
            //经过下面的一系列替换goodsInfoVOList操作，店铺分类id会丢，所以记录一下用于还原
            storeCateIdsMap.put(goodsInfoVO.getGoodsInfoId(), goodsInfoVO.getStoreCateIds());
        }
        Map<String, String> storeCateNameMap = new HashMap<>();
        for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
            //经过下面的一系列替换goodsInfoVOList操作，店铺分类id会丢，所以记录一下用于还原
            storeCateNameMap.put(goodsInfoVO.getGoodsInfoId(), goodsInfoVO.getStoreCateName());
        }

        if (customer != null && StringUtils.isNotBlank(customer.getCustomerId())) {
            GoodsIntervalPriceByCustomerIdResponse priceResponse =
                    goodsIntervalPriceService.getGoodsIntervalPriceVOList(goodsInfoVOList, customer.getCustomerId());
            response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
            goodsInfoVOList = priceResponse.getGoodsInfoVOList();
        } else {
            GoodsIntervalPriceResponse priceResponse =
                    goodsIntervalPriceService.getGoodsIntervalPriceVOList(goodsInfoVOList);
            response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
            goodsInfoVOList = priceResponse.getGoodsInfoVOList();
        }

        //计算会员价
        if (customer != null && StringUtils.isNotBlank(customer.getCustomerId())) {
            goodsInfoVOList = marketingLevelPluginProvider.goodsListFilter(
                    MarketingLevelGoodsListFilterRequest.builder()
                            .customerId(customer.getCustomerId())
                            .goodsInfos(KsBeanUtil.convert(goodsInfoVOList, GoodsInfoDTO.class)).build())
                    .getContext().getGoodsInfoVOList();
        }
        if (Objects.nonNull(customer)) {
            goodsInfoVOList = this.setRestrictedNum(goodsInfoVOList, customer);
        }
        for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
            //还原storeCateIds
            goodsInfoVO.setStoreCateIds(storeCateIdsMap.get(goodsInfoVO.getGoodsInfoId()));
            goodsInfoVO.setStoreCateName(storeCateNameMap.get(goodsInfoVO.getGoodsInfoId()));
        }
        systemPointsConfigService.clearBuyPointsForGoodsInfoVO(goodsInfoVOList);

        //填充供应商名称
        storeBaseService.populateProviderName(goodsInfoVOList);

        response.setGoodsInfoPage(new MicroServicePage<>(goodsInfoVOList, queryRequest.getPageRequest(),
                response.getGoodsInfoPage().getTotalElements()));
        return BaseResponse.success(response);
    }


    /**
     * 设置限售数据
     *
     * @param goodsInfoVOS
     * @param customerVO
     * @return
     */
    private List<GoodsInfoVO> setRestrictedNum(List<GoodsInfoVO> goodsInfoVOS, CustomerVO customerVO) {
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(goodsInfoVOS)) {
            List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS = new ArrayList<>();
            goodsInfoVOS = goodsInfoVOS.stream().map(g -> {
                GoodsRestrictedValidateVO rvv = new GoodsRestrictedValidateVO();
                rvv.setNum(g.getBuyCount());
                rvv.setSkuId(g.getGoodsInfoId());
                goodsRestrictedValidateVOS.add(rvv);
                if (Objects.equals(DeleteFlag.NO, g.getDelFlag())
                        && Objects.equals(CheckStatus.CHECKED, g.getAuditStatus())) {
                    g.setGoodsStatus(GoodsStatus.OK);
                    if (Objects.isNull(g.getStock()) || g.getStock() < 1) {
                        g.setGoodsStatus(GoodsStatus.OUT_STOCK);
                    }
                } else {
                    g.setGoodsStatus(GoodsStatus.INVALID);
                }
                return g;
            }).collect(Collectors.toList());
            GoodsRestrictedSalePurchaseResponse response = goodsRestrictedSaleQueryProvider.validatePurchaseRestricted(
                    GoodsRestrictedBatchValidateRequest.builder()
                            .goodsRestrictedValidateVOS(goodsRestrictedValidateVOS)
                            .customerVO(customerVO)
                            .build()).getContext();
            if (Objects.nonNull(response) && org.apache.commons.collections4.CollectionUtils.isNotEmpty(response.getGoodsRestrictedPurchaseVOS())) {
                List<GoodsRestrictedPurchaseVO> goodsRestrictedPurchaseVOS = response.getGoodsRestrictedPurchaseVOS();
                Map<String, GoodsRestrictedPurchaseVO> purchaseMap =
                        goodsRestrictedPurchaseVOS.stream().collect((Collectors.toMap(GoodsRestrictedPurchaseVO::getGoodsInfoId, g -> g)));
                goodsInfoVOS = goodsInfoVOS.stream().map(g -> {
                    GoodsRestrictedPurchaseVO goodsRestrictedPurchaseVO = purchaseMap.get(g.getGoodsInfoId());
                    if (Objects.nonNull(goodsRestrictedPurchaseVO)) {
                        if (DefaultFlag.YES.equals(goodsRestrictedPurchaseVO.getDefaultFlag())) {
                            g.setMaxCount(goodsRestrictedPurchaseVO.getRestrictedNum());
                            g.setCount(goodsRestrictedPurchaseVO.getStartSaleNum());
                        } else {
                            g.setGoodsStatus(GoodsStatus.INVALID);
                        }
                    }
                    return g;
                }).collect(Collectors.toList());
            }
        }
        return goodsInfoVOS;
    }

    /**
     * 批量获取商品信息
     */
    @Operation(summary = "批量获取商品信息")
    @RequestMapping(value = "/order/skus", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoViewListResponse> findSkus(@RequestBody GoodsInfoRequest request) {
        return BaseResponse.success(storeGoodsInfoService.findSkus(request));
    }


    /**
     * 批量查询商品生效的营销活动
     */
    @Operation(summary = "批量查询商品生效的营销活动，返回为单品营销信息map, key为单品id，value为营销列表")
    @RequestMapping(value = "/goods/marketings", method = RequestMethod.POST)
    public BaseResponse<Map<String, List<MarketingViewVO>>> getGoodsMarketings(@RequestBody GoodsInfoRequest request) {
        //参数验证
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds()) || StringUtils.isEmpty(request.getCustomerId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //获取会员
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(request
                .getCustomerId())).getContext();
        if (Objects.isNull(customer)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010001);
        }
        request.setStoreId(commonUtil.getStoreId());
        request.setDeleteFlag(DeleteFlag.NO);

        GoodsInfoViewByIdsRequest idsRequest = new GoodsInfoViewByIdsRequest();
        KsBeanUtil.copyPropertiesThird(request, idsRequest);
        GoodsInfoViewByIdsResponse idsResponse = goodsInfoQueryProvider.listViewByIds(idsRequest).getContext();
        PurchaseGetGoodsMarketingRequest purchaseGetGoodsMarketingRequest = new PurchaseGetGoodsMarketingRequest();
        purchaseGetGoodsMarketingRequest.setCustomer(customer);
        purchaseGetGoodsMarketingRequest.setGoodsInfos(idsResponse.getGoodsInfos());
        PurchaseGetGoodsMarketingResponse purchaseGetGoodsMarketingResponse =
                purchaseQueryProvider.getGoodsMarketing(purchaseGetGoodsMarketingRequest).getContext();
        return BaseResponse.success(purchaseGetGoodsMarketingResponse.getMap());
    }


    /**
     * 批量删除商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量删除商品")
    @RequestMapping(value = "/goods/sku", method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody GoodsInfoRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsInfoProvider.deleteByIds(GoodsInfoDeleteByIdsRequest.builder().goodsInfoIds(request.getGoodsInfoIds()).build());
        if (1 == request.getGoodsInfoIds().size()) {
            GoodsInfoByIdRequest goodsByIdRequest = new GoodsInfoByIdRequest();
            goodsByIdRequest.setGoodsInfoId(request.getGoodsInfoIds().get(0));
            GoodsInfoByIdResponse response = goodsInfoQueryProvider.getById(goodsByIdRequest).getContext();
            operateLogMQUtil.convertAndSend("商品", "删除商品",
                    "删除商品：SKU编码" + response.getGoodsInfoNo());
        } else {
            operateLogMQUtil.convertAndSend("商品", "批量删除",
                    "批量删除");
        }
        esGoodsInfoElasticProvider.delete(EsGoodsDeleteByIdsRequest.builder().deleteIds(request.getGoodsInfoIds()).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 代销sku信息
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "SKU代销设置")
    @RequestMapping(value = "/goods/sku/consignUpdate", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoConsignIdResponse> consignUpdate(@RequestBody GoodsInfoConsignIdRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setCompanyType(commonUtil.getCompanyType());
        Boolean isDelSpu = Boolean.FALSE;
        if( Objects.nonNull(request.getGoodsInfos().getIsNewAdd()) && request.getGoodsInfos().getIsNewAdd().toValue() == 1){
            standardImportProvider.importGoodsInfo(request);
        } else {
            //表面此条数据已导入商家，不需要在更改
            isDelSpu = goodsInfoProvider.updateByIds(request).getContext();
        }
        //初始化ES
        esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().
                deleteIds(Collections.singletonList(request.getGoods().getGoodsId())).build());

        if (!isDelSpu) {
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(request.getGoods().getGoodsId()).build());
        } else {
            goodsAuditProvider.deleteByIdList(GoodsAuditDelByIdListRequest.builder().goodsIdList(Arrays.asList(request.getGoods().getGoodsId())).goodsIdType(Constants.ONE).build());
        }

        //更新redis商品基本数据
        String goodsDetailInfo = redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoods().getGoodsId());
        if (StringUtils.isNotBlank(goodsDetailInfo)) {
            redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoods().getGoodsId());
        }
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 批量上架商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量上架商品")
    @RequestMapping(value = "/goods/sku/sale", method = RequestMethod.PUT)
    public BaseResponse onSale(@RequestBody GoodsInfoRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        goodsInfoProvider.modifyAddedStatus(
                GoodsInfoModifyAddedStatusRequest.builder().addedFlag(AddedFlag.YES.toValue())
                        .goodsInfoIds(request.getGoodsInfoIds())
                        .build()
        );
        esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                addedFlag(AddedFlag.YES.toValue()).goodsIds(null).goodsInfoIds(request.getGoodsInfoIds()).build());

        if (1 == request.getGoodsInfoIds().size()) {
            GoodsInfoByIdRequest goodsByIdRequest = new GoodsInfoByIdRequest();
            goodsByIdRequest.setGoodsInfoId(request.getGoodsInfoIds().get(0));
            GoodsInfoByIdResponse response = goodsInfoQueryProvider.getById(goodsByIdRequest).getContext();
            operateLogMQUtil.convertAndSend("商品", "上架",
                    "上架：SKU编码" + response.getGoodsInfoNo());
        } else {
            operateLogMQUtil.convertAndSend("商品", "批量上架", "批量上架");
        }
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 批量下架商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量下架商品")
    @RequestMapping(value = "/goods/sku/sale", method = RequestMethod.DELETE)
    public BaseResponse offSale(@RequestBody GoodsInfoRequest request) {

        if (CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        goodsInfoProvider.modifyAddedStatus(
                GoodsInfoModifyAddedStatusRequest.builder().addedFlag(AddedFlag.NO.toValue())
                        .goodsInfoIds(request.getGoodsInfoIds())
                        .build()
        );
        esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                addedFlag(AddedFlag.NO.toValue()).goodsIds(null).goodsInfoIds(request.getGoodsInfoIds()).build());
        if (1 == request.getGoodsInfoIds().size()) {
            GoodsInfoByIdRequest goodsByIdRequest = new GoodsInfoByIdRequest();
            goodsByIdRequest.setGoodsInfoId(request.getGoodsInfoIds().get(0));
            GoodsInfoByIdResponse response = goodsInfoQueryProvider.getById(goodsByIdRequest).getContext();
            operateLogMQUtil.convertAndSend("商品", "下架",
                    "下架：SKU编码" + response.getGoodsInfoNo());
        } else {
            operateLogMQUtil.convertAndSend("商品", "批量下架", "批量下架");
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 提审微信商品时的商品选择弹窗
     * @param request
     * @return
     */
    @Operation(summary = "提审微信商品时的商品选择弹窗")
    @RequestMapping(value = "/forWechat/skus", method = RequestMethod.POST)
    public BaseResponse<EsSkuPageResponse> pageForWechat(@RequestBody EsSkuPageRequest request) {
        if (!commonUtil.findVASBuyOrNot(VASConstants.VAS_WECHAT_CHANNELS)) {
            return BaseResponse.SUCCESSFUL();
        }
        request.setStoreId(commonUtil.getStoreId());
        EsSkuPageResponse context = esSkuQueryProvider.pageForWechat(request).getContext();
        context.getGoodsInfoPage().getContent();
        return BaseResponse.success(context);
    }


}
