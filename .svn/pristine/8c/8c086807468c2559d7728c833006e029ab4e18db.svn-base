package com.wanmi.sbc.empower.api.request.wechatauth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 分享商品生成携带分享人id的request
 */
@Schema
@Data
public class ShareMiniProgramRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 分享人id
     */
    @Schema(description = "分享人id")
    private String shareUserId;

    /**
     * 商品SkuId
     */
    @Schema(description = "商品SkuId")
    private String skuId;


    @Schema(description = "门店id")
    private Long storeId;

    @Schema(description = "分享id")
    public String shareId;
}
