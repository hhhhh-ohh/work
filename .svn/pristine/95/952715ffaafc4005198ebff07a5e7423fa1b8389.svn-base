package com.wanmi.sbc.goods.api.request.adjustprice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>确认调价请求参数</p>
 * Created by of628-wenzhi on 2020-12-17-11:54 上午.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AdjustPriceConfirmRequest extends GoodsBaseRequest {

    private static final long serialVersionUID = 5400943981235559646L;
    /**
     * 调价单号
     */
    @Schema(description = "调价单号")
    @NotBlank
    private String adjustNo;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    @NotNull
    private Long storeId;

    /**
     * 生效时间
     */
    @Schema(description = "生效时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime effectiveTime;

    @Schema(description = "是否需要审核-true:审核 false:不审核")
    private boolean audit;

}
