package com.wanmi.sbc.goods.api.request.enterprise.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsInfoSelectStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品SKU分页查询请求
 * Created by 柏建忠 on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseGoodsInfoPageRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -8312305040718761890L;

    /**
     * 批量SKU编号
     */
    @Schema(description = "批量SKU编号")
    private List<String> goodsInfoIds;

    /**
     * SPU编号
     */
    @Schema(description = "SPU编号")
    private String goodsId;

    /**
     * 批量SPU编号
     */
    @Schema(description = "批量SPU编号")
    private List<String> goodsIds;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    private Long brandId;

    /**
     * 批量品牌编号
     */
    @Schema(description = "批量品牌编号")
    private List<Long> brandIds;

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 店铺分类id
     */
    @Schema(description = "店铺分类id")
    private Long storeCateId;

    /**
     * 模糊条件-商品名称
     */
    @Schema(description = "模糊条件-商品名称")
    private String likeGoodsName;

    /**
     * 精确条件-批量SKU编码
     */
    @Schema(description = "精确条件-批量SKU编码")
    private List<String> goodsInfoNos;

    /**
     * 模糊条件-SKU编码
     */
    @Schema(description = "模糊条件-SKU编码")
    private String likeGoodsInfoNo;

    /**
     * 模糊条件-SPU编码
     */
    @Schema(description = "模糊条件-SPU编码")
    private String likeGoodsNo;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private Integer addedFlag;

    /**
     * 上下架状态-批量
     */
    @Schema(description = "上下架状态-批量", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private List<Integer> addedFlags;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 客户等级
     */
    @Schema(description = "客户等级")
    private Long customerLevelId;

    /**
     * 客户等级折扣
     */
    @Schema(description = "客户等级折扣")
    private BigDecimal customerLevelDiscount;

    /**
     * 非GoodsId
     */
    @Schema(description = "非GoodsId")
    private String notGoodsId;

    /**
     * 非GoodsInfoId
     */
    @Schema(description = "非GoodsInfoId")
    private String notGoodsInfoId;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 批量店铺ID
     */
    @Schema(description = "批量店铺ID")
    private List<Long> storeIds;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态，0：待审核 1：已审核 2：审核失败 3：禁售中")
    private CheckStatus auditStatus;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态，0：待审核 1：已审核 2：审核失败 3：禁售中")
    private List<CheckStatus> auditStatuses;

    /**
     * 关键词，目前范围：商品名称、SKU编码
     */
    @Schema(description = "关键词，目前范围：商品名称、SKU编码")
    private String keyword;

    /**
     * 业务员app,商品状态筛选
     */
    @Schema(description = "业务员app,商品状态筛选，0：上架中 1：下架中 2：待审核及其他")
    private List<GoodsInfoSelectStatus> goodsSelectStatuses;

    /**
     * 企业购商品审核状态
     */
    @Schema(description = "企业购商品审核状态，0：无状态 1：待审核 2：已审核 3：审核未通过")
    private EnterpriseAuditState enterPriseAuditState;

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
     * 库存范围参数1
     */
    @Schema(description = "库存范围参数1")
    private Long stockFirst;

    /**
     * 库存范围参数2
     */
    @Schema(description = "库存范围参数2")
    private Long stockLast;

    /**
     * 市场价范围参数1
     */
    private BigDecimal salePriceFirst;

    /**
     * 市场价范围参数2
     */
    private BigDecimal salePriceLast;

    /**
     * 标签ID
     */
    @Schema(description = "标签ID")
    private Long labelId;

}
