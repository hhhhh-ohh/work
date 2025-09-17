package com.wanmi.sbc.message.api.request.storenoticescope;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

/**
 * <p>单个删除商家公告发送范围请求参数</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeScopeDelByIdRequest implements Serializable {
private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private Long id;
}
