package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author xuyunpeng
 * @className TradeBuyCycleVO
 * @description
 * @date 2022/10/18 4:13 PM
 **/
@Schema
@Data
public class TradeBuyCycleVO implements Serializable {
    private static final long serialVersionUID = -528345520689644259L;

    /**
     * 配送周期
     * @see com.wanmi.sbc.goods.bean.enums.DeliveryCycleType
     */
    @Schema(description = "配送周期 1、每天一期 2、每周一期 3、每月一期 4、每周固定多期 5、每月固定多期")
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
     * 最低期数
     */
    @Schema(description = "最低期数")
    private Integer minCycleNum;

    /**
     * 首期可选送达开始时间
     */
    @Schema(description = "首期可选送达开始时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate deliveryStartDate;

    /**
     * 首期可选送达结束时间
     */
    @Schema(description = "首期可选送达结束时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate deliveryEndDate;

    /**
     * 配送期数
     */
    @Schema(description = "配送期数")
    private Integer deliveryCycleNum;

    /**
     * 配送计划
     */
    @Schema(description = "配送计划")
    private List<CycleDeliveryPlanVO> deliveryPlanS;


    /**
     * 实际周期配送
     */
    @Schema(description = "实际周期配送")
    private String deliveryCycleDay;


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
     * 每期价格
     */
    @Schema(description = "每期价格")
    private BigDecimal cyclePrice;

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
