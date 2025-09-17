package com.wanmi.sbc.communityactivity.service;

import com.google.common.collect.Maps;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityProvider;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitydeliveryorder.CommunityDeliveryOrderProvider;
import com.wanmi.sbc.marketing.api.provider.communityregionsetting.CommunityRegionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitysetting.CommunitySettingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitystockorder.CommunityStockOrderProvider;
import com.wanmi.sbc.marketing.api.provider.communitystockorder.CommunityStockOrderQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityModifyGenerateFlagByIdListRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderAddRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderDelByActivityIdRequest;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingListRequest;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingListRequest;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderAddRequest;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderDelByActivityIdRequest;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderPageRequest;
import com.wanmi.sbc.marketing.bean.dto.CommunityDeliveryItemDTO;
import com.wanmi.sbc.marketing.bean.dto.CommunityDeliveryOrderDTO;
import com.wanmi.sbc.marketing.bean.dto.CommunityStockOrderDTO;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderAreaSummaryType;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import com.wanmi.sbc.marketing.bean.vo.CommunitySettingVO;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByConditionRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListAllRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.ReturnItemVO;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
@Slf4j
public class CommunityActivityService {

    @Autowired
    private CommunityActivityProvider communityActivityProvider;

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CommunitySettingQueryProvider communitySettingQueryProvider;

    @Autowired
    private CommunityRegionSettingQueryProvider communityRegionSettingQueryProvider;

    @Autowired
    private CommunityStockOrderProvider communityStockOrderProvider;

    @Autowired
    private CommunityStockOrderQueryProvider communityStockOrderQueryProvider;

    @Autowired
    private CommunityDeliveryOrderProvider communityDeliveryOrderProvider;

    @Autowired
    private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private CommunityActivityService communityActivityService;

    @Autowired
    private PlatformAddressQueryProvider queryProvider;

    /**
     * 批量生成活动
     * @param activityList
     */
    public void generate(List<CommunityActivityVO> activityList) {
        LocalDateTime now = LocalDateTime.now();
        List<Long> storeIds = activityList.stream().map(CommunityActivityVO::getStoreId).collect(Collectors.toList());
        CommunitySettingListRequest listRequest = new CommunitySettingListRequest();
        listRequest.setStoreIdList(storeIds);
        List<CommunitySettingVO> settingList = communitySettingQueryProvider.list(listRequest).getContext().getCommunitySettingVOList();
        Map<Long, CommunitySettingVO> settingMap = settingList.stream().collect(Collectors.toMap(CommunitySettingVO::getStoreId, Function.identity()));
        List<Long> settingStoreIds = settingList.stream().filter(s -> CollectionUtils.isNotEmpty(s.getDeliveryOrderTypes()) && s.getDeliveryOrderTypes().contains(DeliveryOrderSummaryType.AREA) && DeliveryOrderAreaSummaryType.CUSTOM.equals(s.getDeliveryAreaType())).map(CommunitySettingVO::getStoreId).collect(Collectors.toList());
        CommunityRegionSettingListRequest regionListRequest = CommunityRegionSettingListRequest.builder().storeIdList(settingStoreIds).build();

        List<CommunityRegionSettingVO> settingRegionList = communityRegionSettingQueryProvider.list(regionListRequest).getContext().getCommunityRegionSettingList();
        Map<Long, List<CommunityRegionSettingVO>> storeRegionMap = settingRegionList.stream().collect(Collectors.groupingBy(CommunityRegionSettingVO::getStoreId));

        List<String> activityIds = activityList.stream().map(CommunityActivityVO::getActivityId).collect(Collectors.toList());
        //查询订单
        TradeQueryDTO tradeQueryDTO = TradeQueryDTO.builder().communityActivityIds(activityIds).build();
        List<TradeVO> allTradeList = tradeQueryProvider.listAll(TradeListAllRequest.builder().tradeQueryDTO(tradeQueryDTO).build()).getContext().getTradeVOList();

        //配货单
        List<TradeVO> stockTradeList = allTradeList.stream().filter(trade -> {
            // 发货状态为已发货or作废则排除
            if(trade.getTradeState() != null
                    && (DeliverStatus.VOID.equals(trade.getTradeState().getDeliverStatus())
                    || DeliverStatus.SHIPPED.equals(trade.getTradeState().getDeliverStatus()))) {
                return false;
            }
            //已付款
            if (trade.getTradeState() != null && PayState.PAID.equals(trade.getTradeState().getPayState())) {
                return true;
            }
            //先货后款，未作废
            if (PaymentOrder.NO_LIMIT.equals(trade.getPaymentOrder()) && (!FlowState.VOID.equals(trade.getTradeState().getFlowState()))) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        //获取退单
        List<String> tidList = stockTradeList.stream().map(TradeVO::getId).collect(Collectors.toList());
        Map<String, List<ReturnOrderVO>> returnOrderMap = Optional.of(returnOrderQueryProvider.listByCondition(
                ReturnOrderByConditionRequest.builder().tids(tidList).build()).getContext().getReturnOrderList()).orElse(Collections.emptyList())
                .stream()
                .filter(item -> !(item.getReturnFlowState() == ReturnFlowState.VOID) && !(item.getReturnFlowState() == ReturnFlowState.REJECT_RECEIVE)
                        && !(item.getReturnType() == ReturnType.REFUND && item.getReturnFlowState() == ReturnFlowState.REJECT_REFUND))
                .collect(Collectors.groupingBy(ReturnOrderVO::getTid));
        //排除存在进行中的退单的商品数量
        stockTradeList.forEach(t -> {
            //减去已发货的商品数量
            t.setTradeItems(t.getTradeItems().stream().peek(item -> {
                item.setNum((item.getNum() == null ? 0 : item.getNum()) - (item.getDeliveredNum() == null ? 0 : item.getDeliveredNum()));
            }).filter(item -> item.getNum() > 0).collect(Collectors.toList()));
            //每个商品的已退数量
            if (returnOrderMap.containsKey(t.getId())) {
                Map<String, Integer> skuReturnNumMap = returnOrderMap.get(t.getId()).stream().flatMap(r -> r.getReturnItems().stream()).collect(Collectors.groupingBy(ReturnItemVO::getSkuId,
                        Collectors.collectingAndThen(Collectors.toList(),
                                collections -> collections.stream().map(ReturnItemVO::getNum).reduce(Integer::sum).orElse(0))));
                t.setTradeItems(t.getTradeItems().stream().peek(item -> {
                    Integer returnNum = skuReturnNumMap.getOrDefault(item.getSkuId(), 0);
                    item.setNum((item.getNum() == null ? 0 : item.getNum()) - returnNum);
                }).filter(item -> item.getNum() > 0).collect(Collectors.toList()));
            }
        });
        //排除已退数量的订单
        stockTradeList = stockTradeList.stream().filter(t -> CollectionUtils.isNotEmpty(t.getTradeItems())).collect(Collectors.toList());
        //自提发货单
        List<TradeVO> deliveryTradeList = stockTradeList.stream().filter(trade -> DeliverWay.PICKUP.equals(trade.getDeliverWay())).collect(Collectors.toList());

        List<String> leaderIds = deliveryTradeList.stream().filter(t -> StringUtils.isNotBlank(t.getCommunityTradeCommission().getLeaderId())).map(t -> t.getCommunityTradeCommission().getLeaderId()).collect(Collectors.toList());
        CommunityLeaderPickupPointListRequest pointListRequest = CommunityLeaderPickupPointListRequest.builder().leaderIds(leaderIds).build();
        List<CommunityLeaderPickupPointVO> pointList = communityLeaderPickupPointQueryProvider.list(pointListRequest).getContext().getCommunityLeaderPickupPointList();
        Map<String, CommunityLeaderPickupPointVO> leaderPickupPointMap = pointList.stream().collect(Collectors.toMap(CommunityLeaderPickupPointVO::getLeaderId, Function.identity(), (a, b) -> a));

        //在一个店铺下获得区域与团长的对应关系，一个区域分配多个团长
        Map<Long, Map<Long, List<String>>> leaderGroupRegionMap = this.distributionLeaderByRegion(pointList, settingRegionList);

        Map<String, List<TradeVO>> activityStockMap = stockTradeList.stream().collect(Collectors.groupingBy((s -> s.getCommunityTradeCommission().getActivityId())));

        Map<String, List<TradeVO>> activityDeliveryMap = deliveryTradeList.stream().collect(Collectors.groupingBy((s -> s.getCommunityTradeCommission().getActivityId())));

        activityList.forEach(activity -> {
            log.info("生成备货单、发货单开始，活动编号{}", activity.getActivityId());
            List<TradeVO> stockTrades = activityStockMap.getOrDefault(activity.getActivityId(), Collections.emptyList());
            List<TradeVO> deliveryTrades = activityDeliveryMap.getOrDefault(activity.getActivityId(), Collections.emptyList());
            try {
                communityActivityService.add(activity, stockTrades, deliveryTrades, settingMap, leaderPickupPointMap, leaderGroupRegionMap, storeRegionMap, now);
            } catch (Exception e) {
                log.error("生成备货单、发货单出现异常,活动编号为：{}", activity.getActivityId(), e);
            }
            log.info("生成备货单、发货单结束，活动编号{}", activity.getActivityId());
        });
    }

    /**
     * 单个活动生成
     * */
    @GlobalTransactional
    public void add(CommunityActivityVO activity, List<TradeVO> stockTrades, List<TradeVO> deliveryTrades, Map<Long, CommunitySettingVO> settingMap,
                    Map<String, CommunityLeaderPickupPointVO> leaderPickupPointMap, Map<Long, Map<Long, List<String>>> leaderGroupRegionMap,
                    Map<Long, List<CommunityRegionSettingVO>> storeRegionMap, LocalDateTime now) {
        Map<Long, List<String>> leaderRegionMap = leaderGroupRegionMap.getOrDefault(activity.getStoreId(), Maps.newHashMap());
        final String activityId = activity.getActivityId();
        CommunitySettingVO defSetting = new CommunitySettingVO();
        defSetting.setDeliveryOrderTypes(Collections.singletonList(DeliveryOrderSummaryType.LEADER));

        CommunityLeaderPickupPointVO emptyPickup = new CommunityLeaderPickupPointVO();
        emptyPickup.setLeaderName("该团长不存在");
        //生成配货单
        if (CollectionUtils.isNotEmpty(stockTrades)) {
            log.info("生成备货单，活动编号{}", activityId);
            //先删除原有数据
            communityStockOrderProvider.deleteByActivityId(CommunityStockOrderDelByActivityIdRequest.builder().activityId(activityId).build());
            List<CommunityStockOrderDTO> communityStockOrderDTOList = new ArrayList<>();
            Map<String, List<TradeItemVO>> itemsMap = stockTrades.stream().filter(t -> CollectionUtils.isNotEmpty(t.getTradeItems())).flatMap(t -> t.getTradeItems().stream()).collect(Collectors.groupingBy(TradeItemVO::getSkuId));
            itemsMap.forEach((skuId, items) -> {
                CommunityStockOrderDTO stockOrder = new CommunityStockOrderDTO();
                stockOrder.setActivityId(activityId);
                stockOrder.setSkuId(skuId);
                if (CollectionUtils.isNotEmpty(items)) {
                    stockOrder.setGoodsName(items.get(0).getSpuName());
                    stockOrder.setSpecName(items.get(0).getSpecDetails());
                    stockOrder.setGoodsImg(items.get(0).getPic());
                } else {
                    stockOrder.setGoodsName(skuId);
                }
                stockOrder.setNum(items.stream().filter(i -> i.getNum() != null).mapToLong(TradeItemVO::getNum).sum());
                communityStockOrderDTOList.add(stockOrder);
            });
            communityStockOrderProvider.add(CommunityStockOrderAddRequest.builder().communityStockOrderDTOList(communityStockOrderDTOList).build());
        }

        //生成发货单
        if (CollectionUtils.isNotEmpty(deliveryTrades)) {
            log.info("生成发货单，活动编号{}", activityId);
            //先删除原有数据
            communityDeliveryOrderProvider.deleteByActivityId(CommunityDeliveryOrderDelByActivityIdRequest.builder().activityId(activityId).build());
            CommunitySettingVO activitySetting = settingMap.getOrDefault(activity.getStoreId(), defSetting);
            //生成发货单
            if (CollectionUtils.isNotEmpty(activitySetting.getDeliveryOrderTypes())) {
                Map<String, List<TradeVO>> leaderGroupMap = deliveryTrades.stream().collect(Collectors.groupingBy(t -> t.getCommunityTradeCommission().getLeaderId()));
                if (activitySetting.getDeliveryOrderTypes().contains(DeliveryOrderSummaryType.LEADER)) {
                    List<CommunityDeliveryOrderDTO> deliveryDtoList = new ArrayList<>();
                    leaderGroupMap.forEach((leaderId, trades) -> {
                        CommunityLeaderPickupPointVO pointVO = leaderPickupPointMap.getOrDefault(leaderId, emptyPickup);
                        CommunityDeliveryOrderDTO dto = new CommunityDeliveryOrderDTO();
                        dto.setActivityId(activityId);
                        dto.setLeaderId(leaderId);
                        dto.setLeaderName(pointVO.getLeaderName());
                        dto.setPickupPointName(pointVO.getPickupPointName());
                        dto.setContactNumber(pointVO.getContactNumber());
                        dto.setFullAddress(pointVO.getFullAddress());
                        dto.setGoodsList(this.wrapperItem(trades));
                        dto.setType(DeliveryOrderSummaryType.LEADER);
                        dto.setDeliveryStatus(Constants.no);
                        deliveryDtoList.add(dto);
                    });
                    if(CollectionUtils.isNotEmpty(deliveryDtoList)) {
                        communityDeliveryOrderProvider.add(CommunityDeliveryOrderAddRequest.builder().communityDeliveryOrderDTOList(deliveryDtoList).build());
                    }
                }
                if (activitySetting.getDeliveryOrderTypes().contains(DeliveryOrderSummaryType.AREA)) {
                    List<CommunityDeliveryOrderDTO> deliveryDtoList = new ArrayList<>();
                    if(DeliveryOrderAreaSummaryType.CUSTOM.equals(activitySetting.getDeliveryAreaType())) {
                        List<CommunityRegionSettingVO> regionSettingList = storeRegionMap.getOrDefault(activity.getStoreId(), Collections.emptyList());
                        regionSettingList.stream().filter(r -> leaderRegionMap.containsKey(r.getRegionId())).forEach(r -> {
                            List<TradeVO> trades = leaderRegionMap.getOrDefault(r.getRegionId(), Collections.emptyList())
                                    .stream().filter(leaderGroupMap::containsKey).flatMap(l -> leaderGroupMap.get(l).stream()).collect(Collectors.toList());
                            CommunityDeliveryOrderDTO dto = new CommunityDeliveryOrderDTO();
                            dto.setActivityId(activityId);
                            dto.setLeaderId(String.valueOf(r.getRegionId()));
                            dto.setAreaName(r.getRegionName());
                            dto.setGoodsList(this.wrapperItem(trades));
                            dto.setType(DeliveryOrderSummaryType.AREA);
                            dto.setDeliveryStatus(Constants.no);
                            deliveryDtoList.add(dto);
                        });
                    } else if (DeliveryOrderAreaSummaryType.CITY.equals(activitySetting.getDeliveryAreaType())) {
                        Map<Long, List<CommunityLeaderPickupPointVO>> leaderMap = leaderPickupPointMap.values().stream().filter(k -> Objects.nonNull(k.getPickupCityId()))
                                .collect(Collectors.groupingBy(CommunityLeaderPickupPointVO::getPickupCityId));
                        if(MapUtils.isNotEmpty(leaderMap)) {
                            PlatformAddressListRequest listRequest = PlatformAddressListRequest.builder().addrIdList(leaderMap.keySet().stream().map(String::valueOf).collect(Collectors.toList())).build();
                            Map<String, String> addrMap = queryProvider.list(listRequest).getContext().getPlatformAddressVOList().stream().collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName));
                            leaderMap.forEach((k, pointList) -> {
                                CommunityDeliveryOrderDTO dto = new CommunityDeliveryOrderDTO();
                                dto.setActivityId(activityId);
                                dto.setLeaderId(String.valueOf(k));
                                dto.setAreaName(addrMap.getOrDefault(String.valueOf(k), "未知城市"));
                                List<TradeVO> tradeList = pointList.stream()
                                        .flatMap(p -> leaderGroupMap.getOrDefault(p.getLeaderId(), Collections.emptyList()).stream())
                                        .collect(Collectors.toList());
                                dto.setGoodsList(this.wrapperItem(tradeList));
                                dto.setType(DeliveryOrderSummaryType.AREA);
                                dto.setDeliveryStatus(Constants.no);
                                deliveryDtoList.add(dto);
                            });
                        }
                    } else {
                        Map<Long, List<CommunityLeaderPickupPointVO>> leaderMap = leaderPickupPointMap.values().stream().filter(k -> Objects.nonNull(k.getPickupProvinceId()))
                                .collect(Collectors.groupingBy(CommunityLeaderPickupPointVO::getPickupProvinceId));
                        if(MapUtils.isNotEmpty(leaderMap)) {
                            PlatformAddressListRequest listRequest = PlatformAddressListRequest.builder().addrIdList(leaderMap.keySet().stream().map(String::valueOf).collect(Collectors.toList())).build();
                            Map<String, String> addrMap = queryProvider.list(listRequest).getContext().getPlatformAddressVOList().stream().collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName));
                            leaderMap.forEach((k, pointList) -> {
                                CommunityDeliveryOrderDTO dto = new CommunityDeliveryOrderDTO();
                                dto.setActivityId(activityId);
                                dto.setLeaderId(String.valueOf(k));
                                dto.setAreaName(addrMap.getOrDefault(String.valueOf(k), "未知省份"));
                                List<TradeVO> tradeList = pointList.stream()
                                        .flatMap(p -> leaderGroupMap.getOrDefault(p.getLeaderId(), Collections.emptyList()).stream())
                                        .collect(Collectors.toList());
                                dto.setGoodsList(this.wrapperItem(tradeList));
                                dto.setType(DeliveryOrderSummaryType.AREA);
                                dto.setDeliveryStatus(Constants.no);
                                deliveryDtoList.add(dto);
                            });
                        }
                    }

                    if (CollectionUtils.isNotEmpty(deliveryDtoList)) {
                        communityDeliveryOrderProvider.add(CommunityDeliveryOrderAddRequest.builder().communityDeliveryOrderDTOList(deliveryDtoList).build());
                    }
                }
            }
        }
        communityActivityProvider.modifyGenerateFlagByIdList(CommunityActivityModifyGenerateFlagByIdListRequest.builder().activityIdList(Collections.singletonList(activityId))
                .generateTime(now).generateFlag(Constants.yes).build());
    }

    /**
     * 验证活动是否存在超卖订单
     * @param activityId 活动
     * @param storeId 店铺
     * @return 0：正常 1：未生成 2：存在超卖订单
     */
    public Integer checkOversoldTrade(String activityId, Long storeId) {
        CommunityActivityByIdRequest idRequest = CommunityActivityByIdRequest.builder().activityId(activityId)
                .storeId(storeId).build();
        CommunityActivityVO activity = communityActivityQueryProvider.getById(idRequest).getContext().getCommunityActivityVO();
        //未生成，
        if (Objects.isNull(activity.getGenerateTime())) {
            CommunityStockOrderPageRequest pageRequest = new CommunityStockOrderPageRequest();
            pageRequest.setActivityId(activity.getActivityId());
            pageRequest.setPageNum(0);
            pageRequest.setPageSize(1);
            long total = communityStockOrderQueryProvider.page(pageRequest).getContext().getCommunityStockOrderVOPage().getTotalElements();
            if (total == 0) {
                return 1;
            }
            //可能出现第二次情求，避免更新为未生成状态影响定时器处理
            return 2;
        }
        TradeQueryDTO tradeQueryDTO = TradeQueryDTO.builder().communityActivityId(activityId).payTimeBegin(activity.getGenerateTime()).build();
        List<TradeVO> tradeList = tradeQueryProvider.listAll(TradeListAllRequest.builder().tradeQueryDTO(tradeQueryDTO).build()).getContext().getTradeVOList();
        //存在超出当成生成时间的订单
        if (CollectionUtils.isNotEmpty(tradeList)) {
            log.info("存在未支付订单号{}，活动编号：{}", tradeList.stream().map(TradeVO::getId).collect(Collectors.joining(",")), activity.getActivityId());
            //将活动更新为未生成，由任务重新处理
            communityActivityProvider.modifyGenerateFlagByIdList(CommunityActivityModifyGenerateFlagByIdListRequest.builder().activityIdList(Collections.singletonList(activityId))
                    .generateTime(null).generateFlag(Constants.no).build());
            return 2;
        }
        return 0;
    }

    /**
     * 订单转商品维度
     * @param trades 订单
     * @return 发货单商品
     */
    private List<CommunityDeliveryItemDTO> wrapperItem(List<TradeVO> trades) {
        List<CommunityDeliveryItemDTO> goodsList = new ArrayList<>();
        Map<String, List<TradeItemVO>> itemsMap = trades.stream().filter(t -> CollectionUtils.isNotEmpty(t.getTradeItems())).flatMap(t -> t.getTradeItems().stream()).collect(Collectors.groupingBy(TradeItemVO::getSkuId));
        itemsMap.forEach((skuId, items) -> {
            CommunityDeliveryItemDTO deliveryItemDTO = new CommunityDeliveryItemDTO();
            if (CollectionUtils.isNotEmpty(items)) {
                deliveryItemDTO.setGoodsName(items.get(0).getSpuName());
                deliveryItemDTO.setSpecDesc(items.get(0).getSpecDetails());
            } else {
                deliveryItemDTO.setGoodsName(skuId);
            }
            deliveryItemDTO.setNum(items.stream().filter(i -> i.getNum() != null).mapToLong(TradeItemVO::getNum).sum());
            goodsList.add(deliveryItemDTO);
        });
        return goodsList;
    }


    /**
     * 将团长按区域分组
     *
     * @param pickupPointList 所有团长自提点
     * @param regionList      区域
     * @return <店铺id, <区域id, List<团长id>>>
     */
    private Map<Long,  Map<Long, List<String>>> distributionLeaderByRegion(List<CommunityLeaderPickupPointVO> pickupPointList, List<CommunityRegionSettingVO> regionList) {
        Map<Long,  Map<Long, List<String>>> regionMap = new HashMap<>();
        Map<Long, List<String>> hasLeaderMap = new HashMap<>();
        //首先填充自提点
        regionList.forEach(r -> {
            Map<Long, List<String>> map = regionMap.getOrDefault(r.getStoreId(), Maps.newHashMap());
            List<String> leaderIds = map.getOrDefault(r.getRegionId(), new ArrayList<>());
            //店铺下所有自定义团长
            List<String> storeLeaderIds = hasLeaderMap.getOrDefault(r.getRegionId(), new ArrayList<>());
            //首先填充自提点
            if (CollectionUtils.isNotEmpty(r.getPickupPointIdList())) {
                leaderIds.addAll(pickupPointList.stream().filter(p -> r.getPickupPointIdList().contains(p.getPickupPointId())).map(CommunityLeaderPickupPointVO::getLeaderId).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(leaderIds)) {
                map.put(r.getRegionId(), leaderIds);
                regionMap.put(r.getStoreId(), map);
                //填放店铺下指定自提点/团长
                storeLeaderIds.addAll(leaderIds);
                hasLeaderMap.put(r.getStoreId(), storeLeaderIds);
            }
        });
        //填充区域时，首先排除存在指定自提点的团长，因为指定自提点的区域优先
        regionList.forEach(r -> {
            Map<Long, List<String>> map = regionMap.getOrDefault(r.getStoreId(), Maps.newHashMap());
            List<String> leaderIds = map.getOrDefault(r.getRegionId(), new ArrayList<>());
            //排除店铺下所有自定义团长
            List<String> storeLeaderIds = hasLeaderMap.getOrDefault(r.getStoreId(), Collections.emptyList());
            if (CollectionUtils.isNotEmpty(r.getAreaIdList())) {
                leaderIds.addAll(pickupPointList.stream()
                        .filter(p -> !storeLeaderIds.contains(p.getLeaderId()) && this.isAreaContains(r.getAreaIdList(), p))
                        .map(CommunityLeaderPickupPointVO::getLeaderId).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(leaderIds)) {
                map.put(r.getRegionId(), leaderIds);
                regionMap.put(r.getStoreId(), map);
            }
        });
        return regionMap;
    }

    /**
     * 区域包含
     *
     * @param areaIds 区域
     * @param point   团信自提点
     * @return true 包含，否则不包含
     */
    private boolean isAreaContains(List<String> areaIds, CommunityLeaderPickupPointVO point) {
        if (CollectionUtils.isEmpty(areaIds)) {
            return false;
        }
        return Stream.of(point.getPickupProvinceId(), point.getPickupCityId(), point.getPickupAreaId()).anyMatch(s -> s != null && areaIds.contains(String.valueOf(s)));
    }
}

