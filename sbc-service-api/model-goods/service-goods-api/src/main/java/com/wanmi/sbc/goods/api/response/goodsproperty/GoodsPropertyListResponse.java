package com.wanmi.sbc.goods.api.response.goodsproperty;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品属性列表结果</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品属性列表结果
     */
    @Schema(description = "商品属性列表结果")
    private List<GoodsPropertyVO> goodsPropertyVOList;
}
