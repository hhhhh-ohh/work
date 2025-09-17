package com.wanmi.ares.response;

import lombok.*;

import java.math.BigDecimal;

/**
 * 预约营销效果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppointmentAnalysisForGoods extends MarketingAnalysisBase {

    // 预约人数
    private Long appointmentCount;

    // 商品名称
    private String  goodsInfoId;

    // 商品名称
    private String  goodsInfoName;

    // 规格信息
    private String  specDetails;

    // sku编码
    private String goodsInfoNo;

    // 预约支付转换率
    private BigDecimal appointmentPayRate;

    /**
     * 访问-预约转化率
     */
    private BigDecimal uvAppointmentRate;
}
