package com.wanmi.sbc.goods.api.response.goodspropcaterel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsPropCateRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品类目与属性关联分页结果</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropCateRelPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品类目与属性关联分页结果
     */
    @Schema(description = "商品类目与属性关联分页结果")
    private MicroServicePage<GoodsPropCateRelVO> goodsPropCateRelVOPage;
}
