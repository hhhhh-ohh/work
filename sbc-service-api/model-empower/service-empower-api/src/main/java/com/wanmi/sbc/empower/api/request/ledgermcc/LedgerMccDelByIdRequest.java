package com.wanmi.sbc.empower.api.request.ledgermcc;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除拉卡拉mcc表请求参数</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerMccDelByIdRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * mcc编号
     */
    @Schema(description = "mcc编号")
    @NotNull
    private Long mccId;
}
