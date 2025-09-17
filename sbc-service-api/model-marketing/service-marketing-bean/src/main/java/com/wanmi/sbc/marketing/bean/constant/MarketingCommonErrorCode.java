package com.wanmi.sbc.marketing.bean.constant;

/**
 * @author lvzhenwei
 * @className CommonErrorCode
 * @description marketing 服务错误异常码常量类
 * @date 2022/12/10 2:31 下午
 **/
public final class MarketingCommonErrorCode {
    private MarketingCommonErrorCode() {
    }

    /**
     * 礼品卡兑换：卡密无效
     */
    public static final String USER_GIFT_CARD_EXCHANGE_ERROR = "K-080029";

    /**
     * 礼品卡不存在
     */
    public static final String GIFT_CARD_NOT_EXIST = "K-080044";

    /**
     * 礼品卡激活失败
     */
    public static final String USER_GIFT_CARD_ACTIVATE_ERROR = "K-080045";

    /**
     * 礼品卡验证失败：礼品卡余额发生变化，请重新选择
     */
    public static final String GIFT_CARD_CHECK_ERROR = "K-080046";

    /**
     * 已销卡，请刷新后充实
     */
    public static final String GIFT_CARD_CANCELED = "K-080047";

    /**
     * 销卡失败，请刷新页面后重试
     */
    public static final String GIFT_CARD_CANCEL_ERROR = "K-080048";

    /**
     * 礼品卡已过期
     */
    public static final String GIFT_CARD_EXPIRED_ERROR = "K-080038";

    /**
     * 礼品卡库存不足
     */
    public static final String GIFT_CARD_STOCK_OUT_ERROR = "K-080039";

    /**
     * 礼品卡批次不存在
     */
    public static final String GIFT_CARD_BATCH_NO_EXIST = "K-080040";

    /**
     * 礼品卡批次已被审核
     */
    public static final String GIFT_CARD_BATCH_ALREADY_AUDITED = "K-080041";

    /**
     * 礼品卡已过期，自动审核失败
     */
    public static final String GIFT_CARD_BATCH_AUTO_REJECT_CAUSE_BY_EXPIRED = "K-080042";

    /**
     * 发卡失败的礼品卡，不允许销卡
     */
    public static final String GIFT_CARD_CANCEL_FAIL_CAUSE_BY_SEND_FAIL = "K-080043";

}
