package com.wanmi.sbc.empower.sms.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sms.SmsSendRequest;

/**
 * 对接第三方短信平台发生短信接口
 */
public interface SmsSendBaseService {

    BaseResponse send(SmsSendRequest smsSendRequest);

}
