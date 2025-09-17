package com.wanmi.sbc.setting.api.request.baseconfig;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author houshuai
 * @date 2022/4/6 10:17
 * @description <p> </p>
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseConfigUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @NotNull
    @Schema(description = "主键ID")
    private Integer baseConfigId;

    /**
     * 会员注册协议
     */
    @Schema(description = "会员注册协议")
    private String registerContent;

    /**
     * 会员注销协议
     */
    @Schema(description = "会员注销协议")
    private String cancellationContent;

    /**
     * 付费会员协议
     */
    @Schema(description = "付费会员协议")
    private String payingMemberContent;
}
