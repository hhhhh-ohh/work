package com.wanmi.sbc.goods.api.response.goodspropertydetailrel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品与属性值关联列表结果</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailRelListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品与属性值关联列表结果
     */
    @Schema(description = "商品与属性值关联列表结果")
    private List<GoodsPropertyDetailRelVO> goodsPropertyDetailRelVOList;
}
