package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsModifyAddedStatusRequest
 * 商品同步
 * @author lipeng
 * @dateTime 2018/11/5 上午10:51
 */
@Schema
@Data
public class GoodsSynRequest extends BaseRequest {

    private static final long serialVersionUID = -5299206942974942478L;

    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private Integer addedFlag;

    @Schema(description = "商品Id")
    private List<String> goodsIds;

    private Long storeId;
}
