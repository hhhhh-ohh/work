package com.wanmi.sbc.setting.api.request.systemconfig;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dyt on 2019/11/6.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListShowTypeModifyRequest extends BaseRequest {

    /**
     * 状态
     */
    @NotNull
    @Schema(description ="状态")
    private Integer status;

}
