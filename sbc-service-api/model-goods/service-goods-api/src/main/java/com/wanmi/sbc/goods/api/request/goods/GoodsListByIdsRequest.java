package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据编号批量查询商品信息请求对象
 * @author daiyitian
 * @dateTime 2018/11/19 上午9:40
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsListByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -124804493327268015L;

    @Schema(description = "商品Id")
    private List<String> goodsIds;

    @Schema(description = "是否查询goodsInfo")
    private BoolFlag getGoodsInfoFlag;
}
