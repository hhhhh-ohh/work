package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goods.GoodsModifyResponse 修改商品响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午10:24
 */
@Schema
@Data
public class GoodsModifyResponse extends BasicResponse {

    @Schema(description = "编辑商品详细信息")
    private GoodsModifyInfoResponse returnMap;

    /** 商品库id */
    @Schema(description = "商品库id")
    private List<String> standardIds;
}
