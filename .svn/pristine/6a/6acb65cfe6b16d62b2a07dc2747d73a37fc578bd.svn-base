package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: songhanlin
 * @Date: Created In 5:18 PM 2018/9/13
 * @Description: 优惠券分类修改Request
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCateModifyRequest extends BaseRequest {

    /**
     * 优惠券分类Id
     */
    @Schema(description = "优惠券分类Id")
    private String couponCateId;

    /**
     * 优惠券分类名称
     */
    @Schema(description = "优惠券分类名称")
    private String couponCateName;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;
}
