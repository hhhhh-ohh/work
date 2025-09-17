package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * VOP商品同步时 商品验证
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VopGoodsSyncCheckRequest extends BaseRequest {

    private static final long serialVersionUID = 6636321075674610130L;

    /**
     * 商品Id
     */
    @Schema(description = "商品Id")
    private String goodsId;

    /**
     * VO的SKUID集合
     */
    @Schema(description = "VO的SKUID集合")
    private List<String> thirdPlatformSkuIdList;
}
