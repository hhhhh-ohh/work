package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 交易设置请求实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeSettingDTO implements Serializable{

    @Schema(description = "配置类型")
    private ConfigType configType;

    @Schema(description = "配置键")
    private ConfigKey configKey;

    /**
     * 开关状态
     */
    @Schema(description = "开关状态")
    private Integer status;

    /**
     * 设置天数
     */
    @Schema(description = "设置天数")
    private String context;
}
