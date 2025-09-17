package com.wanmi.sbc.marketing.bean.constant;

/**
 * @ClassName DrawRedisKeyConstant
 * @Description redis key对应常量
 * @Author wwc
 * @Date 2021/4/12 17:13
 **/
public final class DrawRedisKeyConstant {

    private DrawRedisKeyConstant() {

    }

    /**
     * 活动基础信息
     */
    public final static String DRAW_ACTIVITY = "draw";

    /**
     * 活动基础信息的field字段名称
     */
    public final static String ACTIVITY_FIELD_NAME = "info";

    /**
     * 奖品集合field字段名称
     */
    public final static String PRIZE_LIST_FIELD_NAME = "prizeList";

    /**
     * 奖品库存的field字段名称
     */
    public final static String PRIZE_FIELD_NAME = "prizeStock";


    /**
     * 活动奖品关联客户后缀
     */
    public final static String DRAW_USER_INFO = "userInfo";

    /**
     * 活动奖品关联客户filed字段名称
     */
    public final static String USER_REL = "relList";

    /**
     * 用户当天剩余抽奖次数filed字段名称
     */
    public final static String USER_DRAW_TODAY_COUNT = "todayCount";

    /**
     * 用户本周剩余抽奖次数filed字段名称
     */
    public final static String USER_DRAW_WEEK_COUNT = "weekCount";

    /**
     * 用户本月剩余抽奖次数filed字段名称
     */
    public final static String USER_DRAW_MONTH_COUNT = "monthCount";


    /**
     * 用户活动剩余抽奖次数filed字段名称
     */
    public final static String USER_DRAW_ACTIVITY_COUNT = "activityCount";

    /**
     * 用户中奖次数filed字段名称
     */
    public final static String USER_DRAW_TOTAL_AWARD = "totalAwardCount";

    /**
     * 用户当天剩余中奖次数filed字段名称
     */
    public final static String USER_DRAW_TODAY_AWARD = "todayAwardCount";

    /**
     * 用户本次活动剩余中奖次数filed字段名称
     */
    public final static String USER_DRAW_ACTIVITY_AWARD = "activityAwardCount";

    /**
     * 活动抽奖随机数区间
     */
    public final static String DRAW_SCOPE = "scope";


    /**
     * 用户参与活动的lock key
     */
    public final static String DRAW_USER_JOIN_LOCK = "userJoin";



    /**
     * 用户参与活动的lock key
     */
    public final static String DRAW_ACTIVITY_LOCK_KEY = "drawActivity";

    /**
     * 获取活动信息key
     *
     * @param activityId
     * @return
     */
    public static String getActivityKey(Long activityId) {
        return DRAW_ACTIVITY + ":" +activityId;
    }


    /**
     * 获取用户关系key
     *
     * @param activityId
     * @param customerId
     * @return
     */
    public static String getCustomerKey(Long activityId, String customerId) {
        return DRAW_USER_INFO + ":" + activityId + ":" + customerId;
    }


    /**
     * 获取参与活动用户lock key
     *
     * @param activityId
     * @return
     */
    public static String getUserLockKey(Long activityId, String customerId) {
        return DRAW_USER_JOIN_LOCK + ":" + activityId + ":" + customerId;
    }

    /**
     * 获取活动lock key
     *
     * @param activityId
     * @return
     */
    public static String getActivityLockKey(Long activityId) {
        return DRAW_ACTIVITY_LOCK_KEY + ":" + activityId;
    }

    /**
     * 获取参与活动用户lock key
     *
     * @param activityId
     * @return
     */
    public static String getDrawScope(Long activityId) {
        return DRAW_ACTIVITY + DRAW_SCOPE + ":" + activityId;
    }

}
