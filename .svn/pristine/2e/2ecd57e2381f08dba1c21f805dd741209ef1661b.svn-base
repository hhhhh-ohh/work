package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单支付信息
 * @author wumeng[OF2627]
 *         company qianmi.com
 *         Date 2017-04-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PayInfoVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 支付类型标识
     */
    @Schema(description = "支付类型标识,0：在线支付 1：线下支付")
    private String payTypeId;

    /**
     * 支付类型名称
     */
    @Schema(description = "支付类型名称")
    private  String payTypeName;

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
