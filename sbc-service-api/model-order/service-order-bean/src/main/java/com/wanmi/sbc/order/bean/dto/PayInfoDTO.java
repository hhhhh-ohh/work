package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单支付信息
 *
 * @author wumeng[OF2627]
 * company qianmi.com
 * Date 2017-04-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PayInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付类型标识
     */
    @Schema(description = "支付类型标识")
    private String payTypeId;

    /**
     * 支付类型名称
     */
    @Schema(description = "支付类型名称")
    private String payTypeName;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String desc;

    /**
     * 是否合并支付（在线支付场景）
     */
    @Schema(description = "是否合并支付（在线支付场景）")
    private boolean isMergePay;
}
