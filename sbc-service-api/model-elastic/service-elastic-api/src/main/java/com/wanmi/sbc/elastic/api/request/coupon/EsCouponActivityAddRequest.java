package com.wanmi.sbc.elastic.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponActivityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增优惠券活动
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsCouponActivityAddRequest extends BaseRequest {


    private EsCouponActivityDTO esCouponActivityDTO;
}
