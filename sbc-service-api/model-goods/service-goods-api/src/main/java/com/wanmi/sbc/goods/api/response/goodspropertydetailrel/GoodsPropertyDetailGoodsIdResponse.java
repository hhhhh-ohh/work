package com.wanmi.sbc.goods.api.response.goodspropertydetailrel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 * @date 2021/4/27 11:19
 * @description <p> 商品属性信息 </p>
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPropertyDetailGoodsIdResponse extends BasicResponse {
    /**
     * 商品属性信息
     */
    @Schema(description = "商品属性信息")
    private List<GoodsPropertyDetailRelDTO> goodsDetailRel;
}
