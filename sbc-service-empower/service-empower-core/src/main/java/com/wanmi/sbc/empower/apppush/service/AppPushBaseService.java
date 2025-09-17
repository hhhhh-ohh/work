package com.wanmi.sbc.empower.apppush.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushCancelRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushQueryRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSendRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushCancelResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushQueryResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSendResponse;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * 对接第三方APP消息推送接口
 */
public interface AppPushBaseService {

    /**
     * 消息发送
     * @param request
     * @return
     */
    BaseResponse<AppPushSendResponse> send(@RequestBody @Valid AppPushSendRequest request);

    /**
     * 任务类消息状态查询
     * @param request
     * @return
     */
    BaseResponse<AppPushQueryResponse> query(@RequestBody @Valid AppPushQueryRequest request);

    /**
     * 任务类消息取消
     * @param request
     * @return
     */
    BaseResponse<AppPushCancelResponse> cancel(@RequestBody @Valid AppPushCancelRequest request);

}
