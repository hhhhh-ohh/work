package com.wanmi.sbc.marketing.api.request.bargain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>砍价新增参数</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainAddRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 砍价商品id
     */
    @Schema(description = "砍价商品id")
    @NotNull
    @Max(9223372036854775807L)
    private Long bargainGoodsId;


    /**
     * goodsInfoId
     */
    @Schema(description = "goodsInfoId")
    @NotBlank
    @Length(max = 32)
    private String goodsInfoId;

    /**
     * 发起时间
     */
    @Schema(description = "发起时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 发起人id
     */
    @Schema(description = "发起人id")
    @NotBlank
    @Length(max = 32)
    private String customerId;

    /**
     * 目标砍价人数
     */
    @Schema(description = "目标砍价人数")
    @NotNull
    private Integer targetJoinNum;


    /**
     * 目标砍价金额
     */
    @Schema(description = "目标砍价金额")
    @NotNull
    private BigDecimal targetBargainPrice;


}