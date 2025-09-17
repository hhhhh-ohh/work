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
 * com.wanmi.sbc.goods.api.request.intervalprice.GoodsIntervalPriceByCustomerIdRequest
 *
 * @author lipeng
 * @dateTime 2018/11/13 上午9:46
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsIntervalPriceByCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = 4028414810762966954L;

    @Schema(description = "商品信息")
    private List<GoodsInfoDTO> goodsInfoDTOList;

    @Schema(description = "用户Id")
    private String customerId;
}
