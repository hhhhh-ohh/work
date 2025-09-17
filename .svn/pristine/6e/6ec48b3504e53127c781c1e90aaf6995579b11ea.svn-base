package com.wanmi.sbc.empower.api.provider.channel.vop.message;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.vop.message.VopDeleteMessageRequest;
import com.wanmi.sbc.empower.api.request.vop.message.VopGetMessageRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 京东VOP消息推送API服务
 * @author  hanwei
 * @date 2021/5/13
 **/
@FeignClient(value = "${application.empower.name}", contextId = "VopMessageProvider")
public interface VopMessageProvider {

    /**
     * @description 查询VOP消息
     * @author  hanwei
     * @date 2021/5/13 19:31
     * @param request 
     * @return com.wanmi.sbc.common.base.BaseResponse<java.util.List<com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse>>
     **/
    @PostMapping("/vop/${application.empower.version}/message/get")
    BaseResponse<List<VopMessageResponse>> getMessage(@RequestBody @Valid VopGetMessageRequest request);

    /**
     * @description 删除VOP消息
     * @author  hanwei
     * @date 2021/5/13 19:34
     * @param request 
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/vop/${application.empower.version}/message/delete")
    BaseResponse deleteMessage(@RequestBody @Valid VopDeleteMessageRequest request);
}
