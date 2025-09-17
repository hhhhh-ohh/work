package com.wanmi.sbc.communityactivity.service;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.communityactivity.response.CommunityActivitySiteVO;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerIdsListRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailWithImgVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoPartColsByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import com.wanmi.sbc.order.api.provider.leadertradedetail.LeaderTradeDetailQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailPageRequest;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailTopGroupByActivityRequest;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailTradeCountGroupByActivityRequest;
import com.wanmi.sbc.order.api.request.trade.TradePageCriteriaRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动跟团信息表业务逻辑</p>
 *
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Service
public class CommunityActivityTradeDetailService {

    @Autowired
    private LeaderTradeDetailQueryProvider leaderTradeDetailQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private ThirdLoginRelationQueryProvider thirdLoginRelationQueryProvider;


    /**
     * 填充跟团列表
     * @param activityList 活动
     * @param leaderId 帮卖团长
     */
    public void fillTradeList(List<CommunityActivitySiteVO> activityList, String leaderId) {
        if (CollectionUtils.isEmpty(activityList)) {
            return;
        }
        //排除未开始的活动
        List<String> activityIds = activityList.stream().filter(a -> !CommunityTabStatus.NOT_START.equals(a.getActivityStatus()))
                .map(CommunityActivitySiteVO::getActivityId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(activityIds)) {
            return;
        }
        LeaderTradeDetailTopGroupByActivityRequest activityRequest = LeaderTradeDetailTopGroupByActivityRequest.builder()
                .activityIds(activityIds).build();
        if (StringUtils.isNotBlank(leaderId)) {
            activityRequest.setLeaderIds(Collections.singletonList(leaderId));
            activityRequest.setBoolFlag(Constants.yes);
        }
        List<LeaderTradeDetailVO> tradeDetailList = leaderTradeDetailQueryProvider.topGroupActivity(activityRequest).getContext().getLeaderTradeDetailList();
        List<String> skuIds = tradeDetailList.stream().map(LeaderTradeDetailVO::getGoodsInfoId).collect(Collectors.toList());
        GoodsInfoPartColsByIdsRequest idsRequest = new GoodsInfoPartColsByIdsRequest();
        idsRequest.setGoodsInfoIds(skuIds);
        Map<String, String> skuNameMap = goodsInfoQueryProvider.listPartColsByIds(idsRequest).getContext().getGoodsInfos().stream()
                .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, GoodsInfoVO::getGoodsInfoName));
        //提取成 <活动id,List<订单明细>> 结构
        Map<String, List<LeaderTradeDetailVO>> detailsMap = tradeDetailList.stream().collect(
                Collectors.groupingBy(LeaderTradeDetailVO::getCommunityActivityId));
        //为每个活动填充跟团记录
        activityList.stream().filter(a -> detailsMap.containsKey(a.getActivityId()))
                .forEach(a -> {
                    List<LeaderTradeDetailVO> sortTrades = detailsMap.get(a.getActivityId())
                            .stream().sorted(Comparator.comparing(LeaderTradeDetailVO::getActivityTradeNo, Comparator.reverseOrder())).collect(Collectors.toList());
                    Map<Long, List<LeaderTradeDetailVO>> tradeMap = sortTrades.stream().collect(Collectors.groupingBy(LeaderTradeDetailVO::getActivityTradeNo));
                    List<CommunitySimpleTradeVO> tradeList = new ArrayList<>();
                    sortTrades.stream().map(LeaderTradeDetailVO::getActivityTradeNo).filter(tradeMap::containsKey).distinct()
                            .forEach(tradeNo -> {
                                List<LeaderTradeDetailVO> items = tradeMap.get(tradeNo);
                                if (CollectionUtils.isNotEmpty(items) && tradeList.size() < 5) {
                                    CommunitySimpleTradeVO trade = new CommunitySimpleTradeVO();
                                    trade.setCommunityOrderNo(Objects.toString(tradeNo));
                                    trade.setCustomerHead(items.get(0).getCustomerPic());
                                    trade.setCustomerName(this.getLeaderNameSign(items.get(0).getCustomerName()));
                                    trade.setTradeItems(items.stream().map(i -> {
                                        TradeItemVO item = new TradeItemVO();
                                        item.setSpuName(skuNameMap.get(i.getGoodsInfoId()));
                                        item.setSpecDetails(i.getGoodsInfoSpec());
                                        item.setNum(i.getGoodsInfoNum());
                                        return item;
                                    }).collect(Collectors.toList()));
                                    tradeList.add(trade);
                                }
                            });
                    a.setTradeList(tradeList);
                });
    }

    /**
     * 统计跟团数
     * @param activityList 活动
     * @param leaderId 帮卖团长
     */
    public void fillTradeTotal(List<CommunityActivitySiteVO> activityList, String leaderId) {
        if (CollectionUtils.isEmpty(activityList)) {
            return;
        }
        //排除未开始的活动
        List<String> activityIds = activityList.stream().filter(a -> !CommunityTabStatus.NOT_START.equals(a.getActivityStatus()))
                .map(CommunityActivitySiteVO::getActivityId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(activityIds)) {
            return;
        }
        LeaderTradeDetailTradeCountGroupByActivityRequest activityRequest = LeaderTradeDetailTradeCountGroupByActivityRequest.builder()
                .activityIds(activityIds).build();
        if (StringUtils.isNotBlank(leaderId)) {
            activityRequest.setLeaderIds(Collections.singletonList(leaderId));
            activityRequest.setBoolFlag(BoolFlag.YES);
        }
        Map<String, Long> tradeCountMap = leaderTradeDetailQueryProvider.countTradeGroupActivity(activityRequest).getContext()
                .getTradeCountList().stream().collect(Collectors.toMap(CommunityTradeCountVO::getActivityId, CommunityTradeCountVO::getPayOrderNum));
        activityList.forEach(s -> s.setPayNum(tradeCountMap.getOrDefault(s.getActivityId(), 0L)));
    }

    /**
     * 获取跟团记录
     * @param pageRequest 跟团记录分页参数
     * @return 跟团记录分页结果
     */
    public MicroServicePage<CommunitySimpleTradeVO> getTradeList(LeaderTradeDetailPageRequest pageRequest) {
        String activityId = pageRequest.getCommunityActivityId();
        String leaderId = pageRequest.getLeaderId();
        if (StringUtils.isBlank(activityId)) {
            return new MicroServicePage<>(Collections.emptyList());
        }
        TradeQueryDTO queryDTO = TradeQueryDTO.builder().communityActivityId(activityId)
                .tradeState(TradeStateDTO.builder().payState(PayState.PAID).build())
                .leaderId(leaderId).build();
        //帮卖
        if (StringUtils.isNotBlank(leaderId)) {
            queryDTO.setSalesType(CommunitySalesType.LEADER);
        }
        //创建时间排序
        queryDTO.setPageNum(pageRequest.getPageNum());
        queryDTO.setPageSize(pageRequest.getPageSize());
        queryDTO.setCustomSortFlag(Boolean.TRUE);
        queryDTO.setSortColumn("tradeState.payTime");
        queryDTO.setSortRole(SortType.DESC.toValue());
        TradePageCriteriaRequest order = TradePageCriteriaRequest.builder().tradePageDTO(queryDTO).isReturn(false).build();
        MicroServicePage<TradeVO> tradePage = tradeQueryProvider.pageCriteriaOptimize(order).getContext().getTradePage();
        if(CollectionUtils.isEmpty(tradePage.getContent())) {
            return new MicroServicePage<>(Collections.emptyList(), tradePage.getPageable(), tradePage.getTotal());
        }
        List<String> customerIds = tradePage.getContent().stream().map(t -> t.getBuyer().getId()).collect(Collectors.toList());
        CustomerIdsListRequest customerRequest = new CustomerIdsListRequest();
        customerRequest.setCustomerIds(customerIds);
        List<CustomerDetailWithImgVO> imgList = thirdLoginRelationQueryProvider.listWithImgByCustomerIds(customerRequest).getContext().getCustomerVOList();
        if(imgList == null) {
            imgList = Collections.emptyList();
        }
        Map<String, String> headerImgMap = imgList.stream().filter(t -> StringUtils.isNotBlank(t.getHeadimgurl()))
                .collect(Collectors.toMap(CustomerDetailWithImgVO::getCustomerId, CustomerDetailWithImgVO::getHeadimgurl));
        //提取成 <活动id,订单id,List<订单明细>> 结构
        List<CommunitySimpleTradeVO> tradeVoList = tradePage.getContent().stream().map(trade -> {
            CommunitySimpleTradeVO tradeVO = new CommunitySimpleTradeVO();
            tradeVO.setCustomerName(this.getLeaderNameSign(trade.getBuyer().getName()));
            tradeVO.setCommunityOrderNo(Objects.toString(trade.getCommunityTradeCommission().getActivityTradeNo()));
            tradeVO.setCustomerHead(headerImgMap.get(trade.getBuyer().getId()));
            tradeVO.setTradeItems(trade.getTradeItems().stream().map(item -> {
                TradeItemVO itemVO = new TradeItemVO();
                itemVO.setSpuName(item.getSpuName());
                itemVO.setSpecDetails(item.getSpecDetails());
                itemVO.setNum(item.getNum());
                return itemVO;
            }).collect(Collectors.toList()));
            return tradeVO;
        }).collect(Collectors.toList());
        return new MicroServicePage<>(tradeVoList, pageRequest.getPageable(), tradePage.getTotal());
    }

    /**
     * 团长名称脱敏
     * @param leaderName
     * @return
     */
    public String getLeaderNameSign(String leaderName) {
        if (StringUtils.isBlank(leaderName)) {
            return "***";
        }
        if (leaderName.length() == 11) {
            return StringUtils.substring(leaderName, 0, 3).concat("****").concat(StringUtils.substring(leaderName, 7));
        } else {
            return StringUtils.substring(leaderName, 0, 1).concat("**");
        }
    }
}

