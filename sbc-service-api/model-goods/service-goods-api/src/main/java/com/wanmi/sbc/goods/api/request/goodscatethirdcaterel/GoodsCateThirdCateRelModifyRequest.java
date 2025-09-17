package com.wanmi.sbc.goods.api.request.goodscatethirdcaterel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>平台类目和第三方平台类目映射修改参数</p>
 *
 * @author
 * @date 2020-08-18 19:51:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateThirdCateRelModifyRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @Max(9223372036854775807L)
    private Long id;

    /**
     * 平台类目主键
     */
    @Schema(description = "平台类目主键")
    @NotNull
    @Max(9223372036854775807L)
    private Long cateId;

    /**
     * 第三方平台类目主键
     */
    @Schema(description = "第三方平台类目主键")
    @NotNull
    @Max(9223372036854775807L)
    private Long thirdCateId;

    /**
     * 第三方渠道(0，linkedmall)
     */
    @Schema(description = "第三方渠道(0，linkedmall)")
    @NotNull
    @Max(127)
    private Integer thirdPlatformType;

    /**
     * createTime
     */
    @Schema(description = "createTime", hidden = true)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "updateTime", hidden = true)
    private LocalDateTime updateTime;

}