package com.wanmi.sbc.pay.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.pay.request.PayChannelRequest;

/**
 * @author zhanggaolei
 * @type PayInterface.java
 * @desc
 * @date 2022/11/17 10:29
 */
public interface PayInterface {

    BaseResponse getResult(PayChannelRequest request);

}
