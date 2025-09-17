package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.order.api.provider.payingmemberrecordtemp.PayingMemberRecordTempQueryProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesProvider;
import com.wanmi.sbc.order.api.provider.plugin.PluginPayInfoProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempByIdRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesAddRequest;
import com.wanmi.sbc.order.api.request.plugin.PluginPayInfoAddRequest;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordTempVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.bean.PayBaseBean;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import com.wanmi.sbc.pay.service.PayInterface;
import com.wanmi.sbc.third.wechat.WechatSetService;
import com.wanmi.sbc.trade.PayServiceHelper;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhanggaolei
 * @type ExternalPayService.java
 * @desc 外部支付渠道处理类
 * @date 2022/11/17 11:04
 */
@Slf4j
@Service
public abstract class ExternalPayService<T> implements PayInterface {
    @Autowired PayServiceHelper payServiceHelper;

    @Autowired PayProvider payProvider;

    @Autowired PluginPayInfoProvider pluginPayInfoProvider;

    @Autowired WechatSetService wechatSetService;

    @Autowired PayingMemberRecordTempQueryProvider payingMemberRecordTempQueryProvider;

    @Autowired PayTimeSeriesProvider payTimeSeriesProvider;

    @Autowired CommonUtil commonUtil;

    @Override
    public BaseResponse getResult(PayChannelRequest request) {
        this.check(request);
        boolean isPayMember = isPayMember(request);
        PayBaseBean payBaseBean = new PayBaseBean();
        if (isPayMember) {
            payBaseBean = payMemberProcess(request);
        } else {
            payBaseBean = tradeProcess(request);
        }
        if (StringUtils.isEmpty(payBaseBean.getOpenId())) {
            payBaseBean.setOpenId(request.getOpenid());
        }
        T t = buildRequest(payBaseBean, request);

        //        if(!isPayMember){
        //            addPayInfo(t,payBaseBean.getId());
        //        }
        BasePayRequest basePayRequest = buildPayRequest(t, payBaseBean);
        BaseResponse baseResponse = this.pay(basePayRequest, payBaseBean);

        return resultProcess(baseResponse);
    }

    protected void check(PayChannelRequest request) {
        // 验证H5支付开关
        DefaultFlag wxOpenFlag =
                wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.H5);
        if (DefaultFlag.NO.equals(wxOpenFlag)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
    }

    protected boolean isPayMember(PayChannelRequest request) {
        return payServiceHelper.isPayMember(request.getId());
    }

    protected PayBaseBean payMemberProcess(PayChannelRequest request) {

        String id = request.getId();
        // 根据付费记录id 查询记录
        PayingMemberRecordTempVO payingMemberRecordTempVO =
                payingMemberRecordTempQueryProvider
                        .getById(PayingMemberRecordTempByIdRequest.builder().recordId(id).build())
                        .getContext()
                        .getPayingMemberRecordTempVO();
        String body =
                payingMemberRecordTempVO
                        .getLevelName()
                        .concat("付费会员")
                        .concat(payingMemberRecordTempVO.getPayNum() + "个月");
        String title = payingMemberRecordTempVO.getLevelName().concat("付费会员");
        BigDecimal totalPrice = payingMemberRecordTempVO.getPayAmount();

        return PayBaseBean.builder()
                .totalPrice(totalPrice)
                .body(body)
                .title(title)
                .id(id)
                .payNo(id)
                .payChannelType(request.getPayChannelType())
                .build();
    }

    protected PayBaseBean tradeProcess(PayChannelRequest request) {
        LocalDateTime orderTimeOut = LocalDateTime.now().plusMinutes(10L);

        BigDecimal totalPrice;
        String id = payServiceHelper.getPayBusinessId(request.getId());
        List<TradeVO> trades = this.checkTrades(id);
        // 订单总金额
        Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
        if (creditRepayFlag) {
            totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
        } else {
            this.checkLakala(trades);
            totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
            orderTimeOut = trades.get(0).getOrderTimeOut();
        }
        String body = payServiceHelper.buildBody(trades);
        String title = payServiceHelper.buildTitle(trades);
        String productId = trades.get(0).getTradeItems().get(0).getSkuId();
        //String payNo = payServiceHelper.regenerateId(id);
        String payNo = id + "_pay_no" ;
        return PayBaseBean.builder()
                .totalPrice(totalPrice)
                .body(body)
                .title(title)
                .id(id)
                .payNo(payNo)
                .productId(productId)
                .orderTimeOut(orderTimeOut)
                .payChannelType(request.getPayChannelType())
                .channelItemId(request.getChannelItemId())
                .build();
    }

    /**
     * 该实现目前是跨境支付使用
     *
     * @param t
     * @param id
     */
    protected void addPayInfo(T t, String id) {
        // 保存支付请求信息到插件
        PluginPayInfoAddRequest pluginPayInfoAddRequest = new PluginPayInfoAddRequest();
        pluginPayInfoAddRequest.setOrderCode(id);
        pluginPayInfoAddRequest.setPayRequest(t.toString());
        pluginPayInfoProvider.add(pluginPayInfoAddRequest);
    }

    /**
     * 使用者自行实现对应的方法 此处用于构建对应的对应渠道的request
     *
     * @param bean
     * @return
     */
    protected abstract T buildRequest(PayBaseBean bean, PayChannelRequest request);

    /**
     * 使用者自行实现对应的方法 此处用于构建BasePayRequest
     *
     * @param t
     * @return
     */
    protected abstract BasePayRequest buildPayRequest(T t, PayBaseBean bean);

    protected BaseResponse pay(BasePayRequest basePayRequest, PayBaseBean payBaseBean) {

        // 如果是付费会员或者是授信支付则直接跳过
        // 将此记录放置在调用第三方支付接口之前，防止回调了还没有生成记录问题
        if (!payServiceHelper.isPayMember(payBaseBean.getId())) {

            // 增加支付流水，每次点击去支付都会重新生成一条记录
            PayTimeSeriesAddRequest payTimeSeriesAddRequest = new PayTimeSeriesAddRequest();
            payTimeSeriesAddRequest.setPayChannelType(payBaseBean.getPayChannelType().name());
            //视频号必须传真实的订单id
            payTimeSeriesAddRequest.setPayNo(
                    payBaseBean.getPayChannelType().equals(PayChannelType.WX_VIDEO)
                            ? payBaseBean.getId()
                            : payBaseBean.getPayNo());
            payTimeSeriesAddRequest.setApplyPrice(payBaseBean.getTotalPrice());
            payTimeSeriesAddRequest.setBusinessId(payBaseBean.getId());
            payTimeSeriesAddRequest.setClientIp(HttpUtil.getIpAddr());
            payTimeSeriesAddRequest.setChannelItemId(payBaseBean.getChannelItemId()!=null?payBaseBean.getChannelItemId():payBaseBean.getPayChannelType().getChannelItem());
            payTimeSeriesProvider.add(payTimeSeriesAddRequest);
        }
        BaseResponse baseResponse = payProvider.pay(basePayRequest);

        return baseResponse;
    }

    protected BaseResponse resultProcess(BaseResponse baseResponse) {
        return baseResponse;
    }

    protected List<TradeVO> checkTrades(String id) {
        return payServiceHelper.checkTrades(id);
    }

    protected void checkLakala(List<TradeVO> trades) {
        if (commonUtil.lakalaPayIsOpen()) {
            log.warn(
                    "非法支付渠道：订单ID：{}，用户ID：{}",
                    trades.get(0).getId(),
                    trades.get(0).getBuyer().getAccount());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }
}
