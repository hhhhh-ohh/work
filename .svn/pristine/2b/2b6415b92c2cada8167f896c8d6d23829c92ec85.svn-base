package com.wanmi.sbc.empower.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderConfirmReceivedRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderQuerySkuListRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderCreateRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderPayRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelOrderCreateResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelOrderQuerySkuListResponse;

/**
 * @description 对接第三方渠道订单服务
 * @author daiyitian
 * @date 2021/5/11 14:36
 */
public interface ChannelOrderBaseService extends ChannelBaseService{

    /**
     * 订单创建
     *
     * @description 订单创建
     * @author daiyitian
     * @date 2021/5/11 14:37
     * @param request 请求参数
     * @return com.wanmi.sbc.empower.api.response.channel.base.ChannelOrderCreateResponse 创建结果
     */
    ChannelOrderCreateResponse create(ChannelOrderCreateRequest request);

    /**
     * 订单支付
     *
     * @description 订单支付
     * @author daiyitian
     * @date 2021/5/11 14:37
     * @param request 请求参数
     * @return com.wanmi.sbc.common.base.BaseResponse 支付结果
     */
    BaseResponse pay(ChannelOrderPayRequest request);

    /**
     * 订单确认收货
     *
     * @description 订单确认收货
     * @author daiyitian
     * @date 2021/5/11 14:38
     * @param request 请求参数
     * @return com.wanmi.sbc.common.base.BaseResponse 确认收货结果
     */
    BaseResponse confirmReceived(ChannelOrderConfirmReceivedRequest request);

    /**
     * @description 查询订单SKU列表
     * @author  hanwei
     * @date 2021/5/29 14:27
     * @param request
     * @return java.lang.Object
     **/
    ChannelOrderQuerySkuListResponse batchQueryOrderSkuList(ChannelOrderQuerySkuListRequest request);
}
