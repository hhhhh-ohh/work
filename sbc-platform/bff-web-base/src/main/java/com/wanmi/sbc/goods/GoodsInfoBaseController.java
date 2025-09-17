package com.wanmi.sbc.goods;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerDelFlagGetRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailWithNotDeleteByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelListRequest;
import com.wanmi.sbc.customer.api.request.quicklogin.ThirdLoginRelationByCustomerRequest;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailGetWithNotDeleteByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.quicklogin.ThirdLoginRelationResponse;
import com.wanmi.sbc.customer.bean.enums.LedgerBindState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.distribute.response.ShopInfoResponse;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsDistributorGoodsListQueryRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsInfoDTO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsevaluate.GoodsEvaluateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsproperty.GoodsPropertyQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoSiteQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.provider.prop.GoodsPropQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsSpecQueryProvider;
import com.wanmi.sbc.goods.api.provider.xsitegoodscate.XsiteGoodsCateProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoListByCustomerIdRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoPageByCustomerIdRequest;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyByGoodsIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.ProviderGoodsStockSyncRequest;
import com.wanmi.sbc.goods.api.request.prop.GoodsPropListAllByCateIdRequest;
import com.wanmi.sbc.goods.api.request.prop.GoodsPropListIndexByCateIdRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsSpecQueryRequest;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateQueryByCateUuidRequest;
import com.wanmi.sbc.goods.api.response.distributor.goods.DistributorGoodsInfoListByCustomerIdResponse;
import com.wanmi.sbc.goods.api.response.distributor.goods.DistributorGoodsInfoPageByCustomerIdResponse;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyListForGoodsResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdsResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoSimpleDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.api.response.prop.GoodsPropListAllByCateIdResponse;
import com.wanmi.sbc.goods.api.response.prop.GoodsPropListIndexByCateIdResponse;
import com.wanmi.sbc.goods.api.response.spec.GoodsDetailSpecInfoResponse;
import com.wanmi.sbc.goods.api.response.xsitegoodscate.XsiteGoodsCateByCateUuidResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.DistributorGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.request.GoodsSpuSpecRequest;
import com.wanmi.sbc.goods.response.DistributorGoodsInfoListVO;
import com.wanmi.sbc.goods.response.GoodsInfoDetailSimpleResponse;
import com.wanmi.sbc.goods.response.GoodsInfoListResponse;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.detail.GoodsDetailInterface;
import com.wanmi.sbc.goods.service.list.GoodsListInterface;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseProvider;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseQueryProvider;
import com.wanmi.sbc.order.api.request.purchase.PurchaseQueryRequest;
import com.wanmi.sbc.order.api.response.purchase.PurchaseQueryResponse;
import com.wanmi.sbc.order.bean.vo.PurchaseVO;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.third.goods.ThirdPlatformGoodsService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.GoodsInfoConvertMapper;
import com.wanmi.sbc.util.LaKaLaUtils;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品Controller
 * Created by daiyitian on 17/4/12.
 */
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name = "GoodsInfoBaseController", description = "S2B web公用-商品信息API")
public class GoodsInfoBaseController {

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private GoodsInfoSiteQueryProvider goodsInfoSiteQueryProvider;

    @Autowired
    private PurchaseProvider purchaseProvider;

    @Autowired
    private PurchaseQueryProvider purchaseQueryProvider;

    @Autowired
    private GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired
    private MarketingPluginProvider marketingPluginProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private GoodsPropQueryProvider goodsPropQueryProvider;

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private DistributorGoodsInfoQueryProvider distributorGoodsInfoQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private DistributionCacheService distributionCacheService;

    @Autowired
    private DistributionService distributionService;
    @Autowired
    private ThirdLoginRelationQueryProvider thirdLoginRelationQueryProvider;
    @Autowired
    private SystemPointsConfigService systemPointsConfigService;
    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;
    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;
    @Autowired
    private CustomerQueryProvider customerQueryProvider;
    @Autowired
    private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;
    @Autowired
    private GoodsPropertyQueryProvider goodsPropertyQueryProvider;
    @Autowired
    private GoodsSpecQueryProvider goodsSpecQueryProvider;

    @Resource
    private GoodsInfoConvertMapper goodsInfoConvertMapper;

    @Autowired private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Resource(name = "goodsInfoListService")
    private GoodsListInterface<EsGoodsInfoQueryRequest,EsGoodsInfoSimpleResponse> goodsListInterface;

    @Resource(name = "distributorGoodsInfoListService")
    private GoodsListInterface<EsGoodsInfoQueryRequest,EsGoodsInfoSimpleResponse> distributorGoodsListInterface;
    @Autowired
    private GoodsBaseService goodsBaseService;

    @Resource(name = "goodsInfoDetailService")
    private GoodsDetailInterface<GoodsInfoSimpleDetailByGoodsInfoResponse> goodsDetailInterface;
    @Autowired
    GoodsEvaluateQueryProvider goodsEvaluateQueryProvider;
    @Autowired
    private XsiteGoodsCateProvider xsiteGoodsCateProvider;

    @Autowired
    private ThirdPlatformGoodsService thirdPlatformGoodsService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Resource(name = "storeBagGoodsInfoDetailService")
    private GoodsDetailInterface<GoodsInfoSimpleDetailByGoodsInfoResponse> storeBagGoodsDetailInterface;

    @Autowired
    private LaKaLaUtils laKaLaUtils;

    /**
     * 商品分页(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "查询商品分页")
    @RequestMapping(value = "/skuList", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListResponse> skuList(@RequestBody EsGoodsInfoQueryRequest queryRequest) {
        if (TerminalSource.PC.equals(commonUtil.getTerminal())) {
            //pc只展示实物商品
            queryRequest.setGoodsType(GoodsType.REAL_GOODS.toValue());
        }

        // 分类uuid不为空，组装skuIds
        if (StringUtils.isNotBlank(queryRequest.getCateUuid())) {
            XsiteGoodsCateByCateUuidResponse xsiteGoodsCateByCateUuidResponse =
                    xsiteGoodsCateProvider.findByCateUuid(XsiteGoodsCateQueryByCateUuidRequest.builder().cateUuid(queryRequest.getCateUuid()).build()).getContext();
            if (Objects.nonNull(xsiteGoodsCateByCateUuidResponse) && Objects.nonNull(xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO())){
                String goodsInfoIds = xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO().getGoodsInfoIds();
                if (StringUtils.isNotBlank(goodsInfoIds)) {
                    List<String> goodsInfoIdList = JSON.parseArray(goodsInfoIds, String.class);
                    queryRequest.setGoodsInfoIds(goodsInfoIdList);
                }
            }
        }
        //配置boost
        goodsBaseService.getEsGoodsBoost(queryRequest);
        queryRequest.setCustomerId(commonUtil.getOperatorId());
        EsGoodsInfoSimpleResponse response = goodsListInterface.getList(queryRequest);
        //设置订货号
        goodsBaseService.setListQuickOrderNo(response);
        // 设置商品主图
        goodsBaseService.setGoodsMainImage(response);
        return BaseResponse.success(goodsBaseService.skuListConvert(response, queryRequest.getSortFlag()));
    }

    /**
     * 未登录时,查询商品分页(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "未登录时,查询商品分页")
    @RequestMapping(value = "/unLogin/skuList", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListResponse> unLoginSkuList(@RequestBody EsGoodsInfoQueryRequest queryRequest) {
        if (TerminalSource.PC.equals(commonUtil.getTerminal())) {
            //pc只展示实物商品
            queryRequest.setGoodsType(GoodsType.REAL_GOODS.toValue());
        }

        // 分类uuid不为空，组装skuIds
        if (StringUtils.isNotBlank(queryRequest.getCateUuid())) {
            XsiteGoodsCateByCateUuidResponse xsiteGoodsCateByCateUuidResponse =
                    xsiteGoodsCateProvider.findByCateUuid(XsiteGoodsCateQueryByCateUuidRequest.builder().cateUuid(queryRequest.getCateUuid()).build()).getContext();
            if (Objects.nonNull(xsiteGoodsCateByCateUuidResponse) && Objects.nonNull(xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO())){
                String goodsInfoIds = xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO().getGoodsInfoIds();
                if (StringUtils.isNotBlank(goodsInfoIds)) {
                    List<String> goodsInfoIdList = JSON.parseArray(goodsInfoIds, String.class);
                    queryRequest.setGoodsInfoIds(goodsInfoIdList);
                }
            }
        }
        // 配置boost
        goodsBaseService.getEsGoodsBoost(queryRequest);
        EsGoodsInfoSimpleResponse response = goodsListInterface.getList(queryRequest);
        if (CollectionUtils.isNotEmpty(queryRequest.getEsGoodsInfoDTOList()) &&
                CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
            Map<String, List<EsGoodsInfoDTO>> buyCountMap =
                    queryRequest.getEsGoodsInfoDTOList().stream()
                            .collect(Collectors.groupingBy(EsGoodsInfoDTO::getGoodsInfoId));

            response.getEsGoodsInfoPage().getContent().stream()
                    .filter(esGoodsInfo -> Objects.nonNull(esGoodsInfo.getGoodsInfo())
                            && buyCountMap.containsKey(esGoodsInfo.getGoodsInfo().getGoodsInfoId()))
                    .forEach(esGoodsInfo -> {
                        GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
                        goodsInfo.setBuyCount(
                                buyCountMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId()).get(0).getGoodsNum());
                    });
        }
        //设置订货号
        goodsBaseService.setListQuickOrderNo(response);
        // 设置商品主图
        goodsBaseService.setGoodsMainImage(response);
        return BaseResponse.success(goodsBaseService.skuListConvert(response, queryRequest.getSortFlag()));
    }


    @Operation(summary = "根据分销员-会员ID查询店铺精选小店名称")
    @Parameter( name = "distributorId", description = "分销员-会员id", required =
            true)
    @RequestMapping(value = "/shop-info/{distributorId}", method = RequestMethod.GET)
    public BaseResponse<ShopInfoResponse> getShopInfo(@PathVariable String distributorId) {
        ShopInfoResponse response = new ShopInfoResponse();
        //验证会员是否存在
        Boolean delFlag =
                customerQueryProvider.getCustomerDelFlag(new CustomerDelFlagGetRequest(distributorId)).getContext().getDelFlag();
        if (Boolean.TRUE.equals(delFlag)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080181);
        }
        BaseResponse<CustomerDetailGetWithNotDeleteByCustomerIdResponse> baseResponse = customerDetailQueryProvider
                .getCustomerDetailWithNotDeleteByCustomerId(new CustomerDetailWithNotDeleteByCustomerIdRequest
                        (distributorId));
        CustomerDetailGetWithNotDeleteByCustomerIdResponse customerDetailGetWithNotDeleteByCustomerIdResponse =
                baseResponse.getContext();
        if (Objects.nonNull(customerDetailGetWithNotDeleteByCustomerIdResponse)) {
            String customerName = customerDetailGetWithNotDeleteByCustomerIdResponse.getCustomerName();
            String shopName = distributionCacheService.getShopName();
            response.setShopName(customerName + "的" + shopName);

        }
        ThirdLoginRelationResponse thirdLoginRelationResponse = thirdLoginRelationQueryProvider
                .listThirdLoginRelationByCustomer
                        (ThirdLoginRelationByCustomerRequest.builder()
                                .customerId(distributorId)
                                .thirdLoginType(ThirdLoginType.WECHAT)
                                .build()).getContext();
        if (Objects.nonNull(thirdLoginRelationResponse) && Objects.nonNull(thirdLoginRelationResponse
                .getThirdLoginRelation())) {
            response.setHeadImg(thirdLoginRelationResponse.getThirdLoginRelation().getHeadimgurl());
        }
        return BaseResponse.success(response);
    }

    /**
     * 小C-店铺精选页(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "小C-店铺精选页")
    @RequestMapping(value = "/shop/sku-list-to-c", method = RequestMethod.POST)
    public BaseResponse<EsGoodsInfoResponse> shopSkuListToC(@RequestBody EsGoodsInfoQueryRequest queryRequest) {
        String customerId = queryRequest.getCustomerId();
//        MicroServicePage<DistributorGoodsInfoVO> microServicePage = pageDistributorGoodsInfoPageByCustomerId
//                (queryRequest, queryRequest.getCustomerId());
//        List<DistributorGoodsInfoVO> distributorGoodsInfoVOList = microServicePage.getContent();
        List<DistributorGoodsInfoVO> distributorGoodsInfoVOList = distributorGoodsInfoQueryProvider.listByCustomerId(new DistributorGoodsInfoListByCustomerIdRequest(customerId, null))
                .getContext().getDistributorGoodsInfoVOList();
        if (CollectionUtils.isEmpty(distributorGoodsInfoVOList)) {
            return BaseResponse.success(new EsGoodsInfoResponse());
        }
        List<String> goodsIdList = distributorGoodsInfoVOList.stream().map(DistributorGoodsInfoVO::getGoodsInfoId)
                .collect(Collectors.toList());
        Map<String, String> map = distributorGoodsInfoVOList.stream().collect(Collectors.toMap
                (DistributorGoodsInfoVO::getGoodsInfoId, DistributorGoodsInfoVO::getId));
        PageRequest pageable = queryRequest.getPageable();
        queryRequest.setGoodsInfoIds(goodsIdList);
        queryRequest.setPageNum(0);
        queryRequest.setPageSize(goodsIdList.size());
        queryRequest.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED.toValue());
        queryRequest = wrapEsGoodsInfoQueryRequest(queryRequest);
        queryRequest.setCustomerLevelId(null);
        queryRequest.setCustomerLevelDiscount(null);
        EsGoodsInfoResponse response =
                esGoodsInfoElasticQueryProvider.distributorGoodsListByCustomerId(queryRequest).getContext();
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = response.getEsGoodsInfoPage().getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getGoodsInfo().getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getGoodsInfo().getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());
        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        }
        if (stocks != null) {
            for (EsGoodsInfoVO esGoodsInfo : response.getEsGoodsInfoPage().getContent()) {
                if (ThirdPlatformType.LINKED_MALL.equals(esGoodsInfo.getGoodsInfo().getThirdPlatformType())) {
                    for (LinkedMallStockVO spuStock : stocks) {
                        Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                                .filter(v -> String.valueOf(spuStock.getItemId()).equals(esGoodsInfo.getGoodsInfo().getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(esGoodsInfo.getGoodsInfo().getThirdPlatformSkuId()))
                                .findFirst();
                        if (stock.isPresent()) {
                            Long skuStock = stock.get().getStock();
                            esGoodsInfo.getGoodsInfo().setStock(skuStock);
                            if (!GoodsStatus.INVALID.equals(esGoodsInfo.getGoodsInfo().getGoodsStatus())) {
                                esGoodsInfo.getGoodsInfo().setGoodsStatus(skuStock > 0 ? GoodsStatus.OK :
                                        GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
            }
            for (GoodsVO goodsVO : response.getGoodsList()) {
                if (ThirdPlatformType.LINKED_MALL.equals(goodsVO.getThirdPlatformType())) {
                    Optional<LinkedMallStockVO> optional =
                            stocks.stream().filter(v -> String.valueOf(v.getItemId()).equals(goodsVO.getThirdPlatformSpuId())).findFirst();
                    if (optional.isPresent()) {
                        Long spuStock = optional.get().getSkuList().stream()
                                .map(v -> v.getStock())
                                .reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                        goodsVO.setStock(spuStock);
                    }
                }
            }
        }
        //供应商库存同步
        if (CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
            providerSkuStockSync(response);
        }
        response = filterDistributionGoods(queryRequest, response, map);
        if (Objects.nonNull(response.getEsGoodsInfoPage()) && CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage
                ().getContent())) {
            //还需要拉卡拉开启状态
            PayGatewayVO payGatewayVO = laKaLaUtils.getPayGatewayVO();
            boolean isOpen = IsOpen.YES.equals(Objects.nonNull(payGatewayVO) ? payGatewayVO.getIsOpen() : IsOpen.NO);
            List<EsGoodsInfoVO> esGoodsInfoVOList = response.getEsGoodsInfoPage().getContent();
            if (isOpen) {
                List<Long> storeIds = response.getEsGoodsInfoPage().getContent().parallelStream()
                        .map(esGoodsInfoVO -> esGoodsInfoVO.getGoodsInfo().getStoreId()).distinct().collect(Collectors.toList());
                //获取店铺数据
                List<StoreVO> storeVOList = storeQueryProvider.listNoDeleteStoreByIds(ListNoDeleteStoreByIdsRequest.builder()
                        .storeIds(storeIds)
                        .build()).getContext().getStoreVOList();
                //根据商户id集合
                List<Long> companyInfoIds = storeVOList.parallelStream().map(StoreVO::getCompanyInfoId).collect(Collectors.toList());
                //根据商户id跟接收方id查询绑定关系
                List<LedgerReceiverRelVO> ledgerReceiverRelVOList = ledgerReceiverRelQueryProvider.list(LedgerReceiverRelListRequest.builder()
                        .supplierIdList(companyInfoIds)
                        .receiverId(customerId)
                        .build()).getContext().getLedgerReceiverRelVOList();
                final List<Long> companyIds = ledgerReceiverRelVOList.parallelStream()
                        .map(LedgerReceiverRelVO::getSupplierId).collect(Collectors.toList());
                final List<StoreVO> storeVOS = storeVOList.parallelStream().filter(storeVO -> {
                    Long companyInfoId = storeVO.getCompanyInfoId();
                    return companyIds.contains(companyInfoId);
                }).collect(Collectors.toList());
                esGoodsInfoVOList = esGoodsInfoVOList.parallelStream().map(esGoodsInfoVO -> {
                    Long storeId = esGoodsInfoVO.getGoodsInfo().getStoreId();
                    Optional<StoreVO> optional = storeVOS.parallelStream().filter(storeVO -> storeId.equals(storeVO.getStoreId())).findFirst();
                    if (optional.isPresent()) {
                        StoreVO storeVO = optional.get();
                        Long companyInfoId = storeVO.getCompanyInfoId();
                        Optional<LedgerReceiverRelVO> ledgerReceiverRelVOOptional = ledgerReceiverRelVOList.parallelStream().filter(ledgerReceiverRelVO -> ledgerReceiverRelVO.getSupplierId().equals(companyInfoId)).findFirst();
                        LedgerReceiverRelVO ledgerReceiverRelVO = ledgerReceiverRelVOOptional.orElse(null);
                        esGoodsInfoVO.setBindState(Objects.nonNull(ledgerReceiverRelVO) ? ledgerReceiverRelVO.getBindState() : LedgerBindState.UNBOUND.toValue());
                    } else {
                        esGoodsInfoVO.setBindState(LedgerBindState.UNBOUND.toValue());
                    }
                    return esGoodsInfoVO;
                }).filter(esGoodsInfoVO -> Objects.equals(esGoodsInfoVO.getBindState(),LedgerBindState.BINDING.toValue())).sorted(Comparator.comparing(EsGoodsInfoVO::getSortNo)).collect(Collectors.toList());
            }
            // 起始索引
            int fromIndex = pageable.getPageSize() * (pageable.getPageNumber());

            // 结束索引
            int toIndex = Math.min(pageable.getPageSize() * (pageable.getPageNumber()+1), esGoodsInfoVOList.size());
            if (fromIndex > toIndex) {
                return BaseResponse.success(new EsGoodsInfoResponse());
            }
            List<EsGoodsInfoVO> finalEsGoodsInfoVOList = esGoodsInfoVOList.subList(fromIndex, toIndex);
            response.setEsGoodsInfoPage(new MicroServicePage<>(finalEsGoodsInfoVOList, PageRequest.of
                    (pageable.getPageNumber(), pageable.getPageSize()), esGoodsInfoVOList.size()));
        }
        return BaseResponse.success(response);
    }

    /**
     * 分销员-我的店铺-店铺精选页(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "分销员-我的店铺-店铺精选页")
    @RequestMapping(value = "/shop/sku-list", method = RequestMethod.POST)
    public BaseResponse<EsGoodsInfoResponse> shopSkuList(@RequestBody EsGoodsInfoQueryRequest queryRequest) {
        // 是否分销员
        String customerId = commonUtil.getOperator().getUserId();
        if (!distributionService.isDistributor(customerId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        List<DistributorGoodsInfoVO> distributorGoodsInfoVOList = distributorGoodsInfoQueryProvider.listByCustomerId(new DistributorGoodsInfoListByCustomerIdRequest(customerId, null))
                .getContext().getDistributorGoodsInfoVOList();

        if (CollectionUtils.isEmpty(distributorGoodsInfoVOList)) {
            return BaseResponse.success(new EsGoodsInfoResponse());
        }
        List<String> goodsIdList = distributorGoodsInfoVOList.stream().map(DistributorGoodsInfoVO::getGoodsInfoId)
                .collect(Collectors.toList());
        Map<String, String> map = distributorGoodsInfoVOList.stream().collect(Collectors.toMap
                (DistributorGoodsInfoVO::getGoodsInfoId, DistributorGoodsInfoVO::getId));
        PageRequest pageable = queryRequest.getPageable();
        queryRequest.setPageNum(0);
        queryRequest.setPageSize(goodsIdList.size());
        queryRequest.setGoodsInfoIds(goodsIdList);
        queryRequest.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED.toValue());
        queryRequest = wrapEsGoodsInfoQueryRequest(queryRequest);
        queryRequest.setCustomerLevelId(null);
        queryRequest.setCustomerLevelDiscount(null);
        queryRequest.setIsBuyCycle(Constants.no);
        EsGoodsInfoResponse response =
                esGoodsInfoElasticQueryProvider.distributorGoodsListByCustomerId(queryRequest).getContext();
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = response.getEsGoodsInfoPage().getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getGoodsInfo().getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getGoodsInfo().getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());
        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        }
        if (stocks != null) {
            for (EsGoodsInfoVO esGoodsInfo : response.getEsGoodsInfoPage().getContent()) {
                if (ThirdPlatformType.LINKED_MALL.equals(esGoodsInfo.getGoodsInfo().getThirdPlatformType())) {

                    for (LinkedMallStockVO spuStock : stocks) {
                        Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                                .filter(v -> String.valueOf(spuStock.getItemId()).equals(esGoodsInfo.getGoodsInfo().getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(esGoodsInfo.getGoodsInfo().getThirdPlatformSkuId()))
                                .findFirst();
                        if (stock.isPresent()) {
                            Long skuStock = stock.get().getStock();
                            esGoodsInfo.getGoodsInfo().setStock(skuStock);
                            if (!GoodsStatus.INVALID.equals(esGoodsInfo.getGoodsInfo().getGoodsStatus())) {
                                esGoodsInfo.getGoodsInfo().setGoodsStatus(skuStock > 0 ? GoodsStatus.OK :
                                        GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
            }
            for (GoodsVO goodsVO : response.getGoodsList()) {
                if (ThirdPlatformType.LINKED_MALL.equals(goodsVO.getThirdPlatformType())) {
                    Optional<LinkedMallStockVO> optional =
                            stocks.stream().filter(v -> String.valueOf(v.getItemId()).equals(goodsVO.getThirdPlatformSpuId())).findFirst();
                    if (optional.isPresent()) {
                        Long spuStock = optional.get().getSkuList().stream()
                                .map(v -> v.getStock())
                                .reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                        goodsVO.setStock(spuStock);
                    }
                }
            }
        }
        //供应商库存同步
        if (CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
            providerSkuStockSync(response);
        }
        response = filterDistributionGoods(queryRequest, response, map);

        if (Objects.nonNull(response.getEsGoodsInfoPage()) && CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage
                ().getContent())) {
            //根据分销员排序规则 排序
            List<EsGoodsInfoVO>  esGoodsInfoVOList = response.getEsGoodsInfoPage().getContent();
            //积分价清零
            systemPointsConfigService.clearBuyPoinsForEsSku(esGoodsInfoVOList);

            if (Objects.isNull(queryRequest.getSortFlag()) || queryRequest.getSortFlag() == Constants.ZERO){
                esGoodsInfoVOList.forEach(
                        esGoodsInfoVO -> {
                            distributorGoodsInfoVOList.forEach(
                                    distributorGoodsInfoVO -> {
                                        if (distributorGoodsInfoVO .getGoodsInfoId().equals(esGoodsInfoVO.getGoodsInfo().getGoodsInfoId())) {
                                            esGoodsInfoVO.setSortNo(
                                                    distributorGoodsInfoVO.getSequence().longValue());
                                        }
                                    });
                        });
            }
            // 起始索引
            int fromIndex = pageable.getPageSize() * (pageable.getPageNumber());

            // 结束索引
            int toIndex = Math.min(pageable.getPageSize() * (pageable.getPageNumber()+1), response.getEsGoodsInfoPage().getContent().size());
            if (fromIndex > toIndex) {
                return BaseResponse.success(new EsGoodsInfoResponse());
            }
            esGoodsInfoVOList = esGoodsInfoVOList.stream().sorted(Comparator.comparing(EsGoodsInfoVO::getSortNo)).collect(Collectors.toList()).subList(fromIndex, toIndex);
            //还需要拉卡拉开启状态
            PayGatewayVO payGatewayVO = laKaLaUtils.getPayGatewayVO();
            boolean isOpen = IsOpen.YES.equals(Objects.nonNull(payGatewayVO) ? payGatewayVO.getIsOpen() : IsOpen.NO);
            if (isOpen) {
                List<Long> storeIds = esGoodsInfoVOList.parallelStream()
                        .map(esGoodsInfoVO -> esGoodsInfoVO.getGoodsInfo().getStoreId()).distinct().collect(Collectors.toList());
                //获取店铺数据
                List<StoreVO> storeVOList = storeQueryProvider.listNoDeleteStoreByIds(ListNoDeleteStoreByIdsRequest.builder()
                        .storeIds(storeIds)
                        .build()).getContext().getStoreVOList();
                //根据商户id集合
                List<Long> companyInfoIds = storeVOList.parallelStream().map(StoreVO::getCompanyInfoId).collect(Collectors.toList());
                //根据商户id跟接收方id查询绑定关系
                List<LedgerReceiverRelVO> ledgerReceiverRelVOList = ledgerReceiverRelQueryProvider.list(LedgerReceiverRelListRequest.builder()
                        .supplierIdList(companyInfoIds)
                        .receiverId(customerId)
                        .build()).getContext().getLedgerReceiverRelVOList();
                final List<Long> companyIds = ledgerReceiverRelVOList.parallelStream()
                        .map(LedgerReceiverRelVO::getSupplierId).collect(Collectors.toList());
                final List<StoreVO> storeVOS = storeVOList.parallelStream().filter(storeVO -> {
                    Long companyInfoId = storeVO.getCompanyInfoId();
                    return companyIds.contains(companyInfoId);
                }).collect(Collectors.toList());
                esGoodsInfoVOList = esGoodsInfoVOList.parallelStream().peek(esGoodsInfoVO -> {
                    Long storeId = esGoodsInfoVO.getGoodsInfo().getStoreId();
                    Optional<StoreVO> optional = storeVOS.parallelStream().filter(storeVO -> storeId.equals(storeVO.getStoreId())).findFirst();
                    if (optional.isPresent()) {
                        StoreVO storeVO = optional.get();
                        Long companyInfoId = storeVO.getCompanyInfoId();
                        Optional<LedgerReceiverRelVO> ledgerReceiverRelVOOptional = ledgerReceiverRelVOList.parallelStream().filter(ledgerReceiverRelVO -> ledgerReceiverRelVO.getSupplierId().equals(companyInfoId)).findFirst();
                        LedgerReceiverRelVO ledgerReceiverRelVO = ledgerReceiverRelVOOptional.orElse(null);
                        esGoodsInfoVO.setBindState(Objects.nonNull(ledgerReceiverRelVO) ? ledgerReceiverRelVO.getBindState() : LedgerBindState.UNBOUND.toValue());
                    } else {
                        esGoodsInfoVO.setBindState(LedgerBindState.UNBOUND.toValue());
                    }
                }).collect(Collectors.toList());

            }
            response.setEsGoodsInfoPage(new MicroServicePage<>(esGoodsInfoVOList, PageRequest.of
                    (pageable.getPageNumber(), pageable.getPageSize()), response.getEsGoodsInfoPage().getContent().size()));
        }
        return BaseResponse.success(response);
    }


    /**
     * 分销员-我的店铺-选品功能(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "分销员-我的店铺-选品功能")
    @RequestMapping(value = "/shop/add-distributor-goods", method = RequestMethod.POST)
    public BaseResponse<EsGoodsInfoResponse> addDistributorGoods(@RequestBody EsGoodsInfoQueryRequest queryRequest) {

        DistributorGoodsInfoListByCustomerIdRequest request = new DistributorGoodsInfoListByCustomerIdRequest();
        request.setCustomerId(commonUtil.getUserInfo().getUserId());

        BaseResponse<DistributorGoodsInfoListByCustomerIdResponse> baseResponse =
                distributorGoodsInfoQueryProvider.listByCustomerId(request);
        List<DistributorGoodsInfoVO> distributorGoodsInfoVOList = baseResponse.getContext()
                .getDistributorGoodsInfoVOList();
        List<String> goodsIdList = distributorGoodsInfoVOList.stream().map(DistributorGoodsInfoVO::getGoodsInfoId)
                .collect(Collectors.toList());
        Map<String, String> map = distributorGoodsInfoVOList.stream().collect(Collectors.toMap
                (DistributorGoodsInfoVO::getGoodsInfoId, DistributorGoodsInfoVO::getId));
        queryRequest.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED.toValue());
        queryRequest.setDistributionGoodsStatus(NumberUtils.INTEGER_ZERO);
        queryRequest = wrapEsGoodsInfoQueryRequest(queryRequest);
        queryRequest.setCustomerLevelId(null);
        queryRequest.setCustomerLevelDiscount(null);
        queryRequest.setDistributionGoodsInfoIds(goodsIdList);
        EsDistributorGoodsListQueryRequest esDistributorGoodsListQueryRequest =
                new EsDistributorGoodsListQueryRequest();
        esDistributorGoodsListQueryRequest.setRequest(queryRequest);
        esDistributorGoodsListQueryRequest.setGoodsIdList(goodsIdList);
        EsGoodsInfoResponse response =
                esGoodsInfoElasticQueryProvider.distributorGoodsList(esDistributorGoodsListQueryRequest).getContext();
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = response.getEsGoodsInfoPage().getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getGoodsInfo().getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getGoodsInfo().getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());
        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        }
        if (stocks != null) {
            for (EsGoodsInfoVO esGoodsInfo : response.getEsGoodsInfoPage().getContent()) {
                if (ThirdPlatformType.LINKED_MALL.equals(esGoodsInfo.getGoodsInfo().getThirdPlatformType())) {

                    for (LinkedMallStockVO spuStock : stocks) {
                        Optional<LinkedMallStockVO.SkuStock> stock =
                                spuStock.getSkuList().stream().filter(v -> String.valueOf(spuStock.getItemId()).equals(esGoodsInfo.getGoodsInfo().getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(esGoodsInfo.getGoodsInfo().getThirdPlatformSkuId())).findFirst();
                        if (stock.isPresent()) {
                            Long skuStock = stock.get().getStock();
                            esGoodsInfo.getGoodsInfo().setStock(skuStock);
                            if (!GoodsStatus.INVALID.equals(esGoodsInfo.getGoodsInfo().getGoodsStatus())) {
                                esGoodsInfo.getGoodsInfo().setGoodsStatus(skuStock > 0 ? GoodsStatus.OK :
                                        GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
            }
        }
        //供应商库存同步
        if (CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
            providerSkuStockSync(response);
        }
        response = filterDistributionGoods(queryRequest, response, map);
        //查询优惠券
        if (CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
            response = this.distributionGetCoupon(response, commonUtil.getOperatorId());
        }
        //非商品积分模式下清零
        if (CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
            systemPointsConfigService.clearBuyPoinsForEsSku(response.getEsGoodsInfoPage().getContent());
        }
        return BaseResponse.success(response);
    }

    /**
     * 分销员-我的店铺-选品功能(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "分销员-我的店铺-选品功能")
    @RequestMapping(value = "/shop/add-distributor-sku-list", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListResponse> distributorSkuList(@RequestBody EsGoodsInfoQueryRequest queryRequest) {
        // 是否分销员
        if (!distributionService.isDistributor(commonUtil.getOperatorId())) {
            return BaseResponse.success(new GoodsInfoListResponse());
        }
        return BaseResponse.success(this.distributorSkuListConvert(distributorGoodsListInterface.getList(queryRequest)));
    }



    /**
     * 根据商品分类id查询索引的商品属性列表
     *
     * @param cateId 商品分类id
     * @return 索引的属性列表
     */
    @Operation(summary = "根据商品分类id查询索引的商品属性列表")
    @Parameter( name = "cateId", description = "商品分类id", required = true)
    @RequestMapping(value = "/props/{cateId}", method = RequestMethod.GET)
    public BaseResponse<List<GoodsPropVO>> listByCateId(@PathVariable Long cateId) {
        BaseResponse<GoodsPropListIndexByCateIdResponse> baseResponse = goodsPropQueryProvider.listIndexByCateId(new
                GoodsPropListIndexByCateIdRequest(cateId));
        GoodsPropListIndexByCateIdResponse goodsPropListIndexByCateIdResponse = baseResponse.getContext();
        if (Objects.isNull(goodsPropListIndexByCateIdResponse)) {
            return BaseResponse.success(Collections.emptyList());
        }
        return BaseResponse.success(goodsPropListIndexByCateIdResponse.getGoodsPropVOList());
    }

    /**
     * 根据商品分类id查询商品属性列表
     *
     * @param cateId 商品分类id
     * @return 所有属性列表
     */
    @Operation(summary = "根据商品分类id查询商品属性列表")
    @Parameter( name = "cateId", description = "商品分类id", required = true)
    @RequestMapping(value = "/props/all/{cateId}", method = RequestMethod.GET)
    public BaseResponse<List<GoodsPropVO>> propsList(@PathVariable Long cateId) {
        if(cateId <= 0){
            return BaseResponse.success(Collections.emptyList());
        }
        BaseResponse<GoodsPropListAllByCateIdResponse> baseResponse = goodsPropQueryProvider.listAllByCateId(new
                GoodsPropListAllByCateIdRequest(cateId));
        GoodsPropListAllByCateIdResponse goodsPropListAllByCateIdResponse = baseResponse.getContext();
        if (Objects.isNull(goodsPropListAllByCateIdResponse)) {
            return BaseResponse.success(Collections.emptyList());
        }
        return BaseResponse.success(goodsPropListAllByCateIdResponse.getGoodsPropVOList());
    }



    /**
     * 商品详情
     *
     * @return 返回分页结果
     */
    @Operation(summary = "查询商品详情")
    @Parameter( name = "skuId", description = "skuId", required = true)
    @RequestMapping(value = "/new/sku/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GoodsInfoDetailSimpleResponse> newDetail(@PathVariable String skuId) {
        //封装用户地址信息
        PlatformAddress address = null;
        GoodsInfoSimpleDetailByGoodsInfoResponse response = goodsDetailInterface.getDetail(skuId, address);
        //设置订货号
        goodsBaseService.setQuickOrderNo(response);
        return BaseResponse.success(
                goodsInfoConvertMapper.detailResponseToDetailResponse(
                        response));
    }

    /**
     * 开店礼包商品详情
     *
     * @return 返回分页结果
     */
    @Operation(summary = "查询商品详情")
    @Parameter( name = "skuId", description = "skuId", required = true)
    @RequestMapping(value = "/new/sku/storeBag/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GoodsInfoDetailSimpleResponse> storeBagNewDetail(@PathVariable String skuId) {
        //封装用户地址信息
        PlatformAddress address = null;
        GoodsInfoSimpleDetailByGoodsInfoResponse response = storeBagGoodsDetailInterface.getDetail(skuId, address);
        return BaseResponse.success(
                goodsInfoConvertMapper.detailResponseToDetailResponse(
                        response));
    }

    /**
     * 开店礼包商品详情
     *
     * @return 返回分页结果
     */
    @Operation(summary = "查询商品详情")
    @Parameter(name = "skuId", description = "skuId", required = true)
    @RequestMapping(value = "/unLogin/new/sku/storeBag/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GoodsInfoDetailSimpleResponse> unLoginStoreBagNewDetail(@PathVariable String skuId) {
        GoodsInfoSimpleDetailByGoodsInfoResponse response = storeBagGoodsDetailInterface.getDetail(skuId, null);
        return BaseResponse.success(
                goodsInfoConvertMapper.detailResponseToDetailResponse(
                        response));
    }

    /**
     * 带有 地址的 商品详情
     *
     * @return 返回分页结果
     */
    @Operation(summary = "查询商品详情")
    @Parameter(name = "skuId", description = "skuId", required = true)
    @RequestMapping(value = "/new/sku/byAddress/{skuId}", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoDetailSimpleResponse> newDetailByAddress(@PathVariable String skuId, @RequestBody PlatformAddress address) {
        //封装用户地址信息  地址为空查询默认的
        if(StringUtils.isBlank(address.getProvinceId()) && StringUtils.isBlank(address.getCityId())
                && StringUtils.isBlank(address.getAreaId()) && StringUtils.isBlank(address.getStreetId())) {
            CustomerDeliveryAddressRequest queryRequest = new CustomerDeliveryAddressRequest();
            queryRequest.setCustomerId(commonUtil.getOperatorId());
            CustomerDeliveryAddressResponse addressResponse = customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(queryRequest).getContext();
            if (Objects.nonNull(addressResponse)) {
                address = new PlatformAddress();
                address.setProvinceId(Objects.isNull(addressResponse.getProvinceId()) ? null : addressResponse.getProvinceId().toString());
                address.setCityId(Objects.isNull(addressResponse.getCityId()) ? null : addressResponse.getCityId().toString());
                address.setAreaId(Objects.isNull(addressResponse.getAreaId()) ? null : addressResponse.getAreaId().toString());
                address.setStreetId(Objects.isNull(addressResponse.getStreetId()) ? null : addressResponse.getStreetId().toString());
            }
        }
        // 如果  一、二、三级地址有一个为空则不按照地址查询
        if (address.hasNull()) {
            address = null;
        }
        GoodsInfoSimpleDetailByGoodsInfoResponse response = goodsDetailInterface.getDetail(skuId, address);
        //设置订货号
        goodsBaseService.setQuickOrderNo(response);
        return BaseResponse.success(
                goodsInfoConvertMapper.detailResponseToDetailResponse(
                        response));
    }

    /**
     * 商品详情未登录
     *
     * @return 返回分页结果
     */
    @Operation(summary = "查询商品详情")
    @Parameter(name = "skuId", description = "skuId", required = true)
    @RequestMapping(value = "/unLogin/new/sku/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GoodsInfoDetailSimpleResponse> unloginNewDetail(@PathVariable String skuId) {
        GoodsInfoSimpleDetailByGoodsInfoResponse response = goodsDetailInterface.getDetail(skuId, null);
        //设置订货号
        goodsBaseService.setQuickOrderNo(response);
        return BaseResponse.success(
                goodsInfoConvertMapper.detailResponseToDetailResponse(
                        response));
    }



    /**
     * 根据skuIds获取商品信息
     *
     * @param skuIds
     * @return
     */
    @Operation(summary = "根据skuIds获取商品信息")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoViewByIdsResponse> queryGoodsListBySkuIds(@RequestBody List<String> skuIds) {
        GoodsInfoViewByIdsRequest goodsInfoRequest = GoodsInfoViewByIdsRequest.builder()
                .goodsInfoIds(skuIds)
                .isHavSpecText(Constants.yes)
                .build();
        return goodsInfoQueryProvider.listViewByIds(goodsInfoRequest);
    }

    @Operation(summary = "奖励中心热销商品(销量前10的分销商品)")
    @PostMapping(value = "/hot/goodsInfo")
    public BaseResponse<EsGoodsInfoResponse> hotGoods(@RequestBody EsGoodsInfoQueryRequest queryRequest) {

        EsGoodsInfoResponse response = this.esGoodsInfoResult(null);
        // 用户体验优化迭代六，sku列表不再回显当前商品购物车已加购数量
//        if (CollectionUtils.isNotEmpty(queryRequest.getEsGoodsInfoDTOList()) &&
//                CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
//            Map<String, List<EsGoodsInfoDTO>> buyCountMap =
//                    queryRequest.getEsGoodsInfoDTOList().stream()
//                            .collect(Collectors.groupingBy(EsGoodsInfoDTO::getGoodsInfoId));

//            response.getEsGoodsInfoPage().getContent().stream()
//                    .filter(esGoodsInfo -> Objects.nonNull(esGoodsInfo.getGoodsInfo())
//                            && buyCountMap.containsKey(esGoodsInfo.getGoodsInfo().getGoodsInfoId()))
//                    .forEach(esGoodsInfo -> {
//                        GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
//                        goodsInfo.setBuyCount(
//                                buyCountMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId()).get(0).getGoodsNum());
//                    });
//        }

        return BaseResponse.success(response);
    }


    @Operation(summary = "奖励中心热销商品(销量前10的分销商品)")
    @GetMapping(value = "/login/hot/goodsInfo")
    public BaseResponse<EsGoodsInfoResponse> loginHotGoods() {

        EsGoodsInfoResponse response = this.esGoodsInfoResult(commonUtil.getOperatorId());

        Page<EsGoodsInfoVO> esGoodsInfoPage = response.getEsGoodsInfoPage();
        List<EsGoodsInfoVO> esGoodsInfoList = esGoodsInfoPage.getContent();
        List<String> goodsInfoIdList = Optional.of(esGoodsInfoList)
                .orElseGet(Collections::emptyList).stream()
                .map(esGoodsInfo ->
                        esGoodsInfo.getGoodsInfo().getGoodsInfoId()
                ).collect(Collectors.toList());
        String customerId = commonUtil.getOperatorId();

        if (StringUtils.isBlank(customerId)) {
            return BaseResponse.success(response);
        }

        //查询热销商品采购单
        PurchaseQueryRequest purchaseQueryRequest = PurchaseQueryRequest.builder()
                .customerId(customerId)
                .goodsInfoIds(goodsInfoIdList)
                .inviteeId(commonUtil.getPurchaseInviteeId())
                .isO2O(false)
                .build();
        BaseResponse<PurchaseQueryResponse> baseResponse = purchaseQueryProvider.query(purchaseQueryRequest);
        PurchaseQueryResponse purchaseQueryResponse = baseResponse.getContext();
        List<PurchaseVO> purchaseList = purchaseQueryResponse.getPurchaseList();

        if (CollectionUtils.isNotEmpty(purchaseList)) {
            Map<String, Long> goodsNumMap = this.goodsNumMap(purchaseList);
            List<EsGoodsInfoVO> goodsInfoList = this.getGoodsInfoList(esGoodsInfoList, goodsNumMap);
            MicroServicePage<EsGoodsInfoVO> goodsInfoPage = new MicroServicePage<>(goodsInfoList, esGoodsInfoPage.getPageable(), esGoodsInfoPage.getTotalPages());
            response.setEsGoodsInfoPage(goodsInfoPage);
            return BaseResponse.success(response);
        }

        return BaseResponse.success(response);
    }


    /**
     * 初始化EsGoodsInfo
     *
     * @param esGoodsInfoList
     * @param goodsNumMap
     * @return
     */
    private List<EsGoodsInfoVO> getGoodsInfoList(List<EsGoodsInfoVO> esGoodsInfoList, Map<String, Long> goodsNumMap) {

        return esGoodsInfoList.stream().peek(esGoodsInfo -> {
            GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
            String goodsInfoId = goodsInfo.getGoodsInfoId();
            Long goodsNum = goodsNumMap.get(goodsInfoId);
            goodsNum = Objects.nonNull(goodsNum) ? goodsNum : 0L;
            goodsInfo.setBuyCount(goodsNum);
        }).collect(Collectors.toList());
    }

    /**
     * key:goodInfoId,value:goodsNum
     *
     * @param purchaseList
     * @return
     */
    private Map<String, Long> goodsNumMap(List<PurchaseVO> purchaseList) {
        return purchaseList.stream()
                .collect(Collectors.toMap(PurchaseVO::getGoodsInfoId, PurchaseVO::getGoodsNum));
    }

    private EsGoodsInfoResponse esGoodsInfoResult(String customerId) {
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        queryRequest.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED.toValue());
        queryRequest.setDistributionGoodsStatus(NumberUtils.INTEGER_ZERO);
        wrapEsGoodsInfoQueryRequest(queryRequest);
        queryRequest.setCustomerLevelId(null);
        queryRequest.setCustomerLevelDiscount(null);
        queryRequest.setPageSize(10);
        queryRequest.setSortFlag(4);

        EsDistributorGoodsListQueryRequest request = new EsDistributorGoodsListQueryRequest();
        request.setRequest(queryRequest);
        request.setGoodsIdList(Collections.emptyList());

        EsGoodsInfoResponse esGoodsInfoResponse = esGoodsInfoElasticQueryProvider.distributorGoodsList(request).getContext();
        // 查询优惠券
        esGoodsInfoResponse = this.distributionGetCoupon(esGoodsInfoResponse, customerId);
        return esGoodsInfoResponse;
    }

    /**
     * 查询优惠券
     *
     * @param esGoodsInfoResponse
     * @param customerId
     * @return
     */
    public EsGoodsInfoResponse distributionGetCoupon(EsGoodsInfoResponse esGoodsInfoResponse, String customerId) {
        //查询优惠券
        List<EsGoodsInfoVO> esGoodsInfoList = esGoodsInfoResponse.getEsGoodsInfoPage().getContent();
        List<GoodsInfoDTO> goodsInfoVOS = esGoodsInfoList.stream().map(e -> KsBeanUtil.convert(e.getGoodsInfo(), GoodsInfoDTO.class)).collect(Collectors.toList());
        MarketingPluginGoodsListFilterRequest request = new MarketingPluginGoodsListFilterRequest();
        request.setGoodsInfos(goodsInfoVOS);
        request.setCustomerId(customerId);
        List<GoodsInfoVO> goodsInfoVOList = marketingPluginProvider.distributionGoodsListFilter(request).getContext().getGoodsInfoVOList();
        //重新赋值于Page内部对象
        Map<String, GoodsInfoNestVO> voMap = goodsInfoVOList.stream()
                .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, g -> KsBeanUtil.convert(g,
                        GoodsInfoNestVO.class), (s, a) -> s));
        esGoodsInfoResponse.getEsGoodsInfoPage().getContent().stream().forEach(e -> {
            GoodsInfoNestVO goodsInfo = e.getGoodsInfo();
            GoodsInfoNestVO goodsInfoNest = voMap.get(goodsInfo.getGoodsInfoId());
            goodsInfo.setCouponLabels(goodsInfoNest.getCouponLabels());
            e.setGoodsInfo(goodsInfo);
        });
        return esGoodsInfoResponse;
    }


    /**
     * 包装EsGoodsInfoQueryRequest搜索对象
     *
     * @param queryRequest
     * @return
     */
    private EsGoodsInfoQueryRequest wrapEsGoodsInfoQueryRequest(EsGoodsInfoQueryRequest queryRequest) {
        queryRequest.setVendibility(Constants.yes);
        queryRequest.setAddedFlag(AddedFlag.YES.toValue());
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setAuditStatus(CheckStatus.CHECKED.toValue());
        queryRequest.setStoreState(StoreState.OPENING.toValue());
        String now = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4);
        queryRequest.setContractStartDate(now);
        queryRequest.setContractEndDate(now);
        queryRequest.setCustomerLevelId(0L);
        queryRequest.setCustomerLevelDiscount(BigDecimal.ONE);
        // 填充终端名称
        if (StringUtils.isBlank(queryRequest.getTerminalSource())) {
            queryRequest.setTerminalSource(commonUtil.getTerminal().toString());
        }
        return queryRequest;
    }

    /**
     * 小B-店铺管理页，选品页、编辑，小C-店铺精选页，分销商品统一验证接口
     *
     * @param queryRequest
     * @param response
     * @return
     */
    private EsGoodsInfoResponse filterDistributionGoods(EsGoodsInfoQueryRequest queryRequest, EsGoodsInfoResponse
            response, Map<String, String> map) {
        List<EsGoodsInfoVO> esGoodsInfoList = response.getEsGoodsInfoPage().getContent();
        if (CollectionUtils.isNotEmpty(esGoodsInfoList)) {


            List<GoodsInfoVO> goodsInfoList = esGoodsInfoList.stream().filter(e -> Objects.nonNull(e.getGoodsInfo()))
                    .map(e -> KsBeanUtil.convert(e.getGoodsInfo(), GoodsInfoVO.class))
                    .collect(Collectors.toList());
            queryRequest.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED.toValue());
            if (CollectionUtils.isEmpty(goodsInfoList)) {
                return new EsGoodsInfoResponse();
            }

            String customerId = commonUtil.getOperatorId();
            BaseResponse<DistributorLevelByCustomerIdResponse> baseResponse = null;
            if (StringUtils.isNotBlank(customerId)) {
                baseResponse = distributionService.getByCustomerId(customerId);
            }
            final BaseResponse<DistributorLevelByCustomerIdResponse> resultBaseResponse = baseResponse;
            //重新赋值于Page内部对象
            Map<String, GoodsInfoNestVO> voMap = goodsInfoList.stream()
                    .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, g -> KsBeanUtil.convert(g, GoodsInfoNestVO
                            .class), (s, a) -> s));
            response.getEsGoodsInfoPage().getContent().forEach(esGoodsInfo -> {
                GoodsInfoNestVO goodsInfoNest = esGoodsInfo.getGoodsInfo();
                if (Objects.nonNull(goodsInfoNest)) {
                    goodsInfoNest = voMap.get(goodsInfoNest.getGoodsInfoId());
                    if (Objects.nonNull(goodsInfoNest)) {
                        goodsInfoNest.setDistributionGoodsInfoId(map.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId()));
                        DistributorLevelVO distributorLevelVO = Objects.isNull(resultBaseResponse) ? null :
                                resultBaseResponse.getContext().getDistributorLevelVO();
                        if (Objects.nonNull(distributorLevelVO) && Objects.nonNull(distributorLevelVO.getCommissionRate()) && DistributionGoodsAudit.CHECKED == goodsInfoNest.getDistributionGoodsAudit()) {
                            goodsInfoNest.setDistributionCommission(distributionService.calDistributionCommission(goodsInfoNest.getDistributionCommission(), distributorLevelVO.getCommissionRate()));
                        }
                        esGoodsInfo.setGoodsInfo(goodsInfoNest);
                    }
                }
            });
        }
        return response;
    }

    /**
     * 分页查询分销员商品列表
     *
     * @param queryRequest
     * @param customerId
     * @return
     */
    private MicroServicePage<DistributorGoodsInfoVO> pageDistributorGoodsInfoPageByCustomerId(EsGoodsInfoQueryRequest
                                                                                                      queryRequest,
                                                                                              String customerId) {
        DistributorGoodsInfoPageByCustomerIdRequest distributorGoodsInfoPageByCustomerIdRequest = new
                DistributorGoodsInfoPageByCustomerIdRequest();
        distributorGoodsInfoPageByCustomerIdRequest.setCustomerId(customerId);
        distributorGoodsInfoPageByCustomerIdRequest.setPageNum(queryRequest.getPageNum());
        distributorGoodsInfoPageByCustomerIdRequest.setPageSize(queryRequest.getPageSize());
        BaseResponse<DistributorGoodsInfoPageByCustomerIdResponse> baseResponse = distributorGoodsInfoQueryProvider
                .pageByCustomerId(distributorGoodsInfoPageByCustomerIdRequest);
        MicroServicePage<DistributorGoodsInfoVO> microServicePage = baseResponse.getContext().getMicroServicePage();
        return microServicePage;
    }


    /**
     * 根据商品skuId批量查询商品sku列表
     *
     * @param request 根据批量商品skuId查询结构 {@link GoodsInfoListByIdsRequest}
     * @return 商品sku列表 {@link GoodsInfoListByIdsResponse}
     */
    @Operation(summary = "根据商品skuId批量查询商品sku列表")
    @RequestMapping(value = "/info/list-by-ids", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListByIdsResponse> listByIds(@RequestBody @Valid GoodsInfoListByIdsRequest request) {
        return goodsInfoQueryProvider.listByIds(request);
    }

    private void providerSkuStockSync(EsGoodsInfoResponse response) {
        //供应商库存同步
        List<GoodsInfoNestVO> goodsInfoNests =
                response.getEsGoodsInfoPage().getContent().stream().map(EsGoodsInfoVO::getGoodsInfo)
                        .collect(Collectors.toList());
        List<GoodsInfoDTO> goodsInfoDTOList = KsBeanUtil.convert(goodsInfoNests, GoodsInfoDTO.class);
        Map<String, GoodsInfoVO> goodsInfoVOMap =
                goodsInfoProvider.providerGoodsStockSync(new ProviderGoodsStockSyncRequest
                        (goodsInfoDTOList)).getContext().getGoodsInfoList().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        for (EsGoodsInfoVO esGoodsInfo : response.getEsGoodsInfoPage().getContent()) {
            esGoodsInfo.getGoodsInfo().setStock(goodsInfoVOMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId()).getStock());
        }
    }

    /**
     * 商品详情页根据商品id查询商品属性列表
     *
     * @param goodsId 商品id
     * @return 所有属性列表
     */
    @Operation(summary = "根据商品id查询商品属性列表")
    @Parameter(name = "goodsId", description = "商品id", required = true)
    @RequestMapping(value = "/property/list/{goodsId}", method = RequestMethod.GET)
    public BaseResponse<GoodsPropertyListForGoodsResponse> getGoodsPropertyListForGoods(@PathVariable String goodsId) {

        return goodsPropertyQueryProvider.getGoodsPropertyListForGoods(GoodsPropertyByGoodsIdRequest.builder().goodsId(goodsId).build());
    }
    @Operation(summary = "根据商品id和会员等级查询商品属性列表")
    @Parameter(name = "goodsId", description = "商品id", required = true)
    @RequestMapping(value = "/property/list/{goodsId}/{giftCardType}", method = RequestMethod.GET)
    public BaseResponse<BigDecimal> getGoodsPropertyListForGoods(@PathVariable String goodsId,@PathVariable Integer giftCardType) {
        GoodsPropertyByGoodsIdRequest build = GoodsPropertyByGoodsIdRequest.builder().goodsId(goodsId).build();
        switch (giftCardType) {
            case 1:build.setPropName("银卡价格");break;
            case 2:build.setPropName("金卡价格");break;
            case 3:build.setPropName("钻石卡价格");break;
        }
        return goodsPropertyQueryProvider.getGoodsPropertyListForGoodsByType(build);
    }
    /**
     * 商品详情页根据商品id查询商品属性列表
     *
     * @param goodsId 商品id
     * @return 所有属性列表
     */
    @Operation(summary = "根据商品id查询商品属性列表")
    @Parameter( name = "goodsId", description = "商品id", required = true)
    @RequestMapping(value = "/unLogin/spuSpecInfo/{goodsId}", method = RequestMethod.GET)
    public BaseResponse<GoodsDetailSpecInfoResponse> spuSpec(@PathVariable String goodsId) {

        return goodsSpecQueryProvider.goodsDetailSpecInfo(GoodsSpecQueryRequest.builder().goodsId(goodsId).build());
    }

    @Operation(summary = "根据商品id查询商品属性列表")
    @Parameter(name = "goodsId", description = "商品id", required = true)
    @RequestMapping(value = "/spuSpecInfo/{goodsId}", method = RequestMethod.POST)
    public BaseResponse<GoodsDetailSpecInfoResponse> spuSpecByAddress(@PathVariable String goodsId, @RequestBody GoodsSpuSpecRequest request) {
        //封装用户地址信息  地址为空查询默认的
        PlatformAddress address = KsBeanUtil.convert(request, PlatformAddress.class);
        if(StringUtils.isBlank(request.getProvinceId()) && StringUtils.isBlank(request.getCityId())
                && StringUtils.isBlank(request.getAreaId()) && StringUtils.isBlank(request.getStreetId())) {
            CustomerDeliveryAddressRequest queryRequest = new CustomerDeliveryAddressRequest();
            queryRequest.setCustomerId(commonUtil.getOperatorId());
            CustomerDeliveryAddressResponse addressResponse = customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(queryRequest).getContext();
            if (Objects.nonNull(addressResponse)) {
                address = new PlatformAddress();
                address.setProvinceId(Objects.isNull(addressResponse.getProvinceId()) ? null : addressResponse.getProvinceId().toString());
                address.setCityId(Objects.isNull(addressResponse.getCityId()) ? null : addressResponse.getCityId().toString());
                address.setAreaId(Objects.isNull(addressResponse.getAreaId()) ? null : addressResponse.getAreaId().toString());
                address.setStreetId(Objects.isNull(addressResponse.getStreetId()) ? null : addressResponse.getStreetId().toString());
            }
        }
        // 如果  一、二、三级地址有一个为空则不按照地址查询
        if (address.hasNull()) {
            address = null;
        }
        return goodsSpecQueryProvider.goodsDetailSpecInfo(GoodsSpecQueryRequest.builder().goodsId(goodsId).address(address).flashStockFlag(request.getFlashStockFlag()).build());
    }

    protected GoodsInfoListResponse distributorSkuListConvert(EsGoodsInfoSimpleResponse response){
        GoodsInfoListResponse goodsInfoListResponse = new GoodsInfoListResponse();
        if(goodsInfoListResponse!=null && CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())){
            List<DistributorGoodsInfoListVO> content = new ArrayList<>();
            for(EsGoodsInfoVO esGoodsInfoVO : response.getEsGoodsInfoPage().getContent()){
                DistributorGoodsInfoListVO goodsInfoListVO = goodsInfoConvertMapper.goodsInfoNestVOToDistributorGoodsInfoListVO(esGoodsInfoVO.getGoodsInfo());
                goodsInfoListVO.setGoodsLabelList(esGoodsInfoVO.getGoodsLabelList());
                goodsInfoListVO.setGoodsSubtitle(esGoodsInfoVO.getGoodsSubtitle());
                content.add(goodsInfoListVO);
            }
            MicroServicePage<DistributorGoodsInfoListVO> page = new MicroServicePage(content);
            page.setTotal(response.getEsGoodsInfoPage().getTotal());
            page.setSize(response.getEsGoodsInfoPage().getSize());
            page.setNumber(response.getEsGoodsInfoPage().getNumber());
            goodsInfoListResponse.setGoodsInfoPage(page);
        }
        return goodsInfoListResponse;
    }



    /**
     * 是否是周期购商品
     *
     * @return
     */
    @Operation(summary = "是否是周期购商品")
    @Parameter(name = "skuId", description = "skuId", required = true)
    @RequestMapping(value = "/isBuyCycle/{skuId}", method = RequestMethod.GET)
    public BaseResponse<Boolean> isBuyCycleSku(@PathVariable String skuId) {
        GoodsInfoVO goodsInfoVO = goodsInfoQueryProvider.getGoodsInfoById(GoodsInfoListByIdRequest.builder()
                .goodsInfoId(skuId)
                .build()).getContext().getGoodsInfoVO();
        if (Objects.isNull(goodsInfoVO)) {
            return BaseResponse.success(Boolean.FALSE);
        } else {
            return BaseResponse.success(Objects.equals(Constants.yes,goodsInfoVO.getIsBuyCycle()));
        }
    }
}
