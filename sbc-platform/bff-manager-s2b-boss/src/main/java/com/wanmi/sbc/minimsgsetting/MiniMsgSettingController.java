package com.wanmi.sbc.minimsgsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.provider.minimsgsetting.MiniMsgSettingProvider;
import com.wanmi.sbc.message.api.provider.minimsgsetting.MiniMsgSettingQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingListRequest;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingModifyRequest;
import com.wanmi.sbc.message.api.response.minimsgsetting.MiniMsgSettingListResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "小程序订阅消息配置表管理API", description =  "MiniMsgSettingController")
@RestController
@Validated
@RequestMapping(value = "/minimsgsetting")
public class MiniMsgSettingController {

    @Autowired
    private MiniMsgSettingQueryProvider miniMsgSettingQueryProvider;

    @Autowired
    private MiniMsgSettingProvider miniMsgSettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "列表查询小程序订阅消息配置表")
    @GetMapping("/list")
    public BaseResponse<MiniMsgSettingListResponse> getList() {
        return miniMsgSettingQueryProvider.list(MiniMsgSettingListRequest.builder().build());
    }

    @Operation(summary = "修改小程序订阅消息配置表")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid MiniMsgSettingModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return miniMsgSettingProvider.modify(modifyReq);
    }

}
