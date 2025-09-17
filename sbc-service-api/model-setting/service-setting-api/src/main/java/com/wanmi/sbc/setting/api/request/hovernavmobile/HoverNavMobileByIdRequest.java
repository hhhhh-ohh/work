package com.wanmi.sbc.setting.api.request.hovernavmobile;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询移动端悬浮导航栏请求参数</p>
 *
 * @author dyt
 * @date 2020-04-29 14:28:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoverNavMobileByIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * storeId
     */
    @NotNull
    @Schema(description = "storeId")
    private Long storeId;

}