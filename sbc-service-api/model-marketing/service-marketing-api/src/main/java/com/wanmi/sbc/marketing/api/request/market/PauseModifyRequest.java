package com.wanmi.sbc.marketing.api.request.market;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @className PauseModifyRequest
 * @description
 * @author 黄昭
 * @date 2021/11/10 16:20
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PauseModifyRequest extends BaseRequest {

    /**
     * 营销Id
     */
    @Schema(description = "营销Id")
    @NotNull
    private Long marketingId;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 参加会员 0:全部等级  other:其他等级
     */
    @Schema(description = "参加会员", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    @NotBlank
    private String joinLevel;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;
}
