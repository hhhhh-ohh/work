package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.response.goods.GoodsPageResponse
 * 商品分页响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午9:34
 */
@Schema
@Data
public class GoodsPageByConditionResponse extends BasicResponse {

    private static final long serialVersionUID = -515474645655162126L;

    /**
     * 商品分页数据
     */
    @Schema(description = "商品分页数据")
    private MicroServicePage<GoodsVO> goodsPage;
}
