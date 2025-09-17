package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>获取营销优惠时传入的订单商品信息参数封装</p>
 * Created by of628-wenzhi on 2018-03-14-下午6:02.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeItemInfoDTO implements Serializable {

    private static final long serialVersionUID = -4999050690155144914L;

    /**
     * 单品id
     */
    @Schema(description = "单品sku id")
    private String skuId;

    /**
     * 商品id
     */
    @Schema(description = "商品spu id")
    private String spuId;

    /**
     * 商品价格（计算区间价或会员价后的应付金额）
     */
    @Schema(description = "商品价格（计算区间价或会员价后的应付金额）")
    private BigDecimal price;

    /**
     * 商品购买数量
     */
    @Schema(description = "商品购买数量")
    private Long num;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商品品牌id
     */
    @Schema(description = "商品品牌id")
    private Long brandId;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Long cateId;

    /**
     * 店铺分类
     */
    @Schema(description = "店铺分类")
    private List<Long> storeCateIds;

    /**
     * 分销商品审核状态
     */
    private DistributionGoodsAudit distributionGoodsAudit;

    /**
     * 分销佣金
     */
    @Schema(description = "分销佣金")
    private BigDecimal distributionCommission;
}
