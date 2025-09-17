package com.wanmi.sbc.pushsend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.message.api.provider.pushsendnode.PushSendNodeProvider;
import com.wanmi.sbc.message.api.provider.pushsendnode.PushSendNodeQueryProvider;
import com.wanmi.sbc.message.api.request.pushsendnode.PushSendNodeByIdRequest;
import com.wanmi.sbc.message.api.request.pushsendnode.PushSendNodeModifyRequest;
import com.wanmi.sbc.message.api.request.pushsendnode.PushSendNodePageRequest;
import com.wanmi.sbc.message.api.response.pushsendnode.PushSendNodeByIdResponse;
import com.wanmi.sbc.message.api.response.pushsendnode.PushSendNodeModifyResponse;
import com.wanmi.sbc.message.api.response.pushsendnode.PushSendNodePageResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * @program: sbc-micro-service
 * @description: 推送通知节点
 * @create: 2020-01-13 10:40
 **/
@Tag(name =  "推送通知节点管理API", description =  "PushSendNodeController")
@RestController
@Validated
@RequestMapping(value = "/pushsendnode")
public class PushSendNodeController {

    @Autowired
    private PushSendNodeProvider pushSendNodeProvider;

    @Autowired
    private PushSendNodeQueryProvider pushSendNodeQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "修改通知节点")
    @PostMapping("/modify")
    public BaseResponse<PushSendNodeModifyResponse> modify(@RequestBody @Valid PushSendNodeModifyRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        return pushSendNodeProvider.modify(request);
    }

    @Operation(summary = "根据id查询通知节点")
    @Parameter(name = "id", description = "通知节点id", required = true)
    @GetMapping("/{id}")
    public BaseResponse<PushSendNodeByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PushSendNodeByIdRequest idReq = new PushSendNodeByIdRequest();
        idReq.setId(id);
        return pushSendNodeQueryProvider.getById(idReq);
    }

    @Operation(summary = "分页查询通知节点")
    @PostMapping("/page")
    public BaseResponse<PushSendNodePageResponse> getPage(@RequestBody @Valid PushSendNodePageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        return pushSendNodeQueryProvider.page(pageReq);
    }

    @Operation(summary = "通知节点开关设置")
    @Parameters({
            @Parameter(name = "id", description = "开关标示", required = true),
            @Parameter(name = "status", description = "状态", required = true)
    })
    @PutMapping("/enabled/{id}/{status}")
    public BaseResponse enabled(@PathVariable Long id, @PathVariable Integer status) {
        return pushSendNodeProvider.enabled(PushSendNodeModifyRequest.builder().id(id).status(status).build());
    }


}