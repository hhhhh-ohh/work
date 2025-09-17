package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.api.request.goods.GoodsCheckRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsCheckRequest
 * 商品审核请求对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午11:18
 */
@Schema
@Data
public class GoodsCheckResponse extends BasicResponse {

    private static final long serialVersionUID = 3152811648060226713L;

    /**
     * 商品库id
     */
    @Schema(description = "商品库id")
    private List<String> standardIds;

    /**
     * 需要删除的商品库id
     */
    @Schema(description = "需要删除的商品库id")
    private List<String> deleteStandardIds;

    private GoodsCheckRequest goodsCheckRequest;
}
