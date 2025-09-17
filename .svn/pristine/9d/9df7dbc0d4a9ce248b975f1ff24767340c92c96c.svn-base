package com.wanmi.sbc.marketing.api.request.bargain;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>单个查询砍价请求参数</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainByIdRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * bargainId
     */
    @Schema(description = "bargainId")
    @NotNull
    private Long bargainId;

    @Schema(description = "当前登录的用户")
    private String customerId;
}