package com.wanmi.sbc.third.login.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Data;

@Schema
@Data
@Builder
public class ThirdLoginSendCodeResponse {

    @Schema(description = "是否注册")
    private Boolean isRegister;
}
