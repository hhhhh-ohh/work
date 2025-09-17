package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单业绩VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "订单业绩VO")
public class OrderPerformanceVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String customerId;

    /**
     * 订单ID（业务单号/UUID）
     */
    @Schema(description = "订单ID（业务单号/UUID）")
    private String orderId;

    /**
     * 订单金额（原价）
     */
    @Schema(description = "订单金额（原价）")
    private BigDecimal orderAmount;

    /**
     * 佣金计算状态：0-未计算 1-已计算
     */
    @Schema(description = "佣金计算状态：0-未计算 1-已计算")
    private Integer commissionStatus;

    /**
     * 系统唯一码（不可重复）
     */
    @Schema(description = "系统唯一码（不可重复）")
    private String agentUniqueCode;

    /**
     * 代理商主键ID（UUID）
     */
    @Schema(description = "代理商主键ID（UUID）")
    private String agentId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 校园商品订单总价
     */
    @Schema(description = "校园商品订单总价")
    private BigDecimal schoolUniformAmount;

    /**
     * 订单佣金金额
     */
    @Schema(description = "订单佣金金额")
    private BigDecimal commissionAmount;


    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;


    /**
     *  退款类型：0、正常，1、全部退，2、部分退
     */
    @Schema(description = "退款类型：0、正常，1、全部退，2、部分退")
    private Integer returnType;
}
