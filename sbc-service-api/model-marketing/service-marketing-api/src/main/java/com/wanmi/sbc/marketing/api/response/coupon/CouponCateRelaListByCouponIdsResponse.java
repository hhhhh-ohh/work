package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponCateRelaVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCateRelaListByCouponIdsResponse extends BasicResponse {

    private static final long serialVersionUID = -1707333916000714781L;

    @Schema(description = "优惠券关联分类列表")
    private List<CouponCateRelaVO> cateRelaVOList;
}
