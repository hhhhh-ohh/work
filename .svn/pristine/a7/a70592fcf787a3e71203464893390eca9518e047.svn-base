package com.wanmi.sbc.communityleader;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderProvider;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.*;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;


@Tag(name =  "社区团购团长表管理API", description =  "CommunityLeaderController")
@RestController
@RequestMapping(value = "/communityLeader")
public class CommunityLeaderController {

    @Autowired
    private CommunityLeaderProvider communityLeaderProvider;

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "新增社区团购团长表")
    @PostMapping("/add")
    public BaseResponse add(@RequestBody @Valid CommunityLeaderAddRequest addReq) {
        addReq.setCheckStatus(LeaderCheckStatus.CHECKED);
        CommunityLeaderVO vo = communityLeaderProvider.add(addReq).getContext().getCommunityLeaderVO();
        //刷新ES
        esCustomerDetailProvider.init(EsCustomerDetailInitRequest
                .builder()
                .idList(Collections.singletonList(vo.getCustomerId()))
                .build());
        operateLogMqUtil.convertAndSend("应用", "新增社区团购团长", "新增团长：".concat(addReq.getLeaderName()));
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @Operation(summary = "修改社区团购团长表")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid CommunityLeaderModifyRequest modifyReq) {
        communityLeaderProvider.modify(modifyReq);
        operateLogMqUtil.convertAndSend("应用", "编辑社区团购团长", "编辑团长：".concat(modifyReq.getLeaderName()));
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @Operation(summary = "审核社区团购团长")
    @PutMapping("/check")
    public BaseResponse check(@RequestBody @Valid CommunityLeaderCheckByIdRequest checkReq) {
        CommunityLeaderVO leader = communityLeaderQueryProvider.getById(CommunityLeaderByIdRequest.builder()
                .leaderId(checkReq.getLeaderId()).build()).getContext().getCommunityLeaderVO();
        communityLeaderProvider.checkById(checkReq);
        if(LeaderCheckStatus.FORBADE.equals(checkReq.getCheckStatus())) {
            operateLogMqUtil.convertAndSend("应用", "禁用社区团购团长", "禁用团长：".concat(leader.getLeaderName()));
        } else if (LeaderCheckStatus.CHECKED.equals(checkReq.getCheckStatus())) {
            operateLogMqUtil.convertAndSend("应用", "审核通过社区团购团长", "审核通过团长：".concat(leader.getLeaderName()));
        } else if (LeaderCheckStatus.NOT_PASS.equals(checkReq.getCheckStatus())) {
            operateLogMqUtil.convertAndSend("应用", "驳回社区团购团长", "驳回团长：".concat(leader.getLeaderName()));
        }
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @Operation(summary = "修改帮卖社区团购团长表")
    @PutMapping("/assist")
    public BaseResponse assist(@RequestBody @Valid CommunityLeaderModifyAssistByIdRequest assistReq) {
        CommunityLeaderListRequest listRequest = CommunityLeaderListRequest.builder().leaderIdList(assistReq.getLeaderIds()).build();
        List<CommunityLeaderVO> leaderList = communityLeaderQueryProvider.list(listRequest).getContext().getCommunityLeaderList();
        if(leaderList.stream().anyMatch(l -> !LeaderCheckStatus.CHECKED.equals(l.getCheckStatus()))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "存在没有通过审核的团长，无法操作");
        }
        communityLeaderProvider.assistById(assistReq);
        operateLogMqUtil.convertAndSend("应用", "社区团购团长设为帮卖", "批量设为帮卖");
        return BaseResponse.SUCCESSFUL();
    }
}
