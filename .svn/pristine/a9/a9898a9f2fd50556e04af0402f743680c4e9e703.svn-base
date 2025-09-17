package com.wanmi.sbc.empower.api.request.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description 渠道地址请求
 * @author wur
 * @date 2021/5/13 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelAddressRequest extends BaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @Valid
    @NotNull
    @Schema(description = "渠道服务类型")
    private ThirdPlatformType thirdPlatformType;

    @Valid
    @NotNull
    @Schema(description = "地址级别 0 1 2 3")
    private Integer addrLevel;

    @Schema(description = "上级ID")
    private String parentId;


}
