package com.wanmi.sbc.pay.bean;

import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhanggaolei
 * @type PayBaseBean.java
 * @desc
 * @date 2022/11/17 11:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayBaseBean {

    /**
     * 金额
     */
    private BigDecimal totalPrice;

    private String body;

    /**
     * 订单id
     */
    private String id;

    /**
     * 支付单号
     */
    private String payNo;

    private String openId;

    private String productId;

    private String title;

    private PayChannelType payChannelType;

    private Long channelItemId;

    @Builder.Default
    private LocalDateTime orderTimeOut = LocalDateTime.now().plusMinutes(10L);

    @Builder.Default
    private Map<String, Object> extendMap = new HashMap<>();
}
