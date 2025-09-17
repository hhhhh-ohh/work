
package com.wanmi.sbc.common.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 常量类
 *
 * @author daiyitian
 * @version 0.0.1
 * @since 2017年4月11日 下午3:46:12
 */
public final class Constants {

    public final static Integer yes = 1;

    public final static Integer no = 0;

    // 验证码 有效期 5分钟
    public final static Long SMS_TIME = 5L;

    // 导出最大数量 1000
    public final static Integer EXPORT_MAX_SIZE = 1000;

    //分类在每个父类下上限100
    public final static Integer GOODSCATE_MAX_SIZE = 100;

    //品牌上限2000
    public final static Integer BRAND_MAX_SIZE = 2000;

    //采购单限购50
    public final static Integer PURCHASE_MAX_SIZE = 50;

    //商品收藏上限500
    public final static Integer FOLLOW_MAX_SIZE = 500;

    //商品导入文件大小上限2M
    public final static Integer IMPORT_GOODS_MAX_SIZE = 2;

    //商品导入文件大小上限10M
    public final static Integer IMPORT_GOODS_MAX_SIZE_LIMIT = 10;

    //商品导入错误文件的文件夹名
    public final static String ERR_EXCEL_DIR = "err_excel";

    //商品导入上传文件的文件夹名
    public final static String EXCEL_DIR = "excel";

    //店铺分类最多可添加2个层级
    public final static int STORE_CATE_GRADE = 2;

    //一级店铺分类最多100个
    public final static int STORE_CATE_FIRST_NUM = 100;

    //每个一级店铺分类最多100个二级分类
    public final static int STORE_CATE_SECOND_NUM = 100;

    //店铺图片分类最多可添加3个层级
    public final static int STORE_IMAGE_CATE_GRADE = 3;

    //每个层级店铺分类最多20个
    public final static int STORE_IMAGE_CATE_NUM = 20;

    //平台最多配置100个物流公司
    public final static int EXPRESS_COMPANY_COUNT = 100;

    //每个店铺最多使用20个物流公司
    public final static int STORE_EXPRESS_COUNT = 20;

    // 分类path的分隔符
    public final static String CATE_PATH_SPLITTER = "\\|";

    public final static String STRING_SLASH_SPLITTER = "/";

    //店铺关注上限200
    public final static Integer STORE_FOLLOW_MAX_SIZE = 200;

    /** 商品分类叶子分类层级(最多三层)*/
    public final static int GOODS_LEAF_CATE_GRADE = 3;

    /**
     * 默认收款账号
     */
    public final static Long DEFAULT_RECEIVABLE_ACCOUNT = -1L;


    /**
     * 营销满金额时最小金额
     */
    public final static double MARKETING_FULLAMOUNT_MIN = 0.01;

    /**
     * 营销满金额时最小金额
     */
    public final static double MARKETING_FULLAMOUNT_MIN_PREFERENTIAL = 1;

    /**
     * 营销满金额时最大金额
     */
    public final static double MARKETING_FULLAMOUNT_MAX = 99999999.99;

    /**
     * 营销满金额时最大数量
     */
    public final static double MARKETING_FULLAMOUNT_MAX_PREFERENTIAL = 99999;

    /**
     * 抽奖最大概率
     */
    public final static double DRAW_MIN_WIN_PERCENT = 0.00;

    /**
     * 营销满数量时最小数量
     */
    public final static Long MARKETING_FULLCOUNT_MIN = 1L;

    /**
     * 营销满数量时最大数量
     */
    public final static Long MARKETING_FULLCOUNT_MAX = 9999L;

    /**
     * 营销满数量时最小数量
     */
    public final static double MARKETING_DISCOUNT_MIN = 0.01;

    /**
     * 营销满数量时最大数量
     */
    public final static double MARKETING_DISCOUNT_MAX = 0.99;

    /**
     * 满赠赠品最大种数
     */
    public final static int MARKETING_Gift_TYPE_MAX = 20;

    /**
     * 满赠赠品最小数量
     */
    public final static int MARKETING_Gift_MIN = 1;

    /**
     * 满赠赠品最大数量
     */
    public final static int MARKETING_Gift_MAX = 999;

    /**
     * 商品类目默认属性值
     */
    public static final Long GOODS_DEFAULT_REL = Long.valueOf(0);
    /**
     * 商品类目关联属性最大数量
     */
    public static final int GOODS_PROP_REAL_SIZE = 20;
    /**
     * 属性值最大数量
     */
    public static final int GOODS_PROP_DETAIL_REAL_SIZE = 100;

    /**
     * 用户的全平台ID，非店铺客户的默认等级，用于级别价计算
     */
    public static final long GOODS_PLATFORM_LEVEL_ID = 0;

    /**
     * 单品运费模板最大数量
     */
    public static final int FREIGHT_GOODS_MAX_SIZE = 20;

    public static final int MINUS_ONE = -1;

    public static final int ONE = 1;

    public static final int ZERO = 0;

    public static final int TWO = 2;

    public static final int THREE = 3;

    public static final int FOUR = 4;

    public static final int FIVE = 5;

    public static final int SIX = 6;

    public static final int SEVEN = 7;

    public static final int EIGHT = 8;

    public static final int NINE = 9;

    public static final int TEN = 10;

    /**
     * 预热最大值
     */
    public final static Integer MAX_PRE_TIME = 168;

    /***
     * 采购单distributod_id默认值0
     */
    public  static final String PURCHASE_DEFAULT = "0";


    /**
     * 积分价值
     */
    public static final Long POINTS_WORTH = 100L;

    /**
     * 秒杀活动进行时间
     */
    public static final Long FLASH_SALE_LAST_HOUR = 2L;

    /**
     * 秒杀活动商品抢购资格有效期：5分钟
     */
    public static final Long FLASH_SALE_GOODS_QUALIFICATIONS_VALIDITY_PERIOD = 5L;

    /**
     * 秒杀活动抢购商品订单类型："FLASH_SALE"
     */
    public static final String FLASH_SALE_GOODS_ORDER_TYPE = "FLASH_SALE";

    /**
     * 预约抢购商品订单类型："APPOINTMENT_SALE"
     */
    public static final String APPOINTMENT_SALE_GOODS_ORDER_TYPE = "APPOINTMENT_SALE";

    /**
     * 预售商品订单类型："BOOKING_SALE"
     */
    public static final String BOOKING_SALE_TYPE = "BOOKING_SALE";

    /**
     * 活动商品抢购资格有效期：5分钟
     */
    public static final Long APPOINTMENT_SALE_GOODS_QUALIFICATIONS_VALIDITY_PERIOD = 5L;


    //S2B平台最多配置100个物流公司
    public final static int S2B_EXPRESS_COMPANY_COUNT = 100;


    /**
     * boss默认店铺id
     */
    public static final Long BOSS_DEFAULT_STORE_ID = -1L;

    /**
     * 营销自定义范围最大数量
     */
    public static final Integer MARKETING_GOODS_SIZE_MAX = 500;

    /**
     * 客户导入
     */
    public final static String CUSTOMER_IMPORT_EXCEL_DIR = "customer_import_excel";

    public final static String CUSTOMER_IMPORT_EXCEL_ERR_DIR = "customer_import_err_excel";

    //商品/商品库导入
    public final static String GOODS_EXCEL_DIR = "goods_excel";

    //商品库存导入
    public final static String GOODS_STOCK_EXCEL_DIR = "goods_stock_excel";

    //客户积分导入
    public final static String CUSTOMER_POINTS_EXCEL_DIR = "customer_points_excel";

    //客户积分导入生成的错误文件
    public final static String CUSTOMER_POINTS_ERR_EXCEL_DIR = "customer_points_err_excel";
    //商品/商品库导入生成的错误文件
    public final static String GOODS_ERR_EXCEL_DIR = "goods_err_excel";

    //部门导入
    public final static String DEPARTMENT_EXCEL_DIR = "department_excel";
    //部门导入生成的错误文件
    public final static String DEPARTMENT_ERR_EXCEL_DIR = "department_err_excel";
    //员工导入
    public final static String EMPLOYEE_EXCEL_DIR = "employee_excel";
    //员工导入生成的错误文件
    public final static String EMPLOYEE_ERR_EXCEL_DIR = "employee_err_excel";

    //商品类目导入
    public final static String GOODS_CATE_EXCEL_DIR = "goods_cate_excel";
    //商品类目导入生成的错误文件
    public final static String GOODS_CATE_ERR_EXCEL_DIR = "goods_cate_err_excel";

    //商品品牌导入
    public final static String GOODS_BRAND_EXCEL_DIR = "goods_brand_excel";
    //商品品牌导入生成的错误文件
    public final static String GOODS_BRAND_ERR_EXCEL_DIR = "goods_brand_err_excel";

    //积分商品导入
    public final static String POINTS_GOODS_EXCEL_DIR = "points_goods_excel";
    //积分商品导入生成的错误文件
    public final static String POINTS_GOODS_ERR_EXCEL_DIR = "points_goods_err_excel";

    //批量发货模板导入
    public final static String BATCH_DELIVER_EXCEL_DIR = "batch_deliver_excel_dir";
    //批量发货模板导入生成的错误文件
    public final static String BATCH_DELIVER_ERR_EXCEL_DIR = "batch_deliver_err_excel_dir";

    //商品导入最大错误数
    public final static int IMPORT_GOODS_MAX_ERROR_NUM = 20;

    //跨境商品备案导入
    public final static String CROSS_GOODS_RECORD_EXCEL = "cross_goods_record_excel";
    //跨境商品备案导入生成的错误文件
    public final static String CROSS_GOODS_RECORD_ERR_EXCEL = "cross_goods_record_err_excel";

    //商品/跨境商品导入
    public final static String CROSS_GOODS_EXCEL_DIR = "cross_goods_excel";

    //商品/商品库导入生成的错误文件
    public final static String CROSS_GOODS_ERR_EXCEL_DIR = "cross_goods_err_excel";

    //卡密导入
    public final static String ELECTRONIC_EXCEL_DIR = "electronic_excel";
    //卡密导入生成的错误文件
    public final static String ELECTRONIC_ERROR_EXCEL_DIR = "electronic_err_excel";
    public final static String MARKETING_GOODS_EXCEL = "marketing_goods_excel";

    public final static String MARKETING_GOODS_ERR_EXCEL = "marketing_goods_err_excel";

    // 礼品卡-批量发卡导入
    public final static String GIFT_CARD_BATCH_SEND_EXCEL_DIR = "gift_card_batch_send_excel";
    // 礼品卡-批量发卡导入生成的错误文件
    public final static String GIFT_CARD_BATCH_SEND_ERR_EXCEL_DIR = "gift_card_batch_send_err_excel";

    /**
     * 秒杀商品导入
     */
    public final static String FLASH_SALE_GOODS_EXCEL = "flash_sale_goods_excel";

    public final static String FLASH_SALE_GOODS_ERR_EXCEL = "flash_sale_goods_err_excel";

    /**
     *
     */
    public final static String SUIT_GOODS_EXCEL = "suit_goods_excel";

    public final static String SUIT_GOODS_ERR_EXCEL = "suit_goods_err_excel";

    public final static String RESERVATION_GOODS_EXCEL = "reservation_goods_excel";

    public final static String RESERVATION_GOODS_ERR_EXCEL = "reservation_goods_err_excel";

    public final static String PRESALE_GOODS_EXCEL = "presale_goods_excel";

    public final static String PRESALE_GOODS_ERR_EXCEL = "presale_goods_err_excel";

    public final static String PACK_GOODS_EXCEL = "pack_goods_excel";

    public final static String PACK_GOODS_ERR_EXCEL = "pack_goods_err_excel";

    public final static String DISCOUNT_GOODS_EXCEL = "discount_goods_excel";

    public final static String DISCOUNT_GOODS_ERR_EXCEL = "discount_goods_err_excel";

    public final static String DISTRIBUTION_GOODS_EXCEL = "distribution_goods_excel";

    public final static String DISTRIBUTION_GOODS_ERR_EXCEL = "distribution_goods_err_excel";

    public final static String ENTERPRISE_GOODS_EXCEL = "enterprise_goods_excel";

    public final static String ENTERPRISE_GOODS_ERR_EXCEL = "enterprise_goods_err_excel";

    public final static String RESTRICTED_SALE_GOODS_EXCEL = "restricted_sale_goods_excel";

    public final static String RESTRICTED_SALE_GOODS_ERR_EXCEL = "restricted_sale_goods_err_excel";

    public final static String FULL_DISCOUNT_SALE_GOODS_EXCEL = "full_discount_sale_goods_excel";

    public final static String FULL_DISCOUNT_SALE_GOODS_ERR_EXCEL = "full_discount_sale_goods_err_excel";

    public final static String FULL_REDUCTION_SALE_GOODS_EXCEL = "full_reduction_sale_goods_excel";

    public final static String FULL_REDUCTION_SALE_GOODS_ERR_EXCEL = "full_reduction_sale_goods_err_excel";

    public final static String EARNEST_GOODS_EXCEL = "earnest_goods_excel";

    public final static String EARNEST_GOODS_ERR_EXCEL = "earnest_goods_err_excel";

    public final static String FULL_RETURN_GOODS_EXCEL = "full_return_goods_excel";

    public final static String FULL_RETURN_GOODS_ERR_EXCEL = "full_return_goods_err_excel";

    public final static String PREFERENTIAL_GOODS_EXCEL = "preferential_goods_excel";

    public final static String PREFERENTIAL_GOODS_ERR_EXCEL = "preferential_goods_err_excel";

    public final static String PAYING_MEMBER_DISCOUNT_EXCEL = "paying_member_discount_excel";

    public final static String PAYING_MEMBER_DISCOUNT_ERROR_EXCEL = "paying_member_discount_error_excel";

    public final static String PAYING_MEMBER_RECOMMEND_EXCEL = "paying_member_recommend_excel";

    public final static String PAYING_MEMBER_RECOMMEND_ERROR_EXCEL = "paying_member_recommend_error_excel";
    /**
     * 限时抢购商品导入
     */
    public final static String LIMIT_BUY_GOODS_EXCEL = "limit_buy_goods_excel";

    public final static String LIMIT_BUY_GOODS_ERR_EXCEL = "limit_buy_goods_err_excel";


    //代客下单商品导入
    public final static String ORDER_FOR_CUSTOMER_GOODS_EXCEL = "order_for_customer_goods_excel";
    //代客下单导入错误文件
    public final static String ORDER_FOR_CUSTOMER_GOODS_ERR_EXCEL = "order_for_customer_goods_err_excel";

    //调价导入文件大小上限5M
    public final static Integer IMPORT_PRICE_ADJUST_MAX_SIZE = 5;
    // 失败
    public static final String FAIL = "fail";

    // 错误路径
    public static final String ERROR_URI = "/error";

    // excel后缀
    public static final String XLS = "xls";

    public static final String XLSX = "xlsx";

    public static final String PDF = "pdf";

    public static final int NUM_3 = 3;

    public static final int NUM_10 = 10;

    public static final int NUM_11 = 11;

    public static final int NUM_12 = 12;

    public static final int NUM_13 = 13;

    public static final int NUM_14 = 14;

    public static final int NUM_15 = 15;

    public static final int NUM_16 = 16;

    public static final int NUM_18 = 18;

    public static final int NUM_20 = 20;

    public static final int NUM_22 = 22;

    public static final int NUM_23 = 23;

    public static final int NUM_24 = 24;

    public static final int NUM_25 = 25;

    public static final int NUM_29 = 29;

    public static final int NUM_30 = 30;

    public static final int NUM_31 = 31;

    public static final int NUM_32 = 32;

    public static final int NUM_40 = 40;

    public static final int NUM_45 = 45;

    public static final int NUM_50 = 50;

    public static final int NUM_59 = 59;

    public static final int NUM_60 = 60;

    public static final int NUM_70 = 70;

    public static final int NUM_100 = 100;

    public static final int NUM_200 = 200;

    public static final int NUM_300 = 300;

    public static final int NUM_500 = 500;

    public static final int NUM_255 = 255;

    public static final int NUM_365 = 365;

    public static final int NUM_1000 = 1000;

    public static final int NUM_2000 = 2000;

    public static final int NUM_1024 = 1024;

    public static final int NUM_2061 = 2061;

    public static final int NUM_2076 = 2076;

    public static final int NUM_8192 = 8192;

    public static final int NUM_5000 = 5000;

    public static final int NUM_10000 = 10000;

    public static final int NUM_10001 = 10001;

    public static final int NUM_30000 = 30000;

    public final static int NUM_999 = 999;

    public final static int NUM_999999 = 999999;

    public final static int NUM_99999 = 99999;

    public final static int NUM_99999999 = 99999999;

    public static final Long NUM_0L = 0L;

    public static final Long NUM_1L = 1L;

    public static final Long NUM_MINUS_1L = -1L;

    public static final Long NUM_5L = 5L;

    public static final Long NUM_11L = 11L;

    public static final Long NUM_14L = 14L;

    public static final Long NUM_15L = 15L;

    public static final Long NUM_16L = 16L;

    public static final Long NUM_17L = 17L;

    public static final Long NUM_18L = 18L;

    public static final Long NUM_19L = 19L;

    public static final Long NUM_20L = 20L;

    public static final Long NUM_27L = 27L;

    public static final Long NUM_28L = 28L;

    public static final Long NUM_29L = 29L;

    public static final Long NUM_30L = 30L;
    public static final Long NUM_31L = 31L;
    public static final Long NUM_32L = 32L;
    public static final Long NUM_33L = 33L;
    public static final Long NUM_34L = 34L;
    public static final Long NUM_35L = 35L;
    public static final Long NUM_36L = 36L;
    public static final Long NUM_37L = 37L;
    public static final Long NUM_38L = 38L;
    public static final Long NUM_39L = 39L;

    public static final Long NUM_40L = 40L;


    public static final Long NUM_100L = 100L;

    public static final Long NUM_300L = 300L;

    public static final Long NUM_9999L = 9999L;

    public static final Long NUM_1000L = 1000L;

    public static final String STR_MINUS_1 = "-1";

    public static final String STR_MINUS_2 = "-2";

    public static final String STR_0 = "0";

    public static final String STR_00 = "00";

    public static final String STR_01 = "01";

    public static final String STR_1 = "1";

    public static final String STR_2 = "2";

    public static final String STR_3 = "3";

    public static final String STR_100 = "100";

    public static final String STR_1004 = "1004";

    public static final String STR_3101 = "3101";

    public static final String STR_3102 = "3102";

    public static final String STR_3103 = "3103";

    public static final String STR_40001 = "40001";

    public static final String STR_42001 = "42001";

    // 视频号重复发货出错误吗
    public static final String STR_1010037 = "1010037";

    // 视频号订单流转失败
    public static final String STR_1000000 = "1000000";

    public static final String STR_10000 = "10000";

    public static final String STR_S = "S";

    public static final String STR_NULL = "null";

    public static final String RESPCODE = "respCode";

    public static final String LOGGED_OUT = "（已注销）";


    // 业务id(订单或退单号)前缀
    public static final String STR_RT = "RT";

    public static final String SUCCESS = "SUCCESS";

    public static final String ERRCODE = "errcode";

    public static final String ERROR = "error";

    public static final String ANONYMOUS = "匿名用户";

    public static final String MAX_DRAW = "99.99";

    public static final String SYSTEM = "system";

    public static final String PART_A = "${partA}";

    public static final String PART_B = "${partB}";

    public static final String EC003 = "EC003";

    public static final String LAKALA_LEDGER_CONTRACT = "lakala_ledger_contract.html";

    // 防止sortName sql 注入风险
    public final static List<String> SORT_NAMES = Lists.newArrayList("date", "totalUv", "totalPv", "skuTotalUv", "skuTotalPv",
            "orderCount", "orderNum", "orderAmt", "payOrderCount", "payOrderNum", "payOrderAmt", "everyUnitPrice", "orderConversionRate", "customerUnitPrice");

    public static final String SUCCESS_000000 = "000000";

    private Constants() {
    }

}
