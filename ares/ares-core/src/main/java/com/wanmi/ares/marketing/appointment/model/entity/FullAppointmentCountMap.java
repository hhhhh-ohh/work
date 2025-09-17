package com.wanmi.ares.marketing.appointment.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author 10486
 * @Date 18:16 2021/1/11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullAppointmentCountMap implements Serializable {

    // 预约人数
    private Long appointmentCount;

    // 订单数
    private Long payTradeCount;

    // 预约支付转换率
    private BigDecimal appointmentPayRate;

    // 商品id
    private String  goodsInfoId;
}
