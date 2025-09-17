package com.wanmi.sbc.goods.api.response.goodspropertydetailrel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）商品与属性值关联信息response</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailRelByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品与属性值关联信息
     */
    @Schema(description = "商品与属性值关联信息")
    private GoodsPropertyDetailRelVO goodsPropertyDetailRelVO;
}
