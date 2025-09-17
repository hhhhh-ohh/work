package com.wanmi.sbc.marketing.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.MarketingCustomerType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author xuyunpeng
 * @className RightsCouponRequest
 * @description
 * @date 2022/5/14 9:53 AM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RightsCouponRequest extends BaseRequest {
    private static final long serialVersionUID = 5276461001417509376L;

    /**
     * 普通会员
     */
    @Schema(description = "普通会员")
    private List<String> customerIds;

    /**
     * 营销会员类型 0、普通会员 1、付费会员
     */
    @Schema(description = "营销会员类型 0、普通会员 1、付费会员")
    @NotNull
    private MarketingCustomerType customerType;

    /**
     * 券活动id
     */
    @Schema(description = "券活动id")
    @NotBlank
    private String activityId;

    /**
     * 付费会员信息 key:customerId value:payingMemberRecordId
     */
    @Schema(description = "付费会员信息 key:customerId value:payingMemberRecordId")
    private Map<String, String> payingMemberInfo;

    /**
     * 校验时间
     */
    @Schema(description = "校验时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime checkTime;
}
