package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 黄昭
 * @className GoodsSecondaryAuditRequest
 * @description TODO
 * @date 2021/12/16 19:59
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsSecondaryAuditRequest extends BaseRequest {

    @Schema(description = "配置类型")
    @NotNull
    private ConfigType configType;
}