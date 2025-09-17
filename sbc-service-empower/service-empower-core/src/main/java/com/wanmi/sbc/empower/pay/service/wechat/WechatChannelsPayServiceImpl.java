package com.wanmi.sbc.empower.pay.service.wechat;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayCloseOrderRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.*;
import com.wanmi.sbc.empower.api.request.sellplatform.order.PlatformOrderRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.returnorder.PlatformReturnOrderRequest;
import com.wanmi.sbc.empower.api.response.pay.weixin.*;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformOrderPayParamResponse;
import com.wanmi.sbc.empower.bean.constant.PayServiceConstants;
import com.wanmi.sbc.empower.pay.service.PayBaseService;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformOrderService;
import com.wanmi.sbc.empower.sellplatform.PlatformReturnOrderService;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @description     微信视频号支付
 * @author  wur
 * @date: 2022/4/21 19:20
 **/
@Slf4j
@Service(PayServiceConstants.WECHAT_CHANNELS)
public class WechatChannelsPayServiceImpl implements PayBaseService {


    @Autowired private PayTradeRecordProvider payTradeRecordProvider;

    @Autowired private PlatformContext thirdPlatformContext;

    /**
     * 添加订单支付--交易记录
     *
     * @param request
     */
    private void addTradeRecord(PayTradeRecordRequest request) {
        payTradeRecordProvider.queryAndSave(request);
    }

    /**
     * @return
     * @Description 微信支付查询退款单
     * @Date 14:27 2020/9/17
     * @Param [request]
     **/

    @Override
    public BaseResponse getPayOrderDetail(PayOrderDetailRequest request) {
        PayOrderDetailResponse payOrderDetailResponse = new PayOrderDetailResponse();
        return BaseResponse.success(payOrderDetailResponse);
    }

    @Override
    public BaseResponse payCloseOrder(PayCloseOrderRequest request) {
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse payRefund(PayRefundBaseRequest payBaseRequest) {
        WxChannelsPayRefundRequest wxChannelsRefundRequest = payBaseRequest.getWxChannelsRefundRequest();

        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(SellPlatformType.WECHAT_VIDEO, PlatformServiceType.RETURN_ORDER_SERVICE);

        return returnOrderService.acceptRefund(
                PlatformReturnOrderRequest.builder()
                        .out_aftersale_id(wxChannelsRefundRequest.getOut_aftersale_id())
                        .build());
    }


    @Override
    public BaseResponse pay(BasePayRequest basePayRequest) {
        //视频号获取支付参数处理
        WxChannelsPayRequest wxChannelsPayRequest = basePayRequest.getWxChannelsPayRequest();
        PlatformOrderRequest channelsOrderRequest = PlatformOrderRequest.builder()
                .order_id(wxChannelsPayRequest.getThirdOrderId())
                .out_order_id(wxChannelsPayRequest.getOrderId())
                .openid(wxChannelsPayRequest.getOpenid())
                .build();
        //添加交易记录
        PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
        payTradeRecordRequest.setBusinessId(wxChannelsPayRequest.getOrderId());
        payTradeRecordRequest.setApplyPrice(new BigDecimal(wxChannelsPayRequest.getTotalPrice()).divide(new BigDecimal(100)).
                setScale(2, RoundingMode.DOWN));
        payTradeRecordRequest.setClientIp(wxChannelsPayRequest.getClientIp());
        payTradeRecordRequest.setChannelItemId(Constants.NUM_30L);
        addTradeRecord(payTradeRecordRequest);

        PlatformOrderService orderService =
                thirdPlatformContext.getPlatformService(SellPlatformType.WECHAT_VIDEO, PlatformServiceType.ORDER_SERVICE);

        BaseResponse<PlatformOrderPayParamResponse> channelsBaseResponse = orderService.getOrderPayParam(channelsOrderRequest);

        if (!CommonErrorCodeEnum.K000000.getCode().equals(channelsBaseResponse.getCode())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        PlatformOrderPayParamResponse payParamResponse = channelsBaseResponse.getContext();
        return BaseResponse.success(WxChannelsPayParamResponse.builder()
                .payPackage(payParamResponse.getPay_package())
                .paySign(payParamResponse.getPaySign())
                .signType(payParamResponse.getSignType())
                .nonceStr(payParamResponse.getNonceStr())
                .timeStamp(payParamResponse.getTimeStamp())
                .build());
    }

}
