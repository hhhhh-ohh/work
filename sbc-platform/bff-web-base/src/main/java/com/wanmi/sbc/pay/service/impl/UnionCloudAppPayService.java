package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.unioncloud.UnionCloudPayProvider;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesAddRequest;
import com.wanmi.sbc.order.api.request.trade.TradeUpdateRequest;
import com.wanmi.sbc.order.bean.dto.TradeDTO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.bean.PayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhanggaolei
 * @type UnionCloudAppPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@Slf4j
@PayPluginService(type = PayChannelType.UNION_CLOUD_APP)
public class UnionCloudAppPayService extends ExternalPayService<UnionPayRequest> {

    private static final ThreadLocal<List<TradeVO>> BASE_TRADES = new InheritableThreadLocal();
    @Autowired PaySettingQueryProvider paySettingQueryProvider;

    @Autowired TradeProvider tradeProvider;

    @Autowired TradeQueryProvider tradeQueryProvider;

    @Autowired UnionCloudPayProvider unionCloudPayProvider;

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
        UnionPayRequest buildRequest = new UnionPayRequest();
        buildRequest.setOutTradeNo(bean.getPayNo());
        buildRequest.setSubject(bean.getTitle());
        buildRequest.setBody(bean.getBody());
        buildRequest.setAmount(bean.getTotalPrice());
        buildRequest.setApiKey(payGatewayConfigResponse.getApiKey());
        buildRequest.setFrontUrl(request.getSuccessUrl());
        // 后台回调地址
        buildRequest.setNotifyUrl(
                payGatewayConfigResponse.getBossBackUrl() + "/tradeCallback/unionPayCallBack");
        // 来源
        buildRequest.setTerminal(TerminalType.APP);
        // 银联支付pc渠道编号
        buildRequest.setChannelItemId(29L);
        buildRequest.setClientIp(HttpUtil.getIpAddr());
        // 订单有效时间最后的有效时间
        buildRequest.setOrderTimeOut(bean.getOrderTimeOut());
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
        String id = basePayRequest.getUnionPayRequest().getOutTradeNo();


        // todo 此处待确认是否可以去掉
        if (!payServiceHelper.isPayMember(id)) {
            log.info("payUnionCloud组装调用支付provider结束,开始更新订单数据");
            TradeVO trade = BASE_TRADES.get().get(0);
            // 6.初步更新订单的开始支付时间数据
            trade.getTradeState().setStartPayTime(LocalDateTime.now());
            tradeProvider.update(
                    TradeUpdateRequest.builder()
                            .trade(KsBeanUtil.convert(trade, TradeDTO.class))
                            .build());
        }
        try {
            // 增加支付流水，每次点击去支付都会重新生成一条记录
            PayTimeSeriesAddRequest payTimeSeriesAddRequest = new PayTimeSeriesAddRequest();
            payTimeSeriesAddRequest.setPayChannelType(payBaseBean.getPayChannelType().name());
            //视频号必须传真实的订单id
            payTimeSeriesAddRequest.setPayNo(payBaseBean.getPayNo());
            payTimeSeriesAddRequest.setApplyPrice(payBaseBean.getTotalPrice());
            payTimeSeriesAddRequest.setBusinessId(payBaseBean.getId());
            payTimeSeriesAddRequest.setClientIp(HttpUtil.getIpAddr());
            payTimeSeriesAddRequest.setChannelItemId(payBaseBean.getChannelItemId()!=null?payBaseBean.getChannelItemId():payBaseBean.getPayChannelType().getChannelItem());
            payTimeSeriesProvider.add(payTimeSeriesAddRequest);
            baseResponse =  unionCloudPayProvider.getTn(basePayRequest);
        }catch (SbcRuntimeException e){
            throw e;
        }finally {
            BASE_TRADES.remove();

        }
        return baseResponse;
    }

}
