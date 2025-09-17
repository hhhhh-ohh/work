package com.wanmi.ares.marketing.coupon.model;

import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.view.coupon.CouponOverviewView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author 
 * 优惠券概况——月
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponOverviewWeek extends CouponOverviewView {
    private transient Long id;

    /**
     * 周开始时间
     */
    private transient LocalDate weekStartDate;

    /**
     * 周结束时间
     */
    private transient LocalDate weekEndDate;


    /**
     * 创建时间
     */
    private transient Date createTime;

    @Override
    public String getXDate(){
        return DateUtil.format(getWeekStartDate(),DateUtil.FMT_DATE_3)+"~"+DateUtil.format(getWeekEndDate(),DateUtil.FMT_DATE_3);
    }
}