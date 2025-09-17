package com.wanmi.sbc.marketing.fullreturn.model.root;

import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

/**
 * 满返
 */
@Entity
@Table(name = "marketing_full_return_detail")
@Data
public class MarketingFullReturnDetail {

    /**
     *  满返赠券Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_detail_id")
    private Long returnDetailId;

    /**
     *  满返多级促销Id
     */
    @Column(name = "return_level_id")
    private Long returnLevelId;

    /**
     *  赠券Id
     */
    @Column(name = "coupon_id")
    private String couponId;

    /**
     *  赠券数量
     */
    @Column(name = "coupon_num")
    private Long couponNum;

    /**
     *  满返ID
     */
    @Column(name = "marketing_id")
    private Long marketingId;

    /**
     * 优惠券信息
     */
    @Transient
    private CouponInfo couponInfo;

}
