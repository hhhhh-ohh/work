package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMarketingPriceDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>批量查询SKU市场价返回数据结构</p>
 * Created by of628-wenzhi on 2020-12-14-9:30 下午.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class GoodsInfoMarketingPriceByNosResponse extends BasicResponse {

    private static final long serialVersionUID = -1805648033804799832L;

    /**
     * 商品市场价集合
     */
    @Schema(description = "商品市场价集合")
    private List<GoodsInfoMarketingPriceDTO> dataList;
}
