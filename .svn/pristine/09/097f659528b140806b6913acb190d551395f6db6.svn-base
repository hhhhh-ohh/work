package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckAccessInfoVO
 * @description 审核状态
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformPriceVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义商品ID
     */
    @NotNull
    @Schema(description = "运费，单位：分, 必填包邮填：0")
    private Integer freight;
    /**
     * 订单优惠金额
     */
    @Schema(description = "订单优惠金额，单位：分")
    private Integer discounted_price;

    /**
     * 其他费用
     */
    @Schema(description = "其他费用, 税费之类的")
    private Integer additional_price;

    /**
     * 其他费用描述
     */
    @Schema(description = "其他费用描述")
    private String additional_remarks;

    /**
     * 订单总价
     */
    @NotNull
    @Schema(description = "订单的总价")
    private Integer order_price;

}