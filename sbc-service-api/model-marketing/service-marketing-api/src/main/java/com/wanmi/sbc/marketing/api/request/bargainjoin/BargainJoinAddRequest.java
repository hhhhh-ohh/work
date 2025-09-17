package com.wanmi.sbc.marketing.api.request.bargainjoin;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>帮砍记录新增参数</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainJoinAddRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 砍价记录id
     */
    @Schema(description = "砍价记录id")
    @NotNull
    @Max(9223372036854775807L)
    private Long bargainId;

    /**
     * 砍价商品id
     */
    @Schema(description = "砍价商品id")
    @NotBlank
    @Length(max = 32)
    private String goodsInfoId;

    /**
     * 砍价的发起人
     */
    @Schema(description = "砍价的发起人")
    @NotBlank
    @Length(max = 32)
    private String customerId;

    /**
     * 帮砍人id
     */
    @Schema(description = "帮砍人id")
    @NotBlank
    @Length(max = 32)
    private String joinCustomerId;

    /**
     * 帮砍金额
     */
    @Schema(description = "帮砍金额")
    @NotNull
    private BigDecimal bargainAmount;


}