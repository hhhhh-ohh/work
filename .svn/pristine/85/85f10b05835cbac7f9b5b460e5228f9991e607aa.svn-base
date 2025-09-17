package com.wanmi.sbc.order.api.response.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.FlowState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
public class CreditTradePageResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderNo;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String customerAccount;

    /**
     * 订单应还款金额
     */
    @Schema(description = "订单应还款金额")
    private BigDecimal orderPrice;

    /**
     * 订单原始金额
     */
    @Schema(description = "订单原始金额")
    private BigDecimal orderOriginPrice;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态 0未支付 1待确认 2已支付")
    private String payOrderStatus;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private FlowState flowState;

    /**
     * 下单时间
     */
    @Schema(description = "下单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime payTime;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private List<String> urlList;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Integer goodsNum;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    /**
     * 是否周期购订单
     */
    @Schema(description = "是否周期购订单")
    private Boolean buyCycleFlag;

}