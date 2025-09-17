package com.wanmi.sbc.message;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.message.api.provider.messagesendnode.MessageSendNodeProvider;
import com.wanmi.sbc.message.api.provider.messagesendnode.MessageSendNodeQueryProvider;
import com.wanmi.sbc.message.api.request.messagesendnode.MessageSendNodeByIdRequest;
import com.wanmi.sbc.message.api.request.messagesendnode.MessageSendNodeModifyRequest;
import com.wanmi.sbc.message.api.request.messagesendnode.MessageSendNodePageRequest;
import com.wanmi.sbc.message.api.request.messagesendnode.MessageSendNodeUpdateStatusRequest;
import com.wanmi.sbc.message.api.response.messagesendnode.MessageSendNodeByIdResponse;
import com.wanmi.sbc.message.api.response.messagesendnode.MessageSendNodeModifyResponse;
import com.wanmi.sbc.message.api.response.messagesendnode.MessageSendNodePageResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController("MessageNodeController")
@Validated
@RequestMapping("/messageNode")
@Tag(name = "MessageNodeController", description = "站内信通知节点相关API")
public class MessageNodeController {

    @Autowired
    private MessageSendNodeQueryProvider messageSendNodeQueryProvider;

    @Autowired
    private MessageSendNodeProvider messageSendNodeProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @Operation(summary = "分页查看站内信通知节点")
    public BaseResponse<MessageSendNodePageResponse> page(@Valid @RequestBody MessageSendNodePageRequest request){
        MessageSendNodePageResponse response = messageSendNodeQueryProvider.page(request).getContext();
        return BaseResponse.success(response);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    @Operation(summary = "修改通知节点")
    public BaseResponse modify(@Valid @RequestBody MessageSendNodeModifyRequest request){
        request.setUpdatePerson(commonUtil.getOperatorId());
        BaseResponse<MessageSendNodeModifyResponse> response = messageSendNodeProvider.modify(request);
        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
            operateLogMQUtil.convertAndSend("消息", "修改站内信节点", "站内信节点：" + request.getNodeName());
        }
        return BaseResponse.SUCCESSFUL();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Parameter(name = "id", description = "通知节点id", required = true)
    @Operation(summary = "查看通知节点详情")
    public BaseResponse<MessageSendNodeByIdResponse> getNodeById(@PathVariable("id") Long id){
        MessageSendNodeByIdRequest request = new MessageSendNodeByIdRequest(id);
        MessageSendNodeByIdResponse response = messageSendNodeQueryProvider.getById(request).getContext();
        return BaseResponse.success(response);
    }

    @RequestMapping(value = "/status/{id}", method = RequestMethod.GET)
    @Parameter(name = "id", description = "通知节点id", required = true)
    @Operation(summary = "通知节点开关")
    public BaseResponse<MessageSendNodeByIdResponse> updateStatus(@PathVariable("id") Long id){
        messageSendNodeProvider.updateStatus(new MessageSendNodeUpdateStatusRequest(id));
        return BaseResponse.SUCCESSFUL();
    }
}
