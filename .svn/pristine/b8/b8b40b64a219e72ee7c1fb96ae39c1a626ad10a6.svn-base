package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className ReturnTagDTO
 * @description 退单标记
 * @date 2022/2/17 2:46 下午
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnTagDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否是虚拟订单
     */
    @Schema(description = "是否是虚拟订单")
    private Boolean virtualFlag = Boolean.FALSE;


    /**
     * 是否是卡券订单
     */
    @Schema(description = "是否是卡券订单")
    private Boolean electronicCouponFlag = Boolean.FALSE;

    /**
     * 是否是周期购订单
     */
    @Builder.Default
    private Boolean buyCycleFlag = Boolean.FALSE;
}
