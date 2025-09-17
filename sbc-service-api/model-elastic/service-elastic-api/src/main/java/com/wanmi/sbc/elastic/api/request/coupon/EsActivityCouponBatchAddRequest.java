package com.wanmi.sbc.elastic.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsActivityCouponDTO;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponScopeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 新增优惠券
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsActivityCouponBatchAddRequest extends BaseRequest {


    private List<EsActivityCouponDTO> esActivityCoupons;
}
