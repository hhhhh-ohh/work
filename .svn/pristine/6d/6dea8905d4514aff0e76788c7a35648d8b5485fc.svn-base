package com.wanmi.sbc.elastic.api.request.sku;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.RequestSource;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsInfoSelectStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品Sku分页请求对象 - 应用于管理台
 *
 * @author dyt
 * @dateTime 2018/11/5 上午9:33
 */
@Schema
@Data
public class EsSkuPageRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -7972557462976673056L;

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

    private Integer cateGrade;

    /**
     * 多个分类编号
     */
    @Schema(description = "多个分类编号")
    private List<Long> cateIds;

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
     * 非GoodsInfoId
     */
    @Schema(description = "非GoodsInfoIds")
    private List<String> notGoodsInfoIds;

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
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

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
     * 商家类型
     */
    @Schema(description = "商家类型，0、平台自营 1、第三方商家")
    private BoolFlag companyType;

    /**
     * 销售类别
     */
    @Schema(description = "销售类别，0、批发 1、零售")
    private Integer saleType;

    /**
     * 商品来源，0供应商，1商家
     */
    @Schema(description = "商品来源，0供应商，1商家")
    private Integer goodsSource;

    /**
     * 商品来源，0品牌商城，1商家
     */
    @Schema(description = "商品来源，0品牌商城，1商家")
    private GoodsSource searchGoodsSource;

    /**
     *需要排除的三方渠道
     */
    @Schema(description = "需要排除的三方渠道")
    private List<Integer> notThirdPlatformType;

    /**
     * 是否可售
     */
    @Schema(description = "是否可售")
    private Integer vendibility;

    /**
     * 是否过滤积分价商品
     */
    @Schema(description = "是否过滤积分价商品")
    private Boolean integralPriceFlag;

    /**
     * 标签ID
     */
    @Schema(description = "标签ID")
    private Long labelId;

    /**
     * 是否显示购买积分
     */
    @Schema(description = "是否显示购买积分 true:显示")
    private Boolean showPointFlag;

    @Schema(description = "是否返回可售性 true:返回")
    private Boolean showVendibilityFlag;

    @Schema(description = "是否返回供应商商品相关信息 true:返回")
    private Boolean showProviderInfoFlag;

    @Schema(description = "是否填充LM商品库存")
    private Boolean fillLmInfoFlag;

    @Schema(description = "市场价范围开始")
    private BigDecimal salePriceFirst;

    @Schema(description = "市场价范围结束")
    private BigDecimal salePriceLast;

    @Schema(description = "分销佣金比例开始")
    private BigDecimal commissionRateFirst;

    @Schema(description = "分销佣金比例结束")
    private BigDecimal commissionRateLast;

    @Schema(description = "分销预估佣金开始")
    private BigDecimal distributionCommissionFirst;

    @Schema(description = "分销预估佣金结束")
    private BigDecimal distributionCommissionLast;

    @Schema(description = "库存范围开始")
    private Long stockFirst;

    @Schema(description = "库存范围结束")
    private Long stockLast;

    @Schema(description = "企业购商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止企业购")
    private Integer distributionGoodsAudit;

    @Schema(description = "企业购商品审核状态，0：无状态 1：待审核 2：已审核 3：审核未通过")
    private EnterpriseAuditState enterPriseAuditState;

    @Schema(description = "是否填充店铺分类")
    private Boolean fillStoreCate;

    /**
     * ES过滤不必要的字段
     */
    @Schema(description = "ES过滤不必要的字段")
    private List<String> filterCols;

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
     * 店铺名称模糊查询
     */
    @Schema(description = "店铺名称模糊查询")
    private String likeSupplierName;

    /**
     * 是否排除供应商商品(包括由供应商品库导入的商品)
     */
    @Schema(description = "是否排除供应商商品")
    private Boolean excludeProviderFlag;


    @Schema(description = "商品类型")
    private Integer pluginType;

    /**
     * 贸易类型
     */
    @Schema(description = "贸易类型")
    private String tradeType;

    /**
     * 贸易类型
     */
    @Schema(description = "贸易类型")
    private List<String> tradeTypes;

    /**
     * 电子口岸
     */
    @Schema(description = "电子口岸")
    private String electronPort;

    /**
     * 备案状态集合：0：未备案，1：申请备案,2：备案成功，3：已失败
     */
    @Schema(description = "备案状态 0：待备案，1：备案中,2：备案成功，3：备案失败")
    private List<Integer> recordStatus;

    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;

    /**
     * 排序规则
     */
    private String sortRole;


    /**
     * 排序字段
     */
    private String sortColumn;

    /**
     * 批量店铺分类编号
     */
    @Schema(description = "批量店铺分类编号")
    private List<Long> storeCateIds;

    /**
     * 卡券idList
     */
    @Schema(description = "卡券idList")
    private List<Long> electronicCouponsIds;

    /**
     * 卡券名称
     */
    @Schema(description = "卡券名称")
    private String electronicCouponsName;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 需排除的商品类型列表
     */
    @Schema(description = "需排除的商品类型列表")
    private List<Integer> excludeGoodsTypes;

    /***
     * 过滤结果是否成功
     */
    private BoolFlag filterEmpty;

    /**
     * 精确条件-批量SKU编码
     */
    @Schema(description = "精确条件-批量SPU编码")
    private List<String> goodsNos;


    /**
     * 是否新人购商品
     */
    @Schema(description = "是否新人购商品")
    private Boolean isNewCustomerGoods;

    @Schema(description = "请求来源")
    private RequestSource requestSource;

}
