package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest
 * 根据编号查询商品信息请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午9:40
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 712116094839998228L;

    @Schema(description = "商品Id")
    @NotBlank
    private String goodsId;
}
