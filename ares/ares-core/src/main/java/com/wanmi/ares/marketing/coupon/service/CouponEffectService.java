package com.wanmi.ares.marketing.coupon.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanmi.ares.common.DataPeriod;
import com.wanmi.ares.enums.StatType;
import com.wanmi.ares.marketing.coupon.dao.CouponActivityEffectRecentMapper;
import com.wanmi.ares.marketing.coupon.dao.CouponEffectMapper;
import com.wanmi.ares.marketing.coupon.dao.CouponInfoEffectRecentMapper;
import com.wanmi.ares.marketing.coupon.dao.CouponStoreEffectRecentMapper;
import com.wanmi.ares.request.coupon.CouponActivityEffectRequest;
import com.wanmi.ares.request.coupon.CouponEffectRequest;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.view.coupon.CouponActivityEffectView;
import com.wanmi.ares.view.coupon.CouponInfoEffectView;
import com.wanmi.ares.view.coupon.CouponStoreEffectView;
import com.wanmi.sbc.common.util.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName CouponScheduled
 * @Description
 * @Author zhanggaolei
 * @Date 2021/1/11 9:36
 * @Version 1.0
 **/
@Service
public class CouponEffectService {

    @Autowired
    private CouponEffectMapper couponEffectMapper;
    @Autowired
    private CouponInfoEffectRecentMapper couponInfoEffectRecentMapper;
    @Autowired
    private CouponActivityEffectRecentMapper couponActivityEffectRecentMapper;
    @Autowired
    private CouponStoreEffectRecentMapper couponStoreEffectRecentMapper;

    private static  final  String ACTIVITY_ID ="activity_id";
    private static  final  String COUPON_ID ="coupon_id";


    public void generator(){
        LocalDate localDate = LocalDate.now();
        String endTime = DateUtil.format(localDate,DateUtil.FMT_DATE_1);



        couponEffectMapper.deleteActivityRecent();

        //插入最近日期的数据
        String beginTime = DataPeriod.getStartDate();
        couponEffectMapper.saveBossActivityRecent(beginTime,endTime, ACTIVITY_ID, StatType.RECENT.getValue());
        couponEffectMapper.saveSupplierActivityRecent(beginTime,endTime, ACTIVITY_ID,StatType.RECENT.getValue());

        //插入近三十天的数据
        if(DataPeriod.period!=30) {
            beginTime = localDate.minusDays(30).toString();
            couponEffectMapper.saveBossActivityRecent(beginTime, endTime, ACTIVITY_ID, StatType.THIRTY.getValue());
            couponEffectMapper.saveSupplierActivityRecent(beginTime, endTime, ACTIVITY_ID, StatType.THIRTY.getValue());
        }else{
            couponEffectMapper.saveActivityByRecent(StatType.THIRTY.getValue());
        }


        //插入近九十天的数据
        if(DataPeriod.period!=90) {
            beginTime = localDate.minusDays(90).toString();
            couponEffectMapper.saveBossActivityRecent(beginTime,endTime, ACTIVITY_ID, StatType.NINETY.getValue());
            couponEffectMapper.saveSupplierActivityRecent(beginTime,endTime, ACTIVITY_ID,StatType.NINETY.getValue());
        }else{
            couponEffectMapper.saveActivityByRecent(StatType.NINETY.getValue());
        }

        couponEffectMapper.deleteCouponRecent();
        //插入最近日期的数据
        beginTime = DataPeriod.getStartDate();
        couponEffectMapper.saveBossCouponRecent(beginTime,endTime,COUPON_ID,StatType.RECENT.getValue());
        couponEffectMapper.saveSupplierCouponRecent(beginTime,endTime,COUPON_ID,StatType.RECENT.getValue());

        //插入近三十天的数据
        if(DataPeriod.period!=30) {
            beginTime = localDate.minusDays(30).toString();
            couponEffectMapper.saveBossCouponRecent(beginTime,endTime,COUPON_ID,StatType.THIRTY.getValue());
            couponEffectMapper.saveSupplierCouponRecent(beginTime,endTime,COUPON_ID,StatType.THIRTY.getValue());
        }else{
            couponEffectMapper.saveCouponByRecent(StatType.THIRTY.getValue());
        }

        //插入最近九十天的数据
        if(DataPeriod.period!=90) {
            beginTime = localDate.minusDays(90).toString();
            couponEffectMapper.saveBossCouponRecent(beginTime,endTime,COUPON_ID,StatType.NINETY.getValue());
            couponEffectMapper.saveSupplierCouponRecent(beginTime,endTime,COUPON_ID,StatType.NINETY.getValue());
        }else{
            couponEffectMapper.saveCouponByRecent(StatType.NINETY.getValue());
        }

        couponEffectMapper.deleteStoreRecent();
        beginTime = DataPeriod.getStartDate();
        couponEffectMapper.saveStoreRecent(beginTime, endTime,StatType.RECENT.getValue());
        //插入近三十天的数据
        if(DataPeriod.period!=30) {
            beginTime = localDate.minusDays(30).toString();
            couponEffectMapper.saveStoreRecent(beginTime, endTime, StatType.THIRTY.getValue());
        }else{
            couponEffectMapper.saveStoreByRecent(StatType.THIRTY.getValue());
        }

        //插入近三十天的数据
        if(DataPeriod.period!=90) {
            beginTime = localDate.minusDays(90).toString();
            couponEffectMapper.saveStoreRecent(beginTime, endTime, StatType.NINETY.getValue());
        }else{
            couponEffectMapper.saveStoreByRecent(StatType.NINETY.getValue());
        }

    }



    public PageInfo<CouponInfoEffectView> pageCouponInfoEffect(CouponEffectRequest request){
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<CouponInfoEffectView> list = couponInfoEffectRecentMapper.selectList(request);
        list = packageDenominationStr(list);
        return new PageInfo<>(list);

    }

    /**
     *  封装优惠券面值文案
     * @param list
     * @return
     */
    private List<CouponInfoEffectView> packageDenominationStr(List<CouponInfoEffectView> list) {
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        list.stream().forEach(couponInfoEffectView -> {
            couponInfoEffectView.setDenominationStr(this.getDenominationStr(couponInfoEffectView));
        });
        return list;
    }

    /**
     * 获取优惠券的面值描述
     * @param couponInfoEffectView
     * @return
     */
     private String getDenominationStr(CouponInfoEffectView couponInfoEffectView) {
        if (Objects.isNull(couponInfoEffectView.getCouponMarketingType())
                || Objects.isNull(couponInfoEffectView.getCouponDiscountMode())
                || Objects.isNull(couponInfoEffectView.getFullbuyType())) {
            return "-";
        }
        BigDecimal fullBuyPriceNew = BigDecimal.ZERO;
        if (couponInfoEffectView.getFullbuyType().intValue() == 1 && Objects.nonNull(couponInfoEffectView.getFullBuyPrice())) {
            fullBuyPriceNew = couponInfoEffectView.getFullBuyPrice();
        }
        if (couponInfoEffectView.getCouponMarketingType().intValue() == 0) {
            // 满减券，满x减y
            return String.format("满%s减%s", fullBuyPriceNew.intValue(), couponInfoEffectView.getDenomination().setScale(0));
        } else if (couponInfoEffectView.getCouponMarketingType().intValue() == 1) {
            // 满折券，满x打y折
            BigDecimal denominationNew = this.getDiscountLabel(couponInfoEffectView.getDenomination());
            if(Objects.nonNull(couponInfoEffectView.getMaxDiscountLimit()) && couponInfoEffectView.getMaxDiscountLimit().compareTo(BigDecimal.ZERO) > 0) {
                return String.format("满%s打%s折，最多减%s元", fullBuyPriceNew.intValue(), denominationNew, couponInfoEffectView.getMaxDiscountLimit().intValue());
            } else {
                return String.format("满%s打%s折", fullBuyPriceNew.intValue(), denominationNew);
            }
        } else if (couponInfoEffectView.getCouponMarketingType().intValue() == 2) {
            if (couponInfoEffectView.getCouponDiscountMode().intValue() == 1) {
                if (couponInfoEffectView.getFullbuyType().intValue() == 1) {
                    return String.format("满%s包邮", fullBuyPriceNew.intValue());
                } else {
                    return "包邮";
                }
            }
            // 运费券，满x减y元运费/满x包邮
            return String.format("满%s减%s元运费", fullBuyPriceNew.intValue(), couponInfoEffectView.getDenomination().setScale(0));
        }
        return "-";
    }

    private BigDecimal getDiscountLabel(BigDecimal denomination) {
        String s = denomination.toPlainString();
        int index = s.indexOf('.');
        if (index < 0) {
            return denomination;
        }
        if (s.length() == Constants.THREE) {
            return denomination
                    .multiply(BigDecimal.TEN)
                    .setScale(Constants.ZERO, RoundingMode.FLOOR);
        } else {
            return denomination
                    .multiply(BigDecimal.TEN)
                    .setScale(Constants.ONE, RoundingMode.FLOOR);
        }
    }

    public PageInfo<CouponInfoEffectView> pageCouponInfoEffectByActivityId(CouponEffectRequest request){

        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<CouponInfoEffectView> list = couponInfoEffectRecentMapper.selectListByActivityId(request);
        list = this.packageDenominationStr(list);
        return  new PageInfo<>(list);
    }

    public PageInfo<CouponActivityEffectView> pageCouponActivityEffect(CouponActivityEffectRequest request){

        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<CouponActivityEffectView> list = couponActivityEffectRecentMapper.selectList(request);
        return  new PageInfo<>(list);
    }

    public PageInfo<CouponStoreEffectView> pageCouponStoreEffect(CouponEffectRequest request){

        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<CouponStoreEffectView> list = couponStoreEffectRecentMapper.selectList(request);
        return new PageInfo<>(list);
    }

    public Long countByPageTotal(CouponEffectRequest request){
        return couponInfoEffectRecentMapper.couponInfoTotalCount(request);
    }
}
