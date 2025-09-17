package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeGetPayOrderByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayCallBackOnlineBatchRequest;
import com.wanmi.sbc.order.api.request.trade.TradeUpdateRequest;
import com.wanmi.sbc.order.bean.dto.PayOrderDTO;
import com.wanmi.sbc.order.bean.dto.TradeDTO;
import com.wanmi.sbc.order.bean.dto.TradePayCallBackOnlineDTO;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.bean.PayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.ServletOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaolei
 * @type UnionCloudPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@Slf4j
@PayPluginService(type = PayChannelType.UNION_CLOUD)
public class UnionCloudPayService extends ExternalPayService<UnionPayRequest> {

    private static final ThreadLocal<List<TradeVO>> BASE_TRADES = new InheritableThreadLocal();
    @Autowired PaySettingQueryProvider paySettingQueryProvider;

    @Autowired TradeProvider tradeProvider;

    @Autowired TradeQueryProvider tradeQueryProvider;

    @Override
    protected void check(PayChannelRequest request) {}

    @Override
    protected UnionPayRequest buildRequest(PayBaseBean bean, PayChannelRequest request) {
        PayGatewayConfigResponse payGatewayConfigResponse =
                paySettingQueryProvider
                        .getGatewayConfigByGateway(
                                new GatewayConfigByGatewayRequest(
                                        PayGatewayEnum.UNIONPAY, Constants.BOSS_DEFAULT_STORE_ID))
                        .getContext();
        // 4.组装消费接口数据
        log.info("payUnionCloud组装银联云闪付支付数据开始");
        UnionPayRequest buildRequest = new UnionPayRequest();
        buildRequest.setOutTradeNo(bean.getPayNo());
        buildRequest.setSubject(bean.getTitle());
        buildRequest.setBody(bean.getBody());
        buildRequest.setAmount(bean.getTotalPrice());
        buildRequest.setApiKey(payGatewayConfigResponse.getApiKey());
        // 前端回调地址 \(^o^)/~ h5  自己传值
        buildRequest.setFrontUrl(request.getSuccessUrl());
        // 后台回调地址
        buildRequest.setNotifyUrl(
                payGatewayConfigResponse.getBossBackUrl() + "/tradeCallback" + "/unionPayCallBack");
        // 来源
        buildRequest.setTerminal(request.getTerminal());
        // 银联支付pc渠道编号
        buildRequest.setChannelItemId(request.getChannelItemId());
        buildRequest.setClientIp(HttpUtil.getIpAddr());
        // 订单有效时间最后的有效时间
        buildRequest.setOrderTimeOut(bean.getOrderTimeOut());
        log.info("payUnionCloud组装调用支付provider开始 requset:{}", buildRequest.toString());
        return buildRequest;
    }

    @Override
    protected BasePayRequest buildPayRequest(UnionPayRequest t,PayBaseBean bean) {
        return BasePayRequest.builder().payType(PayType.UNIONCLONDPAY).unionPayRequest(t).tradeId(bean.getId()).build();
    }

    /**
     * 重写父类方法，为了获得Trade
     *
     * @param id
     * @return
     */
    @Override
    protected List<TradeVO> checkTrades(String id) {
        List<TradeVO> tradeVOS = super.checkTrades(id);

        // 将订单列表放入本地线城中
        BASE_TRADES.set(tradeVOS);
        return tradeVOS;
    }

    @Override
    protected BaseResponse pay(BasePayRequest basePayRequest,PayBaseBean payBaseBean) {
        BaseResponse baseResponse = null;
        String html = null;
        String id = basePayRequest.getUnionPayRequest().getOutTradeNo();
        try {

            html = (String)super.pay(basePayRequest,payBaseBean).getContext();
            //todo 此处待确认是否可以去掉
            if (!payServiceHelper.isPayMember(id)) {
                log.info("payUnionCloud组装调用支付provider结束,开始更新订单数据");
                TradeVO trade = BASE_TRADES.get().get(0);
                // 6.初步更新订单的开始支付时间数据
                trade.getTradeState().setStartPayTime(LocalDateTime.now());
                tradeProvider.update(
                        TradeUpdateRequest.builder()
                                .trade(KsBeanUtil.convert(trade, TradeDTO.class))
                                .build());
                log.info(
                        "结束更新订单数据，订单号：{},开始支付时间：{StartPayTime}",
                        trade.getTradeState().getStartPayTime());
            }
        } catch (SbcRuntimeException e) {
            log.error("银联支付异常", e);
            if (e.getErrorCode() != null && e.getErrorCode().equals(EmpowerErrorCodeEnum.K060003.getCode())) {
                // 已支付，手动回调
                Operator operator =
                        Operator.builder()
                                .ip(HttpUtil.getIpAddr())
                                .adminId("1")
                                .name("SYSTEM")
                                .platform(Platform.BOSS)
                                .build();
                if (!payServiceHelper.isCreditRepayFlag(id)) {
                    // 订单回调
                    List<TradePayCallBackOnlineDTO> list = new ArrayList<>();
                    BASE_TRADES
                            .get()
                            .forEach(
                                    i -> {
                                        // 获取订单信息
                                        PayOrderVO payOrder =
                                                tradeQueryProvider
                                                        .getPayOrderById(
                                                                TradeGetPayOrderByIdRequest
                                                                        .builder()
                                                                        .payOrderId(
                                                                                i.getPayOrderId())
                                                                        .build())
                                                        .getContext()
                                                        .getPayOrder();
                                        TradePayCallBackOnlineDTO dto =
                                                TradePayCallBackOnlineDTO.builder()
                                                        .payOrderOld(
                                                                KsBeanUtil.convert(
                                                                        payOrder,
                                                                        PayOrderDTO.class))
                                                        .trade(
                                                                KsBeanUtil.convert(
                                                                        i, TradeDTO.class))
                                                        .build();
                                        list.add(dto);
                                    });
                    tradeProvider.payCallBackOnlineBatch(
                            TradePayCallBackOnlineBatchRequest.builder()
                                    .requestList(list)
                                    .operator(operator)
                                    .build());
                }
            }
        }catch (Exception e) {
            log.error("银联支付异常", e);
        } finally {
            BASE_TRADES.remove();
            if (StringUtils.isBlank(html)) {
                html = "发生未知错误，请重试";
            }
            baseResponse = BaseResponse.success(html);
        }
        return baseResponse;
    }

    /**
     * 放在此处处理防止被父类的方法篡改
     *
     * @param baseResponse
     * @return
     */
    @Override
    protected BaseResponse resultProcess(BaseResponse baseResponse) {

        HttpServletResponse response = HttpUtil.getResponse();
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write((String)baseResponse.getContext()); // 直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("银联支付将生成的html写到浏览器失败", e);
        }
        return null;
    }
}
