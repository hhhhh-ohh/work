package com.wanmi.sbc.communityleader.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.marketing.api.provider.communityassist.CommunityAssistRecordQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsCustomerQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordLeaderCountRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerCountCustomerRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsListRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.order.api.provider.leadertradedetail.LeaderTradeDetailQueryProvider;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailTradeCountCustomerRequest;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailTradeCountLeaderRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动统计信息表业务逻辑</p>
 *
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Service
public class CommunityStatisticsService {

    @Autowired
    private CommunityStatisticsQueryProvider communityStatisticsQueryProvider;

    @Autowired
    private LeaderTradeDetailQueryProvider leaderTradeDetailQueryProvider;

    @Autowired
    private CommunityAssistRecordQueryProvider communityAssistRecordQueryProvider;

    @Autowired
    private CommunityStatisticsCustomerQueryProvider communityStatisticsCustomerQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 根据团长合计统计列表
     *
     * @param leaderIds 团长id列表
     * @return 统计列表
     */
    public List<CommunityStatisticsVO> totalByLeaders(List<String> leaderIds) {
        if (CollectionUtils.isEmpty(leaderIds)) {
            return Collections.emptyList();
        }
        List<CommunityStatisticsVO> statisticsList = new ArrayList<>(leaderIds.size());
        Map<String, CommunityStatisticsVO> leaderMap = communityStatisticsQueryProvider.findByLeaderIdsGroup(CommunityStatisticsListRequest.builder()
                .idList(leaderIds).build()).getContext().getCommunityStatisticsVOMap();

        CommunityStatisticsCustomerCountCustomerRequest customerRequest = CommunityStatisticsCustomerCountCustomerRequest.builder().leaderIds(leaderIds).build();
        Map<String, Long> customerAddCountMap = communityStatisticsCustomerQueryProvider.countCustomer(customerRequest).getContext().getResult();
        leaderIds.forEach(id -> {
            CommunityStatisticsVO vo = leaderMap.getOrDefault(id, new CommunityStatisticsVO());
            vo.setCustomerAddNum(customerAddCountMap.getOrDefault(id, 0L));
            vo.setLeaderId(id);
            statisticsList.add(vo);
        });
        return statisticsList;
    }

    /**
     * 根据活动合计统计列表
     *
     * @param activityIds 活动id列表
     * @return 统计列表
     */
    public List<CommunityStatisticsVO> totalByActivity(List<String> activityIds) {
        if (CollectionUtils.isEmpty(activityIds)) {
            return Collections.emptyList();
        }
        List<CommunityStatisticsVO> statisticsList = new ArrayList<>(activityIds.size());
        //活动统计
        Map<String,CommunityStatisticsVO> listMap = communityStatisticsQueryProvider.findByActivityIdsGroup(CommunityStatisticsListRequest.builder()
                .activityIds(activityIds).build()).getContext().getCommunityStatisticsVOMap();
        //成团团长数Map
        Map<String, Long> payLeaderCountMap = leaderTradeDetailQueryProvider.countLeaderGroupActivity(
                LeaderTradeDetailTradeCountLeaderRequest.builder().activityIds(activityIds).boolFlag(BoolFlag.YES).build())
                .getContext().getResult();
        //参团人数Map
        Map<String, Long> customerCountMap = leaderTradeDetailQueryProvider.countCustomerGroupActivity(
                        LeaderTradeDetailTradeCountCustomerRequest.builder().activityIds(activityIds).build())
                .getContext().getResult();
        //帮卖团长数Map
        Map<String, Long> leaderCountMap = communityAssistRecordQueryProvider.countLeader(CommunityAssistRecordLeaderCountRequest.builder().activityIds(activityIds).build())
                .getContext().getResult();
        activityIds.forEach(id -> {
            CommunityStatisticsVO vo = listMap.getOrDefault(id, new CommunityStatisticsVO());
            vo.setAssistPayNum(payLeaderCountMap.getOrDefault(id, 0L));
            vo.setCustomerNum(customerCountMap.getOrDefault(id, 0L));
            vo.setAssistNum(leaderCountMap.getOrDefault(id, 0L));
            vo.setActivityId(id);
            statisticsList.add(vo);
        });
        return statisticsList;
    }

    /**
     * 团长填充注销状态
     * @param leaders 团长列表
     */
    public void fillLogoutStatus(List<CommunityLeaderVO> leaders) {
        if (CollectionUtils.isEmpty(leaders)) {
            return;
        }
        List<String> customerIds = leaders.stream().map(CommunityLeaderVO::getCustomerId).collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        leaders.forEach(l -> l.setLogOutStatus(map.getOrDefault(l.getCustomerId(), LogOutStatus.NORMAL)));
    }

    /**
     * 团长填充注销状态
     * @param leader 团长
     */
    public void fillLogoutStatus(CommunityLeaderVO leader) {
        List<String> customerIds = Collections.singletonList(leader.getCustomerId());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        leader.setLogOutStatus(map.getOrDefault(leader.getCustomerId(), LogOutStatus.NORMAL));
    }

    /**
     * 团长自提点填充注销状态
     * @param pickupPointList 自提点
     */
    public void fillLogoutStatusWithPickUp(List<CommunityLeaderPickupPointVO> pickupPointList) {
        if (CollectionUtils.isEmpty(pickupPointList)) {
            return;
        }
        List<String> customerIds = pickupPointList.stream().map(CommunityLeaderPickupPointVO::getCustomerId).collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        pickupPointList.forEach(l -> l.setLogOutStatus(map.getOrDefault(l.getCustomerId(), LogOutStatus.NORMAL)));
    }
}

