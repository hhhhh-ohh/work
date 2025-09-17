package com.wanmi.sbc.goods.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className BuyCycleVO
 * @description
 * @date 2022/10/17 2:25 PM
 **/
@Schema
@Data
public class BuyCycleVO implements Serializable {
    private static final long serialVersionUID = 4085254327560983710L;

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
}
