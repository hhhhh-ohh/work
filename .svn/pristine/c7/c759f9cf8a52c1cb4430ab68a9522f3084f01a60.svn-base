package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品SKU更新同步视图响应
 *
 * @author daiyitian
 * @date 2017/3/24
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class GoodsInfoSyncSpuResponse extends BasicResponse {

    @Schema(description = "更新后的spu信息")
    private List<GoodsVO> spuList;

    @Schema(description = "更新后的商品SKU信息")
    private List<GoodsInfoVO> skuList;
}
