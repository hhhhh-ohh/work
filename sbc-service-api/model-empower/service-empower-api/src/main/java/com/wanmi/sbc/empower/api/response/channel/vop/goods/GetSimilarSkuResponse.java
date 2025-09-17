package com.wanmi.sbc.empower.api.response.channel.vop.goods;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xufan
 * @Date: 2020/2/27 14:35
 * @Description:
 *
 */
@Data
public class GetSimilarSkuResponse implements Serializable {

    private static final long serialVersionUID = -3128673943799801924L;
    /**
     * 维度
     */
    private Integer dim;

    /**
     * 销售名称
     */
    private String saleName;

    /**
     * 商品销售标签
     */
    private List<SaleAttr> saleAttrList;
}
