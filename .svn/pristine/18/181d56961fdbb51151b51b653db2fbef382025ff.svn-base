package com.wanmi.sbc.goods.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import java.util.List;

/**
 * Created by feitingting on 2019/1/8.
 */
@Schema
@Data
public class GoodsInfoByGoodsIdsRequest extends BaseRequest {
    /**
     * sku编号
     */
    @NotEmpty
    @Schema(description = "sku编号")
    private List<String> goodsIds;

    /**
     * 是否屏蔽跨境商品
     */
    @Schema(description = "是否屏蔽跨境商品")
    private Boolean notShowCrossGoodsFlag;

    /**
     * 跨境商品备案状态
     */
    @Schema(description = "0：待备案，1：备案中,2：备案成功，3：备案失败")
    private List<Integer> recordStatus;

    @Schema(description = "上下架状态0：未上架 1：已上架 2：部分上架")
    private Integer addedFlag;

}

