package com.wanmi.sbc.marketing.bean.constant;

/**
 * @author xuyunpeng
 * @className EletronicErrorCode
 * @description 卡券异常常量类
 * @date 2022/2/3 9:49 下午
 **/
public final class EletronicErrorCode {

    private EletronicErrorCode() {
    }

    /**
     * 卡券模版未配置
     */
    public final static String TEMPLATE_NOT_SETTING = "K-083001";

    /**
     * 仅限xlsx、xls格式文件
     */
    public final static String FILE_EXT_ERROR = "K-083002";

    /**
     * 数据不正确，请使用模板文件填写
     */
    public final static String DATA_FILE_FAIL = "K-083003";

    /**
     * 数据为空，请完善内容
     */
    public final static String EMPTY_ERROR = "K-083004";

    /**
     * 卡密无法删除
     */
    public final static String CARD_CAN_NOT_DEL = "K-083005";

    /**
     * 卡密信息至少填一项
     */
    public final static String CARD_INFO_NOT_BLANK = "K-083006";

    /**
     * 该卡密已无法修改
     */
    public final static String CARD_CAN_NOT_MODIFY= "K-083007";

    /**
     * 卡号已存在
     */
    public final static String CARD_NUMBER_EXISTS= "K-083008";

    /**
     * 卡密已存在
     */
    public final static String CARD_PASSWORD_EXISTS= "K-083009";

    /**
     * 优惠码已存在
     */
    public final static String CARD_PROMO_CODE_EXISTS= "K-083010";

    /**
     * 选择的记录无法重发卡密，请检查后重新选择
     */
    public final static String CARD_CAN_NOT_SEND= "K-083011";

    /**
     * 卡券名称已存在
     */
    public final static String COUPON_NAME_EXISTS= "K-083012";
}
