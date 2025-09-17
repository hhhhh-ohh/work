package com.wanmi.sbc.marketing.api.request.bargainjoin;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

/**
 * <p>单个查询帮砍记录请求参数</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainJoinByIdRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * bargainJoinId
     */
    @Schema(description = "bargainJoinId")
    @NotNull
    private Long bargainJoinId;
}