package com.wanmi.sbc.marketing.api.request.market;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingStatus;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-27 10:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingMapGetByGoodsIdRequest extends BaseQueryRequest {

    /**
     * 商品Id集合，查询出对应的营销信息
     */
    @Schema(description = "商品Id列表")
    private List<String> goodsInfoIdList;

    /**
     * 查询某个状态下的营销活动
     */
    @Schema(description = "查询某个状态下的营销活动")
    private MarketingStatus marketingStatus;

    /**
     * 排除某个状态下的营销活动
     */
    @Schema(description = "排除某个状态下的营销活动")
    private MarketingStatus excludeStatus;

    /**
     * 是否是删除状态
     */
    @Schema(description = "是否是删除状态")
    private DeleteFlag deleteFlag;

    /**
     * 是否关联活动级别
     */
    @Schema(description = "是否关联活动级别")
    private Boolean cascadeLevel;

    /**
     * 店铺分类
     */
    private List<Long> storeCateIds;

    /**
     * 品牌
     */
    private List<Long> brandIds;

    /**
     * 店铺
     */
    private List<Long> storeIds;

    /**
     * 门店分类
     */
    private List<Long> o2oStoreCateIds;

    /**
     * 平台分类
     */
    private List<Long> cateIds;

    /**
     * 插件类型
     */
    private PluginType pluginType;
    /**
     * 促销类型 促销类型 0：满减 1:满折 2:满赠 3一口价优惠 4第二件半价 5秒杀(无用) 6组合套餐 7满返
     */
    private MarketingType marketingType;

    private List<MarketingGoodsRequest> marketingGoods;
}
