package com.wanmi.sbc.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.coupon.request.CouponGoodsPageRequest;
import com.wanmi.sbc.coupon.response.CouponGoodsPageResponse;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponScopeQueryProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponScopePageRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.coupon.EsCouponScopePageResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponScopeVO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoByGoodsIdRequest;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCacheProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoQueryProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.response.coupon.*;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseProvider;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by CHENLI on 2018/9/25.
 */
@Slf4j
@RestController
@Validated
@RequestMapping("/coupon-info")
@Tag(name = "CouponInfoController", description = "S2B web公用-优惠券信息API")
public class CouponInfoController {

    @Autowired
    private CouponCacheProvider couponCacheProvider;

    @Autowired
    private CouponInfoQueryProvider couponInfoQueryProvider;

    @Autowired
    private CouponActivityQueryProvider couponActivityQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired
    private PurchaseProvider purchaseProvider;

    @Autowired
    private MarketingPluginProvider marketingPluginProvider;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired
    private DistributionCacheService distributionCacheService;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private EsCouponScopeQueryProvider esCouponScopeQueryProvider;

    @Autowired
    private BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;

    /**
     * 未登录时，领券中心列表
     *
     * @return
     */
    @Operation(summary = "未登录时，领券中心列表")
    @RequestMapping(value = "/front/center", method = RequestMethod.POST)
    public BaseResponse<CouponCacheCenterPageResponse> getCouponStartedFront(@RequestBody CouponCacheCenterPageRequest queryRequest) {

        return couponCacheProvider.pageCouponStarted(queryRequest);
    }

    /**
     * 领券中心列表
     *
     * @return
     */
    @Operation(summary = "登录后，领券中心列表")
    @RequestMapping(value = "/center", method = RequestMethod.POST)
    public BaseResponse<CouponCacheCenterPageResponse> getCouponStarted(@RequestBody CouponCacheCenterPageRequest queryRequest) {


        queryRequest.setCustomerId(commonUtil.getOperatorId());


        return couponCacheProvider.pageCouponStarted(queryRequest);
    }

    /**
     * 未登录时,通过商品id列表，查询与商品相关优惠券
     * 购物车 - 优惠券列表
     *
     * @return
     */
    @Operation(summary = "未登录时，通过商品id列表，查询与商品相关优惠券")
    @RequestMapping(value = "/front/goods-list", method = RequestMethod.POST)
    public BaseResponse<CouponCacheListNoScopeResponse> listCouponForGoodsListFront(@RequestBody List<String> goodsInfoIds) {
        CouponCacheListForGoodsListRequest request = new CouponCacheListForGoodsListRequest();
        request.setGoodsInfoIds(goodsInfoIds);
        return couponCacheProvider.listCouponForGoodsList(request);
    }

    /**
     * 通过商品id列表，查询与商品相关优惠券
     * 购物车 - 优惠券列表
     *
     * @return
     */
    @Operation(summary = "登录后，通过商品id列表，查询与商品相关优惠券")
    @RequestMapping(value = "/goods-list", method = RequestMethod.POST)
    public BaseResponse<CouponCacheListNoScopeResponse> listCouponForGoodsList(@RequestBody List<String> goodsInfoIds) {
        CouponCacheListForGoodsListRequest request = new CouponCacheListForGoodsListRequest();
        request.setGoodsInfoIds(goodsInfoIds);
        request.setCustomerId(commonUtil.getOperatorId());
        return couponCacheProvider.listCouponForGoodsList(request);
    }

    /**
     * 未登录时,通过商品Id，查询单个商品相关优惠券
     *
     * @return
     */
    @Operation(summary = "未登录时，通过商品Id，查询单个商品相关优惠券")
    @Parameter( name = "goodsInfoId", description = "商品Id", required = true)
    @RequestMapping(value = "/front/goods-detail/{goodsInfoId}", method = RequestMethod.GET)
    public BaseResponse<CouponCacheListForGoodsListResponse> listCouponForGoodsDetailFront(@PathVariable String goodsInfoId) {

        CouponCacheListForGoodsDetailRequest request = new CouponCacheListForGoodsDetailRequest();
        request.setGoodsInfoId(goodsInfoId);
        CouponCacheListForGoodsListResponse goodsDetailResponse =
                couponCacheProvider.listCouponForGoodsDetail(request).getContext();

        return BaseResponse.success(goodsDetailResponse);
    }

    /**
     * 通过商品Id，查询单个商品相关优惠券
     *
     * @return
     */
    @Operation(summary = "登录后，通过商品Id，查询单个商品相关优惠券")
    @Parameter( name = "goodsInfoId", description = "商品Id", required = true)
    @RequestMapping(value = "/goods-detail/{goodsInfoId}", method = RequestMethod.GET)
    public BaseResponse<CouponCacheListForGoodsListResponse> listCouponForGoodsDetail(@PathVariable String goodsInfoId) {
        CouponCacheListForGoodsDetailRequest request = new CouponCacheListForGoodsDetailRequest();
        request.setGoodsInfoId(goodsInfoId);
        request.setCustomerId(commonUtil.getOperatorId());
        return couponCacheProvider.listCouponForGoodsDetail(request);
    }

    /**
     * 优惠券凑单页
     *
     * @return
     */
    @Operation(summary = "优惠券凑单页")
    @RequestMapping(value = "/coupon-goods", method = RequestMethod.POST)
    public BaseResponse<CouponGoodsPageResponse> listGoodsByCouponId(@RequestBody CouponGoodsPageRequest request) {

        CouponGoodsPageResponse couponGoodsPageResponse = new CouponGoodsPageResponse();
        EsGoodsInfoResponse esGoodsInfoResponse = new EsGoodsInfoResponse();
        BaseResponse<CouponGoodsListResponse> baseResponsequeryResponse;
        if (StringUtils.isBlank(request.getActivity())){
            CouponGoodsListRequest requ = CouponGoodsListRequest.builder()
                    .couponId(request.getCouponId()).build();
            baseResponsequeryResponse = couponCacheProvider.couponGoodsById(requ);
        }else {
            // 列表排序默认按最新上架的SKU排序
            // 凑单页面的筛选条件排序按照 默认、最新、价格排序
            CouponGoodsListRequest requ = CouponGoodsListRequest.builder()
                    .activityId(request.getActivity())
                    .customerId(commonUtil.getOperatorId())
                    .couponId(request.getCouponId()).build();
            baseResponsequeryResponse = couponCacheProvider.listGoodsByCouponId(requ);
        }

        CouponGoodsListResponse queryResponse = baseResponsequeryResponse.getContext();

        couponGoodsPageResponse.setCouponInfo(queryResponse);
        if (CollectionUtils.isNotEmpty(queryResponse.getBrandIds()) && CollectionUtils.isEmpty(queryResponse.getQueryBrandIds())) {
            couponGoodsPageResponse.setEsGoodsInfoResponse(esGoodsInfoResponse);
            return BaseResponse.success(couponGoodsPageResponse);
        }

        EsGoodsInfoQueryRequest esGoodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        esGoodsInfoQueryRequest.setAuditStatus(CheckStatus.CHECKED.toValue());
        esGoodsInfoQueryRequest.setStoreState(StoreState.OPENING.toValue());
        esGoodsInfoQueryRequest.setAddedFlag(AddedFlag.YES.toValue());
        esGoodsInfoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        esGoodsInfoQueryRequest.setSortFlag(request.getSortType() == null ? 0 : request.getSortType());
        esGoodsInfoQueryRequest.setPageNum(request.getPageNum());
        esGoodsInfoQueryRequest.setPageSize(request.getPageSize());
        esGoodsInfoQueryRequest.setCateAggFlag(true);
        esGoodsInfoQueryRequest.setStoreId(queryResponse.getStoreId());
        esGoodsInfoQueryRequest.setCompanyType(request.getCompanyType());
        String now = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4);
        esGoodsInfoQueryRequest.setContractStartDate(now);
        esGoodsInfoQueryRequest.setContractEndDate(now);
        esGoodsInfoQueryRequest.setVendibility(Constants.yes);
        esGoodsInfoQueryRequest.setKeywords(request.getKeywords());
        esGoodsInfoQueryRequest.setIsDistinctGoodsId(true);
        if (commonUtil.getTerminal() == TerminalSource.PC) {
            esGoodsInfoQueryRequest.setGoodsType(GoodsType.REAL_GOODS.toValue());
        }
        esGoodsInfoQueryRequest.setSortFlag(request.getSortFlag());
        if (CollectionUtils.isNotEmpty(request.getCateIds())) {
            esGoodsInfoQueryRequest.setCateIds(request.getCateIds());
        }
        if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
            esGoodsInfoQueryRequest.setBrandIds(request.getBrandIds());
        }

        switch (queryResponse.getScopeType()) {
            case ALL:
                break;
            case BOSS_CATE:
                if (CollectionUtils.isEmpty(esGoodsInfoQueryRequest.getCateIds()) && CollectionUtils.isNotEmpty(queryResponse.getCateIds())) {
                    esGoodsInfoQueryRequest.setCateIds(queryResponse.getCateIds4es());
                }
                break;
            case BRAND:
                if (CollectionUtils.isEmpty(esGoodsInfoQueryRequest.getBrandIds()) && CollectionUtils.isNotEmpty(queryResponse.getQueryBrandIds())) {
                    esGoodsInfoQueryRequest.setBrandIds(queryResponse.getQueryBrandIds());
                }
                break;
            case SKU:
                if (CollectionUtils.isNotEmpty(queryResponse.getGoodsInfoId())) {
                    esGoodsInfoQueryRequest.setGoodsInfoIds(queryResponse.getGoodsInfoId());
                }
                break;
            case STORE_CATE:
                if (CollectionUtils.isNotEmpty(queryResponse.getStoreCateIds())) {
                    esGoodsInfoQueryRequest.setStoreCateIds(new ArrayList(queryResponse.getStoreCateIds()));
                }
                break;
            case STORE:
                if(CollectionUtils.isNotEmpty(queryResponse.getStoreIds())){
                    esGoodsInfoQueryRequest.setStoreIds(queryResponse.getStoreIds());
                }
                break;
            default:
                break;
        }


        List<String> couponIdList = new ArrayList<>();
        couponIdList.add(request.getCouponId());
        List<String> couponActivityIdList = new ArrayList<>();
        if(StringUtils.isNotBlank(request.getActivity())){
            couponActivityIdList.add(request.getActivity());
        }
        EsCouponScopePageResponse esCouponScopePageResponse = esCouponScopeQueryProvider.page(EsCouponScopePageRequest.builder().couponIdList(couponIdList).couponActivityIds(couponActivityIdList).build()).getContext();
        List<EsCouponScopeVO> esCouponScopeVOList =  esCouponScopePageResponse.getCouponScopes().getContent();
        List<Long> brandIds = new ArrayList<>();
        List<Long> cateIds = new ArrayList<>();
        List<Long> storeCateIds = new ArrayList<>();
        List<String> goodsInfoIds = new ArrayList<>();
        esCouponScopeVOList.forEach(esCouponScopeVO -> {
            if(esCouponScopeVO.getScopeType() == ScopeType.BRAND){
                brandIds.add(Long.valueOf(esCouponScopeVO.getScopeId()));
            }
            if(esCouponScopeVO.getScopeType() == ScopeType.BOSS_CATE){
                cateIds.add(Long.valueOf(esCouponScopeVO.getScopeId()));
            }
            if(esCouponScopeVO.getScopeType() == ScopeType.STORE_CATE){
                storeCateIds.add(Long.valueOf(esCouponScopeVO.getScopeId()));
            }
            if(esCouponScopeVO.getScopeType() == ScopeType.SKU){
                goodsInfoIds.add(esCouponScopeVO.getScopeId());
            }
        });
        if(CollectionUtils.isNotEmpty(brandIds)){
            esGoodsInfoQueryRequest.setBrandIds(brandIds);
        }
        if(CollectionUtils.isNotEmpty(cateIds)){
            esGoodsInfoQueryRequest.setCateIds(cateIds);
        }
        if(CollectionUtils.isNotEmpty(storeCateIds)){
            esGoodsInfoQueryRequest.setStoreCateIds(storeCateIds);
        }
        if(CollectionUtils.isNotEmpty(goodsInfoIds)){
            esGoodsInfoQueryRequest.setGoodsInfoIds(goodsInfoIds);
        }
        if (TerminalSource.PC.equals(commonUtil.getTerminal())) {
            esGoodsInfoQueryRequest.setIsBuyCycle(Constants.no);
        }
        esGoodsInfoResponse = esGoodsInfoElasticQueryProvider.page(esGoodsInfoQueryRequest).getContext();
        List<EsGoodsInfoVO> goodsInfoVOs = esGoodsInfoResponse.getEsGoodsInfoPage().getContent();

        if (CollectionUtils.isNotEmpty(goodsInfoVOs)) {
            //未开启商品抵扣时，清零buyPoint
            systemPointsConfigService.clearBuyPoinsForEsSku(goodsInfoVOs);
            //获取会员和等级
            String customerId = commonUtil.getOperatorId();

            //组装优惠券标签
            List<GoodsInfoVO> goodsInfoList = goodsInfoVOs.stream().filter(e -> Objects.nonNull(e.getGoodsInfo()))
                    .map(e -> KsBeanUtil.convert(e.getGoodsInfo(), GoodsInfoVO.class))
                    .collect(Collectors.toList());

            // 构建[skuId => 起售数量]Map，防止原始list被后续代码覆盖，丢失字段
            Map<String, Long> skuStartSaleNumMap = goodsInfoList.stream().collect(
                    Collectors.toMap(GoodsInfoVO::getGoodsInfoId, goodsInfoVO -> goodsInfoVO.getStartSaleNum()));

            //计算营销
            MarketingPluginGoodsListFilterRequest filterRequest = new MarketingPluginGoodsListFilterRequest();
            filterRequest.setGoodsInfos(KsBeanUtil.convert(goodsInfoList, GoodsInfoDTO.class));
            filterRequest.setCustomerId(customerId);
            goodsInfoList = marketingPluginProvider.goodsListFilter(filterRequest).getContext().getGoodsInfoVOList();

            //计算区间价
            GoodsIntervalPriceByCustomerIdResponse priceResponse =
                    goodsIntervalPriceService.getGoodsIntervalPriceVOList(goodsInfoList, customerId);
            esGoodsInfoResponse.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
            goodsInfoList = priceResponse.getGoodsInfoVOList();

            // 用户体验优化迭代六，sku列表不再回显当前商品购物车已加购数量
//            //填充
//            PurchaseFillBuyCountRequest purchaseFillBuyCountRequest = new PurchaseFillBuyCountRequest();
//            purchaseFillBuyCountRequest.setCustomerId(commonUtil.getOperatorId());
//            purchaseFillBuyCountRequest.setGoodsInfoList(goodsInfoList);
//            goodsInfoList = purchaseProvider.fillBuyCount(purchaseFillBuyCountRequest).getContext().getGoodsInfoList();

            //重新赋值于Page内部对象
            Map<String, GoodsInfoVO> voMap = goodsInfoList.stream()
                    .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, g -> g));
            esGoodsInfoResponse
                    .getEsGoodsInfoPage()
                    .getContent()
                    .forEach(
                            esGoodsInfo -> {
                                GoodsInfoVO vo =
                                        voMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId());
                                // 填充起售数量
                                vo.setStartSaleNum(
                                        skuStartSaleNumMap.getOrDefault(vo.getGoodsInfoId(), 1L));
                                if (Objects.nonNull(vo)) {
                                    GoodsInfoNestVO goodsInfoNestVO =
                                            KsBeanUtil.convert(vo, GoodsInfoNestVO.class);
                                    //分销商品 将付费会员价 改为null
                                    if (Objects.equals(DistributionGoodsAudit.CHECKED, goodsInfoNestVO.getDistributionGoodsAudit())) {
                                        goodsInfoNestVO.setPayMemberPrice(null);
                                    }
                                    esGoodsInfo.setGoodsInfo(goodsInfoNestVO);
                                }
                            });

            couponGoodsPageResponse.setEsGoodsInfoResponse(esGoodsInfoResponse);
            return BaseResponse.success(couponGoodsPageResponse);
        } else {
            couponGoodsPageResponse.setEsGoodsInfoResponse(esGoodsInfoResponse);
            return BaseResponse.success(couponGoodsPageResponse);
        }
    }

    /**
     * @description h5/app优惠券魔方页面，优惠券数据状态查询
     * @author  EDZ
     * @date 2021/6/11 11:25
     * @param requestList
     * @return com.wanmi.sbc.common.base.BaseResponse<java.util.List<com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoResponse>>
     **/
    @Operation(summary = "根据优惠券ID查询优惠券信息")
    @RequestMapping(value = "/magicCouponInfoStatus", method = RequestMethod.POST)
    public BaseResponse<List<MagicCouponInfoResponse>> magicCouponInfoStatus(@RequestBody List<MagicCouponInfoRequest> requestList) {
        String customerId = commonUtil.getOperatorId();
        log.info("魔方优惠券查询request={}, customerId={}", requestList, customerId);
        BaseResponse<List<MagicCouponInfoResponse>> baseResponse = couponInfoQueryProvider.magicCouponInfoStatus(requestList, commonUtil.getOperatorId());
        log.info(
                "魔方优惠券返回response={}, request={}, customerId={}",
                baseResponse,
                requestList,
                customerId);
        return baseResponse;
    }

    /**
     * @description h5/app优惠券魔方页面，优惠券数据状态查询（未登录时查询）
     * @author  EDZ
     * @date 2021/6/11 11:25
     * @param requestList
     * @return com.wanmi.sbc.common.base.BaseResponse<java.util.List<com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoResponse>>
     **/
    @Operation(summary = "根据优惠券ID查询优惠券信息（未登录时查询）")
    @RequestMapping(value = "/magicCouponInfoStatus/unLogin", method = RequestMethod.POST)
    public BaseResponse<List<MagicCouponInfoResponse>> magicCouponInfoStatusUnLogin(@RequestBody List<MagicCouponInfoRequest> requestList) {
        log.info("魔方优惠券查询unLogin request={}", requestList);
        BaseResponse<List<MagicCouponInfoResponse>> baseResponse = couponInfoQueryProvider.magicCouponInfoStatus(requestList, null);
        log.info("魔方优惠券返回unLogin response={}, request={}", baseResponse, requestList);
        return baseResponse;
    }

    /**
    *
     * @description
     * @author  wur
     * @date: 2021/9/15 18:45
     * @param request
     * @return
     **/
    @Operation(summary = "根据优惠券ID查询优惠券信息")
    @RequestMapping(value = "/couponInfo", method = RequestMethod.POST)
    public BaseResponse<MagicCouponInfoResponse> couponInfo(@RequestBody MagicCouponInfoRequest request) {
        String customerId = commonUtil.getOperatorId();
        log.info("根据优惠券ID查询优惠券信息 request={}, customerId={}", request, customerId);
        BaseResponse<MagicCouponInfoResponse> baseResponse = couponInfoQueryProvider.queryCouponInfo(request, commonUtil.getOperatorId());
        log.info(
                "根据优惠券ID查询优惠券信息 返回response={}, request={}, customerId={}",
                baseResponse,
                request,
                customerId);
        return baseResponse;
    }

    @Operation(summary = "根据scene查询优惠券活动基本信息")
    @RequestMapping(value = "/coupon-activity-config/{scene}", method = RequestMethod.GET)
    public BaseResponse<CouponActivityConfigBySceneResponse> couponActivityConfig(@PathVariable String scene) {
        log.info("根据scene查询优惠券活动信息 scene={}", scene);
        CouponActivityConfigBySceneRequest request = new CouponActivityConfigBySceneRequest(scene);
        BaseResponse<CouponActivityConfigBySceneResponse> response = couponActivityQueryProvider.getCouponActivityConfigByScene(request);
        log.info("根据scene查询优惠券活动信息 返回response={}", response);
        return response;
    }

    /**
     *
     * @description
     * @author  xufeng
     * @date: 2022/4/13 13:45
     * @param request
     * @return
     **/
    @Operation(summary = "根据优惠券ID查询优惠券信息")
    @RequestMapping(value = "/couponInfoById", method = RequestMethod.POST)
    public BaseResponse<MagicCouponInfoResponse> couponInfoById(@RequestBody @Valid CouponInfoByIdRequest request) {
        log.info("根据优惠券ID查询优惠券信息 request={}", request);
        BaseResponse<MagicCouponInfoResponse> baseResponse = couponInfoQueryProvider.queryCouponInfoById(request);
        log.info("根据优惠券ID查询优惠券信息 返回response={}, request={}", baseResponse, request);
        return baseResponse;
    }

}
