package com.wanmi.ares.report.goods.model.root;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-9-6
 * \* Time: 15:19
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GoodsBrandReport extends GoodsReport {

    /**
     * 商品品牌名称
     */
    private String brandName;


}
