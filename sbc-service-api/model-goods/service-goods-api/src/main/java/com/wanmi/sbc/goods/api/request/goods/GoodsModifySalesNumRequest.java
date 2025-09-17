package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * GoodsModifySalesNum
 * 更新商品销量Request
 * @author lvzhenwei
 * @date 2019/4/11 15:49
 **/
@Data
public class GoodsModifySalesNumRequest extends BaseRequest {

    private static final long serialVersionUID = -393599446301001125L;

    /**
     * 商品编号
     */
    @NotNull
    private String goodsId;

    /**
     * 商品销量
     */
    @NotNull
    private Long goodsSalesNum;
}
