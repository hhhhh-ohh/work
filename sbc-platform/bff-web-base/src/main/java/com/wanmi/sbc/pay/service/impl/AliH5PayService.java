package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.ali.PayExtraRequest;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeGetPayOrderByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayCallBackOnlineBatchRequest;
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
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zhanggaolei
 * @type AliH5PayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@Slf4j
@PayPluginService(type = PayChannelType.ALI_H5)
public class AliH5PayService extends ExternalPayService<PayExtraRequest> {

    private static final ThreadLocal<List<TradeVO>> BASE_TRADES =
            new InheritableThreadLocal();

    @Autowired TradeProvider tradeProvider;

    @Autowired TradeQueryProvider tradeQueryProvider;

    @Override
    protected void check(PayChannelRequest request) {}

    @Override
    protected PayExtraRequest buildRequest(PayBaseBean bean, PayChannelRequest request) {
        PayExtraRequest buildRequest = new PayExtraRequest();

        buildRequest.setChannelItemId(request.getChannelItemId());
        buildRequest.setTerminal(request.getTerminal());
        if (TerminalType.H5.equals(request.getTerminal())|| TerminalType.PC.equals(request.getTerminal())) {
            buildRequest.setSuccessUrl(request.getSuccessUrl());
        }
        buildRequest.setOpenId(request.getOpenid());

        buildRequest.setAmount(bean.getTotalPrice());
        buildRequest.setOutTradeNo(bean.getPayNo());
        buildRequest.setSubject(bean.getTitle());
        buildRequest.setBody(bean.getBody());

        buildRequest.setClientIp(HttpUtil.getIpAddr());
        buildRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);

        return buildRequest;
    }

    @Override
    protected BasePayRequest buildPayRequest(PayExtraRequest t,PayBaseBean bean) {
        return BasePayRequest.builder().payType(PayType.ALIPAY).payExtraRequest(t).tradeId(bean.getId()).build();
    }

    /**
     * 重写父类方法，为了获得Trade
     * @param id
     * @return
     */
    @Override
    protected List<TradeVO> checkTrades(String id) {
        List<TradeVO> tradeVOS = super.checkTrades(id);

        //将订单列表放入本地线城中
        BASE_TRADES.set(tradeVOS);
        return tradeVOS;
    }

    @Override
    protected BaseResponse pay(BasePayRequest basePayRequest, PayBaseBean payBaseBean) {
        BaseResponse baseResponse = null;
        try {
            baseResponse = super.pay(basePayRequest,payBaseBean);
            LinkedHashMap linkedHashMap = (LinkedHashMap) baseResponse.getContext();
            String.valueOf(linkedHashMap.get("form"));
        } catch (SbcRuntimeException e) {
            if (e.getErrorCode() != null && e.getErrorCode().equals(EmpowerErrorCodeEnum.K060003.getCode())) {
                // 已支付，手动回调
                Operator operator =
                        Operator.builder()
                                .ip(HttpUtil.getIpAddr())
                                .adminId("1")
                                .name("SYSTEM")
                                .platform(Platform.BOSS)
                                .build();
                String id = payBaseBean.getId();
                if (!payServiceHelper.isPayMember(id)) {
                    List<TradePayCallBackOnlineDTO> list = new ArrayList<>();
                    BASE_TRADES.get().forEach(
                            i -> {
                                // 获取订单信息
                                PayOrderVO payOrder =
                                        tradeQueryProvider
                                                .getPayOrderById(
                                                        TradeGetPayOrderByIdRequest.builder()
                                                                .payOrderId(i.getPayOrderId())
                                                                .build())
                                                .getContext()
                                                .getPayOrder();
                                TradePayCallBackOnlineDTO dto =
                                        TradePayCallBackOnlineDTO.builder()
                                                .payOrderOld(
                                                        KsBeanUtil.convert(
                                                                payOrder, PayOrderDTO.class))
                                                .trade(KsBeanUtil.convert(i, TradeDTO.class))
                                                .build();
                                list.add(dto);
                            });
                    tradeProvider.payCallBackOnlineBatch(
                            TradePayCallBackOnlineBatchRequest.builder()
                                    .requestList(list)
                                    .operator(operator)
                                    .build());

                } else {
                    tradeProvider.payCallBackOnlineBatch(
                            TradePayCallBackOnlineBatchRequest.builder()
                                    .recordId(id)
                                    .operator(operator)
                                    .build());
                }
            }
            throw e;
//            throw new SbcRuntimeException(e.getErrorCode(), e.getParams());
        }finally{
            BASE_TRADES.remove();
        }
        return baseResponse;
    }

    /**
     * 放在此处处理防止被父类的方法篡改
     * @param baseResponse
     * @return
     */
    @Override
    protected BaseResponse resultProcess(BaseResponse baseResponse) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) baseResponse.getContext();
        String form = String.valueOf(linkedHashMap.get("form"));
        HttpServletResponse response = HttpUtil.getResponse();
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            // TODO: 2019-01-28 gb支付异常未处理
            log.error("execute alipay has IO exception:{} ", e);
        }
        return null;
    }
}
