package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * GoodsModifyCollectNumRequest
 * 更新商品收藏量Request
 * @author lvzhenwei
 * @date 2019/4/11 15:47
 **/
@Data
public class GoodsModifyCollectNumRequest extends BaseRequest {

    private static final long serialVersionUID = -8240304696391956356L;

    /**
     * 商品编号
     */
    @NotNull
    private String goodsId;

    /**
     * 商品收藏量
     */
    @NotNull
    private Long goodsCollectNum;
}
