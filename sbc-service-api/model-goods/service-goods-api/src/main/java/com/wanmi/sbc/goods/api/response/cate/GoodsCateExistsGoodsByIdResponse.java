package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.response.goodscate.GoodsCateExistsGoodsByIdResponse
 * 根据编号查询当前分类下面是否存在商品响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/2 上午9:13
 */
@Schema
@Data
public class GoodsCateExistsGoodsByIdResponse extends BasicResponse {

    private static final long serialVersionUID = 5659134050178142603L;

    @Schema(description = "是否存在商品", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer result;
}
