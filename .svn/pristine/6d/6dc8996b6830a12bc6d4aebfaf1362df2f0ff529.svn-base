package com.wanmi.sbc.marketing.api.request.appointmentsale;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RushToAppointmentSaleGoodsRequest
 * @Description 预约活动Request请求类
 * @Author zhangxiaodong
 * @Date 2019/6/14 9:38
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RushToAppointmentSaleGoodsRequest extends BaseRequest {

    private static final long serialVersionUID = 8951476807053544247L;

    /**
     * 预约活动Id
     */
    @Schema(description = "预约活动Id")
    @NotNull
    private Long appointmentSaleId;


    /**
     * 预约商品skuId
     */
    @Schema(description = "预约商品skuId")
    @NotBlank
    private String skuId;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 抢购数量
     */
    @Schema(description = "抢购数量")
    private Long num;
}
