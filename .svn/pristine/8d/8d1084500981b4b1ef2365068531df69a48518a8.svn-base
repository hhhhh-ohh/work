package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsDeleteByIdsRequest
 * 批量删除商品信息请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午10:48
 */
@Schema
@Data
public class GoodsDeleteByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -4145494497424843080L;

    /***
     * 商品Id
     */
    @Schema(description = "商品Id")
    private List<String> goodsIds;

    /**
     * 删除原因
     */
    @Schema(description = "deleteReason")
    private String deleteReason;

    /***
     * 店铺ID
     */
    @Schema(description = "店铺ID", hidden = true)
    private Long storeId;

    /***
     * 插件类型
     */
    private PluginType pluginType = PluginType.NORMAL;
}
