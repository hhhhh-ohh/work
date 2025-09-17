package com.wanmi.sbc.setting.api.response.baseconfig;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by feitingting on 2019/11/8.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BossLogoResponse extends BasicResponse {

    /**
     * PC商城logo
     */
    @Schema(description = "PC商城logo")
    private String pcLogo;
}
