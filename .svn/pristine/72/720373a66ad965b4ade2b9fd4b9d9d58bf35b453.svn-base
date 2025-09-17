package com.wanmi.sbc.goods.service.detail.imp;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DistributionCommissionUtils;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributorLevelByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.DistributionBindStateRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelListRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.customer.bean.enums.LedgerBindState;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.customer.bean.vo.StoreCacheVO;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoSiteQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoByGoodsIdRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsListRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleListRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoSimpleDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.enums.SaleType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.service.detail.GoodsDetailInterface;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.MarketingPluginPreRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.setting.api.provider.flashsalesetting.FlashSaleSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.flashsalesetting.FlashSaleSettingListRequest;
import com.wanmi.sbc.setting.api.response.flashsalesetting.FlashSaleSettingListResponse;
import com.wanmi.sbc.third.goods.ThirdPlatformGoodsService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.LaKaLaUtils;
import com.wanmi.sbc.util.MarketingConvertMapper;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className GoodsInfoDetailService
 * @description TODO
 * @date 2021/10/29 5:42 下午
 */
@Service
public class GoodsInfoDetailService
        implements GoodsDetailInterface<GoodsInfoSimpleDetailByGoodsInfoResponse> {

    @Autowired CommonUtil commonUtil;

    @Autowired GoodsInfoSiteQueryProvider goodsInfoSiteQueryProvider;

    @Autowired private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired private MarketingConvertMapper marketingConvertMapper;

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Autowired private ThirdPlatformGoodsService thirdPlatformGoodsService;

    @Autowired private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired private DistributionCacheService distributionCacheService;

    @Autowired private DistributorLevelQueryProvider distributorLevelQueryProvider;

    @Autowired private RedisUtil redisService;

    @Autowired private FlashSaleSettingQueryProvider flashSaleSettingQueryProvider;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired private BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;

    @Autowired
    private LaKaLaUtils laKaLaUtils;

    @Override
    public GoodsInfoSimpleDetailByGoodsInfoResponse getData(String skuId) {
        String customerId = commonUtil.getOperatorId();
        GoodsInfoRequest goodsInfoRequest = new GoodsInfoRequest();
        goodsInfoRequest.setGoodsInfoId(skuId);
        goodsInfoRequest.setShowLabelFlag(Boolean.TRUE);
        goodsInfoRequest.setShowSiteLabelFlag(Boolean.TRUE);
        if (StringUtils.isNotEmpty(customerId)) {
            goodsInfoRequest.setCustomerId(customerId);
        }
        GoodsInfoSimpleDetailByGoodsInfoResponse response =
                goodsInfoSiteQueryProvider.getSimpleByGoodsInfo(goodsInfoRequest).getContext();
        if (response == null ||
                TerminalSource.PC.equals(commonUtil.getTerminal()) && Constants.yes.equals(response.getGoodsInfo().getIsBuyCycle())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        return response;
    }

    @Override
    public GoodsInfoSimpleDetailByGoodsInfoResponse setStock(
            GoodsInfoSimpleDetailByGoodsInfoResponse t, Long storeId) {
        if (ThirdPlatformType.LINKED_MALL.equals(t.getGoodsInfo().getThirdPlatformType())) {
            List<LinkedMallStockVO> stocks =
                    linkedMallStockQueryProvider
                            .batchGoodsStockByDivisionCode(
                                    LinkedMallStockGetRequest.builder()
                                            .providerGoodsIds(
                                                    Collections.singletonList(
                                                            Long.valueOf(
                                                                    t.getGoodsInfo()
                                                                            .getThirdPlatformSpuId())))
                                            .build())
                            .getContext();
            if (CollectionUtils.isNotEmpty(stocks)) {
                Optional<Long> skuStock =
                        stocks.get(0).getSkuList().stream()
                                .filter(
                                        s ->
                                                s.getSkuId()
                                                        .equals(
                                                                t.getGoodsInfo()
                                                                        .getThirdPlatformSkuId()))
                                .map(LinkedMallStockVO.SkuStock::getStock)
                                .findFirst();
                if (skuStock.isPresent()) {

                    t.getGoodsInfo().setStock(skuStock.get());
                    if (!GoodsStatus.INVALID.equals(t.getGoodsInfo().getGoodsStatus())) {
                        t.getGoodsInfo()
                                .setGoodsStatus(
                                        skuStock.get() > 0
                                                ? GoodsStatus.OK
                                                : GoodsStatus.OUT_STOCK);
                    }
                }
            }
        }

        return t;
    }

    @Override
    public GoodsInfoSimpleDetailByGoodsInfoResponse filter(
            GoodsInfoSimpleDetailByGoodsInfoResponse t, PlatformAddress address) {
        filterDistributionGoods(t);
        // 验证渠道商品
        thirdPlatformGoodsService.verifyChannelGoodsInfoSimple(t.getGoodsInfo(), address);
        return t;
    }

    @Override
    public GoodsInfoSimpleDetailByGoodsInfoResponse setMarketing(
            GoodsInfoSimpleDetailByGoodsInfoResponse t, Long storeId) {

        GoodsInfoPluginRequest goodsInfoPluginRequest = GoodsInfoPluginRequest.builder()
                .goodsInfoPluginRequest(t.getGoodsInfo())
                .storeId(storeId)
                .build();
        String  customerId = commonUtil.getOperatorId();
        goodsInfoPluginRequest.setTerminalSource(commonUtil.getTerminal().name());
        if ( StringUtils.isNotBlank(customerId)) {
            goodsInfoPluginRequest.setCustomerId(customerId);

        }
        GoodsInfoDetailPluginResponse pluginResponse =
                newMarketingPluginProvider
                        .goodsInfoDetailPlugin(goodsInfoPluginRequest)
                        .getContext();
        if (pluginResponse != null) {
            t.getGoodsInfo().setMarketingPluginLabels(pluginResponse.getMarketingLabels());
            t.getGoodsInfo().setPluginPrice(pluginResponse.getPluginPrice());
            t.getGoodsInfo().setGoodsStatus(pluginResponse.getGoodsStatus());
        }


        //查询秒杀预热时间
        String redisTime = redisService.getString(CacheKeyConstant.FLASH_PRE_TIME);
        Long preTime = 0L;

        if(Objects.nonNull(redisTime)){
            preTime = Long.valueOf(redisTime);
        }else{
            FlashSaleSettingListRequest flashSaleSettingListRequest = FlashSaleSettingListRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .build();
            FlashSaleSettingListResponse flashSaleSettingListResponse
                    = flashSaleSettingQueryProvider.list(flashSaleSettingListRequest).getContext();
            if(Objects.nonNull(flashSaleSettingListResponse)
                    && CollectionUtils.isNotEmpty(flashSaleSettingListResponse.getFlashSaleSettingVOList())){
                Integer pre = flashSaleSettingListResponse.getFlashSaleSettingVOList().get(0).getPreTime();
                if(Objects.nonNull(pre)){
                    preTime = Long.valueOf(pre);
                }
                redisService.setString(CacheKeyConstant.FLASH_PRE_TIME, String.valueOf(preTime));
                redisService.expireByMinutes(CacheKeyConstant.FLASH_PRE_TIME,24*60L);
            }
        }

        //查询预热活动
        MarketingPluginPreRequest marketingPluginPreRequest = MarketingPluginPreRequest.builder()
                .goodsInfoId(t.getGoodsInfo().getGoodsInfoId())
                .preTime(preTime)
                .build();

        GoodsInfoDetailPluginResponse preResponse =
                newMarketingPluginProvider
                        .goodsInfoDetailPrePlugin(marketingPluginPreRequest)
                        .getContext();

        List<GoodsInfoMarketingCacheDTO> preMarketing = preResponse.getPreMarketings();
        MarketingPluginLabelVO marketingPluginLabelVO = new MarketingPluginLabelVO();

        //处理优先级 最新开始的 秒杀 = 限时抢购 > 拼团
        if(CollectionUtils.isNotEmpty(preMarketing)){
            Comparator<GoodsInfoMarketingCacheDTO> timeSort = Comparator.comparing(GoodsInfoMarketingCacheDTO::getBeginTime);
            Comparator<GoodsInfoMarketingCacheDTO> typeSort = Comparator.comparing(GoodsInfoMarketingCacheDTO::getMarketingPluginType);
            preMarketing.sort(timeSort.thenComparing(typeSort));

            GoodsInfoMarketingCacheDTO marketingCacheDTO = preMarketing.get(0);
            marketingPluginLabelVO.setMarketingId(marketingCacheDTO.getId());
            marketingPluginLabelVO.setStartTime(marketingCacheDTO.getBeginTime());
            marketingPluginLabelVO.setEndTime(marketingCacheDTO.getEndTime());
            marketingPluginLabelVO.setPluginPrice(marketingCacheDTO.getPrice());
            marketingPluginLabelVO.setMarketingType(marketingCacheDTO.getMarketingPluginType().getId());
            marketingPluginLabelVO.setGrouponNum(marketingCacheDTO.getGrouponNum());
        }

        t.getGoodsInfo().setPreMarketing(marketingPluginLabelVO);

        if (t.getGoods().getSaleType().equals(SaleType.WHOLESALE.toValue())
                && CollectionUtils.isNotEmpty(t.getWholesaleSkus())) {
            List<GoodsInfoSimpleVO> goodsInfoSimpleVOS = t.getWholesaleSkus();
            GoodsListPluginRequest request = new GoodsListPluginRequest();
            request.setGoodsInfoPluginRequests(goodsInfoSimpleVOS);
            if (StringUtils.isNotBlank(customerId)) {
                request.setCustomerId(customerId);
            }
            request.setTerminalSource(commonUtil.getTerminal().name());
            request.setStoreId(storeId);
            GoodsListPluginResponse response =
                    newMarketingPluginProvider.goodsListPlugin(request).getContext();
            if (response != null && MapUtils.isNotEmpty(response.getSkuMarketingLabelMap())) {
                t.getWholesaleSkus().stream()
                        .forEach(
                                i ->
                                        i.setMarketingPluginLabels(
                                                marketingConvertMapper.simplLabelToLabel(
                                                        response.getSkuMarketingLabelMap()
                                                                .get(i.getGoodsInfoId()))));
            }
        }
        Integer isBuyCycle = t.getGoodsInfo().getIsBuyCycle();
//        Boolean isStoreBag =
//                distributionSettingQueryProvider
//                        .isStoreBag(t.getGoodsInfo().getGoodsInfoId())
//                        .getContext();
        if (Objects.equals(isBuyCycle,Constants.yes)) {
            BuyCycleGoodsInfoVO buyCycleGoodsInfoVO = buyCycleGoodsInfoQueryProvider.getById(BuyCycleGoodsInfoByGoodsIdRequest.builder()
                    .goodsInfoId(t.getGoodsInfo().getGoodsInfoId())
                    .build()).getContext().getBuyCycleGoodsInfoVO();
            //如果是周期购商品，则设置市场价为周期购的每期价格
            if (Objects.nonNull(buyCycleGoodsInfoVO)) {
                t.getGoodsInfo().setMarketPrice(buyCycleGoodsInfoVO.getCyclePrice());
                t.getGoodsInfo().setBuyPoint(BigDecimal.ZERO.longValue());
            }
        }
        return t;
    }

    @Override
    public GoodsInfoSimpleDetailByGoodsInfoResponse afterProcess(
            GoodsInfoSimpleDetailByGoodsInfoResponse t, Long storeId) {

        if (Objects.nonNull(t.getGoods().getStoreId())) {
            StoreCacheVO store =
                    storeQueryProvider
                            .getCacheNoDeleteStoreById(
                                    new NoDeleteStoreByIdRequest(t.getGoods().getStoreId()))
                            .getContext()
                            .getStoreVO();
            t.setStoreLogo(store.getStoreLogo());
            t.setStoreName(store.getStoreName());
            t.setCompanyType(store.getCompanyType());
            t.setStoreId(store.getStoreId());
        }

        //还需要拉卡拉开启状态
        PayGatewayVO payGatewayVO = laKaLaUtils.getPayGatewayVO();
        boolean isOpen = IsOpen.YES.equals(Objects.nonNull(payGatewayVO) ? payGatewayVO.getIsOpen() : IsOpen.NO);
        if (isOpen) {
//            StoreVO storeVO = storeQueryProvider.getById(StoreByIdRequest.builder()
//                    .storeId(t.getGoodsInfo().getStoreId())
//                    .build()).getContext().getStoreVO();
            List<LedgerReceiverRelVO> ledgerReceiverRelVOList = ledgerReceiverRelQueryProvider.list(LedgerReceiverRelListRequest.builder()
                    .supplierId(t.getGoodsInfo().getCompanyInfoId())
                    .receiverId(commonUtil.getOperatorId())
                    .build()).getContext().getLedgerReceiverRelVOList();
            if (CollectionUtils.isEmpty(ledgerReceiverRelVOList)) {
                t.setBindState(LedgerBindState.UNBOUND.toValue());
            } else {
                Integer bindState = ledgerReceiverRelVOList.get(0).getBindState();
                t.setBindState(bindState);
            }
        }
        setStartSaleNum(t, storeId);
        setFlashSaleGoodsStock(t);
        setBindFlag(t);
        return t;
    }

    private GoodsInfoSimpleDetailByGoodsInfoResponse setStartSaleNum(
            GoodsInfoSimpleDetailByGoodsInfoResponse t, Long storeId) {

        // 限售起订量查询
        List<String> goodsInfoIds = new ArrayList<>();
        List<String> flashSaleSkuIds = new ArrayList<>();
        if (t.getGoods().getSaleType().equals(SaleType.WHOLESALE.toValue())) {
            t.getWholesaleSkus().stream().forEach(g -> {
                goodsInfoIds.add(g.getGoodsInfoId());
                if (CollectionUtils.isNotEmpty(g.getMarketingPluginLabels())
                        && g.getMarketingPluginLabels().stream()
                        .anyMatch(m ->m.getMarketingType().equals(MarketingPluginType.FLASH_SALE.getId()) ||
                                m.getMarketingType().equals(MarketingPluginType.FLASH_PROMOTION.getId()))) {
                    flashSaleSkuIds.add(g.getGoodsInfoId());
                }
            });
        } else {
            goodsInfoIds.add(t.getGoodsInfo().getGoodsInfoId());
            if (CollectionUtils.isNotEmpty(t.getGoodsInfo().getMarketingPluginLabels())
                    && t.getGoodsInfo().getMarketingPluginLabels().stream()
                    .anyMatch(m -> m.getMarketingType().equals(MarketingPluginType.FLASH_SALE.getId()) ||
                            m.getMarketingType().equals(MarketingPluginType.FLASH_PROMOTION.getId()))) {
                flashSaleSkuIds.add(t.getGoodsInfo().getGoodsInfoId());
            }
        }
        // 2. 查询具有限售配置的商品列表
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            List<GoodsRestrictedSaleVO> restrictedSaleGoods = goodsRestrictedSaleQueryProvider.list(
                    GoodsRestrictedSaleListRequest.builder()
                            .delFlag(DeleteFlag.NO)
                            .goodsInfoIds(goodsInfoIds)
                            .storeId(storeId)
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
            if (!TerminalSource.PC.equals(commonUtil.getTerminal())
                    && CollectionUtils.isNotEmpty(flashSaleSkuIds)) {
                // 1. 查询正在进行的秒杀活动商品列表
                List<FlashSaleGoodsVO> flashSaleGoods =
                        flashSaleGoodsQueryProvider
                                .list(
                                        FlashSaleGoodsListRequest.builder()
                                                .goodsinfoIds(goodsInfoIds)
                                                .delFlag(DeleteFlag.NO)
                                                .queryDataType(1)
                                                .build())
                                .getContext()
                                .getFlashSaleGoodsVOList();
                if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(flashSaleGoods)) {
                    // 1.1 填充命中的秒杀商品起售数量
                    flashSaleGoods.forEach(
                            flashSaleGood -> {
                                Integer minNum = flashSaleGood.getMinNum();
                                skuStartSaleNumMap.put(
                                        flashSaleGood.getGoodsInfoId(),
                                        Objects.isNull(minNum) ? 1L : (long) minNum);
                            });
                }
            }
            Long startSaleNum = skuStartSaleNumMap.get(t.getGoodsInfo().getGoodsInfoId());
            t.getGoodsInfo().setStartSaleNum(startSaleNum == null ? 1 : startSaleNum);
            if (t.getGoods().getSaleType().equals(SaleType.WHOLESALE.toValue())) {
                t.getWholesaleSkus().stream()
                        .forEach(
                                g -> {
                                    Long num = skuStartSaleNumMap.get(g.getGoodsInfoId());
                                    g.setStartSaleNum(num == null ? 1 : num);
                                });
            }
        }

        return t;
    }

    /**
     * 过滤符合条件的分销商品数据
     *
     * @param response
     * @return
     */
    private GoodsInfoSimpleDetailByGoodsInfoResponse filterDistributionGoods(
            GoodsInfoSimpleDetailByGoodsInfoResponse response) {
        // 根据开关重新设置分销商品标识
        // 需要叠加访问端Pc\app不体现分销业务
        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        String customerId = commonUtil.getOperatorId();
        Boolean distributionFlag =
//                Objects.equals(
//                                ChannelType.PC_MALL,
//                                commonUtil.getDistributeChannel().getChannelType())
//                        ||
                        DefaultFlag.NO.equals(openFlag)
                        || DefaultFlag.NO.equals(
                                distributionCacheService.queryStoreOpenFlag(
                                        String.valueOf(response.getGoodsInfo().getStoreId())))
                //        || customer == null
                ;

        if (distributionFlag) {
            response.getGoodsInfo().setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
            if (response.getGoods().getSaleType().equals(SaleType.WHOLESALE.toValue())) {
                response.getWholesaleSkus().stream()
                        .forEach(
                                w ->
                                        w.setDistributionGoodsAudit(
                                                DistributionGoodsAudit.COMMON_GOODS));
            }
        } else {
            if (StringUtils.isNotBlank(customerId)) {
                DistributorLevelByCustomerIdResponse disResponse =
                        distributorLevelQueryProvider
                                .getByCustomerId(
                                        new DistributorLevelByCustomerIdRequest(
                                                customerId))
                                .getContext();
                if (disResponse != null) {
                    DistributorLevelVO distributorLevelVO = disResponse.getDistributorLevelVO();
                    response.getGoodsInfo()
                            .setDistributionCommission(
                                    getDistributionCommission(
                                            distributorLevelVO, response.getGoodsInfo()));
                    if (response.getGoods().getSaleType().equals(SaleType.WHOLESALE.toValue())) {
                        response.getWholesaleSkus().stream()
                                .forEach(
                                        w ->
                                                w.setDistributionCommission(
                                                        getDistributionCommission(
                                                                distributorLevelVO, w)));
                    }
                }
            }
        }
        return response;
    }

    /**
     * 获取分销佣金
     *
     * @param distributorLevelVO
     * @param goodsInfo
     * @return
     */
    private BigDecimal getDistributionCommission(
            DistributorLevelVO distributorLevelVO, GoodsInfoSimpleVO goodsInfo) {
        if (Objects.nonNull(distributorLevelVO)
                && Objects.nonNull(goodsInfo.getDistributionCommission())
                && Objects.nonNull(distributorLevelVO.getCommissionRate())
                && DistributionGoodsAudit.CHECKED == goodsInfo.getDistributionGoodsAudit()) {
            BigDecimal commissionRate = distributorLevelVO.getCommissionRate();
            BigDecimal distributionCommission = goodsInfo.getDistributionCommission();
            distributionCommission =
                    DistributionCommissionUtils.calDistributionCommission(
                            distributionCommission, commissionRate);
            return distributionCommission;
        }
        return null;
    }

    /**
     * 设置秒杀商品库存
     *
     * @param t
     */
    private void setFlashSaleGoodsStock(GoodsInfoSimpleDetailByGoodsInfoResponse t) {
        if (!Objects.equals(ChannelType.PC_MALL, commonUtil.getDistributeChannel().getChannelType())
                && CollectionUtils.isNotEmpty(t.getGoodsInfo().getMarketingPluginLabels())) {
            List<Integer> marketingPluginTypes =
                    t.getGoodsInfo().getMarketingPluginLabels().stream()
                            .map(MarketingPluginSimpleLabelVO::getMarketingType)
                            .collect(Collectors.toList());
            if (marketingPluginTypes.contains(MarketingPluginType.FLASH_SALE.getId()) ||
                    marketingPluginTypes.contains(MarketingPluginType.FLASH_PROMOTION.getId())) {
                String key =
                        RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY
                                + t.getGoodsInfo().getGoodsInfoId();
                String val = redisService.getString(key);
                if (StringUtils.isNotBlank(val)) {
                    long stock = Long.parseLong(val);
                    // 秒杀库存还有就填充，否则还是展示普通商品库存
                    if (stock > 0 && Objects.nonNull(t.getGoodsInfo()) && Objects.nonNull(t.getGoodsInfo().getStock()) && t.getGoodsInfo().getStock() > 0){
                        t.getGoodsInfo().setStock(stock);
                    }
                }
            }
        }
    }

    /**
     * 分销商品设置分账绑定标志
     * @param t
     */
    public void setBindFlag(GoodsInfoSimpleDetailByGoodsInfoResponse t) {
        String customerId = commonUtil.getOperatorId();
        Boolean bindFlag = Boolean.TRUE;

        if (StringUtils.isNotBlank(customerId)
                && DistributionGoodsAudit.CHECKED.equals(t.getGoodsInfo().getDistributionGoodsAudit())) {

            DistributionBindStateRequest stateRequest = DistributionBindStateRequest.builder()
                    .customerId(commonUtil.getOperatorId()).storeIds(Collections.singletonList(t.getGoodsInfo().getStoreId())).build();
            List<Long> unBindStores = ledgerReceiverRelQueryProvider.findUnBindStoresByDistribution(stateRequest).getContext().getUnBindStores();
            if (CollectionUtils.isNotEmpty(unBindStores)
                    && unBindStores.contains(t.getGoodsInfo().getStoreId())) {
                bindFlag = Boolean.FALSE;
            }
        }
        t.getGoodsInfo().setLedgerBindFlag(bindFlag);
    }
}
