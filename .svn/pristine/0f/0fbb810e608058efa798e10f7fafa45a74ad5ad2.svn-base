package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
@Builder
public class PlatformOrderDeliveryVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 快递公司ID，通过获取快递公司列表获取，将影响物流信息查询
     */
    @Schema(description = "快递公司ID，通过获取快递公司列表获取，将影响物流信息查询")
    private String delivery_id;

    /**
     * 快递单号
     */
    @NotNull
    @Schema(description = "快递单号")
    private String waybill_id;

    /**
     * 支付信息
     */
    @Schema(description = "fund_type = 0 普通订单必传")
    private List<PlatformOrderDeliveryProductVO> product_info_list;

}