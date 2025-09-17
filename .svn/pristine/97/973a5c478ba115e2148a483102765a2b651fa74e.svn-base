package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
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
 * <p>包含流水的支付单详情</p>
 * Created by of628-wenzhi on 2019-07-27-22:19.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayOrderDetailVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 支付单Id
     */
    @Schema(description = "支付单Id")
    private String payOrderId;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderCode;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;


    /**
     * 支付类型
     */
    @Schema(description = "支付类型" )
    private PayType payType;

    /**
     * 付款状态
     */
    @Schema(description = "支付单状态")
    private PayOrderStatus payOrderStatus;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String comment;

    /**
     * 流水号
     */
    @Schema(description = "流水号")
    private String receivableNo;

    /**
     * 收款金额
     */
    @Schema(description = "收款金额")
    private BigDecimal payOrderPrice;

    /**
     * 支付单积分
     */
    private Long payOrderPoints;

    /**
     * 应付金额
     */
    @Schema(description = "应付金额")
    private BigDecimal totalPrice;

    /**
     * 收款时间
     */
    @Schema(description = "收款时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime receiveTime;

    /**
     * 收款在线渠道
     */
    @Schema(description = "收款在线渠道")
    private String payChannel;

    @Schema(description = "收款在线渠道id")
    private Long payChannelId;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private Long companyInfoId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 附件
     */
    @Schema(description = "附件")
    private String encloses;

    /**
     * 团编号
     */
    private String grouponNo;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 是否平台自营
     */
    @Schema(description = "是否平台自营",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean isSelf;
}
