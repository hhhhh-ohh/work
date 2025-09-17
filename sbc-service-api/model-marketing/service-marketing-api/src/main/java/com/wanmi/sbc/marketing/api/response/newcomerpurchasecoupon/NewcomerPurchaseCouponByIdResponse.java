package com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon;

import com.wanmi.sbc.marketing.bean.vo.NewcomerPurchaseCouponVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）新人购优惠券信息response</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseCouponByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 新人购优惠券信息
     */
    @Schema(description = "新人购优惠券信息")
    private NewcomerPurchaseCouponVO newcomerPurchaseCouponVO;
}
