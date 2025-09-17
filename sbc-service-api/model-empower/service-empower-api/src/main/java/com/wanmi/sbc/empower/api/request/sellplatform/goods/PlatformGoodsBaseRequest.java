package com.wanmi.sbc.empower.api.request.sellplatform.goods;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  商品上下架
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformGoodsBaseRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商品Id
     */
    @NotEmpty
    @Schema(description = "商品Id")
    private String goodsId;

}
