package com.wanmi.sbc.empower.api.response.channel.vop.goods;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wangtao
 * @Date: 2020-3-1 13:49:55
 * @Description: 商品库存请求响应参数
 *
 */
@Data
public class CheckSkuResponse implements Serializable {

    private static final long serialVersionUID = 8353428282420344717L;
    /**
     * sku编号
     */
    private String skuId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 是否可售（1是0否）
     */
    private Integer saleState;

    /**
     * 是否可开专票（1支持0不支持）
     */
    private Integer isCanVAT;

    /**
     * 无理由退货类型
     */
    private Integer noReasonToReturn;

    /**
     * 无理由退货文案类型
     */
    private Integer thwa;
}
