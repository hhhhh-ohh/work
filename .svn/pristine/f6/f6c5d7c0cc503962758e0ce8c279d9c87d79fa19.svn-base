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
 * 订单业绩明细实体类
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_performance_detail")
public class OrderPerformanceDetail implements Serializable {

    @Schema(description = "主键ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "业绩ID（关联order_performance表）")
    @Column(name = "performance_id")
    private String performanceId;

    @Schema(description = "明细类型（1-购买商品，2-退货商品）")
    @Column(name = "detail_type")
    private Integer detailType;

    @Schema(description = "订单ID")
    @Column(name = "order_id")
    private String orderId;

    @Schema(description = "oid")
    @Column(name = "o_id")
    private String oId;

    @Schema(description = "商品SPU ID")
    @Column(name = "spu_id")
    private String spuId;

    @Schema(description = "商品SKU ID")
    @Column(name = "sku_id")
    private String skuId;

    @Schema(description = "商品名称")
    @Column(name = "sku_name")
    private String skuName;

    @Schema(description = "商品编号")
    @Column(name = "sku_no")
    private String skuNo;

    @Schema(description = "规格详情")
    @Column(name = "spec_details")
    private String specDetails;

    @Schema(description = "学校级别（小学/中学）")
    @Column(name = "school_level")
    private String schoolLevel;

    @Schema(description = "季节")
    @Column(name = "season")
    private String season;

    @Schema(description = "服装类型（多个，逗号分隔）")
    @Column(name = "clothing_types")
    private String clothingTypes;

    @Schema(description = "数量")
    @Column(name = "quantity")
    private Integer quantity;

    @Schema(description = "单价")
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Schema(description = "金额")
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Schema(description = "佣金金额")
    @Column(name = "commission_amount")
    private BigDecimal commissionAmount;

    @Schema(description = "校服商品单价")
    @Column(name = "uniform_unit_price")
    private BigDecimal uniformUnitPrice;

    @Schema(description = "校服商品总价")
    @Column(name = "uniform_total_amount")
    private BigDecimal uniformTotalAmount;

    @Schema(description = "退货单ID（退货时填写）")
    @Column(name = "return_order_id")
    private String returnOrderId;

    @Schema(description = "代理唯一码")
    @Column(name = "agent_unique_code")
    private String agentUniqueCode;

    @Schema(description = "代理ID")
    @Column(name = "agent_id")
    private String agentId;

    @Schema(description = "客户ID")
    @Column(name = "customer_id")
    private String customerId;

    @Schema(description = "备注")
    @Column(name = "remark")
    private String remark;

    @Schema(description = "创建时间")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Schema(description = "省ID")
    @CanEmpty
    @Column(name = "province_id")
    private Long provinceId;

    @Schema(description = "市ID")
    @CanEmpty
    @Column(name = "city_id")
    private Long cityId;

    @Schema(description = "区 ID")
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
