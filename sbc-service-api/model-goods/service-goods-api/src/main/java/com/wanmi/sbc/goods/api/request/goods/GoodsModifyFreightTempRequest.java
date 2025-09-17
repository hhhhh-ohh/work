package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsModifyFreightTempRequest
 * 修改运费模板请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午11:01
 */
@Schema
@Data
public class GoodsModifyFreightTempRequest extends BaseRequest {

    private static final long serialVersionUID = 8927018981573157219L;

    @Schema(description = "运费模板Id")
    private Long freightTempId;

    @Schema(description = "商品Id")
    private List<String> goodsIds;

}
