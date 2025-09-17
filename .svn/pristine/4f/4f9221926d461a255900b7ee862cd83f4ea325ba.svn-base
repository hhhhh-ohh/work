package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author 黄昭
 * @className ConfigRequest
 * @description 订单自提设置
 * @date 2021/9/7 11:27
 **/
@Schema
@Data
public class ConfigRequest extends BaseRequest {
    private static final long serialVersionUID = 6599809295389230290L;
    /**
     * 配置键
     */
    @Schema(description = "配置键")
    private ConfigKey configKey;

    /**
     * 类型
     */
    @Schema(description = "类型")
    @NotBlank(message = "类型不可为空")
    private String configType;

    /**
     * 状态,0:未启用1:已启用
     */
    @Schema(description = "状态,0:未启用1:已启用")
    @NotNull(message = "状态不可为空")
    private Integer status;

    @Schema(description = "修改人")
    private String updatePerson;

}
