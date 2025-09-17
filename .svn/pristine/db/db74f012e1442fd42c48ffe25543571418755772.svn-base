package com.wanmi.sbc.goods.adjust.request;

import static com.wanmi.sbc.common.util.ValidateUtil.NULL_EX_MESSAGE;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.Validate;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>商品批量调价确认请求参数</p>
 * Created by of628-wenzhi on 2020-12-18-11:11 上午.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PriceAdjustConfirmRequest extends BaseRequest {
    private static final long serialVersionUID = -5372213432454347648L;
    /**
     * 调价单号
     */
    @Schema(description = "调价单号")
    @NotBlank
    private String adjustNo;

    /**
     * 是否立即生效
     */
    @Schema(description = "是否立即生效")
    @NotNull
    private Boolean isNow;

    /**
     * 如果定时生效，生效时间不为空
     */
    @Schema(description = "如果定时生效，生效时间不为空")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @Override
    public void checkParam() {
        Validate.isTrue(isNow || Objects.nonNull(startTime), NULL_EX_MESSAGE,
                "startTime");
    }
}
