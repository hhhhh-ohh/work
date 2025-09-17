package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.setting.bean.dto.TradeConfigDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class TradeConfigsModifyRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 订单配置列表
     */
    @Schema(description = "订单配置列表")
    @NotNull
    @Valid
    private List<TradeConfigDTO> tradeConfigDTOList;
}
