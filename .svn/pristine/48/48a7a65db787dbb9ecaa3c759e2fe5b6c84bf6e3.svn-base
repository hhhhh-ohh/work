package com.wanmi.sbc.order.api.request.payingmemberrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.RepeatType;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className PayingMemberRecordRightsRequest
 * @description
 * @date 2022/5/16 10:19 AM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberRecordRightsRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -8121265845246992315L;

    /**
     * 权益id
     */
    @Schema(description = "权益id")
    @NotNull
    private Integer rightsId;

    /**
     * 周期类型
     */
    @Schema(description = "周期类型")
    @NotNull
    private RepeatType repeatType;

    /**
     * 会员开通时间
     */
    @Schema(description = "会员开通时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate date;
}
