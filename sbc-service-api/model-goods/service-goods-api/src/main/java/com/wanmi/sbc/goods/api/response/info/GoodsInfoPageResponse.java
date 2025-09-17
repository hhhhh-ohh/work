package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品SKU分页响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
public class GoodsInfoPageResponse extends BasicResponse {

    private static final long serialVersionUID = -6537029038129916678L;

    /**
     * 分页商品SKU信息
     */
    @Schema(description = "分页商品SKU信息")
    private MicroServicePage<GoodsInfoVO> goodsInfoPage;
}
