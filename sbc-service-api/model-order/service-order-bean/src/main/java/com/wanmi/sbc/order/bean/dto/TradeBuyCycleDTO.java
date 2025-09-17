package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author xuyunpeng
 * @className TradeBuyCycleDTO
 * @description
 * @date 2022/10/17 2:15 PM
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeBuyCycleDTO implements Serializable {

    private static final long serialVersionUID = 6064701807930597107L;
    /**
     * spuId
     */
    @Schema(description = "spuId")
    private String goodsId;

    /**
     * skuId
     */
    @Schema(description = "skuId")
    private String goodsInfoId;

    /**
     * 配送周期
     */
    @Schema(description = "配送周期")
    private Integer deliveryCycle;

    /**
     * 周期内自选期数
     */
    @Schema(description = "周期内自选期数")
    private Integer optionalNum;

    /**
     * 用户可选送达日期
     */
    @Schema(description = "用户可选送达日期")
    private String deliveryDate;

    /**
     * 预留时间 日期
     */
    @Schema(description = "预留时间 日期")
    private Integer reserveDay;

    /**
     * 预留时间 时间点
     */
    @Schema(description = "预留时间 时间点")
    private Integer reserveTime;

    /**
     * 最低期数
     */
    @Schema(description = "最低期数")
    private Integer minCycleNum;

    /**
     * 每期价格
     */
    @Schema(description = "每期价格")
    private BigDecimal cyclePrice;

    /**
     * 配送期数
     */
    @Schema(description = "配送期数")
    private Integer deliveryCycleNum;


    /**
     * 配送计划
     */
    @Schema(description = "配送计划")
    private List<CycleDeliveryPlanDTO> deliveryPlanS;



    /**
     * 实际周期配送
     */
    @Schema(description = "实际周期配送")
    private String deliveryCycleDay;

    /**
     * 周期购下一期计划日期
     */
    @Schema(description = "周期购下一期计划日期")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate buyCycleNextPlanDate;


    /**
     * 周期购提前几天提醒发货
     */
    @Schema(description = "周期购提前几天提醒发货")
    private Integer remindShipping;

}
