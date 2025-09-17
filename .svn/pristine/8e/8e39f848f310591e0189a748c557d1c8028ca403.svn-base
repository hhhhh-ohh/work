package com.wanmi.sbc.account.api.response.credit.order;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2021/3/1 15:13
 * @description <p> 授信订单信息响应体 </p>
 */
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class RepayOrderPageResponse extends BasicResponse {

    private static final long serialVersionUID = -6610985687883884021L;
    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderNo;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    private String customerAccount;

    /**
     * 订单金额
     */
    @Schema(description = "订单金额")
    private BigDecimal orderPrice;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态 0未支付 1待确认 2已支付")
    private String payOrderStatus;

    /**
     * 下单时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

}
