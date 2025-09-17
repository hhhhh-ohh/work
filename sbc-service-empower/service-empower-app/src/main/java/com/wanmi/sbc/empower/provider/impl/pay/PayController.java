package com.wanmi.sbc.empower.provider.impl.pay;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.request.pay.*;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaPayVerifySignRequest;
import com.wanmi.sbc.empower.api.response.pay.RefundResponse;
import com.wanmi.sbc.empower.api.response.pay.WxPayV3CertificatesResponse;
import com.wanmi.sbc.empower.legder.lakala.LakalaUtils;
import com.wanmi.sbc.empower.pay.service.PayBaseService;
import com.wanmi.sbc.empower.pay.service.PayService;
import com.wanmi.sbc.empower.pay.service.PayServiceFactory;
import com.wanmi.sbc.empower.pay.service.wechat.WechatPayV3ServiceImpl;
import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Map;
import java.util.Objects;

/**
 * <p>短信基本接口实现</p>
 * @author dyt
 * @date 2019-12-03 15:36:05
 */
@Slf4j
@RestController
@Validated
public class PayController implements PayProvider {

    @Autowired
    private PayServiceFactory payServiceFactory;

    @Autowired
    private PayService payService;

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.empower.api.response.PayOrderDetailResponse>
     * @Author lvzhenwei
     * @Description 查询微信支付单详情
     * @Date 14:30 2020/9/17
     * @Param [request]
     **/
    @Override
    public BaseResponse getPayOrderDetail(@RequestBody PayOrderDetailRequest request) {
        PayBaseService payBaseService = payServiceFactory.create(request.getPayType());
        return  payBaseService.getPayOrderDetail(request);

    }

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.empower.api.response.PayOrderDetailResponse>
     * @Author lvzhenwei
     * @Description 微信支付关闭支付单
     * @Date 15:16 2020/11/9
     * @Param [request]
     **/
    @Override
    public BaseResponse payCloseOrder(@RequestBody PayCloseOrderRequest request) {
        PayBaseService payBaseService = payServiceFactory.create(request.getPayType());
        return payBaseService.payCloseOrder(request);
    }

    /**
     * 退款
     *
     * @param refundInfoRequest
     * @return
     */
    @Override
    public BaseResponse payRefund(@RequestBody PayRefundBaseRequest refundInfoRequest) {
        PayBaseService payBaseService = payServiceFactory.create(refundInfoRequest.getPayType());
        return payBaseService.payRefund(refundInfoRequest);
    }

    @Override
    public BaseResponse pay(@RequestBody BasePayRequest basePayRequest) {
        PayBaseService payBaseService = payServiceFactory.create(basePayRequest.getPayType());
        return payBaseService.pay(basePayRequest);
    }

    @Override
    public BaseResponse<RefundResponse> commonRefund(@RequestBody @Valid RefundRequest refundRequest) {
        return BaseResponse.success(new RefundResponse(payService.refund(refundRequest)));
    }

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private WechatPayV3ServiceImpl wechatPayV3Service;

    @Override
    public BaseResponse<WxPayV3CertificatesResponse> getWxPayV3Certificates(@Valid WxPayV3CertificatesRequest refundRequest) {
        String publicKey = redisService.getString(CacheKeyConstant.EMPOWER_WXPAYV3_CERTIFICATES+refundRequest.getSerial_no());
        if(StringUtils.isNotEmpty(publicKey) && Objects.equals(Boolean.FALSE, refundRequest.getCleanCacheFlag())) {
            return BaseResponse.success(WxPayV3CertificatesResponse.builder().publicKey(publicKey).build());
        }
        RLock bargainLock = redissonClient.getLock(CacheKeyConstant.EMPOWER_WXPAYV3_CERTIFICATES_LOCK);
        bargainLock.lock();
        try {
            Map<String,String> publicKeyMap = wechatPayV3Service.getPublicKey();
            log.info("publicKey===={}",publicKey);
            log.info("publicKeyMap==={}",publicKeyMap);
            publicKey = publicKeyMap.get(refundRequest.getSerial_no());
            publicKey = publicKey.replace("\n", "");
            publicKey = publicKey.replace(" ", "");
            publicKey = publicKey.replace("-----BEGINCERTIFICATE-----", "");
            publicKey = publicKey.replace("-----ENDCERTIFICATE-----", "");
            redisService.setString(CacheKeyConstant.EMPOWER_WXPAYV3_CERTIFICATES+refundRequest.getSerial_no(), publicKey, 60L*60*10);
            return BaseResponse.success(WxPayV3CertificatesResponse.builder().publicKey(publicKey).build());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bargainLock.isHeldByCurrentThread()) {
                bargainLock.unlock();
            }
        }
        return BaseResponse.success(
                WxPayV3CertificatesResponse.builder().publicKey(Strings.EMPTY).build());
    }
}
