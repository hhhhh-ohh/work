package com.wanmi.sbc.vas.api.request.sellplatform.goods;

import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description  删除商品
 * @author  wur
 * @date: 2022/4/6 17:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformDeleteGoodsRequest extends SellPlatformBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义商品ID，与product_id二选一
     */
    @NotEmpty
    @Schema(description = "商品Id")
    private List<String> goodsIds;

}
