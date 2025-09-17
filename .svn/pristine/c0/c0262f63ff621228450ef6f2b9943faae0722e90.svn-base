package com.wanmi.sbc.elastic.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsVO;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品索引SPU查询结果
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EsGoodsSimpleResponse extends BasicResponse {

    /**
     * 索引SKU
     */
    @Schema(description = "索引SKU")
    private MicroServicePage<EsGoodsVO> esGoods = new MicroServicePage<>(new ArrayList<>());

}
