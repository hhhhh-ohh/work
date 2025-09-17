package com.wanmi.sbc.empower.api.request.sellplatform.apply;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  完成接入任务
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformFinishAccessRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 6:完成spu接口，
     * 7:完成订单接口 / 19:完成二级商户号订单，
     * 8:完成物流接口，
     * 9:完成售后接口 / 20:完成二级商户号售后，
     * 10:测试完成，
     * 11:发版完成
     */
    @NotNull
    @Schema(description = "6:完成spu接口，7:完成订单接口 / 19:完成二级商户号订单，8:完成物流接口，9:完成售后接口 / 20:完成二级商户号售后，10:测试完成，11:发版完成")
    private Integer accessInfoItem;
}
