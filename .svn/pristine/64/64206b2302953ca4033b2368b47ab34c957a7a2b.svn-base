package com.wanmi.sbc.goods.api.request.livegoods;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

/**
 * <p>单个删除直播商品请求参数</p>
 * @author zwb
 * @date 2020-06-10 11:05:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsDelByIdRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long goodsId;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * accessToken
     */
    @Schema(description = "accessToken")
    private String accessToken;


}
