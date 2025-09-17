package com.wanmi.sbc.job;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.RepeatType;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.levelrights.CustomerLevelRightsCouponAnalyseProvider;
import com.wanmi.sbc.customer.api.provider.levelrights.CustomerLevelRightsRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerIdsByUpgradeTimeRequest;
import com.wanmi.sbc.customer.api.request.levelrights.CustomerLevelRightsRelRequest;
import com.wanmi.sbc.customer.api.request.levelrights.CustomerLevelRightsRepeatRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerIdByWeekRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelRightsRelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelRightsVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCustomerRightsProvider;
import com.wanmi.sbc.marketing.api.request.coupon.RightsCouponRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingCustomerType;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordQueryProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecord.PayingMemberRecordRightsRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className CustomerLevelRightsCouponRepeatJobHandler
 * @description 权益礼包-周期发券
 * 1、每周X：普通会员以升级时间为准，付费会员以开通时间为准
 * 2、每月1号
 * @date 2022/5/13 4:40 PM
 **/

@Component
public class CustomerLevelRightsCouponRepeatJobHandler {

    @Autowired
    private CustomerLevelRightsCouponAnalyseProvider customerLevelRightsCouponAnalyseProvider;

    @Autowired
    private CustomerLevelRightsRelQueryProvider customerLevelRightsRelQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CouponCustomerRightsProvider couponCustomerRightsProvider;

    @Autowired
    private PayingMemberRecordQueryProvider payingMemberRecordQueryProvider;

    @Autowired
    private PayingMemberCustomerRelQueryProvider payingMemberCustomerRelQueryProvider;

    private static final Integer PAGE_SIZE = 1000;

    private static Logger log = LoggerFactory.getLogger(CustomerLevelRightsCouponRepeatJobHandler.class);

    @XxlJob(value = "customerLevelRightsCouponRepeatJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1);
        LocalDateTime date = LocalDateTime.now();
        //指定时间
        if (StringUtils.isNotBlank(param)) {
            date = LocalDate.parse(param, pattern).atStartOfDay();
        }
        if (date.getDayOfMonth() == LocalDate.MIN.getDayOfMonth()) {
            //当日是1号
            this.mouth(date);
        } else {
            this.week(date);
        }
    }

    /**
     * 每周发券：普通会员以升级时间为准，付费会员以开通时间为准
     */
    public void week(LocalDateTime now) {
        List<CustomerLevelRightsVO> rights = customerLevelRightsCouponAnalyseProvider.queryRepeatCouponsData(CustomerLevelRightsRepeatRequest
                .builder().type(RepeatType.WEEK).build()).getContext().getCustomerLevelRightsVOList();
        //普通会员发券
        this.ordinaryMember(rights, RepeatType.WEEK, now);
        //付费会员发券
        this.payingMember(rights, RepeatType.WEEK, now);

    }

    /**
     * 每月发券
     */
    public void mouth(LocalDateTime now) {
        List<CustomerLevelRightsVO> rights = customerLevelRightsCouponAnalyseProvider.queryRepeatCouponsData(CustomerLevelRightsRepeatRequest
                .builder().type(RepeatType.MONTH).build()).getContext().getCustomerLevelRightsVOList();
        //普通会员发券
        this.ordinaryMember(rights, RepeatType.MONTH, now);
        //付费会员发券
        this.payingMember(rights, RepeatType.MONTH, now);
    }

    /**
     * 普通会员
     */
    public void ordinaryMember(List<CustomerLevelRightsVO> rights, RepeatType type, LocalDateTime now) {
        LocalDateTime checkTime = DateUtil.getCheckTime(now, type);
        rights.forEach(right -> {
            // 查询包含该权益的等级id
            List<Long> levelIds = customerLevelRightsRelQueryProvider
                    .listByRightsId(CustomerLevelRightsRelRequest.builder().rightsId(right.getRightsId()).build())
                    .getContext()
                    .getCustomerLevelRightsRelVOList()
                    .stream()
                    .map(CustomerLevelRightsRelVO::getCustomerLevelId)
                    .distinct()
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(levelIds)) {
                Integer pageNum = NumberUtils.INTEGER_ZERO;
                CustomerIdsByUpgradeTimeRequest upgradeTimeRequest = new CustomerIdsByUpgradeTimeRequest();
                upgradeTimeRequest.setLevelIds(levelIds);
                upgradeTimeRequest.setUpgradeTime(now);
                upgradeTimeRequest.setType(type);
                upgradeTimeRequest.setPageSize(PAGE_SIZE);
                log.info("普通会员权益券礼包发放开始，发放类型：{}，权益id：{}", type.getStateId(), right.getRightsId());
                while(true) {
                    log.info("普通会员权益券礼包发放，发放类型：{}，权益id：{}，当前页码：{}，每页{}条", type.getStateId(), right.getRightsId(), pageNum, PAGE_SIZE);
                    upgradeTimeRequest.setPageNum(pageNum);
                    //查询会员ids
                    List<String> customerIds = customerQueryProvider.listCustomerIdsByUpgradeTime(upgradeTimeRequest).getContext().getCustomerIds();
                    if (CollectionUtils.isEmpty(customerIds)) {
                        log.info("普通会员权益券礼包发放，发放类型：{}，权益id：{}，发放完成", type.getStateId(), right.getRightsId());
                        break;
                    }
                    // 批量发放优惠券
                    couponCustomerRightsProvider.customerRightsRepeatCoupons(RightsCouponRequest.builder()
                            .customerType(MarketingCustomerType.ORDINARY)
                            .checkTime(checkTime)
                            .customerIds(customerIds)
                            .activityId(right.getActivityId()).build());
                    pageNum ++;
                }
            }
        });

    }

    /**
     * 付费会员发券
     */
    public void payingMember(List<CustomerLevelRightsVO> rights, RepeatType type, LocalDateTime now) {
        LocalDateTime checkTime = DateUtil.getCheckTime(now, type);
        rights.forEach(right -> {
            Integer pageNum = NumberUtils.INTEGER_ZERO;
            PayingMemberRecordRightsRequest recordRightsRequest = new PayingMemberRecordRightsRequest();
            recordRightsRequest.setRightsId(right.getRightsId());
            recordRightsRequest.setDate(now.toLocalDate());
            recordRightsRequest.setRepeatType(type);
            recordRightsRequest.setPageSize(PAGE_SIZE);
            log.info("付费会员权益券礼包发放开始，发放类型：{}，发放权益id：{}", type.getStateId(), right.getRightsId());
            while(true) {
                log.info("普通会员权益券礼包发放，发放类型：{}，权益id：{}，当前页码：{}，每页{}条", type.getStateId(), right.getRightsId(), pageNum, PAGE_SIZE);
                recordRightsRequest.setPageNum(pageNum);
                Map<String, String> recordMap = payingMemberRecordQueryProvider.findByRights(recordRightsRequest).getContext().getRecordMap();
                if (recordMap.isEmpty()) {
                    log.info("付费会员权益券礼包发放，发放类型：{}，发放权益id：{}，发放完成", type.getStateId(), right.getRightsId());
                    break;
                }
                //按周发放根据开通时间筛选
                if (RepeatType.WEEK == type) {
                    List<String> customerIds = Lists.newArrayList(recordMap.keySet());
                    List<String> activeCustomerIds = payingMemberCustomerRelQueryProvider.getActiveCustomerIdByWeek(PayingMemberCustomerIdByWeekRequest.builder()
                            .customerIds(customerIds).date(now.toLocalDate()).build()).getContext().getCustomerIds();
                    recordMap = recordMap.entrySet().stream()
                            .filter(entry -> activeCustomerIds.contains(entry.getKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getKey));
                }
                if (!recordMap.isEmpty()) {
                    // 批量发放优惠券
                    couponCustomerRightsProvider.customerRightsRepeatCoupons(RightsCouponRequest.builder()
                            .customerType(MarketingCustomerType.PAYING_MEMBER)
                            .checkTime(checkTime)
                            .payingMemberInfo(recordMap)
                            .activityId(right.getActivityId()).build());
                }
                pageNum ++;
            }
        });
    }
}
