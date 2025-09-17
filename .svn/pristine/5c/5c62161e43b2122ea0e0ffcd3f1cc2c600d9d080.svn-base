package com.wanmi.sbc.mq.report.service.base;


import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderListRequest;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.marketing.api.provider.communitydeliveryorder.CommunityDeliveryOrderQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderListRequest;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;
import com.wanmi.sbc.marketing.bean.vo.CommunityDeliveryOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @return
 * @description 社团团长佣金
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class CommunityDeliveryBaseService {

    @Autowired
    private CommunityDeliveryOrderQueryProvider communityDeliveryOrderQueryProvider;

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @ReturnSensitiveWords(functionName = "f_community_delivery_order_word")
    public List<CommunityDeliveryOrderVO> queryExport(Operator operator,  CommunityDeliveryOrderListRequest idRequest){
        List<CommunityDeliveryOrderVO> orderVOList = communityDeliveryOrderQueryProvider.list(idRequest).getContext().getCommunityDeliveryOrderVOList();
        if (CollectionUtils.isEmpty(orderVOList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "该活动没有发货单数据");
        }
        List<String> leaderIds = orderVOList.stream().filter(o -> DeliveryOrderSummaryType.LEADER.equals(o.getType()))
                .map(CommunityDeliveryOrderVO::getLeaderId).collect(Collectors.toList());
        Map<String, String> leaderAccountMap = communityLeaderQueryProvider.list(CommunityLeaderListRequest.builder()
                        .leaderIdList(leaderIds).build()).getContext()
                .getCommunityLeaderList().stream().collect(Collectors.toMap(CommunityLeaderVO::getLeaderId, CommunityLeaderVO::getLeaderAccount));
        return orderVOList.stream().peek(o -> o.setLeaderAccount(leaderAccountMap.get(o.getLeaderId()))).collect(Collectors.toList());
    }
}
