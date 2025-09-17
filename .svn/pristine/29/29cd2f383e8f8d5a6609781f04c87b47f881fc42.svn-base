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
 * @Date: Created In 2:57 PM 2018/9/13
 * @Description: 优惠券分类新增Request
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCateAddRequest extends BaseRequest {

    private static final long serialVersionUID = -7023979435533305034L;

    /**
     * 优惠券分类名称
     */
    @Schema(description = "优惠券分类名称")
    private String couponCateName;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

}
