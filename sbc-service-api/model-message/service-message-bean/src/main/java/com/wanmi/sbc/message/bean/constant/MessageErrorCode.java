package com.wanmi.sbc.message.bean.constant;

/**
 * 站内信错误码
 */
public class MessageErrorCode {

    /**
     * 发送时间晚于当前时间
     */
    public final static String ERROR_DATR = "K-MES001";

    /**
     * 任务已结束
     */
    public final static String TASK_IS_END = "K-MES002";

    /**
     * 发送时间范围错误，可指定具体message
     */
    public static final String SEND_TIME_RANGE_ERROR = "K-MES003";

    /**
     * 公告更改错误，可指定具体message
     */
    public static final String NOTICES_MODIFY_ERROR = "K-MES004";

    /**
     * 状态已发生变化，请刷新页面后查看
     */
    public static final String STATUS_ALREADY_CHANGED_ERROR = "K-MES005";
}
