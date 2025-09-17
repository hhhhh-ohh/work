package com.wanmi.sbc.goods.api.request.ares;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wanggang
 * @createDate: 2018/11/5 10:52
 * @version: 1.0
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsAresListByGoodsIdRequest extends BaseRequest {

    private static final long serialVersionUID = 414185192305830522L;

    @Schema(description = "商品Id")
    @NotNull
    private String goodsId;
}
