package com.wanmi.sbc.order.orderperformance.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单业绩实体类
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_performance")
public class OrderPerformance implements Serializable {

    @Schema(description = "主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "用户ID")
    @Column(name = "customer_id")
    private String customerId;

    @Schema(description = "订单ID（业务单号/UUID）")
    @Column(name = "order_id")
    private String orderId;

    @Schema(description = "订单金额（原价）")
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @Schema(description = "订单实付金额")
    @Column(name = "pay_amount")
    private BigDecimal payAmount;

    @Schema(description = "支付单状态 0:已收款 1:未收款 2:待确认")
    @Column(name = "pay_order_status")
    private Integer payOrderStatus;

    @Schema(description = "退款金额")
    @Column(name = "refund_amount")
    private BigDecimal refundAmount;

    @Schema(description = "退款单状态：1.待退款 2.拒绝退款 3.已完成")
    @Column(name = "refund_status")
    private Integer refundStatus;

    @Schema(description = "校园商品订单总价")
    @Column(name = "school_uniform_amount")
    private BigDecimal schoolUniformAmount;

    @Schema(description = "订单佣金金额")
    @Column(name = "commission_amount")
    private BigDecimal commissionAmount;

    @Schema(description = "订单终结状态：0-未终结 1-已终结")
    @Column(name = "order_status")
    private Integer orderStatus;

    @Schema(description = "佣金计算状态：0-未计算 1-已计算")
    @Column(name = "commission_status")
    private Integer commissionStatus;

    @Schema(description = "系统唯一码（不可重复）")
    @Column(name = "agent_unique_code")
    private String agentUniqueCode;

    @Schema(description = "代理商主键ID（UUID）")
    @Column(name = "agent_id")
    private String agentId;

    @Schema(description = "创建时间")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    @Column(name = "create_by")
    private String createBy;

    @Schema(description = "修改时间")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Schema(description = "修改人")
    @Column(name = "update_by")
    private String updateBy;

    @Schema(description = "省ID")
    @CanEmpty
    @Column(name = "province_id")
    private Long provinceId;

    @Schema(description = "市ID")
    @CanEmpty
    @Column(name = "city_id")
    private Long cityId;

    @Schema(description = "区ID")
    @Column(name = "area_id")
    private Long areaId;

    @Schema(description = "省名称")
    @CanEmpty
    @Column(name = "province_name")
    private String provinceName;

    @Schema(description = "市名称")
    @CanEmpty
    @Column(name = "city_name")
    private String cityName;

    @Schema(description = "区名称")
    @Column(name = "area_name")
    private String areaName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    @Serial
    private static final long serialVersionUID = 1L;
}
