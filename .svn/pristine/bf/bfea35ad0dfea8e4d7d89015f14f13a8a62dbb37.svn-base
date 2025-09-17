package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsForXsiteVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * program: sbc-micro-service
 *
 * @date 2020-02-28 17:14
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPageForXsiteResponse extends BasicResponse {

    private static final long serialVersionUID = 7209404748441617323L;

    /**
     * 商品分页数据
     */
    @Schema(description = "商品分页数据")
    private MicroServicePage<GoodsForXsiteVO> goodsForXsiteVOPage;
}