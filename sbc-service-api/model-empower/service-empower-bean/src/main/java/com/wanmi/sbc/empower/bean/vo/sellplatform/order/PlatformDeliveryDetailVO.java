package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckAccessInfoVO
 * @description 配送信息
 * @date 2022/4/1 19:52
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformDeliveryDetailVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 1: 正常快递, 2: 无需快递, 3: 线下配送, 4: 用户自提，视频号场景目前只支持 1，正常快递
     */
    @NotNull
    @Schema(description = "1: 正常快递, 2: 无需快递, 3: 线下配送, 4: 用户自提，视频号场景目前只支持 1，正常快递")
    private Integer delivery_type;


}