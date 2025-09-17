package com.wanmi.sbc.empower.api.response.channel.vop.goods;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xufan
 * @Date: 2020/3/5 13:57
 * @Description: 商品上下架状态
 *
 */
@Data
public class SkuStateResponse implements Serializable {

    private static final long serialVersionUID = -2219788489776362508L;
    /**
     * 1上架 0下架
     */
    private int state;

    /**
     * skuId
     */
    private Long sku;
}
