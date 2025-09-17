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
 * @description  查询商品详情
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformGetGoodsRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotEmpty
    @Schema(description = "商品Id")
    private String goodsId;

    /**
     * 默认0:获取线上数据, 1:获取草稿数据
     */
    @Schema(description = "默认0:获取线上数据, 1:获取草稿数据")
    private String need_edit_spu;

}
