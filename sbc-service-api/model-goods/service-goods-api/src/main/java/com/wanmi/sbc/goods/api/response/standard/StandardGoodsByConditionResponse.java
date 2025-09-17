package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.goods.bean.vo.StandardSkuVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 商品SKU列表条件查询响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandardGoodsByConditionResponse {

    private static final long serialVersionUID = -4450756648282745080L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private List<StandardSkuVO> goodsInfos;
}
