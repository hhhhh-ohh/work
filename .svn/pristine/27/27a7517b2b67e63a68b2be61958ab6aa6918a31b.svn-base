package com.wanmi.sbc.empower.api.request.logisticssetting;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除物流配置请求参数</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsSettingDelByIdRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private Long id;
}
