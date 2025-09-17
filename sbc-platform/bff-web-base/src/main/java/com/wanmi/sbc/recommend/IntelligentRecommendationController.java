package com.wanmi.sbc.recommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceByCustomerIdRequest;
import com.wanmi.sbc.goods.api.response.brand.GoodsBrandByIdsResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.response.info.GoodsInfoListByGoodsInfoResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingScopeVO;
import com.wanmi.sbc.recommend.response.IntelligentRecommendationBaseResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.provider.recommend.IntelligentRecommendation.IntelligentRecommendationProvider;
import com.wanmi.sbc.vas.api.provider.recommend.recommendpositionconfiguration.RecommendPositionConfigurationQueryProvider;
import com.wanmi.sbc.vas.api.provider.recommend.recommendsystemconfig.RecommendSystemConfigQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationClickGoodsRequest;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationRequest;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.RecommendationOrderGoodsListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationListRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigRequest;
import com.wanmi.sbc.vas.api.response.recommend.IntelligentRecommendation.IntelligentRecommendationResponse;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.RecommendSystemConfigByIdResponse;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionOpenFlag;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionType;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendPositionConfigurationVO;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendSystemConfigVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 商品智能推荐API
 * @author lvzhenwei
 * @date 2021/4/9 4:09 下午
 */
@RestController
@Validated
@Slf4j
@RequestMapping("/intelligent-recommendation")
@Tag(name = "IntelligentRecommendationController", description = "商品智能推荐API")
public class IntelligentRecommendationController {

    @Autowired private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired private CommonUtil commonUtil;

    @Autowired
    private RecommendPositionConfigurationQueryProvider recommendPositionConfigurationQueryProvider;

    @Autowired private IntelligentRecommendationProvider intelligentRecommendationProvider;

    @Autowired private GoodsQueryProvider goodsQueryProvider;

    @Autowired private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired private MarketingQueryProvider marketingQueryProvider;

    @Autowired private DistributionCacheService distributionCacheService;

    @Autowired private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired private GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired private MarketingPluginProvider marketingPluginProvider;

    @Autowired private RedisUtil redisService;

    @Autowired private SystemPointsConfigService systemPointsConfigService;

    @Autowired private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private RecommendSystemConfigQueryProvider recommendSystemConfigQueryProvider;

    /**
     * 智能推荐商品查询（未登录）
     *
     * @author lvzhenwei
     * @date 2021/4/16 11:42 上午
     * @param queryRequest
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.recommend.response.IntelligentRecommendationBaseResponse>
     */
    @Operation(summary = "智能推荐商品查询（未登录）")
    @RequestMapping(value = "/goods-list/get/unLogin", method = RequestMethod.POST)
    public BaseResponse<IntelligentRecommendationBaseResponse> goodsRecommendListForUnLogin(
            @Valid @RequestBody IntelligentRecommendationRequest queryRequest) {
        queryRequest.setTerminalType(commonUtil.getTerminal().toValue());
        IntelligentRecommendationBaseResponse recommendationResponse =
                new IntelligentRecommendationBaseResponse();
        queryRequest.setLocation(queryRequest.getType().toValue());

        IntelligentRecommendationResponse response =
                intelligentRecommendationProvider
                        .intelligentRecommendation(queryRequest)
                        .getContext();
        if (Objects.isNull(response)) {
            return BaseResponse.success(recommendationResponse);
        }
        recommendationResponse.setRecommendPositionConfigurationVO(response.getRecommendPositionConfigurationVO());
        List<String> goodsIdList = response.getGoodsIdList();
        List<Long> cateIdList = response.getCateIdList();
        List<Long> brandList = response.getInterestBrandList();
        if (PositionType.GOODS_CATE == queryRequest.getType()
                || PositionType.CATE_PAGE == queryRequest.getType()) {
            if (CollectionUtils.isNotEmpty(cateIdList)) {
                GoodsCateByIdsRequest goodsCateByIdsRequest = new GoodsCateByIdsRequest();
                goodsCateByIdsRequest.setCateIds(cateIdList);
                List<GoodsCateVO> goodsCateVOList =
                        goodsCateQueryProvider
                                .getByIds(goodsCateByIdsRequest)
                                .getContext()
                                .getGoodsCateVOList();
                List<GoodsCateVO> goodsCateVoListNew = new ArrayList<>();
                cateIdList.forEach(
                        cateId -> {
                            goodsCateVOList.forEach(
                                    goodsCateVO -> {
                                        if (goodsCateVO.getCateId().equals(cateId)) {
                                            goodsCateVoListNew.add(goodsCateVO);
                                        }
                                    });
                        });
                recommendationResponse.setGoodsCateVOList(goodsCateVoListNew);
            }
        } else {
            if (CollectionUtils.isNotEmpty(goodsIdList)) {
                getRecommendationResponse(recommendationResponse, goodsIdList);
            }
        }
        if (PositionType.CATE_PAGE == queryRequest.getType()) {
            if (CollectionUtils.isNotEmpty(brandList)) {
                GoodsBrandByIdsResponse brandByIdsResponse =
                        goodsBrandQueryProvider
                                .listByIds(
                                        GoodsBrandByIdsRequest.builder()
                                                .brandIds(brandList)
                                                .build())
                                .getContext();
                List<GoodsBrandVO> sortBrands = new ArrayList<>();
                brandList.forEach(
                        brandId -> {
                            brandByIdsResponse
                                    .getGoodsBrandVOList()
                                    .forEach(
                                            vo -> {
                                                if (vo.getBrandId().equals(brandId)) {
                                                    sortBrands.add(vo);
                                                }
                                            });
                        });
                recommendationResponse.setGoodsBrandVOList(sortBrands);
            }
        }
        return BaseResponse.success(recommendationResponse);
    }

    /**
     * 未登录-点击推荐商品埋点
     *
     * @author lvzhenwei
     * @date 2021/4/16 11:42 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "未登录-点击推荐商品埋点")
    @RequestMapping(value = "/goods/click/unLogin", method = RequestMethod.POST)
    public BaseResponse clickGoodsUnLogin(
            @RequestBody @Valid IntelligentRecommendationClickGoodsRequest request) {
        request.setTerminalType(commonUtil.getTerminal().toValue());
        request.setItem(NumberUtils.INTEGER_ONE);
        if (getVasRecommendFlag()) {
            if (StringUtils.isBlank(request.getGoodsId()) && Objects.isNull(request.getCateId())) {
                log.error("点击推荐商品埋点 - 请求参数错误");
                return BaseResponse.SUCCESSFUL();
            }
            if (PositionType.GOODS_CATE == request.getType()) {
                request.setItem(NumberUtils.INTEGER_ZERO);
            }
            intelligentRecommendationProvider.clickGoods(request);
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 点击推荐商品埋点
     *
     * @author lvzhenwei
     * @date 2021/4/16 11:42 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "点击推荐商品埋点")
    @RequestMapping(value = "/goods/click", method = RequestMethod.POST)
    public BaseResponse clickGoods(
            @RequestBody @Valid IntelligentRecommendationClickGoodsRequest request) {
        request.setTerminalType(commonUtil.getTerminal().toValue());
        request.setItem(NumberUtils.INTEGER_ONE);
        if (getVasRecommendFlag()) {
            if (StringUtils.isBlank(request.getGoodsId()) && Objects.isNull(request.getCateId())) {
                log.error("点击推荐商品埋点 - 请求参数错误");
                return BaseResponse.SUCCESSFUL();
            }
            request.setCustomerId(commonUtil.getOperatorId());
            if (PositionType.GOODS_CATE == request.getType()) {
                request.setItem(NumberUtils.INTEGER_ZERO);
            }
            intelligentRecommendationProvider.clickGoods(request);
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 推荐商品订单埋点
     *
     * @author lvzhenwei
     * @date 2021/4/16 11:42 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "推荐商品订单埋点")
    @RequestMapping(value = "/order-goods", method = RequestMethod.POST)
    public BaseResponse orderGoodsInfo(
            @RequestBody @Valid RecommendationOrderGoodsListRequest request) {
        try {
            if (CollectionUtils.isNotEmpty(request.getOrderGoodsLis()) && getVasRecommendFlag()) {
                request.getOrderGoodsLis().stream()
                        .forEach(
                                orderGoodsInfo -> {
                                    orderGoodsInfo.setTerminalType(commonUtil.getTerminal().toValue());
                                    if (getVasRecommendFlag()) {
                                        if (CollectionUtils.isNotEmpty(orderGoodsInfo.getGoodsIds())) {
                                            orderGoodsInfo
                                                    .getGoodsIds()
                                                    .forEach(
                                                            goodsId -> {
                                                                IntelligentRecommendationClickGoodsRequest
                                                                        clickRequest =
                                                                        new IntelligentRecommendationClickGoodsRequest();
                                                                clickRequest.setCustomerId(
                                                                        commonUtil.getOperatorId());
                                                                clickRequest.setGoodsId(goodsId);
                                                                clickRequest.setOrderId(
                                                                        orderGoodsInfo.getOrderId());
                                                                clickRequest.setOrderType(
                                                                        orderGoodsInfo.getOrderType());
                                                                clickRequest.setType(
                                                                        PositionType.SHOP_CART);
                                                                clickRequest.setEventType(orderGoodsInfo.getEventType());
                                                                intelligentRecommendationProvider
                                                                        .clickGoods(clickRequest);
                                                            });
                                        }
                                    }
                                });
            }
        } catch (Exception e) {
            log.error("推荐商品订单埋点 异常：{}", e);
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 商品智能推荐
     *
     * @author lvzhenwei
     * @date 2021/4/16 11:42 上午
     * @param queryRequest
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.recommend.response.IntelligentRecommendationBaseResponse>
     */
    @Operation(summary = "商品智能推荐")
    @RequestMapping(value = "/goods-list/get", method = RequestMethod.POST)
    public BaseResponse<IntelligentRecommendationBaseResponse> goodsRecommendList(@Valid @RequestBody IntelligentRecommendationRequest queryRequest) {
        queryRequest.setTerminalType(commonUtil.getTerminal().toValue());
        IntelligentRecommendationBaseResponse recommendationResponse =
                new IntelligentRecommendationBaseResponse();
        queryRequest.setCustomerId(commonUtil.getOperatorId());
        queryRequest.setLocation(queryRequest.getType().toValue());

        IntelligentRecommendationResponse response =
                intelligentRecommendationProvider
                        .intelligentRecommendation(queryRequest)
                        .getContext();
        if (Objects.isNull(response)) {
            return BaseResponse.success(recommendationResponse);
        }

        recommendationResponse.setRecommendPositionConfigurationVO(response.getRecommendPositionConfigurationVO());

        List<String> goodsIdList = response.getGoodsIdList();
        List<Long> cateIdList = response.getCateIdList();
        List<Long> brandList = response.getInterestBrandList();
        if (PositionType.GOODS_CATE == queryRequest.getType()
                || PositionType.CATE_PAGE == queryRequest.getType()) {
            if (CollectionUtils.isNotEmpty(cateIdList)) {
                GoodsCateByIdsRequest goodsCateByIdsRequest = new GoodsCateByIdsRequest();
                goodsCateByIdsRequest.setCateIds(cateIdList);
                List<GoodsCateVO> goodsCateVOList =
                        goodsCateQueryProvider
                                .getByIds(goodsCateByIdsRequest)
                                .getContext()
                                .getGoodsCateVOList();
                List<GoodsCateVO> goodsCateVoListNew = new ArrayList<>();
                cateIdList.forEach(
                        cateId -> {
                            goodsCateVOList.forEach(
                                    goodsCateVO -> {
                                        if (goodsCateVO.getCateId().equals(cateId)) {
                                            goodsCateVoListNew.add(goodsCateVO);
                                        }
                                    });
                        });
                recommendationResponse.setGoodsCateVOList(goodsCateVoListNew);
            }
        } else {
            if (CollectionUtils.isNotEmpty(goodsIdList)) {
                getRecommendationResponse(recommendationResponse, goodsIdList);
            }
        }
        if (PositionType.CATE_PAGE == queryRequest.getType()) {
            if (CollectionUtils.isNotEmpty(brandList)) {
                GoodsBrandByIdsResponse brandByIdsResponse =
                        goodsBrandQueryProvider
                                .listByIds(
                                        GoodsBrandByIdsRequest.builder()
                                                .brandIds(brandList)
                                                .build())
                                .getContext();
                List<GoodsBrandVO> sortBrands = new ArrayList<>();
                brandList.forEach(
                        brandId -> {
                            brandByIdsResponse
                                    .getGoodsBrandVOList()
                                    .forEach(
                                            vo -> {
                                                if (vo.getBrandId().equals(brandId)) {
                                                    sortBrands.add(vo);
                                                }
                                            });
                        });
                recommendationResponse.setGoodsBrandVOList(sortBrands);
            }
        }
        return BaseResponse.success(recommendationResponse);
    }

    /*
     *
     * @description 基于用户兴趣推荐策略开关状态查询
     * @date: 2021/4/21 10:17
     * @param recommendationResponse
     * @return boolean
     **/
    private boolean configStatus(IntelligentRecommendationBaseResponse recommendationResponse) {
        RecommendSystemConfigByIdResponse recommendSystemConfigByIdResponse =
                recommendSystemConfigQueryProvider
                        .getRecommendSystemConfig(
                                RecommendSystemConfigRequest.builder()
                                        .configKey(
                                                com.wanmi.sbc.vas.bean.enums.ConfigKey
                                                        .USER_INTEREST_RECOMMEND_CONFIG
                                                        .toValue())
                                        .configType(
                                                com.wanmi.sbc.vas.bean.enums.ConfigType
                                                        .USER_INTEREST_RECOMMEND_CONFIG
                                                        .toValue())
                                        .build())
                        .getContext();
        RecommendSystemConfigVO configVO =
                recommendSystemConfigByIdResponse.getRecommendSystemConfigVO();
        if (configVO != null && configVO.getStatus() == 0) {
            recommendationResponse.setGoodsVOList(new ArrayList<>());
            return true;
        }
        return false;
    }

    private void getRecommendationResponse(IntelligentRecommendationBaseResponse recommendationResponse,
                                           List<String> goodsIdList) {
        List<GoodsVO> goodsVOList =
                goodsQueryProvider
                        .listByIds(
                                GoodsListByIdsRequest.builder()
                                        .goodsIds(goodsIdList)
                                        .getGoodsInfoFlag(BoolFlag.YES)
                                        .build())
                        .getContext()
                        .getGoodsVOList();
        EsGoodsInfoQueryRequest esGoodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        esGoodsInfoQueryRequest.setGoodsIds(goodsIdList);
        Integer size =
                goodsVOList.stream()
                        .map(goodsVO -> new BigDecimal(goodsVO.getGoodsInfoList().size()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .intValue();
        esGoodsInfoQueryRequest.setPageSize(size);
        esGoodsInfoQueryRequest.setAggFlag(Boolean.FALSE);
        EsGoodsInfoResponse esGoodsInfoResponse = list(esGoodsInfoQueryRequest, null);
        List<GoodsVO> newGoodsVOList = new ArrayList<>();
        Map<String, EsGoodsInfoVO> esGoodsMap = new HashMap<>();
        esGoodsInfoResponse
                .getEsGoodsInfoPage()
                .getContent()
                .forEach(
                        esGoodsInfoVO -> {
                            esGoodsMap.put(
                                    esGoodsInfoVO.getGoodsInfo().getGoodsInfoId(), esGoodsInfoVO);
                        });
        //是否积分
        final boolean isGoodsPoint = systemPointsConfigService.isGoodsPoint();

        goodsIdList.forEach(
                goodsId -> {
                    goodsVOList.forEach(
                            goodsVO -> {
                                if (goodsVO.getGoodsId().equals(goodsId)) {
                                    List<GoodsInfoVO> goodsInfoVoS = new ArrayList<>();
                                    goodsVO.getGoodsInfoList()
                                            .forEach(
                                                    goodsInfoVO -> {
                                                        if (goodsInfoVO.getAddedFlag() == 1) {
                                                            if (Objects.nonNull(
                                                                    esGoodsMap.get(
                                                                            goodsInfoVO
                                                                                    .getGoodsInfoId()))) {
                                                                GoodsInfoNestVO goodsInfoNestVO =
                                                                        esGoodsMap
                                                                                .get(
                                                                                        goodsInfoVO
                                                                                                .getGoodsInfoId())
                                                                                .getGoodsInfo();
                                                                goodsInfoVO.setMarketingLabels(
                                                                        goodsInfoNestVO
                                                                                .getMarketingLabels());
                                                                goodsInfoVO.setSpecText(
                                                                        goodsInfoNestVO
                                                                                .getSpecText());
                                                                goodsInfoVO.setBuyPoint(
                                                                        goodsInfoNestVO
                                                                                .getBuyPoint());
                                                                if (!isGoodsPoint) {
                                                                    goodsInfoVO.setBuyPoint(0L);
                                                                }
                                                                goodsInfoVoS.add(goodsInfoVO);
                                                            }
                                                        } else {
                                                            if (!isGoodsPoint) {
                                                                goodsInfoVO.setBuyPoint(0L);
                                                            }
                                                        }
                                                    });
                                    if (goodsInfoVoS.size() > 0) {
                                        goodsVO.setGoodsInfoList(goodsInfoVoS);
                                    }
                                    newGoodsVOList.add(goodsVO);
                                }
                            });
                });
        recommendationResponse.setGoodsVOList(newGoodsVOList);
        recommendationResponse.setEsGoodsInfoPage(esGoodsInfoResponse.getEsGoodsInfoPage());
    }

    /**
     * es商品列表
     *
     * @author lvzhenwei
     * @date 2021/4/16 11:43 上午
     * @param queryRequest
     * @param customerId
     * @return com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse
     */
    private EsGoodsInfoResponse list(EsGoodsInfoQueryRequest queryRequest, String customerId) {
        if (Objects.nonNull(queryRequest.getMarketingId())) {
            MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
            marketingGetByIdRequest.setMarketingId(queryRequest.getMarketingId());
            MarketingForEndVO marketingForEndVO = marketingQueryProvider.getByIdForCustomer(marketingGetByIdRequest).getContext()
                    .getMarketingForEndVO();
            List<String> scopeIds = marketingForEndVO.getMarketingScopeList().stream().map(MarketingScopeVO::getScopeId)
                    .collect(Collectors.toList());
            queryRequest.dealMarketingGoods(marketingForEndVO.getScopeType(), scopeIds, marketingForEndVO.getStoreId());
            //非PC端分销商品状态
            if (!Objects.equals(ChannelType.PC_MALL, commonUtil.getDistributeChannel().getChannelType()) && DefaultFlag.YES.equals(distributionCacheService.queryOpenFlag())) {
                queryRequest.setExcludeDistributionGoods(true);
            }
        }
        // 只看分享赚商品信息
        if (Objects.nonNull(queryRequest.getDistributionGoodsAudit())
                && DistributionGoodsAudit.CHECKED.toValue()
                        == queryRequest.getDistributionGoodsAudit()) {
            queryRequest.setDistributionGoodsStatus(NumberUtils.INTEGER_ZERO);
        }

        queryRequest = wrapEsGoodsInfoQueryRequest(queryRequest);
        EsGoodsInfoResponse response =
                esGoodsInfoElasticQueryProvider.page(queryRequest).getContext();
        // 如果是linkedmall商品，实时查库存
        List<Long> itemIds =
                response.getEsGoodsInfoPage().getContent().stream()
                        .filter(
                                v ->
                                        ThirdPlatformType.LINKED_MALL.equals(
                                                v.getGoodsInfo().getThirdPlatformType()))
                        .map(v -> Long.valueOf(v.getGoodsInfo().getThirdPlatformSpuId()))
                        .distinct()
                        .collect(Collectors.toList());
        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks =
                    linkedMallStockQueryProvider
                            .batchGoodsStockByDivisionCode(
                                    LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build())
                            .getContext();
        }
        if (stocks != null) {
            for (EsGoodsInfoVO esGoodsInfo : response.getEsGoodsInfoPage().getContent()) {
                if (ThirdPlatformType.LINKED_MALL.equals(
                        esGoodsInfo.getGoodsInfo().getThirdPlatformType())) {

                    for (LinkedMallStockVO spuStock : stocks) {
                        Optional<LinkedMallStockVO.SkuStock> stock =
                                spuStock.getSkuList().stream()
                                        .filter(
                                                v ->
                                                        String.valueOf(spuStock.getItemId())
                                                                        .equals(
                                                                                esGoodsInfo
                                                                                        .getGoodsInfo()
                                                                                        .getThirdPlatformSpuId())
                                                                && String.valueOf(v.getSkuId())
                                                                        .equals(
                                                                                esGoodsInfo
                                                                                        .getGoodsInfo()
                                                                                        .getThirdPlatformSkuId()))
                                        .findFirst();
                        if (stock.isPresent()) {
                            Long skuStock = stock.get().getStock();
                            esGoodsInfo.getGoodsInfo().setStock(skuStock);
                            if (!GoodsStatus.INVALID.equals(
                                    esGoodsInfo.getGoodsInfo().getGoodsStatus())) {
                                esGoodsInfo
                                        .getGoodsInfo()
                                        .setGoodsStatus(
                                                skuStock > 0
                                                        ? GoodsStatus.OK
                                                        : GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
            }
        }
        response = calIntervalPriceAndMarketingPrice(response, customerId, queryRequest);
        return response;
    }

    /**
     * 包装EsGoodsInfoQueryRequest搜索对象
     *
     * @param queryRequest
     * @return
     */
    private EsGoodsInfoQueryRequest wrapEsGoodsInfoQueryRequest(
            EsGoodsInfoQueryRequest queryRequest) {
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
        return queryRequest;
    }

    /**
     * 计算区间价、营销价
     *
     * @param response
     * @param customerId
     * @return
     */
    private EsGoodsInfoResponse calIntervalPriceAndMarketingPrice(
            EsGoodsInfoResponse response,
            String customerId,
            EsGoodsInfoQueryRequest queryRequest) {
        List<EsGoodsInfoVO> esGoodsInfoList = response.getEsGoodsInfoPage().getContent();
        // 非商品积分模式下清零
        if (CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
            systemPointsConfigService.clearBuyPoinsForEsSku(esGoodsInfoList);
        }
        if (CollectionUtils.isNotEmpty(esGoodsInfoList)) {
            List<GoodsInfoVO> goodsInfoList =
                    esGoodsInfoList.stream()
                            .filter(e -> Objects.nonNull(e.getGoodsInfo()))
                            .map(
                                    e -> {
                                        GoodsInfoVO goodsInfoVO =
                                                KsBeanUtil.convert(
                                                        e.getGoodsInfo(), GoodsInfoVO.class);
                                        Integer enterPriseAuditStatus =
                                                e.getGoodsInfo().getEnterPriseAuditStatus();
                                        if (Objects.nonNull(enterPriseAuditStatus)) {
                                            goodsInfoVO.setEnterPriseAuditState(
                                                    EnterpriseAuditState.CHECKED.toValue()
                                                                    == enterPriseAuditStatus
                                                            ? EnterpriseAuditState.CHECKED
                                                            : null);
                                        }
                                        return goodsInfoVO;
                                    })
                            .collect(Collectors.toList());

            if (CollectionUtils.isEmpty(goodsInfoList)) {
                return new EsGoodsInfoResponse();
            }

            // 计算区间价
            GoodsIntervalPriceByCustomerIdRequest priceRequest =
                    new GoodsIntervalPriceByCustomerIdRequest();
            priceRequest.setGoodsInfoDTOList(KsBeanUtil.convert(goodsInfoList, GoodsInfoDTO.class));

                priceRequest.setCustomerId(customerId);

            GoodsIntervalPriceByCustomerIdResponse priceResponse =
                    goodsIntervalPriceProvider.putByCustomerId(priceRequest).getContext();
            response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
            goodsInfoList = priceResponse.getGoodsInfoVOList();
            // 计算营销价格
            MarketingPluginGoodsListFilterRequest filterRequest =
                    new MarketingPluginGoodsListFilterRequest();
            filterRequest.setGoodsInfos(KsBeanUtil.convert(goodsInfoList, GoodsInfoDTO.class));

            filterRequest.setCustomerId(customerId);

            filterRequest.setMoFangFlag(queryRequest.getMoFangFlag());
            GoodsInfoListByGoodsInfoResponse filterResponse =
                    marketingPluginProvider.goodsListFilter(filterRequest).getContext();
            if (Objects.nonNull(filterResponse)
                    && CollectionUtils.isNotEmpty(filterResponse.getGoodsInfoVOList())) {
                goodsInfoList = filterResponse.getGoodsInfoVOList();
            }

            goodsInfoList =
                    goodsInfoList.stream()
                            .map(
                                    goodsInfoVO -> {
                                        EnterpriseAuditState enterpriseAuditState =
                                                goodsInfoVO.getEnterPriseAuditState();
                                        if (EnterpriseAuditState.CHECKED == enterpriseAuditState) {
                                            goodsInfoVO.setGrouponLabel(null);
                                        }
                                        return goodsInfoVO;
                                    })
                            .collect(Collectors.toList());

            // 重新赋值于Page内部对象
            Map<String, GoodsInfoNestVO> voMap =
                    goodsInfoList.stream()
                            .collect(
                                    Collectors.toMap(
                                            GoodsInfoVO::getGoodsInfoId,
                                            g -> KsBeanUtil.convert(g, GoodsInfoNestVO.class),
                                            (s, a) -> s));
            response.getEsGoodsInfoPage()
                    .getContent()
                    .forEach(
                            esGoodsInfo -> {
                                GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();

                                GoodsInfoNestVO goodsInfoNest =
                                        voMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId());
                                if (Objects.nonNull(goodsInfoNest)) {
                                    goodsInfoNest.setGoodsEvaluateNum(
                                            goodsInfo.getGoodsEvaluateNum());
                                    goodsInfoNest.setGoodsCollectNum(
                                            goodsInfo.getGoodsCollectNum());
                                    goodsInfoNest.setGoodsFavorableCommentNum(
                                            goodsInfo.getGoodsFavorableCommentNum());
                                    goodsInfoNest.setGoodsFeedbackRate(
                                            goodsInfo.getGoodsFeedbackRate());
                                    goodsInfoNest.setGoodsSalesNum(goodsInfo.getGoodsSalesNum());
                                    goodsInfoNest.setEnterPriseAuditStatus(
                                            goodsInfo.getEnterPriseAuditStatus());
                                    goodsInfoNest.setSpecText(goodsInfo.getSpecText());
                                    goodsInfoNest.setBuyPoint(goodsInfo.getBuyPoint());
                                    esGoodsInfo.setGoodsInfo(goodsInfoNest);
                                }
                            });
        }
        return response;
    }

    /**
     * 判断是否购买智能推荐增值服务
     *
     * @return
     */
    public Boolean getVasRecommendFlag() {
        Map<String, String> vasList = redisService.hgetall(ConfigKey.VALUE_ADDED_SERVICES.toString());
        Boolean vasRecommendFlag = Boolean.FALSE;
        if(Objects.nonNull(vasList.get(VASConstants.VAS_RECOMMEND_SETTING.toValue()))&&
                VASStatus.ENABLE.toValue().equals(vasList.get(VASConstants.VAS_RECOMMEND_SETTING.toValue()))
        ){
            vasRecommendFlag = Boolean.TRUE;
        }
        return vasRecommendFlag;
    }

    @Operation(summary = "查询分类页推荐开关")
    @GetMapping("/queryOpenStatus")
    public BaseResponse<RecommendPositionConfigurationVO> findEnableStatus() {
        RecommendPositionConfigurationVO recommendPosition =
                recommendPositionConfigurationQueryProvider.list(RecommendPositionConfigurationListRequest.builder()
                        .type(PositionType.CATE_PAGE).build()).getContext().getRecommendPositionConfigurationVOList().get(0);
        if (!this.getVasRecommendFlag()) {
            recommendPosition.setIsOpen(PositionOpenFlag.CLOSED);
        }
        return BaseResponse.success(recommendPosition);
    }
}
