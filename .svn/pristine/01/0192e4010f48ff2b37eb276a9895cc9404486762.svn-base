package com.wanmi.sbc.job;

import com.wanmi.sbc.empower.api.provider.pay.weixin.WxPayProvider;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayMsgJumpPathSetRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信消息跳转路径设置
 */
@Component
public class WechatPayMsgJumpPathSetHandler {
    
    @Autowired private WxPayProvider wxPayProvider;

    @XxlJob(value = "wechatPayMsgJumpPathSetHandler")
    public void execute() throws Exception {
        String path = XxlJobHelper.getJobParam();
        WxPayMsgJumpPathSetRequest setRequest = new WxPayMsgJumpPathSetRequest();
        setRequest.setPath(path);
        wxPayProvider.setMsgJumpPath(setRequest);
    }
}
