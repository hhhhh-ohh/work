package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
*
 * @description
 * @author  wur
 * @date: 2022/8/25 13:54
 * @param null 
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoIdBySpuIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 3228836714138747313L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private List<String> goodsInfoIds;
}
