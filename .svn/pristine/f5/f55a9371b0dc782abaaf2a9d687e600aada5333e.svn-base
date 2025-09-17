package com.wanmi.sbc.setting.api.request.payadvertisement;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除支付广告页配置请求参数</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayAdvertisementDelByIdRequest extends SettingBaseRequest {

    /**
     * 支付广告id
     */
    @Schema(description = "支付广告id")
    @NotNull
    private Long id;
}
