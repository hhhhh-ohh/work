package com.wanmi.sbc.empower.sellplatform;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformInitMiniMsgTempRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.miniprogramsubscibe.PlatformMiniMsgTempResponse;

import java.util.List;

/**
*
 * @description    PlatformMiniMsgService 小程序订阅消息相关处理
 * @author  xufeng
 * @date: 2022/08/09 10:19
 **/
public interface PlatformMiniMsgService extends PlatformBaseService {
    /*
     * @description  初始化小程序订阅消息模板
     * @author  xufeng
     * @date: 2022/8/10 10:42
     **/
    BaseResponse<List<PlatformMiniMsgTempResponse>> initMiniProgramSubscribeTemplate(PlatformInitMiniMsgTempRequest request);

    /*
     * @description  发送小程序订阅消息
     * @author  xufeng
     * @date: 2022/8/10 10:42
     **/
    BaseResponse sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest request);
}
