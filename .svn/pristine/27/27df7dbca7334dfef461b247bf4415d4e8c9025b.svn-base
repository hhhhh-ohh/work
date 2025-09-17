package com.wanmi.sbc.empower.api.request.wechatauth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Schema
@Data
public class CommunityMiniProgramRequest implements Serializable {

    /**
     * 团长ID
     */
    @Schema(description = "团长ID")
    private String leaderId;

    /**
     * 活动ID
     */
    @Schema(description = "活动ID")
    @NotNull
    private String activityId;

    /**
     * 商品SkuId
     */
    @Schema(description = "商品SkuId")
    private String skuId;

    /**
     * 商品SpuId
     */
    @Schema(description = "商品SpuId")
    private String spuId;

    /**
     * 分享id
     */
    @Schema(description = "分享id")
    public String shareId;
}
