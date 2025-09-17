package com.wanmi.sbc.message.api.request.storenoticesend;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除商家公告请求参数</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeSendDelByIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private Long id;
}
