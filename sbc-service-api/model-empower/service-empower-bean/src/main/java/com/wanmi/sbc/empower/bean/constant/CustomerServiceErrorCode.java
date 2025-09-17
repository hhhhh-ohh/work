package com.wanmi.sbc.empower.bean.constant;

/**
 * <p>模块异常码定义</p>
 * Created by of628-wenzhi on 2018-06-21-下午3:10.
 */
public final class CustomerServiceErrorCode {
    private CustomerServiceErrorCode() {
    }

    /**
     * 在线客服不存在
     */
    public static final String ONLINE_SERVER_NOT_EXIST_ERROR = "online-server-not-exist-error";

    /**
     * 在线客服不能同时开启多个
     */
    public static final String ONLINE_SERVER_OPEN_MULTIPLE_ERROR = "K-090909";

    /**
     * 客服账号已存在
     */
    public static final String ONLINE_SERVER_ACCOUNT_ALREADY_EXIST = "online-server-account-already-exist";

    /**
     * 最多添加10条座席记录
     */
    public static final String ONLINE_SERVER_MAX_ERROR = "online-server-max-error";


}
