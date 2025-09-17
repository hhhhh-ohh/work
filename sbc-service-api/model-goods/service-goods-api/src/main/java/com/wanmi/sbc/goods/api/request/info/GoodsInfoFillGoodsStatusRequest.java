package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品sku填充商品状态请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoFillGoodsStatusRequest extends BaseRequest {

    private static final long serialVersionUID = 2578766932973204098L;

    /**
     * 商品SKU信息列表
     */
    @Schema(description = "商品SKU信息列表")
    private List<GoodsInfoDTO> goodsInfos;
}
