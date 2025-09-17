package com.wanmi.sbc.minimsgactivitysetting;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord.MiniMsgCustomerRecordQueryProvider;
import com.wanmi.sbc.message.api.provider.minimsgactivitysetting.MiniMsgActivitySettingProvider;
import com.wanmi.sbc.message.api.provider.minimsgactivitysetting.MiniMsgActivitySettingQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingAddRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingDelByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingModifyRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingPageRequest;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingAddResponse;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingModifyResponse;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingPageResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "小程序订阅消息配置表管理API", description =  "MiniMsgActivitySettingController")
@RestController
@Validated
@RequestMapping(value = "/minimsgactivitysetting")
public class MiniMsgActivitySettingController {

    @Autowired
    private MiniMsgActivitySettingQueryProvider miniMsgActivitySettingQueryProvider;

    @Autowired
    private MiniMsgActivitySettingProvider miniMsgActivitySettingProvider;

    @Autowired
    private MiniMsgCustomerRecordQueryProvider miniMsgCustomerRecordQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "小程序新增活动消息配置列表分页")
    @PostMapping("/page")
    public BaseResponse<MiniMsgActivitySettingPageResponse> getPage(@RequestBody @Valid MiniMsgActivitySettingPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        pageReq.setDelFlag(DeleteFlag.NO);
        return miniMsgActivitySettingQueryProvider.page(pageReq);
    }

    @Operation(summary = "根据id查询小程序新增活动消息配置")
    @GetMapping("/{id}")
    public BaseResponse<MiniMsgActivitySettingByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        MiniMsgActivitySettingByIdRequest idReq = new MiniMsgActivitySettingByIdRequest();
        idReq.setId(id);
        return miniMsgActivitySettingQueryProvider.getById(idReq);
    }

    @Operation(summary = "查询可推送人数")
    @GetMapping("/pushCount")
    public BaseResponse<Long> getPushCount() {
        return miniMsgCustomerRecordQueryProvider.countRecordsByTriggerNodeId();
    }

    @Operation(summary = "新增小程序新增活动消息配置")
    @PostMapping("/add")
    @MultiSubmit
    public BaseResponse<MiniMsgActivitySettingAddResponse> add(@RequestBody @Valid MiniMsgActivitySettingAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return miniMsgActivitySettingProvider.add(addReq);
    }

    @Operation(summary = "修改小程序新增活动消息配置")
    @PutMapping("/modify")
    public BaseResponse<MiniMsgActivitySettingModifyResponse> modify(@RequestBody @Valid MiniMsgActivitySettingModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return miniMsgActivitySettingProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除小程序新增活动消息配置")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        MiniMsgActivitySettingDelByIdRequest delByIdReq = new MiniMsgActivitySettingDelByIdRequest();
        delByIdReq.setId(id);
        return miniMsgActivitySettingProvider.deleteById(delByIdReq);
    }

}
