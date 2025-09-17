package com.wanmi.sbc.setting.api.request.systemconfig;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author 王超
 * @className CommunityConfigModifyRequest
 * @description TODO
 * @date 2023/8/10 15:23
 **/
@Data
@Schema
public class CommunityConfigModifyRequest extends BaseRequest {

    /**
     * 状态 0:未启用1:已启用
     */
    @Schema(description ="0:未启用1:已启用")
    @NotNull
    private DefaultFlag status;
}