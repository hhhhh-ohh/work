package com.wanmi.sbc.customer.api.request.levelrights;

import com.wanmi.sbc.common.enums.RepeatType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className CustomerLevelRightsRepeatRequest
 * @description
 * @date 2022/5/13 4:28 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class CustomerLevelRightsRepeatRequest implements Serializable {

    private static final long serialVersionUID = 3237530481829378061L;

    /**
     * 周期发券权益类型
     */
    @Schema(description = "周期发券权益类型")
    @NotNull
    private RepeatType type;
}
