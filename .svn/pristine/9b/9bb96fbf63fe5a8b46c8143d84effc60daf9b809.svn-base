package com.wanmi.sbc.communityactivity.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.communityactivity.response.CommunityActivitySiteVO;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StorePartColsListByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceByCustomerIdRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import com.wanmi.sbc.goods.service.list.GoodsListInterface;
import com.wanmi.sbc.goods.service.list.imp.GoodsInfoListService;
import com.wanmi.sbc.marketing.bean.enums.CommunityCommissionFlag;
import com.wanmi.sbc.marketing.bean.enums.CommunityLeaderRangeType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityCommissionAreaRelVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityCommissionLeaderRelVO;
import com.wanmi.sbc.marketing.bean.vo.CommunitySkuRelVO;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.GoodsInfoConvertMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>社区团购活动统计信息表业务逻辑</p>
 *
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Service
public class CommunityActivityService {

    @Autowired
    private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired GoodsInfoConvertMapper goodsInfoConvertMapper;

    @Autowired
    private GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired
    private GoodsInfoListService goodsInfoListService;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Resource(name = "goodsInfoListService")
    private GoodsListInterface<EsGoodsInfoQueryRequest, EsGoodsInfoSimpleResponse> goodsListInterface;


    public List<CommunityActivitySiteVO> getByLeader(List<CommunityActivityVO> activityList, CommunityLeaderPickupPointVO point) {
        if(CollectionUtils.isEmpty(activityList)){
            return Collections.emptyList();
        }
        List<CommunityActivitySiteVO> siteList = this.wrapperSite(activityList);
        List<String> skuIds = siteList.stream().flatMap(s -> s.getSkuList().stream().map(CommunitySkuRelVO::getSkuId)).collect(Collectors.toList());
        List<EsGoodsInfoVO> esGoodsInfoVOList = this.findSku(skuIds, Boolean.FALSE);
        systemPointsConfigService.clearBuyPoinsForEsSku(esGoodsInfoVOList);
        Map<String, GoodsInfoListVO> skuMap = this.skuListConvert(esGoodsInfoVOList).stream()
                .collect(Collectors.toMap(GoodsInfoListVO::getGoodsInfoId, Function.identity()));
        Map<String, CommunityActivityVO> activityMap = activityList.stream()
                .collect(Collectors.toMap(CommunityActivityVO::getActivityId, Function.identity()));
        siteList.forEach(site -> {
            CommunityActivityVO activity = activityMap.get(site.getActivityId());
            //统一佣金
            BigDecimal commission = activity.getAssistCommission();
            if (CommunityCommissionFlag.PICKUP.equals(activity.getCommissionFlag())) {
                //区域佣金
                CommunityCommissionAreaRelVO relVO = activity.getCommissionAreaList().stream()
                        .filter(s -> this.isAreaContains(s.getAreaId(), point)
                                && s.getAssistCommission() != null).findFirst().orElse(null);
                if (relVO != null) {
                    commission = relVO.getAssistCommission();
                }
                //自提点佣金
                CommunityCommissionLeaderRelVO leaderRelVO = activity.getCommissionLeaderList().stream()
                        .filter(l -> point.getPickupPointId().equals(l.getPickupPointId())).findFirst().orElse(null);
                if (leaderRelVO != null) {
                    commission = leaderRelVO.getAssistCommission();
                }
            }
            List<GoodsInfoListVO> infoVoList = new ArrayList<>();
            for (CommunitySkuRelVO sku : site.getSkuList()) {
                GoodsInfoListVO vo = KsBeanUtil.convert(skuMap.get(sku.getSkuId()), GoodsInfoListVO.class);
                if(vo == null) {
                    vo = new GoodsInfoListVO();
                    vo.setGoodsInfoName("失效商品");
                    vo.setGoodsStatus(GoodsStatus.INVALID);
                }
                BigDecimal marketPrice = vo.getMarketPrice() == null ?BigDecimal.ZERO: vo.getMarketPrice();
                if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(vo.getPriceType())
                        && vo.getIntervalMinPrice() != null
                        && vo.getIntervalMinPrice().compareTo(BigDecimal.ZERO) > 0) {
                    marketPrice = vo.getIntervalMinPrice();
                }
                //判断库存
                Long stock = vo.getStock() == null ? 0L : vo.getStock();
                //如果有活动库存，取
                if (Objects.nonNull(sku.getActivityStock())) {
                    Long tmpStock = sku.getActivityStock() - (sku.getSales() == null ? 0L : sku.getSales());
                    if (tmpStock < stock) {
                        stock = tmpStock;
                    }
                }
                if (stock <= 0) {
                    stock = 0L;
                    vo.setGoodsStatus(GoodsStatus.OUT_STOCK);
                }
                vo.setStock(stock);

                BigDecimal communityCommissionRate;
                //活动价
                if (sku.getPrice() != null) {
                    marketPrice = sku.getPrice();
                }
                //商品佣金
                if (CommunityCommissionFlag.GOODS.equals(activity.getCommissionFlag())) {
                    communityCommissionRate = sku.getAssistCommission();
                } else {
                    communityCommissionRate = commission;
                }

                if (communityCommissionRate == null) {
                    communityCommissionRate = BigDecimal.ZERO;
                }

                vo.setMarketPrice(marketPrice);
                vo.setCommunityCommissionRate(communityCommissionRate);
                infoVoList.add(vo);
                sku.setEstimateCommission(vo.getMarketPrice().multiply(vo.getCommunityCommissionRate()).divide(new BigDecimal("100"), 2, RoundingMode.DOWN));
            }
            site.setEsGoodsInfoVOList(infoVoList);
            site.setMinCommission(BigDecimal.ZERO);
            site.setMaxCommission(BigDecimal.ZERO);
            if (CollectionUtils.isNotEmpty(site.getSkuList())) {
                site.setMinCommission(site.getSkuList().stream().map(CommunitySkuRelVO::getEstimateCommission).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
                site.setMaxCommission(site.getSkuList().stream().map(CommunitySkuRelVO::getEstimateCommission).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
            }
            site.setAssistCanFlag(this.getAssistCanFlag(activity, point));
        });
        return siteList;
    }

    public List<CommunityActivitySiteVO> getByCustomer(List<CommunityActivityVO> activityList, String customerId) {
        if (CollectionUtils.isEmpty(activityList)) {
            return Collections.emptyList();
        }

        List<CommunityActivitySiteVO> siteList = this.wrapperSite(activityList);
        List<String> skuIds = siteList.stream().flatMap(s -> s.getSkuList().stream().map(CommunitySkuRelVO::getSkuId)).collect(Collectors.toList());
        List<EsGoodsInfoVO> skus = this.findSku(skuIds, Boolean.TRUE);
        Map<String, GoodsInfoListVO> skuMap = this.skuListConvert(skus).stream().collect(Collectors.toMap(GoodsInfoListVO::getGoodsInfoId, Function.identity()));

        List<GoodsInfoDTO> skuDtoList = skus.stream().map(s -> {
            GoodsInfoDTO d = new GoodsInfoDTO();
            d.setGoodsInfoId(s.getId());
            d.setPriceType(s.getGoodsInfo().getPriceType());
            d.setStoreId(s.getGoodsInfo().getStoreId());
            d.setLevelDiscountFlag(s.getGoodsInfo().getLevelDiscountFlag());
            return d;
        }).collect(Collectors.toList());
        List<GoodsIntervalPriceVO> priceList;
        if (StringUtils.isNotBlank(customerId)) {
            GoodsIntervalPriceByCustomerIdRequest priceRequest = GoodsIntervalPriceByCustomerIdRequest.builder()
                    .goodsInfoDTOList(skuDtoList).customerId(customerId).build();
            priceList = goodsIntervalPriceProvider.putByCustomerId(priceRequest).getContext().getGoodsIntervalPriceVOList();
        } else {
            GoodsIntervalPriceRequest priceRequest = GoodsIntervalPriceRequest.builder().goodsInfoDTOList(skuDtoList).build();
            priceList = goodsIntervalPriceProvider.put(priceRequest).getContext().getGoodsIntervalPriceVOList();
        }
        Map<String, List<GoodsIntervalPriceVO>> priceMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(priceList)) {
            priceMap.putAll(priceList.stream().collect(Collectors.groupingBy(GoodsIntervalPriceVO::getGoodsInfoId)));
        }
        siteList.forEach(site -> {
            List<GoodsInfoListVO> infoVoList = new ArrayList<>();
            for (CommunitySkuRelVO sku : site.getSkuList()) {
                GoodsInfoListVO vo = KsBeanUtil.convert(skuMap.get(sku.getSkuId()), GoodsInfoListVO.class);
                if (vo == null) {
                    vo = new GoodsInfoListVO();
                    vo.setGoodsInfoName("失效商品");
                    vo.setGoodsStatus(GoodsStatus.INVALID);
                    vo.setMarketPrice(BigDecimal.ZERO);
                }
                //活动价
                if (sku.getPrice() != null) {
                    vo.setMarketPrice(sku.getPrice());
                }

                //判断库存
                Long stock = vo.getStock() == null ? 0L : vo.getStock();
                //如果有活动库存
                if (Objects.nonNull(sku.getActivityStock())) {
                    Long tmpStock = sku.getActivityStock() - (sku.getSales() == null ? 0L : sku.getSales());
                    if (tmpStock < stock) {
                        stock = tmpStock;
                    }
                }
                if (stock <= 0) {
                    stock = 0L;
                    vo.setGoodsStatus(GoodsStatus.OUT_STOCK);
                }
                vo.setStock(stock);
                vo.setIntervalPriceList(priceMap.getOrDefault(vo.getGoodsInfoId(), Collections.emptyList()));
                infoVoList.add(vo);
            }
            site.setEsGoodsInfoVOList(infoVoList);
        });
        return siteList;
    }

    /**
     * 获取可转发帮卖标识
     * @param activity 活动
     * @param point 团长自提点
     * @return 转发帮卖标识
     */
    public Boolean getAssistCanFlag(CommunityActivityVO activity, CommunityLeaderPickupPointVO point) {
        Boolean assistCanFlag = Boolean.FALSE;
        //验证是否可转发帮卖
        if (Constants.yes.equals(point.getAssistFlag()) && CommunityTabStatus.STARTED.equals(activity.getActivityStatus())
                && activity.getSalesTypes().contains(CommunitySalesType.LEADER)) {
            if (CommunityLeaderRangeType.ALL.equals(activity.getLeaderRange())) {
                assistCanFlag = Boolean.TRUE;
            } else if (CommunityLeaderRangeType.AREA.equals(activity.getLeaderRange())
                    && CollectionUtils.isNotEmpty(activity.getLeaderRangeContext())
                    && this.isAreaContains(activity.getLeaderRangeContext(), point)) {
                assistCanFlag = Boolean.TRUE;
            } else if (CommunityLeaderRangeType.CUSTOM.equals(activity.getLeaderRange())
                    && CollectionUtils.isNotEmpty(activity.getLeaderRangeContext())
                    && activity.getLeaderRangeContext().contains(point.getPickupPointId())) {
                assistCanFlag = Boolean.TRUE;
            }
        }
        return assistCanFlag;
    }

    public boolean isAreaContains(List<String> areaIds, CommunityLeaderPickupPointVO point) {
        if (CollectionUtils.isEmpty(areaIds)) {
            return false;
        }
        return Stream.of(point.getPickupProvinceId(), point.getPickupCityId(), point.getPickupAreaId()).anyMatch(s -> s != null && areaIds.contains(String.valueOf(s)));
    }

    /**
     * 根据团长合计统计列表
     *
     * @param activityList 原实体活动
     * @return 统计列表
     */
    public List<CommunityActivitySiteVO> wrapperSite(List<CommunityActivityVO> activityList) {
        return activityList.stream().map(s -> KsBeanUtil.convert(s, CommunityActivitySiteVO.class)).collect(Collectors.toList());
    }

    /**
     * 查询商品
     * @param skuIds 商品
     * @param customerFlag 是否会员
     * @return
     */
    public List<EsGoodsInfoVO> findSku(List<String> skuIds, Boolean customerFlag){
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        queryRequest.setGoodsInfoIds(skuIds);
        queryRequest.setPageNum(0);
        queryRequest.setPageSize((int)(skuIds.stream().distinct().count()));
        queryRequest.setBrandAggFlag(Boolean.FALSE);
        queryRequest.setCateAggFlag(Boolean.FALSE);
        queryRequest.setPropAggFlag(Boolean.FALSE);
        queryRequest.setLabelAggFlag(Boolean.FALSE);
        queryRequest.setIsOutOfStockShow(Constants.no);
        if(Boolean.TRUE.equals(customerFlag)) {
            return goodsListInterface.getList(queryRequest).getEsGoodsInfoPage().getContent();
        }

        EsGoodsInfoSimpleResponse response = esGoodsInfoElasticQueryProvider.skuPage(queryRequest).getContext();
        return goodsInfoListService.setStock(response, null).getEsGoodsInfoPage().getContent();
    }

    /**
     * 填充店铺信息
     * @param activity 活动
     */
    public void fillStore(CommunityActivitySiteVO activity) {
        StorePartColsListByIdsRequest idsRequest = StorePartColsListByIdsRequest.builder()
                .storeIds(Collections.singletonList(activity.getStoreId()))
                .cols(Arrays.asList("storeName", "storeLogo")).build();
        storeQueryProvider.listStorePartColsByIds(idsRequest).getContext().getStoreVOList().stream().findFirst()
                .ifPresent(s -> {
                    activity.setStoreName(s.getStoreName());
                    activity.setStoreLogo(s.getStoreLogo());
                });
    }

    /**
     * 验证总开关
     */
    public void checkOpen() {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.COMMUNITY_CONFIG.toValue());
        ConfigVO configVO = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext().getConfig();
        if(configVO == null || configVO.getStatus() == null ||Constants.no.equals(configVO.getStatus())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "平台已关闭了社区团购业务");
        }
    }

    /**
     * 转换
     * @param skuList 商品列表
     * @return 简化
     */
    public List<GoodsInfoListVO> skuListConvert(List<EsGoodsInfoVO> skuList) {
        if(CollectionUtils.isEmpty(skuList)) {
            return Collections.emptyList();
        }
        return skuList.stream().map(e -> {
            GoodsInfoListVO vo = goodsInfoConvertMapper.goodsInfoNestVOToGoodsInfoListVO(e.getGoodsInfo());
            vo.setGoodsLabelList(e.getGoodsLabelList());
            vo.setGoodsSubtitle(e.getGoodsSubtitle());
            GoodsInfoNestVO goodsInfo = e.getGoodsInfo();
            if (Objects.nonNull(goodsInfo)) {
                vo.setFlashStock(goodsInfo.getFlashStock());
            }
            vo.setIsBuyCycle(e.getIsBuyCycle());
            return vo;
        }).collect(Collectors.toList());
    }
}

