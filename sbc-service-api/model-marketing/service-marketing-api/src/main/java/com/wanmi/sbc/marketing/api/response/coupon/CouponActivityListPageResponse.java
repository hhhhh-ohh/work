package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityBaseVO;

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
public class CouponActivityListPageResponse extends BasicResponse {

    private static final long serialVersionUID = -2952732980286341563L;

    @Schema(description = "优惠券活动信息分页列表")
    private List<CouponActivityBaseVO> couponActivityBaseVOList;
}
