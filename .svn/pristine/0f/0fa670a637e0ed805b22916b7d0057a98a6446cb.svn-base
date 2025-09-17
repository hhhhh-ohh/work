package com.wanmi.sbc.marketing.api.request.plugin;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.MarketingPluginDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>插件公共Request</p>
 * author: sunkun
 * Date: 2018-11-19
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class MarketingPluginGoodsListFilterRequest extends MarketingPluginDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品信息列表")
    private List<GoodsInfoDTO> goodsInfos;

    /**
     * 是否魔方商品列表
     */
    @Schema(description = "是否魔方商品列表")
    private Boolean moFangFlag;


    /**
     * 是否为秒杀走购物车普通商品提交订单
     */
    @Schema(description = "是否为秒杀走购物车普通商品提交订单")
    private Boolean isFlashSaleMarketing;

    /**
     * 是否提交订单commit接口
     */
    @Schema(description = "是否提交订单commit接口")
    private Boolean commitFlag = Boolean.FALSE;

    /**
     * 查询数据终端，为了处理pc端分销和企业价逻辑
     */
    @Schema(description = "查询数据终端，为了处理pc端分销和企业价逻辑表")
    private String terminalSource;

    /**
     * 门店ID
     */
    private Long storeId;

    /***
     * 插件类型
     */
    private PluginType pluginType = PluginType.NORMAL;

//    /**
//     * 会员id
//     */
//    private String customerId;

}
