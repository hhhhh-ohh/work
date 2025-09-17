package com.wanmi.sbc.newcomer.response;

import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className NewcomerPurchaseXsiteResponse
 * @description
 * @date 2022/8/24 1:44 PM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseXsiteResponse implements Serializable {
    private static final long serialVersionUID = 4448904024485847090L;

    /**
     * 优惠券列表
     */
    @Schema(description = "优惠券列表")
    private List<CouponInfoVO> coupons;

    /**
     * 商品列表
     */
    @Schema(description = "商品列表")
    private List<GoodsInfoListVO> goods;

    /**
     * 活动是否有效
     */
    @Schema(description = "活动是否有效")
    private Boolean active;
}
