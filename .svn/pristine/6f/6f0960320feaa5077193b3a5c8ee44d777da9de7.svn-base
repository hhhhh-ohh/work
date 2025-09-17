package com.wanmi.sbc.communitypickup;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.communityleader.service.CommunityStatisticsService;
import com.wanmi.sbc.communitypickup.response.CommunityLeaderPickupPointListMainResponse;
import com.wanmi.sbc.communitypickup.response.CommunityLeaderPickupPointPageMainResponse;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointByIdRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointPageRequest;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointByIdResponse;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointListResponse;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointPageResponse;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.stream.Collectors;


@Tag(name =  "团长自提点表管理API", description =  "CommunityLeaderPickupPointController")
@RestController
@RequestMapping(value = "/communityLeaderPickupPoint")
public class CommunityLeaderPickupPointController {

    @Autowired
    private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Autowired
    private CommunityStatisticsService communityStatisticsService;

    @Autowired private CommonUtil commonUtil;

    @ReturnSensitiveWords(functionName = "f_community_leader_page_word")
    @Operation(summary = "分页查询团长自提点表")
    @PostMapping("/page")
    public BaseResponse<CommunityLeaderPickupPointPageMainResponse> getPage(@RequestBody @Valid CommunityLeaderPickupPointPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("createTime", "desc");
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            pageReq.setCheckStatus(LeaderCheckStatus.CHECKED);
        }
        CommunityLeaderPickupPointPageResponse response = communityLeaderPickupPointQueryProvider.page(pageReq).getContext();
        CommunityLeaderPickupPointPageMainResponse mainResponse = new CommunityLeaderPickupPointPageMainResponse();
        mainResponse.setCommunityLeaderPickupPointPage(response.getCommunityLeaderPickupPointPage());
        mainResponse.setCommunityStatisticsList(communityStatisticsService.totalByLeaders(
                response.getCommunityLeaderPickupPointPage().getContent().stream().map(CommunityLeaderPickupPointVO::getLeaderId)
                        .collect(Collectors.toList())));
        communityStatisticsService.fillLogoutStatusWithPickUp(response.getCommunityLeaderPickupPointPage().getContent());
        return BaseResponse.success(mainResponse);
    }

    @ReturnSensitiveWords(functionName = "f_community_leader_page_word")
    @Operation(summary = "列表查询团长自提点表")
    @PostMapping("/list")
    public BaseResponse<CommunityLeaderPickupPointListMainResponse> getList(@RequestBody @Valid CommunityLeaderPickupPointListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("createTime", "desc");
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            listReq.setCheckStatus(LeaderCheckStatus.CHECKED);
        }
        CommunityLeaderPickupPointListResponse response = communityLeaderPickupPointQueryProvider.list(listReq).getContext();
        CommunityLeaderPickupPointListMainResponse mainResponse = new CommunityLeaderPickupPointListMainResponse();
        mainResponse.setCommunityLeaderPickupPointList(response.getCommunityLeaderPickupPointList());
        mainResponse.setCommunityStatisticsList(communityStatisticsService.totalByLeaders(response.getCommunityLeaderPickupPointList()
                .stream().map(CommunityLeaderPickupPointVO::getLeaderId).collect(Collectors.toList())));
        communityStatisticsService.fillLogoutStatusWithPickUp(response.getCommunityLeaderPickupPointList());
        return BaseResponse.success(mainResponse);
    }

    @ReturnSensitiveWords(functionName = "f_community_leader_detail_word")
    @Operation(summary = "根据id查询团长自提点表")
    @GetMapping("/{pickupPointId}")
    public BaseResponse<CommunityLeaderPickupPointByIdResponse> getById(@PathVariable String pickupPointId) {
        if (pickupPointId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CommunityLeaderPickupPointByIdRequest idReq = new CommunityLeaderPickupPointByIdRequest();
        idReq.setPickupPointId(pickupPointId);
        return communityLeaderPickupPointQueryProvider.getById(idReq);
    }
}
