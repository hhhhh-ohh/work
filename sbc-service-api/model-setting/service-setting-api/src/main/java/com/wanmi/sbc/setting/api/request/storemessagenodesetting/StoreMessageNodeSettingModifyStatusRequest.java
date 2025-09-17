package com.wanmi.sbc.setting.api.request.storemessagenodesetting;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.storemessage.StoreMessagePlatform;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

/**
 * <p>商家消息节点修改开关状态参数</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageNodeSettingModifyStatusRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点标识
     */
    @NotBlank
    @Schema(description = "节点标识")
    private String nodeCode;

    /**
     * 启用状态 0:未启用 1:启用
     */
    @NotNull
    @Schema(description = "启用状态 0:未启用 1:启用")
    private BoolFlag status;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long storeId;

    /**
     * 消息平台类型
     */
    @Schema(description = "消息平台类型")
    private StoreMessagePlatform platformType;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;

    /**
     * 商家库存预警值
     */
    @Schema(description = "商家sku库存预警值")
    private Long warningStock;

    /**
     * 是否是变动商家库存预警开关
     */
    @Schema(description = "是否是变动商家库存预警开关")
    private Boolean isSwitchChange;
}

