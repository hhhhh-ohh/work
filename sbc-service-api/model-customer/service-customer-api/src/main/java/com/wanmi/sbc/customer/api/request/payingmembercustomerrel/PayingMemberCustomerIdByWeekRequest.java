package com.wanmi.sbc.customer.api.request.payingmembercustomerrel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author xuyunpeng
 * @className PayingMemberCustomerIdByWeek
 * @description
 * @date 2022/6/6 1:44 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayingMemberCustomerIdByWeekRequest extends BaseRequest {
    private static final long serialVersionUID = 1475018162534832860L;

    /**
     * 指定的会员范围
     */
    @Schema(description = "指定的会员范围")
    @NotEmpty
    private List<String> customerIds;

    /**
     * 指定的时间
     */
    @Schema(description = "指定时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate date;
}
