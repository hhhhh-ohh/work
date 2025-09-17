package com.wanmi.sbc.goods.api.request.price;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品订货区间价格请求对象
 * @author dyt
 * @dateTime 2018/11/6 下午2:27
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsIntervalPriceByGoodsAndSkuRequest extends BaseRequest {

    private static final long serialVersionUID = 8517546127632102044L;

    @Schema(description = "商品Sku信息", required = true)
    private List<GoodsInfoDTO> goodsInfoDTOList;

    @Schema(description = "商品信息", required = true)
    private List<GoodsDTO> goodsDTOList;

    @Schema(description = "用户Id")
    private String customerId;
}
