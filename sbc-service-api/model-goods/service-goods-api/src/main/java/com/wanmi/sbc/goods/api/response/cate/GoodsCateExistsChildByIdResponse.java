package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.response.goodscate.GoodsCateExistsChildByIdResponse
 * 根据编号查询当前分类下面是否存在子分类响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/1 下午5:16
 */
@Schema
@Data
public class GoodsCateExistsChildByIdResponse extends BasicResponse {

    private static final long serialVersionUID = 3205644687254968844L;

    @Schema(description = "是否存在子分类", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer result;
}
