package com.wanmi.sbc.crm.api.request.autotag;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * <p>单个删除自动标签请求参数</p>
 * @author dyt
 * @date 2020-03-11 14:47:32
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoTagDelByIdRequest extends CrmBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;
}
