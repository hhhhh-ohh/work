package com.wanmi.sbc.goods.api.constant;

import java.util.List;

/**
 * @description
 * @author  wur
 * @date: 2021/9/22 11:09
 **/
public class GoodsEditMsg {

    private GoodsEditMsg() {
    }


    public final static String GOODS_FORBADE = "商品被禁售";

    public final static String GOODS_UP = "商品上架";

    public final static String GOODS_DOWN = "商品下架";

    public final static String GOODS_INFO_UP = "代销SKU上架";

    public final static String GOODS_INFO_DOWN = "代销SKU下架";

    public final static String GOODS_DEL = "商品被删除";

    public final static String GOODS_INFO_DEL = "代销SKU被删除";

    public final static String ADD_GOODS_INFO = "新增SKU";

    public final static String UPDATE_GOODS_FREIGHT = "物流信息变更";

    public final static String UPDATE_GOODS = "商品基础信息变更";

    public final static String UPDATE_GOODS_PROP = "商品属性变更";

    public final static String UPDATE_GOODS_DETAIL = "商品详情变更";

    public final static String UPDATE_GOODS_INFO = "代销SKU信息变更";

    public final static String UPDATE_GOODS_WEIGHT = "代销SKU重量体积变更";

    public final static String UPDATE_GOODS_PRICE = "商品供货价变更";

    private final static String APART = "，";

    public static String getEditContent(List<String> list) {
        StringBuffer editContent = new StringBuffer("");
        for (int i = 0 ; i < list.size(); i++) {
            if (i != 0) {
                editContent.append(APART);
            }
            editContent.append(list.get(i));
        }
        return editContent.toString();
    }


}
