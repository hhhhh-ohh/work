package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品关联的营销信息
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoMarketingVO extends BasicResponse {

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 单品id
     */
    private String goodsInfoId;

    /**
     * 分销商品状态
     */
    private DistributionGoodsAudit distributionGoodsAudit;

    /**
     * 商品关联的营销活动列表
     */
    private List<MarketingViewVO> marketingViewList = new ArrayList<>();

    /**
     * 品牌
     */
    private Long brandId;

    /**
     * 店铺分类
     */
    private List<Long> storeCateIds;

    /***
     * 平台类目ID
     */
    private Long cateId;
}
