package com.wanmi.sbc.pay.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.customer.service.LoginBaseService;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaAllPayRequest;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.bean.PayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.LakalaWeChatPayRequest;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhanggaolei
 * @type LakalaWechatScanPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@Slf4j
@PayPluginService(type = PayChannelType.LAKALA_WX_SCAN)
public class LakalaWechatScanPayService extends ExternalPayService<LakalaAllPayRequest> {
    private static final String LAKALA_BEAN = "lakalaBean";
    @Autowired RedisUtil redisUtil;

    @Autowired LoginBaseService loginBaseService;

    @Autowired WechatAuthProvider wechatAuthProvider;

    @Override
    protected void check(PayChannelRequest request) {}

    @Override
    protected PayBaseBean payMemberProcess(PayChannelRequest request) {
        throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
    }

    @Override
    protected LakalaAllPayRequest buildRequest(PayBaseBean bean, PayChannelRequest request) {
        LakalaWeChatPayRequest lakalaWeChatPayRequest = new LakalaWeChatPayRequest();
        if (request.getId().startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID)) {
            lakalaWeChatPayRequest.setParentTid(request.getId());
        } else {
            lakalaWeChatPayRequest.setTid(request.getId());
        }
        lakalaWeChatPayRequest.setChannelItemId(request.getChannelItemId().toString());
        lakalaWeChatPayRequest.setTemporaryCode(loginBaseService.getTemporaryCode());
        lakalaWeChatPayRequest.setPaymentPrice(bean.getTotalPrice().toString());
        Map<String, Object> extendMap = new HashMap<>();
        extendMap.put(LAKALA_BEAN, JSONObject.toJSONString(lakalaWeChatPayRequest));
        bean.setExtendMap(extendMap);
        return null;
    }

    @Override
    protected BasePayRequest buildPayRequest(LakalaAllPayRequest t,PayBaseBean bean) {
        return null;
    }

    @Override
    protected BaseResponse pay(BasePayRequest basePayRequest, PayBaseBean payBaseBean) {
        String customerId =
                Objects.requireNonNull(commonUtil.getOperatorId())
                        .concat(StringUtil.generateNonceStr(5));

        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        // 小程序跳转链接
        String code =
                RedisKeyConstant.QR_CODE_LINK.concat(
                        (MD5Util.md5Hex(customerId, "utf-8")).toUpperCase().substring(16));
        //  存入redis，前端调用使用
        if (StringUtils.isNotBlank(code)) {
            redisUtil.setString(
                    code, (String) payBaseBean.getExtendMap().get(LAKALA_BEAN), 15000000L);
        }
        // 组装获取小程序码参数
        request.setPage("pages/package-C/order/lakala-payment-success/index");
        request.setScene("LA".concat(code));
        request.setCode(code);
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }

    /**
     * 拉卡拉支付不需要校验
     *
     * @param trades
     */
    @Override
    protected void checkLakala(List<TradeVO> trades) {}
}
