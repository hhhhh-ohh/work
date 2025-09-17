package com.wanmi.sbc.empower.bean.vo.sm.recommend;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wur
 * @className RecommendPositionVO
 * @description  商品推荐坑位VO
 * @date 2022/11/17 14:00
 **/
@Data
public class RecommendPositionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 坑位类型，SHOP_CART：购物车，GOODS_DETAIL：商品详情，GOODS_LIST：商品列表；USER_CENTER：个人中心；CUSTOMER_CENTER：会员中心；
     * COLLECT_GOODS：收藏商品；PAY_SUC：支付成功页；GOODS_CATE：分类 CATE_PAGE:分类页面  ORDER_PAGE:订单
     */
    private String positionType;

    /**
     * 坑位开关，0：关闭；1：开启
     */
    private int isOpen;

    /**
     * 推荐策略 0.热门推荐 1.基于商品相关性推荐 2.基于用户兴趣推荐
     */
    private int tacticsType;

    /**
     * 坑位名称
     */
    private String name;

    /**
     * 坑位标题
     */
    private String title;

    public boolean validatePositionType(int type) {
        //坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方
        switch (type) {
            case 0:
                return "SHOP_CART".equals(positionType);
            case 1:
                return "GOODS_DETAIL".equals(positionType);
            case 2:
                return "GOODS_LIST".equals(positionType);
            case 3:
                return "USER_CENTER".equals(positionType);
            case 4:
                return "CUSTOMER_CENTER".equals(positionType);
            case 5:
                return "COLLECT_GOODS".equals(positionType);
            case 6:
                return "PAY_SUC".equals(positionType);
            case 7:
                return "GOODS_CATE".equals(positionType);
            case 8:
                return "MAGIC_BOX".equals(positionType);
            case 9:
                return "CATE_PAGE".equals(positionType);
            default:
                return false;
        }
    }

}