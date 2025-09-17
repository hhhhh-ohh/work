package com.wanmi.sbc.communityleader;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.communityleader.response.CommunityLeaderListMainResponse;
import com.wanmi.sbc.communityleader.response.CommunityLeaderPageMainResponse;
import com.wanmi.sbc.communityleader.service.CommunityStatisticsService;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderByIdRequest;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderListRequest;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderPageRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderByIdResponse;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderListResponse;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderPageResponse;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.stream.Collectors;


@Tag(name =  "社区团购团长表管理API", description =  "CommunityLeaderController")
@RestController
@RequestMapping(value = "/communityLeader")
public class CommunityLeaderBaseController {

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @Autowired
    private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Autowired
    private CommunityStatisticsService communityStatisticsService;

    @Autowired private CommonUtil commonUtil;

    @ReturnSensitiveWords(functionName = "f_community_leader_page_word")
    @Operation(summary = "分页查询社区团购团长表")
    @PostMapping("/page")
    public BaseResponse<CommunityLeaderPageMainResponse> getPage(@RequestBody @Valid CommunityLeaderPageRequest pageReq) {
        if(LeaderCheckStatus.CHECKED.equals(pageReq.getCheckStatus())){
            pageReq.putSort("checkTime", "desc");
        } else if (LeaderCheckStatus.FORBADE.equals(pageReq.getCheckStatus())) {
            pageReq.putSort("disableTime", "desc");
        } else {
            pageReq.putSort("createTime", "desc");
        }
        pageReq.setPickupFlag(Boolean.TRUE);
        pageReq.setDelFlag(DeleteFlag.NO);
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            pageReq.setCheckStatus(LeaderCheckStatus.CHECKED);
        }
        CommunityLeaderPageResponse response = communityLeaderQueryProvider.page(pageReq).getContext();
        CommunityLeaderPageMainResponse mainResponse = new CommunityLeaderPageMainResponse();
        mainResponse.setCommunityLeaderPage(response.getCommunityLeaderPage());
        mainResponse.setPickupPointList(response.getPickupPointList());
        mainResponse.setCommunityStatisticsList(communityStatisticsService.totalByLeaders(
                response.getCommunityLeaderPage().getContent().stream().map(CommunityLeaderVO::getLeaderId)
                        .collect(Collectors.toList())));
        communityStatisticsService.fillLogoutStatus(response.getCommunityLeaderPage().getContent());
        return BaseResponse.success(mainResponse);
    }

    @ReturnSensitiveWords(functionName = "f_community_leader_page_word")
    @Operation(summary = "列表查询社区团购团长表")
    @PostMapping("/list")
    public BaseResponse<CommunityLeaderListMainResponse> getList(@RequestBody @Valid CommunityLeaderListRequest listReq) {
        if(LeaderCheckStatus.CHECKED.equals(listReq.getCheckStatus())){
            listReq.putSort("checkTime", "desc");
        } else if (LeaderCheckStatus.FORBADE.equals(listReq.getCheckStatus())) {
            listReq.putSort("disableTime", "desc");
        } else {
            listReq.putSort("createTime", "desc");
        }
        listReq.setPickupFlag(Boolean.TRUE);
        listReq.setDelFlag(DeleteFlag.NO);
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            listReq.setCheckStatus(LeaderCheckStatus.CHECKED);
        }
        CommunityLeaderListResponse response = communityLeaderQueryProvider.list(listReq).getContext();
        CommunityLeaderListMainResponse mainResponse = new CommunityLeaderListMainResponse();
        mainResponse.setCommunityLeaderList(response.getCommunityLeaderList());
        mainResponse.setPickupPointList(response.getPickupPointList());
        mainResponse.setCommunityStatisticsList(communityStatisticsService.totalByLeaders(
                response.getCommunityLeaderList().stream().map(CommunityLeaderVO::getLeaderId)
                        .collect(Collectors.toList())));
        communityStatisticsService.fillLogoutStatus(response.getCommunityLeaderList());
        return BaseResponse.success(mainResponse);
    }

    @ReturnSensitiveWords(functionName = "f_community_leader_detail_word")
    @Operation(summary = "根据id查询社区团购团长表")
    @GetMapping("/{leaderId}")
    public BaseResponse<CommunityLeaderByIdResponse> getById(@PathVariable String leaderId) {
        CommunityLeaderByIdRequest idReq = new CommunityLeaderByIdRequest();
        idReq.setLeaderId(leaderId);
        BaseResponse<CommunityLeaderByIdResponse> response = communityLeaderQueryProvider.getById(idReq);
        CommunityLeaderPickupPointListRequest pickupPointListRequest = new CommunityLeaderPickupPointListRequest();
        pickupPointListRequest.setLeaderId(leaderId);
        pickupPointListRequest.setDelFlag(DeleteFlag.NO);
        response.getContext().setPickupPointList(communityLeaderPickupPointQueryProvider.list(pickupPointListRequest)
                .getContext().getCommunityLeaderPickupPointList());
        communityStatisticsService.fillLogoutStatus(response.getContext().getCommunityLeaderVO());
        return response;
    }
}
