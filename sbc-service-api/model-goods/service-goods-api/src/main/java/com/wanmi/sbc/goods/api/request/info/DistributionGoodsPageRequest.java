package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * com.wanmi.sbc.goods.api.request.info.DistributionGoodsPageRequest
 * 分销商品分页请求对象
 *
 * @author CHENLI
 * @dateTime 2019/2/19 上午9:33
 */
@Schema
@Data
public class DistributionGoodsPageRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = 1976608100715393368L;

    /**
     * 模糊条件-商品名称
     */
    @Schema(description = "模糊条件-商品名称")
    private String likeGoodsName;

    /**
     * 模糊条件-SKU编码
     */
    @Schema(description = "模糊条件-SKU编码")
    private String likeGoodsInfoNo;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺分类Id
     */
    @Schema(description = "店铺分类Id")
    private Long storeCateId;

    /**
     * 平台类目-仅限三级类目
     */
    @Schema(description = "平台类目-仅限三级类目")
    private Long cateId;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    private Long brandId;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private Integer addedFlag;

    /**
     * 市场价范围参数1
     */
    @Schema(description = "第一个市场价")
    private BigDecimal salePriceFirst;

    /**
     * 市场价范围参数2
     */
    @Schema(description = "第二个市场价")
    private BigDecimal salePriceLast;

    /**
     * 分销佣金范围参数1
     */
    @Schema(description = "第一个分销佣金")
    private BigDecimal distributionCommissionFirst;

    /**
     * 分销佣金范围参数2
     */
    @Schema(description = "第二个分销佣金")
    private BigDecimal distributionCommissionLast;

    /**
     * 佣金比例范围参数1
     */
    @Schema(description = "第一个佣金比例")
    private BigDecimal commissionRateFirst;

    /**
     * 佣金比例范围参数2
     */
    @Schema(description = "第二个佣金比例")
    private BigDecimal commissionRateLast;

    /**
     * 分销销量范围参数1
     */
    @Schema(description = "第一个分销销量")
    private Integer distributionSalesCountFirst;

    /**
     * 分销销量范围参数2
     */
    @Schema(description = "第二个分销销量")
    private Integer distributionSalesCountLast;

    /**
     * 库存范围参数1
     */
    @Schema(description = "第一个库存范围")
    private Long stockFirst;

    /**
     * 库存范围参数2
     */
    @Schema(description = "第二个库存范围")
    private Long stockLast;

    /**
     * 分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
     */
    @NotNull
    @Schema(description = "分销商品审核状态", contentSchema = com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit.class)
    private Integer distributionGoodsAudit;

    /**
     * 是否过滤商品状态为失效的商品  0 否 1 是
     * 商品状态 0：正常 1：缺货 2：失效
     */
    @Schema(description = "是否过滤商品状态为失效的商品", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Integer goodsStatus ;

    /**
     * 销售类型 0:批发, 1:零售
     */
    @Schema(description = "销售类型", contentSchema = com.wanmi.sbc.goods.bean.enums.SaleType.class)
    private Integer saleType;

    /**
     * 标签ID
     */
    @Schema(description = "标签ID")
    private Long labelId;
}
