package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

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
public class CouponCateListForSupplierResponse extends BasicResponse {

    private static final long serialVersionUID = 6260024003725752814L;

    @Schema(description = "优惠券分类列表")
    private List<CouponCateVO> list;
}
