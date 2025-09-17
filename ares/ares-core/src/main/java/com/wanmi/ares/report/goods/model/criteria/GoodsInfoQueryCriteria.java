package com.wanmi.ares.report.goods.model.criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-9-9
 * \* Time: 14:27
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GoodsInfoQueryCriteria extends GoodsQueryCriteria {

    /**
     * 搜索关键字
     * 范围仅限SkuName或SkuNo
     */
    private String keyWord;



}
