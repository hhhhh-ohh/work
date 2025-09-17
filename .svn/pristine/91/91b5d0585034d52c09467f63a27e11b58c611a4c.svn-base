package com.wanmi.sbc.pay;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.customer.service.LoginBaseService;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.request.LakalaWeChatPayRequest;
import com.wanmi.sbc.trade.PayServiceHelper;
import com.wanmi.sbc.util.CommonUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author edz
 * @className PcPayController
 * @description TODO
 * @date 2022/7/21 21:53
 **/
@Slf4j
@RestController
@Validated
@RequestMapping("/pc")
@Tag(name = "PcPayController", description = "S2B pc专用-拉卡拉微信支付")
public class PcPayController {

    @Autowired
    private PayServiceHelper payServiceHelper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;

    @Autowired
    private LoginBaseService loginBaseService;

    @PostMapping(value = "/lakala/wechat/pay/getQrCode")
    public BaseResponse<String> getRecommendQrCode(@RequestBody LakalaWeChatPayRequest lakalaWeChatPayRequest) {
        String customerId = Objects.requireNonNull(commonUtil.getOperatorId()).concat(StringUtil.generateNonceStr(5));
        String temporaryCode = loginBaseService.getTemporaryCode();
        lakalaWeChatPayRequest.setTemporaryCode(temporaryCode);

        // 订单金额
        String id =
                payServiceHelper.getPayBusinessId(
                        lakalaWeChatPayRequest.getTid(), lakalaWeChatPayRequest.getParentTid(), null);

        List<TradeVO> trades = payServiceHelper.checkTrades(id);
        // 支付总金额
        Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
        BigDecimal totalPrice;
        if (creditRepayFlag) {
            totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
        } else {
            totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
        }
        lakalaWeChatPayRequest.setPaymentPrice(totalPrice.toString());

        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        // 小程序跳转链接
        String code = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(customerId, "utf-8")).toUpperCase().substring(16));
        //  存入redis，前端调用使用
        if (StringUtils.isNotBlank(code)) {
            redisUtil.setString(code, JSON.toJSONString(lakalaWeChatPayRequest), 15000000L);
        }
        // 组装获取小程序码参数
        request.setPage("pages/package-C/order/lakala-payment-success/index");
        request.setScene("LA".concat(code));
        request.setCode(code);
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }
}
