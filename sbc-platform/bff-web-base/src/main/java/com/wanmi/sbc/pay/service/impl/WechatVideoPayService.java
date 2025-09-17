package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxChannelsPayRequest;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.bean.PayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhanggaolei
 * @type WechatVideoPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@PayPluginService(type = PayChannelType.WX_VIDEO)
public class WechatVideoPayService extends ExternalPayService<WxChannelsPayRequest> {

    private static final String SELL_PLATFORM_ORDER_ID = "sellPlatformOrderId";

    @Override
    protected void check(PayChannelRequest request) {
        // 验证是否开通视频号增值服务
        if (!commonUtil.findVASBuyOrNot(VASConstants.VAS_WECHAT_CHANNELS)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        // 验证小程序支付
        DefaultFlag wxOpenFlag =
                wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.MINI);
        if (DefaultFlag.NO.equals(wxOpenFlag)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
    }
//
//    @Override
//    protected boolean isPayMember(PayChannelRequest request) {
//        return false;
//    }

    @Override
    protected PayBaseBean payMemberProcess(PayChannelRequest request) {
        throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
    }

    @Override
    protected PayBaseBean tradeProcess(PayChannelRequest request) {
        String id = payServiceHelper.getPayBusinessId(request.getId());
        List<TradeVO> trades = payServiceHelper.checkTrades(id);

        if (CollectionUtils.isEmpty(trades)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }

        if (trades.size() > 1) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }

        Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
        // 视频号不支持授信还款
        if (creditRepayFlag) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070092);
        }
        // 验证订单是否是视频号订单
        TradeVO tradeVO = trades.get(0);
        // SceneGroup= -1 开通视频号流程时输入的强制执行订单
        if (Objects.isNull(tradeVO.getSceneGroup()) || tradeVO.getSceneGroup() != -1) {
            if (!SellPlatformType.WECHAT_VIDEO.equals(tradeVO.getSellPlatformType())
                    || StringUtils.isEmpty(tradeVO.getSellPlatformOrderId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
        }

        if (StringUtils.isBlank(request.getOpenid())
                || !request.getOpenid().equals(tradeVO.getBuyer().getThirdLoginOpenId())) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070099);
        }

        BigDecimal totalPrice = payServiceHelper.calcTotalPriceByPenny(trades);
        Map<String, Object> map = new HashMap<>();
        map.put(SELL_PLATFORM_ORDER_ID, trades.get(0).getSellPlatformOrderId());
        return PayBaseBean.builder()
                .id(id)
                .totalPrice(totalPrice)
                .openId(trades.get(0).getBuyer().getThirdLoginOpenId())
                .extendMap(map)
                .build();
    }

    @Override
    protected WxChannelsPayRequest buildRequest(PayBaseBean bean,PayChannelRequest request) {
        WxChannelsPayRequest buildRequest = new WxChannelsPayRequest();
        buildRequest.setOpenid(bean.getOpenId());
        //注意因为视频号支付必须是实际的订单id所以此处必须放实际的订单id，而不能像其他的支付方式存支付单号
        buildRequest.setOrderId(bean.getId());
        buildRequest.setThirdOrderId(bean.getExtendMap().get(SELL_PLATFORM_ORDER_ID).toString());
        buildRequest.setTotalPrice(bean.getTotalPrice().toString());
        buildRequest.setClientIp(HttpUtil.getIpAddr());
        return buildRequest;
    }

    @Override
    protected BasePayRequest buildPayRequest(WxChannelsPayRequest t,PayBaseBean bean) {
        return BasePayRequest.builder()
                .payType(PayType.WXCHANNELSPAY)
                .wxChannelsPayRequest(t)
                .tradeId(bean.getId())
                .build();
    }
}
