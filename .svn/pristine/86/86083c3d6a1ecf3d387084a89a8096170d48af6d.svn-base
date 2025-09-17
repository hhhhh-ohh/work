package com.wanmi.sbc.setting.api.request.stockWarning;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import java.time.LocalDateTime;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockWarningAddRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    @Max(9223372036854775807L)
    private Long storeId;

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    private String skuId;

    /**
     * 是否已预警 0：已预警 1：未预警
     */
    @Schema(description = "是否已预警 0：已预警 1：未预警")
    private Integer isWarning;

    /**
     * 是否删除 0：未删除 1：已删除
     */
    @Schema(description = "是否删除 0：未删除 1：已删除")
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
}
