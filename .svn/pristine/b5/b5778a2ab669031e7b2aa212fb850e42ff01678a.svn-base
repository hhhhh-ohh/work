package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>拼团活动带拼团人数修改参数bean</p>
 * Created by of628-wenzhi on 2019-05-27-19:07.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class GrouponActivityWaitNumModifyRequest extends BaseRequest {

    private static final long serialVersionUID = -4413001500234038893L;

    /**
     * 活动ID
     */
    @Schema(description = "拼团活动id")
    @NotNull
    private String grouponActivityId;

    /**
     * 增量数，若做减量，则传负值
     */
    @Schema(description = "增量数，若做减量，则传负值")
    @NotNull
    private Integer num;
}
