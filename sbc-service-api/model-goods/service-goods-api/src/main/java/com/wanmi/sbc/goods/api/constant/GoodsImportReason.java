package com.wanmi.sbc.goods.api.constant;

/**
 * @author xuyunpeng
 * @className GoodsImportReason
 * @description 商品不可导入原因
 * @date 2022/7/18 1:56 PM
 **/
public class GoodsImportReason {

    public static final String LEDGER_NOT_BIND = "未和此供应商绑定分账关系，无法代销商品";

    public static final String GOODS_OFF_ADDED = "未和供应商绑定分账关系，自动下架";
}
