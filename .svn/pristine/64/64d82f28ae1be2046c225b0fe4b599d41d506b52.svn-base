package com.wanmi.sbc.pay.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhanggaolei
 * @type InternalPayBaseBean.java
 * @desc
 * @date 2022/11/21 17:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternalPayBaseBean {

    /**
     * 支付id
     */
    private String id;

    /**
     * 支付金额
     */
    private BigDecimal totalPrice;

    /**
     * 订单列表
     */
    private List<TradeVO> tradeVOList;

    /**
     * 账户id
     */
    private String accountId;

    /**
     * 账户余额
     */
    private BigDecimal accountBalance;

    /**
     * 拓展字段
     */
    @Builder.Default
    private Map<String, Object> extendMap = new HashMap<>();


}
