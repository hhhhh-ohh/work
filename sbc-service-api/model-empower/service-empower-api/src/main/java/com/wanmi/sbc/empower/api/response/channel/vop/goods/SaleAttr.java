package com.wanmi.sbc.empower.api.response.channel.vop.goods;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xufan
 * @Date: 2020/2/27 14:31
 * @Description: 商品销售标签
 *
 */
@Data
public class SaleAttr implements Serializable {

    private static final long serialVersionUID = 6758752916953073122L;
    /**
     * 标签图片地址
     */
    private String imagePath;

    /**
     * 标签名称
     */
    private String saleValue;

    /**
     * 当前标签下同类商品skuId
     */
    private List<Long> skuIds;
}
