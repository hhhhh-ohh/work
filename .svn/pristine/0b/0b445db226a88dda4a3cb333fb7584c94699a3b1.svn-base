package com.wanmi.sbc.marketing.coupon.model.entity;

import com.wanmi.sbc.marketing.bean.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午7:58 2018/9/27
 * @Description:
 * 订单优惠券相关信息快照;
 * PS：确认订单页初始方法中生成，确认订单页勾选优惠券时使用；主要是因为，确认订单初始方法中的数据在勾选优惠券时还要使用
 */
@Data
@Document(collection = "tradeCouponSnapshot")
public class TradeCouponSnapshot implements Serializable {

    private static final long serialVersionUID = -2910220369384862778L;

    @Id
    private String id;

    /**
     * 客户id
     */
    private String buyerId;

    /**
     * 用户终端token
     */
    private String terminalToken;

    /**
     * 订单商品列表
     */
    private List<CheckGoodsInfo> goodsInfos = new ArrayList<>();


    /**
     * 可用优惠券列表
     */
    private List<CheckCouponCode> couponCodes = new ArrayList<>();


    @Data
    public static class CheckGoodsInfo {

        /**
         * 店铺id
         */
        private Long storeId;

        /**
         * 商品id
         */
        private String goodsInfoId;

        /**
         * 均摊价
         */
        private BigDecimal splitPrice;

    }

    @Data
    public static class CheckCouponCode {

        /**
         * 店铺id
         */
        private Long storeId;

        /**
         * 活动id
         */
        private String activityId;

        /**
         * 优惠券id
         */
        private String couponId;

        /**
         * 优惠券码id
         */
        private String couponCodeId;

        /**
         * 优惠券面值
         */
        private BigDecimal denomination;

        /**
         * 优惠券码状态(使用优惠券页券码的状态)
         */
        @Schema(description = "使用优惠券码状态")
        private CouponCodeStatus status;

        /**
         * 购满类型 0：无门槛，1：满N元可使用
         */
        private FullBuyType fullBuyType;

        /**
         * 购满多少钱
         */
        private BigDecimal fullBuyPrice;

        /**
         * 优惠券类型 0通用券 1店铺券
         */
        private CouponType couponType;

        /**
         * 优惠券营销类型 0满减券 1满折券 2运费券
         */
        private CouponMarketingType couponMarketingType;

        /**
         * 优惠券优惠方式 0减免 1包邮
         */
        private CouponDiscountMode couponDiscountMode;

        /**
         * 最大优惠金额限制（结合满折券使用）
         */
        private BigDecimal maxDiscountLimit;

        /**
         *  结束时间（用于自动选券策略的依据）
         */
        private LocalDateTime endTime;

        /**
         *  领取时间（用于自动选券策略的依据）
         */
        private LocalDateTime acquireTime;

        /**
         * 优惠券关联的商品
         */
        private List<String> goodsInfoIds;

    }

}
