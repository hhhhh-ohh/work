package com.wanmi.sbc.job;


import com.wanmi.sbc.marketing.api.provider.coupon.CouponCustomerRightsProvider;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordProvider;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务Handler（Bean模式）
 * 查询是否存在符合会员等级权益发券的会员，每月n号发放优惠券
 *
 * @author bail 2019-3-24
 */
@Component
@Slf4j
public class CustomerLevelRightsCouponJobHandler {

    @Autowired
    private CouponCustomerRightsProvider couponCustomerRightsProvider;

    @Autowired
    private PayingMemberRecordProvider payingMemberRecordProvider;

    @XxlJob(value = "customerLevelRightsCouponJobHandler")
    public void execute() throws Exception {
        //普通会员
        couponCustomerRightsProvider.customerRightsIssueCoupons();
        //付费会员
        payingMemberRecordProvider.rightsCoupon();
    }

}
