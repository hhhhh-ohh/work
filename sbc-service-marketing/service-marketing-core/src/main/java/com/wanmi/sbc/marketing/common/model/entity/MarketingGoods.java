package com.wanmi.sbc.marketing.common.model.entity;

import com.wanmi.sbc.common.enums.PluginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 商品数据
 * @author  xuyunpeng
 * @date 2021/5/26 4:13 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketingGoods {

    /**
     * 商品
     */
    private String goodsInfoId;

    /**
     * 品牌
     */
    private Long brandId;

    /**
     * 店铺分类
     */
    private List<Long> storeCateIds;

    /**
     * 店铺
     */
    private Long storeId;

    /**
     * 商品插件类型
     */
    private PluginType pluginType = PluginType.NORMAL;

    /**
     * 平台分类
     */
    private Long cateId;

    /**
     * 所有的平台分类
     */
    private List<Long> cateIds;

    /**
     * spu
     */
    private String goodsId;

    /**
     * 商品类型 0:实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;
}
