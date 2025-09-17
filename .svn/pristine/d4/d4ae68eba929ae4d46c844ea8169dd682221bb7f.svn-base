package com.wanmi.ares.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 预约营销效果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Schema
public class MarketingAnalysisBase implements Serializable {

    private static final long serialVersionUID = -3046329946913047129L;

    // 支付ROI （营销支付金额 / 营销优惠金额）
    @Schema(description = "支付ROI （营销支付金额 / 营销优惠金额）")
    private BigDecimal payROI;

    // 营销支付金额
    @Schema(description = "营销支付金额")
    private BigDecimal payMoney = BigDecimal.ZERO;

    // 营销优惠金额
    @Schema(description = "营销优惠金额")
    private BigDecimal discountMoney;

    // 营销支付件数
    @Schema(description = "营销支付件数")
    private Long payGoodsCount;

    // 营销支付订单数
    @Schema(description = "营销支付订单数")
    private Long payTradeCount;

    // 连带率 （营销支付件数 / 营销支付订单数）
    @Schema(description = "连带率 （营销支付件数 / 营销支付订单数）")
    private BigDecimal jointRate;

    // 新用户
    @Schema(description = "新用户")
    private Long newCustomerCount;

    // 老用户
    @Schema(description = "老用户")
    private Long oldCustomerCount;

    // 营销支付人数
    @Schema(description = "营销支付人数")
    private Long payCustomerCount;

    // 客单价
    @Schema(description = "客单价）")
    private BigDecimal customerPrice;

    // 趋势图用
    @Schema(description = "趋势图用")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate day;

    private String dayInfo;

    // 例："2021-02"，即2021第二周
    @Schema(description = "例：\"2021-02\"，即2021第二周")
    private String week;

    // 例："2021/01/11~2021/01/17"
    @Schema(description = "例：\"2021/01/11~2021/01/17\"")
    private String weekInfo;

    @Schema(description = "前端趋势图x轴用")
    private String title;

    /**
     * PV
     */
    @Schema(description = "PV")
    private Long pv;

    /**
     * UV
     */
    @Schema(description = "UV")
    private Long uv;


    /**
     * 访问-支付转化率
     */
    @Schema(description = "访问-支付转化率")
    private BigDecimal uvPayRate;

    @Schema(description = "供货价）")
    private BigDecimal supplyPrice;

    public void init(){

        if (Objects.isNull(this.payROI) && Objects.nonNull(discountMoney) && discountMoney.compareTo(BigDecimal.ZERO) > 0){
            this.payROI = BigDecimal.ZERO;
        }
        if (Objects.isNull(this.payMoney)){
            this.payMoney = BigDecimal.ZERO;
        }
        if (Objects.isNull(this.discountMoney)){
            this.discountMoney = BigDecimal.ZERO;
        }
        if (Objects.isNull(this.payGoodsCount)){
            this.payGoodsCount = 0L;
        }
        if (Objects.isNull(this.payTradeCount)){
            this.payTradeCount = 0L;
        }
        if (Objects.isNull(this.jointRate) && payTradeCount > 0){
            this.jointRate = BigDecimal.ZERO;
        }
        if (Objects.isNull(this.newCustomerCount)){
            this.newCustomerCount = 0L;
        }
        if (Objects.isNull(this.oldCustomerCount)){
            this.oldCustomerCount = 0L;
        }
        if (Objects.isNull(this.payCustomerCount)){
            this.payCustomerCount = 0L;
        }
        if (Objects.isNull(this.customerPrice)){
            this.customerPrice = BigDecimal.ZERO;
        }
        if (Objects.isNull(this.supplyPrice)){
            this.supplyPrice = BigDecimal.ZERO;
        }
    }
}
