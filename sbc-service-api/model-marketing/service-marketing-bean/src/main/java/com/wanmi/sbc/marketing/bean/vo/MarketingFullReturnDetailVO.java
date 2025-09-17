package com.wanmi.sbc.marketing.bean.vo;


import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 黄昭
 * @className MarketingFullReturnDetailVO
 * @description TODO
 * @date 2022/4/11 10:30
 **/
@Data
@Schema
public class MarketingFullReturnDetailVO implements Serializable {

    private static final long serialVersionUID = 8981043347214169160L;
    /**
     *  满返赠券Id
     */
    private Long returnDetailId;

    /**
     *  满返多级促销Id
     */
    private Long returnLevelId;

    /**
     *  赠券Id
     */
    private String couponId;

    /**
     *  赠券数量
     */
    private Long couponNum;

    /**
     *  满返ID
     */
    private Long marketingId;

    /**
     * 优惠券信息
     */
    private CouponInfoVO couponInfo;

    /**
     * 优惠券详细信息
     */
    private MagicCouponInfoVO magicCouponInfoVO;
}