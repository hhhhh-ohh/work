package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponCateSortVO;

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
@AllArgsConstructor
@NoArgsConstructor
public class CouponCateSortResponse extends BasicResponse {

    private static final long serialVersionUID = -1643375555987359710L;

    @Schema(description = "优惠券分类排序列表")
    private List<CouponCateSortVO> list;
}
