package com.wanmi.sbc.marketing.coupon.request;

import com.wanmi.sbc.marketing.coupon.model.entity.TradeCouponSnapshot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description 券优惠金额计算入参
 * @author malianfeng
 * @date 2022/10/9 11:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponCounterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 券快照商品列表
     */
    private List<TradeCouponSnapshot.CheckGoodsInfo> goodsInfos;

    /**
     * 券
     */
    private TradeCouponSnapshot.CheckCouponCode checkCouponCode;

    /**
     * 店铺运费
     */
    private BigDecimal freight;
}

