package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCateRelaListByCouponIdsRequest extends BaseRequest {
    
    private static final long serialVersionUID = 7087390434282722024L;

    @Schema(description = "优惠券ID : 分类ID集合")
    private Map<String,List<String>> cateIdsMap;
}
