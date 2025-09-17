package com.wanmi.sbc.empower.api.request.wechatauth;

import com.wanmi.sbc.common.enums.PluginType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class DistributionMiniProgramRequest implements Serializable {

    /**
     * 分销员会员ID
     */
    @Schema(description = "分销员会员ID")
    private String inviteeId;

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

    /**
     * 商品SpuId
     */
    @Schema(description = "商品SpuId")
    private String spuId;

    /**
     * 积分商品id
     */
    @Schema(description = "积分商品ID")
    private String pointsGoodsId;

    /**
     * 渠道
     */
    @Schema(description = "渠道,接受mall和shop传值")
    private String channel;


    /**
     * 邀新和店铺表示区分
     */
    @Schema(description = "邀新和店铺表示区分,接受register和shop")
    private String tag;


    @Schema(description = "门店id")
    private Long storeId;

    /**
     * 分享id
     */
    @Schema(description = "分享id")
    public String shareId;

    @Schema(description = "商品插件类型")
    private PluginType pluginType;
}
