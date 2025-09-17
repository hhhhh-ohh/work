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
 * @author 优惠券概况——天
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponOverviewDay extends CouponOverviewView {
    private transient Long id;

    /**
     * 统计日期
     */
    private transient LocalDate statDate;

    /**
     * 创建时间
     */
    private transient Date createTime;

    @Override
    public String getXDate() {
        return DateUtil.format(getStatDate(), DateUtil.FMT_DATE_3) + DateUtil.getWeekStr(getStatDate());
    }
}