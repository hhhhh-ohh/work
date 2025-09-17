package com.wanmi.sbc.empower.api.request.ledgercontent;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除拉卡拉经营内容表请求参数</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContentDelByIdRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 经营内容id
     */
    @Schema(description = "经营内容id")
    @NotNull
    private Long contentId;
}
