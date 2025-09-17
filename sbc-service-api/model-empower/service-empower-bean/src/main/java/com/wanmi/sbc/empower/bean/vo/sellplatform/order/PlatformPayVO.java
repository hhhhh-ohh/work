package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckAccessInfoVO
 * @description 支付信息
 * @date 2022/4/1 19:52
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformPayVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 0: 微信支付, 1: 货到付款, 2: 商家会员储蓄卡（默认0）
     */
    @Schema(description = "0: 微信支付, 1: 货到付款, 2: 商家会员储蓄卡（默认0）")
    private Integer pay_method_type;

}