package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsModifyAddedStatusRequest
 * 修改商品序列号请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午10:51
 */
@Schema
@Data
public class GoodsModifySortNoRequest extends BaseRequest {

    private static final long serialVersionUID = -7968973602327775144L;

    @NotNull
    @Schema(description = "序列号")
    private Long sortNo;

    @NotBlank
    @Schema(description = "商品Id")
    private String goodsId;
}
