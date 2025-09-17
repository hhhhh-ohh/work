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

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: songhanlin
 * @Date: Created In 2:24 PM 2018/9/13
 * @Description: 优惠券分类删除Request
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCateDeleteRequest extends BaseRequest {

    private static final long serialVersionUID = 8237283110431064028L;

    /**
     * 优惠券分类Id
     */
    @Schema(description = "优惠券分类Id")
    private String couponCateId;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String delPerson;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime delTime;

}
