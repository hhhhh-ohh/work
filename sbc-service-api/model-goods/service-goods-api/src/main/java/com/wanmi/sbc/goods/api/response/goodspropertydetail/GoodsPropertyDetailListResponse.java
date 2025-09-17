package com.wanmi.sbc.goods.api.response.goodspropertydetail;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品属性值列表结果</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品属性值列表结果
     */
    @Schema(description = "商品属性值列表结果")
    private List<GoodsPropertyDetailVO> goodsPropertyDetailVOList;
}
