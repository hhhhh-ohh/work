package com.wanmi.sbc.goods.service.list.imp;

import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributorLevelByCustomerIdRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoByGoodsIdRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsListRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.IsInProgressRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleListRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsListResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.IsInProgressResp;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.list.GoodsListInterface;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingStoreType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnStoreVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingScopeVO;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseQueryProvider;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.GoodsInfoConvertMapper;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelGoodsProvider;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsStatusGetRequest;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsStatusGetResponse;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className GoodsInfoListService
 * @description TODO
 * @date 2021/8/11 5:14 下午
 */
@Service
public class GoodsInfoListService implements GoodsListInterface<EsGoodsInfoQueryRequest,EsGoodsInfoSimpleResponse> {

    @Autowired MarketingQueryProvider marketingQueryProvider;

    @Autowired
    protected CommonUtil commonUtil;

    @Autowired DistributionCacheService distributionCacheService;

    @Autowired EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired PurchaseQueryProvider purchaseQueryProvider;

    @Autowired
    protected NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired GoodsInfoConvertMapper goodsInfoConvertMapper;

    @Autowired DistributionService distributionService;

    @Autowired DistributorLevelQueryProvider distributorLevelQueryProvider;

    @Autowired SystemPointsConfigService systemPointsConfigService;

    @Autowired GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired private ChannelGoodsProvider channelGoodsProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;


    protected ThreadLocal<EsGoodsInfoQueryRequest> threadRrequest =  new InheritableThreadLocal();

    @Override
    public EsGoodsInfoQueryRequest setRequest(EsGoodsInfoQueryRequest request) {

        if (Objects.nonNull(request.getMarketingId())) {
            MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
            marketingGetByIdRequest.setMarketingId(request.getMarketingId());
            MarketingForEndVO marketingForEndVO =
                    marketingQueryProvider
                            .getMarketingSimpleByIdForCustomer(marketingGetByIdRequest)
                            .getContext()
                            .getMarketingForEndVO();
            List<String> scopeIds =
                    marketingForEndVO.getMarketingScopeList().stream()
                            .map(MarketingScopeVO::getScopeId)
                            .collect(Collectors.toList());
            // 满返平台店铺组装
            if (marketingForEndVO.getMarketingType() == MarketingType.RETURN && BoolFlag.YES ==
                    marketingForEndVO.getIsBoss() && MarketingStoreType.STORE_TYPE_APPOINT == marketingForEndVO.getStoreType()){
                List<Long> storeIds =
                        marketingForEndVO.getMarketingFullReturnStoreList().stream().map(MarketingFullReturnStoreVO::getStoreId).collect(Collectors.toList());
                request.setStoreIds(storeIds);
            }
            request.dealMarketingGoods(
                    marketingForEndVO.getScopeType(), scopeIds, marketingForEndVO.getStoreId());
            // 非PC端分销商品状态
            if (Objects.equals(
                            ChannelType.PC_MALL, commonUtil.getDistributeChannel().getChannelType())
                    && DefaultFlag.YES.equals(distributionCacheService.queryOpenFlag())) {
                request.setExcludeDistributionGoods(true);
            }
            request.marketingFilterVirtualGoods(marketingForEndVO.getMarketingType());
            request.setIsBuyCycle(Constants.no);
        }

        // 只看分享赚商品信息
        if (Objects.nonNull(request.getDistributionGoodsAudit())
                && DistributionGoodsAudit.CHECKED.toValue()
                        == request.getDistributionGoodsAudit()) {
            request.setDistributionGoodsStatus(NumberUtils.INTEGER_ZERO);
        }
        if (!Boolean.TRUE.equals(request.getMagicSearch()) && TerminalSource.PC.equals(commonUtil.getTerminal())) {
            //非魔方组件的PC端查询 过滤周期购商品
            request.setIsBuyCycle(Constants.no);
        }
        request = wrapEsGoodsInfoQueryRequest(request);
        threadRrequest.set(request);
        return request;
    }

    @Override
    public EsGoodsInfoSimpleResponse getEsDataPage(EsGoodsInfoQueryRequest request) {
        EsGoodsInfoSimpleResponse response =
                esGoodsInfoElasticQueryProvider.skuPage(request).getContext();
        return response;
    }

    @Override
    public EsGoodsInfoSimpleResponse setStock(EsGoodsInfoSimpleResponse t, Long storeId) {
        syncStock(t);
        return syncLinkMallStock(t);
    }

    @Override
    public EsGoodsInfoSimpleResponse filter(EsGoodsInfoSimpleResponse t) {
        t.getEsGoodsInfoPage()
                .setContent(
                        filterDistributionGoods(t.getEsGoodsInfoPage().getContent(), threadRrequest.get()));
//        if (!systemPointsConfigService.isGoodsPoint()) {
//            t.getEsGoodsInfoPage().getContent().forEach(g -> g.getGoodsInfo().setBuyPoint(0L));
//        }
        return t;
    }

    @Override
    public EsGoodsInfoSimpleResponse setPrice(EsGoodsInfoSimpleResponse t) {

        List<EsGoodsInfoVO> esGoodsInfoList = t.getEsGoodsInfoPage().getContent();
        if (CollectionUtils.isNotEmpty(esGoodsInfoList)) {
            List<GoodsInfoDTO> goodsInfoList =
                    esGoodsInfoList.stream()
                            .filter(e -> Objects.nonNull(e.getGoodsInfo()))
                            .map(
                                    e -> {
                                        GoodsInfoDTO goodsInfoDTO =
                                                goodsInfoConvertMapper
                                                        .goodsInfoNestVOToGoodsInfoDTO(
                                                                e.getGoodsInfo());
                                        Integer enterPriseAuditStatus =
                                                e.getGoodsInfo().getEnterPriseAuditStatus();
                                        if (Objects.nonNull(enterPriseAuditStatus)) {
                                            goodsInfoDTO.setEnterPriseAuditState(
                                                    EnterpriseAuditState.CHECKED.toValue()
                                                                    == enterPriseAuditStatus
                                                            ? EnterpriseAuditState.CHECKED
                                                            : null);
                                        }
                                        return goodsInfoDTO;
                                    })
                            .collect(Collectors.toList());

            // 计算区间价
            //            GoodsIntervalPriceByCustomerIdRequest priceRequest =
            //                    new GoodsIntervalPriceByCustomerIdRequest();
            //            priceRequest.setGoodsInfoDTOList(goodsInfoList);
            //            if (Objects.nonNull(customer) &&
            // StringUtils.isNotBlank(customer.getCustomerId())) {
            //                priceRequest.setCustomerId(customer.getCustomerId());
            //            }
            //            GoodsIntervalPriceByCustomerIdResponse priceResponse =
            //
            // goodsIntervalPriceProvider.putByCustomerId(priceRequest).getContext();
            //            t.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
        }
        return t;
    }

    @Override
    public EsGoodsInfoSimpleResponse setMarketing(EsGoodsInfoSimpleResponse t, Long storeId) {
        List<GoodsInfoSimpleVO> goodsInfoSimpleVos = WmCollectionUtils
                .convert(t.getEsGoodsInfoPage().getContent().stream()
                        .filter(goodsInfo -> DeleteFlag.NO == goodsInfo.getGoodsInfo().getDelFlag()).collect(Collectors.toList()),
                        this::convertBean);
        GoodsListPluginRequest request = new GoodsListPluginRequest();
        request.setGoodsInfoPluginRequests(goodsInfoSimpleVos);
        String customerId = commonUtil.getOperatorId();
        if (StringUtils.isNotBlank(customerId)) {
            request.setCustomerId(customerId);
        }
        request.setTerminalSource(commonUtil.getTerminal().name());
        Map<String,List<MarketingPluginSimpleLabelVO>> skuMarketingLabelMap = getMarketingLabelMap(request, storeId);

        // 列表是否缺货，判断秒杀独立库存
        Map<String, Long> flashStockMap = this.getFlashStockMap(goodsInfoSimpleVos, skuMarketingLabelMap);
        if (MapUtils.isNotEmpty(skuMarketingLabelMap) || MapUtils.isNotEmpty(flashStockMap)) {
            t.getEsGoodsInfoPage().getContent().forEach(i -> {
                GoodsInfoNestVO goodsInfo = i.getGoodsInfo();
                goodsInfo.setMarketingPluginLabels(skuMarketingLabelMap.get(goodsInfo.getGoodsInfoId()));
                goodsInfo.setFlashStock(flashStockMap.get(goodsInfo.getGoodsInfoId()));
            });
        }
        Map<String, GoodsInfoSimpleVO> goodsInfoSimpleVOMap = goodsInfoSimpleVos.parallelStream()
                .collect(Collectors.toMap(GoodsInfoSimpleVO::getGoodsInfoId, Function.identity()));
        t.getEsGoodsInfoPage().getContent().forEach(i -> {
            GoodsInfoNestVO goodsInfo = i.getGoodsInfo();
            GoodsInfoSimpleVO goodsInfoSimpleVO = goodsInfoSimpleVOMap.get(goodsInfo.getGoodsInfoId());
            if (goodsInfoSimpleVO != null) {
                i.setIsBuyCycle(goodsInfoSimpleVO.getIsBuyCycle());
                goodsInfo.setMarketPrice(goodsInfoSimpleVO.getMarketPrice());
                goodsInfo.setIsBuyCycle(goodsInfoSimpleVO.getIsBuyCycle());
                goodsInfo.setBuyPoint(goodsInfoSimpleVO.getBuyPoint());
            }
        });
        return t;
    }

    @Override
    public EsGoodsInfoSimpleResponse syncPurchase(EsGoodsInfoSimpleResponse t) {

        //        if (!Objects.isNull(customer)) {
        //            // 填充购买数量
        //            List<String> goodsInfoIds =
        //                    t.getEsGoodsInfoPage().getContent().stream()
        //                            .map(i -> i.getGoodsInfo().getGoodsInfoId())
        //                            .collect(Collectors.toList());
        //            Map<String, Long> skuNumMap =
        //                    purchaseQueryProvider
        //                            .query(
        //                                    PurchaseQueryRequest.builder()
        //                                            .goodsInfoIds(goodsInfoIds)
        //                                            .customerId(customer.getCustomerId())
        //                                            .inviteeId(commonUtil.getPurchaseInviteeId())
        //
        // .marketingId(this.thisRequest.getMarketingId())
        //                                            .build())
        //                            .getContext()
        //                            .getPurchaseList()
        //                            .stream()
        //                            .collect(
        //                                    Collectors.toMap(
        //                                            PurchaseVO::getGoodsInfoId,
        // PurchaseVO::getGoodsNum));
        //            t.getEsGoodsInfoPage()
        //                    .getContent()
        //                    .forEach(
        //                            i -> {
        //                                Long num =
        // skuNumMap.get(i.getGoodsInfo().getGoodsInfoId());
        //                                if (!Objects.isNull(num)) {
        //                                    i.getGoodsInfo().setBuyCount(num);
        //                                } else {
        //                                    i.getGoodsInfo().setBuyCount(NumberUtils.LONG_ZERO);
        //                                }
        //                            });
        //        }
        t.getEsGoodsInfoPage()
                .getContent()
                .forEach(i -> i.getGoodsInfo().setBuyCount(NumberUtils.LONG_ZERO));
        return t;
    }

    @Override
    public EsGoodsInfoSimpleResponse afterProcess(EsGoodsInfoSimpleResponse t, Long storeId) {
        EsGoodsInfoSimpleResponse response =  setStartSaleNum(t, storeId);
        goodsBaseService.setBindFlag(response.getEsGoodsInfoPage());
        threadRrequest.remove();
        return response;
    }

    @Override
    public boolean isEmpty(EsGoodsInfoSimpleResponse t) {
        if(t.getEsGoodsInfoPage().isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * 包装EsGoodsInfoQueryRequest搜索对象
     *
     * @param queryRequest
     * @return
     */
    protected EsGoodsInfoQueryRequest wrapEsGoodsInfoQueryRequest(
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
     * 同步linkMall库存
     *
     * @param response
     */
    private EsGoodsInfoSimpleResponse syncLinkMallStock(EsGoodsInfoSimpleResponse response) {
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
                                    LinkedMallStockGetRequest.builder()
                                            .providerGoodsIds(itemIds)
                                            .build())
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
        return response;
    }

    protected EsGoodsInfoSimpleResponse syncStock(EsGoodsInfoSimpleResponse response) {
        List<String> ids =
                response.getEsGoodsInfoPage().getContent().stream()
                        .map(
                                t -> {
                                    if (StringUtils.isNotEmpty(
                                            t.getGoodsInfo().getProviderGoodsInfoId())) {
                                        return t.getGoodsInfo().getProviderGoodsInfoId();
                                    } else {
                                        return t.getGoodsInfo().getGoodsInfoId();
                                    }
                                })
                        .distinct()
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ids)) {
            Map<String, Long> skuStockMap =
                    goodsInfoQueryProvider
                            .getStockByGoodsInfoIds(
                                    GoodsInfoListByIdsRequest.builder().goodsInfoIds(ids).build())
                            .getContext();
            if (MapUtils.isNotEmpty(skuStockMap)) {
                response.getEsGoodsInfoPage().getContent()
                        .forEach(
                                g -> {
                                    Long stock = 0L;
                                    if (StringUtils.isNotEmpty(
                                            g.getGoodsInfo().getProviderGoodsInfoId())) {
                                        stock =
                                                skuStockMap.get(
                                                        g.getGoodsInfo().getProviderGoodsInfoId());
                                    } else {
                                        stock = skuStockMap.get(g.getGoodsInfo().getGoodsInfoId());
                                    }
                                    g.getGoodsInfo().setStock(stock);
                                    if (stock == null || stock < 1) {
                                        g.getGoodsInfo().setGoodsStatus(GoodsStatus.OUT_STOCK);
                                    }
                                });
            }
        }
        return response;
    }

    protected GoodsInfoSimpleVO convertBean(EsGoodsInfoVO bean) {
        GoodsInfoSimpleVO simpleVO =
                goodsInfoConvertMapper.goodsInfoNestVOToGoodsInfoSimpleVO(bean.getGoodsInfo());
        Integer isBuyCycle = bean.getGoodsInfo().getIsBuyCycle();
        simpleVO.setStoreCateIds(bean.getStoreCateIds());
        simpleVO.setIsBuyCycle(isBuyCycle);
        simpleVO.setEnterPriseAuditState(
                EnterpriseAuditState.values()[bean.getGoodsInfo().getEnterPriseAuditStatus()]);
        if (simpleVO.getPriceType() == GoodsPriceType.STOCK.toValue()) {
            simpleVO.setSalePrice(simpleVO.getIntervalMinPrice());
            simpleVO.setMarketPrice(simpleVO.getIntervalMinPrice());
        }
        if (Constants.yes.equals(isBuyCycle)) {
            BuyCycleGoodsInfoVO buyCycleGoodsInfoVO = buyCycleGoodsInfoQueryProvider.getById(BuyCycleGoodsInfoByGoodsIdRequest.builder()
                    .goodsInfoId(simpleVO.getGoodsInfoId())
                    .build()).getContext().getBuyCycleGoodsInfoVO();
            if (buyCycleGoodsInfoVO != null) {
                simpleVO.setSalePrice(simpleVO.getIntervalMinPrice());
                simpleVO.setMarketPrice(buyCycleGoodsInfoVO.getCyclePrice());
                simpleVO.setBuyPoint(BigDecimal.ZERO.longValue());
            }
        }
        return simpleVO;
    }

    /***
     * 调用营销查询插件
     * 查询商品列表关联营销Label
     * @param request
     * @return
     */
    protected Map<String,List<MarketingPluginSimpleLabelVO>> getMarketingLabelMap(GoodsListPluginRequest request,
                                                                                  Long storeId){
        Map<String,List<MarketingPluginSimpleLabelVO>> labelMap = BaseResUtils.getResultFromRes(newMarketingPluginProvider
                .goodsListPlugin(request), GoodsListPluginResponse::getSkuMarketingLabelMap);
        return Optional.ofNullable(labelMap).orElseGet(()-> Maps.newHashMap());
    }

    /**
     * 过滤符合条件的分销商品数据
     *
     * @param esGoodsInfoVOS
     * @param queryRequest
     * @return
     */
    private List<EsGoodsInfoVO> filterDistributionGoods(List<EsGoodsInfoVO> esGoodsInfoVOS,
                                                        EsGoodsInfoQueryRequest queryRequest) {
        // 根据开关重新设置分销商品标识
        // 需要叠加访问端Pc\app不体现分销业务
        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        //查询积分是否商品抵扣
        Boolean isGoodsPoint = systemPointsConfigService.isGoodsPoint();
        distributionGoodsStatus(esGoodsInfoVOS, openFlag, isGoodsPoint);
        esGoodsInfoVOS = justFetchDistributionGoods(esGoodsInfoVOS, queryRequest);
        setDistributionGoodsCommission(esGoodsInfoVOS, openFlag);
        return esGoodsInfoVOS;
    }

    /**
     * 设置分销商品佣金
     **/
    private void setDistributionGoodsCommission(List<EsGoodsInfoVO> esGoodsInfoVOS, DefaultFlag openFlag) {
        // 修改之前获取所有商品id的方法, 有几率性会使时间复杂度低于之前的时间复杂度
        boolean hasCheckedDistributionGoods = isHasCheckedDistributionGoods(esGoodsInfoVOS);

        String customerId = commonUtil.getOperator().getUserId();
        if (openFlag.equals(DefaultFlag.YES)
                && hasCheckedDistributionGoods
                && StringUtils.isNotEmpty(customerId)) {

            // 提取多个变量到循环外, 减少空间复杂度
            BaseResponse<DistributorLevelByCustomerIdResponse> baseResponse =
                    distributorLevelQueryProvider.getByCustomerId(
                            new DistributorLevelByCustomerIdRequest(customerId));
            DistributorLevelVO distributorLevelVO =
                    baseResponse.getContext().getDistributorLevelVO();

            if(Objects.nonNull(distributorLevelVO) && Objects.nonNull(distributorLevelVO.getCommissionRate())) {
                BigDecimal commissionRate = distributorLevelVO.getCommissionRate();
                esGoodsInfoVOS
                        .forEach(
                                esGoodsInfoVO -> {
                                    BigDecimal distributionCommission =
                                            esGoodsInfoVO
                                                    .getGoodsInfo()
                                                    .getDistributionCommission();
                                    if (Objects.nonNull(distributionCommission)
                                            && DistributionGoodsAudit.CHECKED
                                            == esGoodsInfoVO
                                            .getGoodsInfo()
                                            .getDistributionGoodsAudit()) {

                                        distributionCommission =
                                                DistributionCommissionUtils.calDistributionCommission(
                                                        distributionCommission, commissionRate);
                                        esGoodsInfoVO
                                                .getGoodsInfo()
                                                .setDistributionCommission(distributionCommission);
                                    }
                                });
            }
        }
    }

    /**
     * 判断是否有审核通过的分销商品
     **/
    private boolean isHasCheckedDistributionGoods(List<EsGoodsInfoVO> esGoodsInfoVOS) {
        return esGoodsInfoVOS.stream().anyMatch(esGoodsInfoVO ->
                        DistributionGoodsAudit.CHECKED.equals(
                                esGoodsInfoVO
                                        .getGoodsInfo()
                                        .getDistributionGoodsAudit()));
    }

    /**
     * 只查看分销商品
     **/
    private List<EsGoodsInfoVO> justFetchDistributionGoods(List<EsGoodsInfoVO> esGoodsInfoVOS,
                                                  EsGoodsInfoQueryRequest queryRequest) {
        // 只看分享赚商品信息
        if (Objects.nonNull(queryRequest.getDistributionGoodsAudit())
                && DistributionGoodsAudit.CHECKED.toValue()
                        == queryRequest.getDistributionGoodsAudit()) {
            esGoodsInfoVOS =
                    esGoodsInfoVOS.stream()
                            .filter(
                                    esGoodsInfoVO ->
                                            DistributionGoodsAudit.CHECKED.equals(
                                                    esGoodsInfoVO
                                                            .getGoodsInfo()
                                                            .getDistributionGoodsAudit()))
                            .collect(Collectors.toList());
        }
        return esGoodsInfoVOS;
    }

    /**
     * 设值分销商品状态
     **/
    private void distributionGoodsStatus(List<EsGoodsInfoVO> esGoodsInfoVOS, DefaultFlag openFlag,
                                        Boolean isGoodsPoint) {
        Map<String, DefaultFlag> map = new HashMap<>();
        esGoodsInfoVOS.forEach(
                esGoodsInfoVO -> {
                    Boolean distributionFlag =
                            // 平台未开启社交分销
                            DefaultFlag.NO.equals(openFlag)
                                    // 当前店铺未开启社交分销
                                    || DefaultFlag.NO.equals(this.getStoreFlag(map,
                                    String.valueOf(esGoodsInfoVO.getGoodsInfo().getStoreId())));
                    if(!isGoodsPoint) {
                        esGoodsInfoVO.getGoodsInfo().setBuyPoint(0L);
                    }
                    // 排除积分价商品
                    Boolean pointsFlag = Objects.nonNull(esGoodsInfoVO.getGoodsInfo().getBuyPoint())
                            && (esGoodsInfoVO.getGoodsInfo().getBuyPoint().compareTo(0L) != 0);
                    if (distributionFlag || pointsFlag) {
                        esGoodsInfoVO.getGoodsInfo().setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                    }
                });
    }

    /**
     * 空间换时间, 从java内存获取状态, 如果无法获取则从redis获取, 再者从数据库获取
     **/
    private DefaultFlag getStoreFlag(Map<String,DefaultFlag> map, String storeId) {
        if(map.containsKey(storeId)) {
            return map.get(storeId);
        } else {
            DefaultFlag defaultFlag = distributionCacheService.queryStoreOpenFlag(storeId);
            map.put(storeId, defaultFlag);
            return defaultFlag;
        }
    }

    private EsGoodsInfoSimpleResponse setStartSaleNum(EsGoodsInfoSimpleResponse t, Long storeId) {
        // 限售起订量查询

        List<String> goodsInfoIds = new ArrayList<>();
//        List<String> flashSaleSkuIds = new ArrayList<>();
        t.getEsGoodsInfoPage().getContent().stream().forEach(g -> {
            goodsInfoIds.add(g.getGoodsInfo().getGoodsInfoId());
//            if (CollectionUtils.isNotEmpty(g.getGoodsInfo().getMarketingPluginLabels())
//                    && g.getGoodsInfo().getMarketingPluginLabels()
//                                       .stream()
//                                       .anyMatch(m -> m.getMarketingType()
//                                               .equals(MarketingPluginType.FLASH_SALE.getId()) ||
//                                               m.getMarketingType()
//                                                       .equals(MarketingPluginType.FLASH_PROMOTION.getId()))) {
//                flashSaleSkuIds.add(g.getGoodsInfo().getGoodsInfoId());
//            }
        });
        // 2. 查询具有限售配置的商品列表
        List<GoodsRestrictedSaleVO> restrictedSaleGoods = goodsRestrictedSaleQueryProvider
                                        .list(GoodsRestrictedSaleListRequest.builder()
                                        .delFlag(DeleteFlag.NO)
                                        .storeId(storeId)
                                        .goodsInfoIds(goodsInfoIds)
                                        .build())
                        .getContext()
                        .getGoodsRestrictedSaleVOList();
        Map<String, Long> skuStartSaleNumMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(restrictedSaleGoods)) {
            // 2.1 填充命中的限售配置商品起售数量
            restrictedSaleGoods.forEach(
                    restrictedSaleGood -> {
                        Long startSaleNum = restrictedSaleGood.getStartSaleNum();
                        skuStartSaleNumMap.put(
                                restrictedSaleGood.getGoodsInfoId(),
                                Objects.isNull(startSaleNum) ? 1L : startSaleNum);
                    });
        }

        // 秒杀的覆盖一般商品
//        if (!TerminalSource.PC.equals(commonUtil.getTerminal())
//                && CollectionUtils.isNotEmpty(flashSaleSkuIds)) {
//            // 1. 查询正在进行的秒杀活动商品列表
//            List<FlashSaleGoodsVO> flashSaleGoods =
//                    flashSaleGoodsQueryProvider
//                            .list(
//                                    FlashSaleGoodsListRequest.builder()
//                                            .goodsinfoIds(goodsInfoIds)
//                                            .delFlag(DeleteFlag.NO)
//                                            .queryDataType(1)
//                                            .build())
//                            .getContext()
//                            .getFlashSaleGoodsVOList();
//            if (CollectionUtils.isNotEmpty(flashSaleGoods)) {
//                // 1.1 填充命中的秒杀商品起售数量
//                flashSaleGoods.forEach(
//                        flashSaleGood -> {
//                            Integer minNum = flashSaleGood.getMinNum();
//                            skuStartSaleNumMap.put(
//                                    flashSaleGood.getGoodsInfoId(),
//                                    Objects.isNull(minNum) ? 1L : (long) minNum);
//                        });
//            }
//        }

        t.getEsGoodsInfoPage().getContent().stream()
                .forEach(
                        g -> {
                            Long startSaleNum =
                                    skuStartSaleNumMap.get(g.getGoodsInfo().getGoodsInfoId());
                            g.getGoodsInfo()
                                    .setStartSaleNum(startSaleNum == null ? 1 : startSaleNum);
                        });
        return t;
    }

    /**
     * 查询秒杀、限时抢购库存
     * @param goodsInfoSimpleVOList
     * @return
     */
    private Map<String,Long> getFlashStockMap(List<GoodsInfoSimpleVO> goodsInfoSimpleVOList, Map<String,
            List<MarketingPluginSimpleLabelVO>> skuMarketingLabelMap){
        //取库存为0的skuId
        List<String> skuIdList = goodsInfoSimpleVOList.stream()
                .filter(goodsInfoSimpleVO -> Objects.isNull(goodsInfoSimpleVO.getStock())
                        || Objects.equals(goodsInfoSimpleVO.getStock(), NumberUtils.LONG_ZERO)
                        //库存小于起订数量
                        || (Objects.nonNull(goodsInfoSimpleVO.getCount()) && goodsInfoSimpleVO.getStock() > 0
                        && goodsInfoSimpleVO.getStock() < goodsInfoSimpleVO.getCount()))
                .map(GoodsInfoSimpleVO::getGoodsInfoId)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(skuIdList)){
            return Collections.emptyMap();
        }

        // 限时抢购
        List<Long> ids = new ArrayList<>();
        List<FlashSaleGoodsVO> flashSaleGoodsVOList1 = new ArrayList<>();
        skuIdList.forEach(s -> {
            List<MarketingPluginSimpleLabelVO> marketingPluginSimpleLabelVOS = skuMarketingLabelMap.getOrDefault(s,
                    new ArrayList<>());
            Optional<MarketingPluginSimpleLabelVO> marketingPluginSimpleLabelVO =
                    marketingPluginSimpleLabelVOS.stream().filter(a -> a.getMarketingType() == MarketingPluginType.FLASH_PROMOTION.getId()).findFirst();
            marketingPluginSimpleLabelVO.ifPresent(a -> ids.add(NumberUtils.toLong(Objects.toString(a.getMarketingId()))));
        });

        if (CollectionUtils.isNotEmpty(ids)) {
            FlashSaleGoodsListResponse flashSaleGoodsListResponse =
                    flashSaleGoodsQueryProvider.list(FlashSaleGoodsListRequest.builder().idList(ids).type(Constants.ONE).build()).getContext();
            flashSaleGoodsVOList1 = flashSaleGoodsListResponse.getFlashSaleGoodsVOList();
        }

        Map<String, Long> skuStockMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(flashSaleGoodsVOList1)) {
            skuStockMap = flashSaleGoodsVOList1.stream().collect(Collectors.toMap(FlashSaleGoodsVO::getGoodsInfoId,
                    flashSaleGoodsVO -> {
                Integer stock = flashSaleGoodsVO.getStock();
                Long salesVolume = flashSaleGoodsVO.getSalesVolume();
                long flashStock = stock - salesVolume;
                return flashStock > 0L && flashStock >= flashSaleGoodsVO.getMinNum() ? flashStock : 0L;
            }));
        }
        // 限时抢购

        LocalDateTime begin = LocalDateTime.now();
        LocalDateTime end = begin.minus(Constants.FLASH_SALE_LAST_HOUR, ChronoUnit.HOURS);
        IsInProgressRequest progressRequest = IsInProgressRequest.builder()
                .begin(begin)
                .end(end)
                .goodsInfoIdList(skuIdList)
                .build();
        IsInProgressResp progressResp = flashSaleGoodsQueryProvider.isInProgressBySkuList(progressRequest).getContext();
        List<FlashSaleGoodsVO> flashSaleGoodsVOList = progressResp.getFlashSaleGoodsVOS();
        if(CollectionUtils.isNotEmpty(flashSaleGoodsVOList)){
            skuStockMap.putAll(flashSaleGoodsVOList.stream()
                    .collect(Collectors.toMap(FlashSaleGoodsVO::getGoodsInfoId, flashSaleGoodsVO -> {
                        Integer stock = flashSaleGoodsVO.getStock();
                        Long salesVolume = flashSaleGoodsVO.getSalesVolume();
                        long flashStock = stock - salesVolume;
                        return flashStock > 0L && flashStock >= flashSaleGoodsVO.getMinNum() ? flashStock : 0L;
                    })));
        }
        return skuStockMap;
    }

    private void syncVopStock(EsGoodsInfoSimpleResponse response){
        List<EsGoodsInfoVO> esGoodsInfoVOList = response.getEsGoodsInfoPage().getContent();
        if(CollectionUtils.isEmpty(esGoodsInfoVOList)){
            return;
        }
        List<ChannelGoodsInfoDTO> channelGoodsInfoList = KsBeanUtil.convert(esGoodsInfoVOList, ChannelGoodsInfoDTO.class);
        ChannelGoodsStatusGetRequest getRequest =
                ChannelGoodsStatusGetRequest.builder()
                        .goodsInfoList(channelGoodsInfoList)
                        .build();
        ChannelGoodsStatusGetResponse getResponse =
                channelGoodsProvider.getGoodsStatus(getRequest).getContext();
    }

}
