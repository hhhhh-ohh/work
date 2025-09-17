package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
public class CouponCateSortVO extends BasicResponse {

    private static final long serialVersionUID = 1879018401605607653L;

    /**
     * 优惠券分类Id
     */
    @Schema(description = "优惠券分类Id")
    private String couponCateId;

    /**
     * 优惠券排序顺序
     */
    @Schema(description = "优惠券分类排序顺序")
    private Integer cateSort;

}
