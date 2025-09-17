package com.wanmi.sbc.job.jdvop.service;

import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;

import java.util.List;

/**
 * @description 京东VOP消息处理器接口
 * @author  hanwei
 * @date 2021/6/1
 **/
public interface JdVopMessageHandler {

    /**
     * 要处理的京东VOP消息类型
     * 1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更
     * @return
     */
    Integer getVopMessageType();

    /**
     * @description 消息处理
     * @author  hanwei
     * @date 2021/6/2 10:26
     * @param messageList
     * @return java.util.List<java.lang.String>
     **/
    List<String> handleMessage(List<VopMessageResponse> messageList);
}
