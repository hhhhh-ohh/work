package com.wanmi.sbc.empower.api.request.logisticslog;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除物流记录请求参数</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogDelByIdRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private String id;
}
