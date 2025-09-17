package com.wanmi.sbc.goods.api.request.price;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.intervalprice.GoodsIntervalPriceRequest
 * 商品订货区间价格请求对象
 * @author lipeng
 * @dateTime 2018/11/6 下午2:27
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsIntervalPriceRequest extends BaseRequest {

    private static final long serialVersionUID = -1317135135892191249L;

    @Schema(description = "商品信息")
    private List<GoodsInfoDTO> goodsInfoDTOList;
}
