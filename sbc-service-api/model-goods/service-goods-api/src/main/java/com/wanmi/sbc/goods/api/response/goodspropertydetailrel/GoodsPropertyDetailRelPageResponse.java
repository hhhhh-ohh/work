package com.wanmi.sbc.goods.api.response.goodspropertydetailrel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品与属性值关联分页结果</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailRelPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品与属性值关联分页结果
     */
    @Schema(description = "商品与属性值关联分页结果")
    private MicroServicePage<GoodsPropertyDetailRelVO> goodsPropertyDetailRelVOPage;
}
