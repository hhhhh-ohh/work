package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.bean.enums.DeliveryCycleType;
import com.wanmi.sbc.goods.bean.vo.BuyCycleVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className BuyCycleDeliveryPlanService
 * @description
 * @date 2022/10/17 3:16 PM
 **/
@Service
public class BuyCycleDeliveryPlanService {

    /**
     * 计算下一期配送时间
     * 第一期的计算开始时间为当前时间
     * 第N（N>1）期的计算开始时间为上一期的配送时间
     * @param numberOfPeriods 第几期
     * @param dateTime 计算的开始时间
     * @param buyCycleVO 周期购信息
     * @return
     */
    public LocalDate getNextDeliveryDate(Integer numberOfPeriods, LocalDateTime dateTime, BuyCycleVO buyCycleVO){
        if (numberOfPeriods == null || dateTime == null || buyCycleVO == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (Constants.ONE == numberOfPeriods) {
            //第一期
            return firstDate(buyCycleVO);
        } else {
            //第N期
            return nextDate(dateTime.toLocalDate(), buyCycleVO);
        }
    }

    /**
     * 计算首期送达时间
     * @param buyCycleVO
     * @return
     */
    private LocalDate firstDate(BuyCycleVO buyCycleVO) {
        LocalDate deliveryDate;
        LocalDateTime dateTime = LocalDateTime.now();
        if (buyCycleVO.getReserveDay() != null && buyCycleVO.getReserveTime() != null) {
            if (dateTime.getHour() < buyCycleVO.getReserveTime()) {
                deliveryDate = dateTime.toLocalDate().plusDays(buyCycleVO.getReserveDay());
            } else {
                deliveryDate = dateTime.toLocalDate().plusDays(buyCycleVO.getReserveDay() + Constants.ONE);
            }
        } else {
            deliveryDate = dateTime.toLocalDate();
        }
        switch (DeliveryCycleType.fromValue(buyCycleVO.getDeliveryCycle())) {
            case WEEK_ONE:
            case WEEK_MANY:
            case WEEK_OPT_MANY:
                deliveryDate= getDateByWeekRule(deliveryDate.minusDays(Constants.ONE), buyCycleVO.getDeliveryDate(), buyCycleVO.getOptionalNum());
                break;
            case MONTH_ONE:
            case MONTH_MANY:
            case MONTH_OPT_MANY:
                deliveryDate = getDateByMonthRule(deliveryDate.minusDays(Constants.ONE), buyCycleVO.getDeliveryDate(), buyCycleVO.getOptionalNum());
                break;
            default:
                break;
        }
        return deliveryDate;
    }

    /**
     * 计算第N（N > 1）期送达时间
     * @param date
     * @param buyCycleVO
     * @return
     */
    private LocalDate nextDate(LocalDate date, BuyCycleVO buyCycleVO) {
        switch (DeliveryCycleType.fromValue(buyCycleVO.getDeliveryCycle())) {
            case DAY:
                return date.plusDays(Constants.ONE);
            case WEEK_ONE:
                return date.plusDays(Constants.SEVEN);
            case WEEK_MANY:
            case WEEK_OPT_MANY:
                return getDateByWeekRule(date, buyCycleVO.getDeliveryDate(), buyCycleVO.getOptionalNum());
            case MONTH_ONE:
                return getNextMonthDay(date);
            case MONTH_MANY:
            case MONTH_OPT_MANY:
                return getDateByMonthRule(date, buyCycleVO.getDeliveryDate(), buyCycleVO.getOptionalNum());
            default:
                return null;
        }
    }

    /**
     * 根据规则计算日期
     * @param date
     * @param ruleStr
     * @return
     */
    private LocalDate getDateByWeekRule(LocalDate date, String ruleStr, Integer optionalNum) {
        String[] ruleArrays = ruleStr.split(",");
        List<Integer> rules = Arrays.stream(ruleArrays).map(Integer::parseInt).sorted().collect(Collectors.toList());
        if (Objects.nonNull(optionalNum)) {
            rules = rules.subList(0, optionalNum-1);
        }
        int value = date.getDayOfWeek().getValue();
        Integer rule = rules.stream().filter(r -> r > value).findFirst().orElse(null);
        if (rule != null) {
            date = date.plusDays((long) rule - value);
        } else {
            date = date.plusDays(Constants.SEVEN - value + rules.get(Constants.ZERO));
        }
        return date;
    }

    /**
     * 根据规则计算日期
     * @param date
     * @param ruleStr
     * @return
     */
    private LocalDate getDateByMonthRule(LocalDate date, String ruleStr, Integer optionalNum) {
        String[] ruleArrays = ruleStr.split(",");
        List<Integer> rules = Arrays.stream(ruleArrays).map(Integer::parseInt).sorted().collect(Collectors.toList());
        if (Objects.nonNull(optionalNum)) {
            rules = rules.subList(0, optionalNum-1);
        }
        int value = date.getDayOfMonth();
        Integer rule = rules.stream().filter(r -> r > value).findFirst().orElse(null);
        if (rule != null) {
            int maxDate = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
            if (rule > maxDate) {
                rule = rules.get(Constants.ZERO);
                date = date.plusMonths(Constants.ONE).withDayOfMonth(rule);
            } else {
                date = date.plusDays((long) rule - value);
            }
        } else {
            rule = rules.get(Constants.ZERO);
            int maxDate = date.plusMonths(Constants.ONE).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
            if (rule > maxDate) {
                date = date.plusMonths(Constants.TWO).withDayOfMonth(rule);
            } else {
                date = date.plusMonths(Constants.ONE).withDayOfMonth(rule);
            }
        }
        return date;
    }

    /**
     * 计算下个同天的日期
     * @param date
     * @return
     */
    private LocalDate getNextMonthDay(LocalDate date){
        LocalDate nextMonthDate = date.plusMonths(Constants.ONE);
        LocalDate deliveryDate;
        //下月最大的一天
        int nextMaxDate = nextMonthDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        if (date.getDayOfMonth() > nextMaxDate) {
            deliveryDate = date.plusMonths(Constants.TWO);
        } else {
            deliveryDate = nextMonthDate;
        }
        return deliveryDate;
    }

    /**
     * 计算用户可选开始日期
     * @param buyCycleVO
     * @return
     */
    public LocalDate getChoseStartDay(BuyCycleVO buyCycleVO){
        return firstDate(buyCycleVO);
    }

    /**
     * 计算用户可选结束日期
     * @param startDate
     * @return
     */
    public LocalDate getChoseEndDate(LocalDate startDate) {
        LocalDate date = startDate.plusMonths(Constants.ONE);
        int maxDate = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        if (date.getDayOfMonth() == maxDate) {
            return date;
        } else {
            return date.minusDays(Constants.ONE);
        }
    }

}
