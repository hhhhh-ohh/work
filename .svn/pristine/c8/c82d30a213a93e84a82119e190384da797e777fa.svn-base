package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.StandardGoodsRelVO;
import com.wanmi.sbc.goods.bean.vo.StandardSkuVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * author: dyt
 * Date: 2020-11-07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardGoodsRelByGoodsIdsResponse extends BasicResponse {

    private static final long serialVersionUID = -5612797753282841621L;

    /**
     * 商品SPU关联数据
     */
    @Schema(description = "商品SPU关联数据")
    private List<StandardGoodsRelVO> standardGoodsRelList = new ArrayList<>();
}
