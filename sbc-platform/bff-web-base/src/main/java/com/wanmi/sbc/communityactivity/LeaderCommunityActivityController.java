package com.wanmi.sbc.communityactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.communityactivity.response.CommunityActivityGetSiteByIdResponse;
import com.wanmi.sbc.communityactivity.response.CommunityActivityPageSiteResponse;
import com.wanmi.sbc.communityactivity.response.CommunityActivitySiteVO;
import com.wanmi.sbc.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.communityactivity.service.CommunityActivityTradeDetailService;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityPageRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByConditionRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListAllRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.order.service.CommunityTradeService;
import com.wanmi.sbc.util.CommonUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@Tag(name =  "团长社区团购活动表管理API", description =  "CommunityActivityBaseController")
@RestController
@Slf4j
@RequestMapping(value = "/leader/communityActivity")
public class LeaderCommunityActivityController {

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CommunityTradeService communityTradeService;

    @Autowired
    private CommunityActivityService communityActivityService;

    @Autowired
    private CommunityActivityTradeDetailService communityActivityTradeDetailService;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询社区团购活动表")
    @PostMapping("/page")
    public BaseResponse<CommunityActivityPageSiteResponse> getPage(@RequestBody @Valid CommunityActivityPageRequest pageReq) {
        communityActivityService.checkOpen();
        CommunityLeaderPickupPointVO leader = commonUtil.getLeader();
        //非绑卖团长，没有数据
        if(!Constants.yes.equals(leader.getAssistFlag())) {
            return BaseResponse.success(CommunityActivityPageSiteResponse.builder()
                    .communityActivityPage(new MicroServicePage<>(Collections.emptyList())).build());
        }
        if (CommunityTabStatus.STARTED.equals(pageReq.getTabType())) {
            pageReq.putSort("endTime", "asc");
        } else if (CommunityTabStatus.NOT_START.equals(pageReq.getTabType())) {
            pageReq.putSort("startTime", "asc");
        } else if (CommunityTabStatus.ENDED.equals(pageReq.getTabType())) {
            pageReq.putSort("endTime", "desc");
        }
        pageReq.setSkuRelFlag(Boolean.TRUE);
        pageReq.setSaleRelFlag(Boolean.TRUE);
        pageReq.setCommissionRelFlag(Boolean.TRUE);
        pageReq.setAssistLeaderFlag(Boolean.TRUE);
        pageReq.setSalesTypes(Collections.singletonList(CommunitySalesType.LEADER));
        pageReq.setAssistPickupPointId(leader.getPickupPointId());
        pageReq.setAssistAreaIds(Arrays.asList(Objects.toString(leader.getPickupProvinceId()),Objects.toString(leader.getPickupCityId()),
                        Objects.toString(leader.getPickupAreaId())));
        MicroServicePage<CommunityActivityVO> page = communityActivityQueryProvider.page(pageReq).getContext().getCommunityActivityPage();
        List<CommunityActivitySiteVO> voList = communityActivityService.getByLeader(page.getContent(), leader);
        //填充跟团记录
        communityActivityTradeDetailService.fillTradeList(voList, null);
        //填充跟团数
        communityActivityTradeDetailService.fillTradeTotal(voList, null);
        CommunityActivityPageSiteResponse siteResponse = CommunityActivityPageSiteResponse.builder()
                .communityActivityPage(new MicroServicePage<>(voList, pageReq.getPageable(), page.getTotal()))
                .build();
        return BaseResponse.success(siteResponse);
    }

    @Operation(summary = "根据id查询社区团购活动表")
    @GetMapping("/{activityId}")
    public BaseResponse<CommunityActivityGetSiteByIdResponse> getById(@PathVariable String activityId) {
        if (activityId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        communityActivityService.checkOpen();
        CommunityLeaderPickupPointVO leader = commonUtil.getLeader();
        CommunityActivityByIdRequest idReq = new CommunityActivityByIdRequest();
        idReq.setActivityId(activityId);
        idReq.setSkuRelFlag(Boolean.TRUE);
        idReq.setCommissionRelFlag(Boolean.TRUE);
        idReq.setSaleRelFlag(Boolean.TRUE);
        CommunityActivityByIdResponse response = communityActivityQueryProvider.getById(idReq).getContext();
        List<CommunityActivitySiteVO> voList = communityActivityService.getByLeader(
                Collections.singletonList(response.getCommunityActivityVO()), leader);
        communityActivityService.fillStore(voList.get(0));
        //填充跟团数
        communityActivityTradeDetailService.fillTradeTotal(voList, null);
        return BaseResponse.success(CommunityActivityGetSiteByIdResponse.builder().communityActivity(voList.get(0)).build());
    }

    @Operation(summary = "根据帮卖记录id通知取货")
    @GetMapping("/notifyPickup/{activityId}")
    public BaseResponse notifyPickup(@PathVariable String activityId) {
        communityActivityService.checkOpen();
        CommunityLeaderPickupPointVO point = commonUtil.getLeader();
        CommunityActivityByIdRequest idReq = new CommunityActivityByIdRequest();
        idReq.setActivityId(activityId);
        CommunityActivityByIdResponse response = communityActivityQueryProvider.getById(idReq).getContext();
        if (!CommunityTabStatus.ENDED.equals(response.getCommunityActivityVO().getActivityStatus())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "该活动未结束，无法操作");
        }
        //在活动、团长、自提、社团类型、未发货状态
        TradeQueryDTO queryDTO = TradeQueryDTO.builder()
                .communityActivityId(activityId).leaderId(point.getLeaderId()).deliverWay(DeliverWay.PICKUP).communityOrder(Constants.ONE)
                .tradeState(TradeStateDTO.builder().deliverStatus(DeliverStatus.NOT_YET_SHIPPED).build()).build();
        TradeListAllRequest criteriaRequest = TradeListAllRequest.builder().tradeQueryDTO(queryDTO).build();
        List<TradeVO> tradeList = tradeQueryProvider.listAll(criteriaRequest).getContext().getTradeVOList();
        //过滤未付款的先款后货订单
        tradeList = tradeList.stream()
                .filter(i -> !(PaymentOrder.PAY_FIRST.equals(i.getPaymentOrder()) && !PayState.PAID.equals(i.getTradeState().getPayState())))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tradeList)) {
            return BaseResponse.SUCCESSFUL();
        }
        //排除存在退单的
        tradeList = this.excludeReturn(tradeList);
        boolean result = true;
        //循环通知
        for (TradeVO tradeVO : tradeList) {
            try {
                communityTradeService.deliver(tradeVO);
            } catch (Exception e) {
                log.error("活动id{}, 订单号{}，通知取货发生异常", activityId, tradeVO.getId(), e);
                result = false;
            }
        }
        if (!result) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "部分订单通知取货存在异常，请联系管理员");
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 排除有效退单
     * @param tradeVOList 原订单
     * @return 排除后的订单
     */
    public List<TradeVO> excludeReturn(List<TradeVO> tradeVOList) {
        List<String> tidList = tradeVOList.stream().map(TradeVO::getId).collect(Collectors.toList());
        List<String> returnTidList = Optional.of(returnOrderQueryProvider.listByCondition(
                        ReturnOrderByConditionRequest.builder().tids(tidList).build()).getContext().getReturnOrderList()).orElse(Collections.emptyList())
                .stream()
                .filter(item -> !(item.getReturnFlowState() == ReturnFlowState.VOID) && !(item.getReturnFlowState() == ReturnFlowState.REJECT_RECEIVE)
                        && !(item.getReturnType() == ReturnType.REFUND && item.getReturnFlowState() == ReturnFlowState.REJECT_REFUND))
                .map(ReturnOrderVO::getTid)
                .distinct()
                .collect(Collectors.toList());
        return tradeVOList.stream().filter(t -> !returnTidList.contains(t.getId())).collect(Collectors.toList());
    }
}
