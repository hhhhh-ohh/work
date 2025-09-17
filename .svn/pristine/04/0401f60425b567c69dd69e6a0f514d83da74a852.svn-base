package com.wanmi.sbc.empower.wechatwaybill.service;


import com.wanmi.sbc.empower.api.request.wechatwaybill.TraceWaybillRequest;
import com.wanmi.sbc.empower.wechat.WechatApiUtil;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.trade.TradeGetByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>微信分享配置业务逻辑</p>
 *
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@Service("WechatWaybillService")
public class WechatWaybillService {

    @Autowired
    private WechatApiUtil wechatApiUtil;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    /**
     * 获取 access_token 接口
     *
     * @return 返回获取到的 access_token 字符串
     */
    public String getAccessToken() {
        return wechatApiUtil.getAccessTokenPlus("PUBLIC");
    }

    /**
     * 查询运单物流轨迹信息（trace_waybill）
     *
     * @param accessToken 请求token
     * @param traceWaybillRequest     微信物流传运单接口请求
     * @return 返回获取到的 waybill_token 字符串
     */
    public String traceWaybill(String accessToken, TraceWaybillRequest traceWaybillRequest) {
        return wechatApiUtil.traceWaybill(accessToken, traceWaybillRequest);
    }

    /**
     * 查询运单 status 接口
     *
     * @param accessToken 请求token
     * @param openId 用户 OpenID
     * @param waybillToken 查询id
     * @return 返回运单的物流订单状态（如 4 表示已签收）
     */
    public Integer queryTraceStatus(String accessToken, String openId, String waybillToken) {
        return wechatApiUtil.queryTraceStatus(accessToken, openId, waybillToken);
    }



    /**
     * 同步物流状态
     */
    public void syncState() {

        //1、查询组装微信订单相关信息(已付款)
        //订单信息
        TradeGetByIdResponse tradeGetByIdResponse =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid("45").build())
                        .getContext();

        //2、遍历订单组装微信订单信息

        //3、遍历查询微信物流状态(已完成)

        //4、微信物流状态转换成平台物流状态(已完成)

        //5、更新平台物流状态


    }
}
