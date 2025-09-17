package com.wanmi.sbc.xsite;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema
@Data
public class GoodsInfoRequest extends BaseRequest {

    @Schema(description = "分类编号")
    private Long catName;

    @Schema(description = "第几页")
    private Integer pageNum;

    @Schema(description = "每页显示多少条")
    private Integer pageSize;

    @Schema(description = "模糊条件-商品名称")
    private String q;

    @Schema(description = "类型")
    private Integer type;

    @Schema(description = "店铺建站Id")
    private Long storeId;

    @Schema(description = "店铺搜索Id")
    private Long searchByStore;

    @Schema(description = "品牌ID")
    private Long brandId;

    @Schema(description = "排序标识")
    private Integer sortFlag;

    @Schema(description = "标签ID")
    private Long labelId;

    /**
     * 模糊条件-SPU编码
     */
    @Schema(description = "模糊条件-SPU编码")
    private String likeGoodsNo;

    /**
     * 模糊条件-SKU编码
     */
    @Schema(description = "模糊条件-SKU编码")
    private String likeGoodsInfoNo;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String likeSupplierName;

    /**
     * 最小市场价
     */
    @Schema(description = "最小市场价")
    private BigDecimal minMarketPrice;

    /**
     * 最大市场价
     */
    @Schema(description = "最大市场价")
    private BigDecimal maxMarketPrice;

    /**
     * 最小实际销量
     */
    @Schema(description = "实际销量查询区间：最小实际销量")
    private Long minGoodsSalesNum;

    /**
     * 最大实际销量
     */
    @Schema(description = "实际销量查询区间：最大实际销量")
    private Long maxGoodsSalesNum;

    /**
     * 最小库存
     */
    @Schema(description = "库存查询区间：最小库存")
    private Long minStock;

    /**
     * 最大库存
     */
    @Schema(description = "库存查询区间：最大库存")
    private Long maxStock;

    /**
     * 店铺分类Id
     */
    @Schema(description = "店铺分类Id")
    private Long storeCateId;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态", contentSchema = com.wanmi.sbc.goods.bean.enums.CheckStatus.class)
    private CheckStatus auditStatus;

    /**
     * 商品来源，0品牌商城，1商家
     */
    @Schema(description = "商品来源，0品牌商城，1商家")
    private Integer goodsSource;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private Long likeStoreName;

    /**
     * 类目id
     */
    @Schema(description = "类目ID")
    private Long cateId;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0:实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型")
    private Integer companyType;

}
