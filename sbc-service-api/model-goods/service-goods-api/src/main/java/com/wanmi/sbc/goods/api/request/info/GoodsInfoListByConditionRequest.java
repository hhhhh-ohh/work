package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsInfoSelectStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品SKU列表条件查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoListByConditionRequest extends BaseRequest {

    private static final long serialVersionUID = 8654883151997646528L;

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
     * 分类编号列表
     */
    @Schema(description = "分类编号列表")
    private List<Long> cateIds;

    /**
     * 店铺分类id
     */
    @Schema(description = "店铺分类id")
    private Long storeCateId;

    /**
     * 批量查询-店铺分类id
     */
    @Schema(description = "批量查询-店铺分类id")
    private List<Long> storeCateIds;

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
     * 是否显示购买积分
     */
    @Schema(description = "是否显示购买积分 true:显示")
    private Boolean showPointFlag;

    /**
     * 批量供应商商品SKU编号
     */
    @Schema(description = "批量供应商商品SKU编号")
    private List<String> providerGoodsInfoIds;

    /**
     * 商品来源，0供应商，1商家
     */
    private Integer goodsSource;

    /**
     * 批量商品来源，0供应商，1商家
     */
    private List<GoodsSource> goodsSourceList;

    /**
     * 三方平台类型，0，linkedmall
     */
    private Integer thirdPlatformType;
    /**
     *第三方平台的skuId
     */
    private List<String> thirdPlatformSkuId;

    @Schema(description = "是否返回规格明细 true:返回")
    private Boolean showSpecFlag;

    @Schema(description = "是否返回可售性 true:返回")
    private Boolean showVendibilityFlag;

    @Schema(description = "是否返回供应商商品相关信息 true:返回")
    private Boolean showProviderInfoFlag;

    @Schema(description = "是否填充LM商品库存")
    private Boolean fillLmInfoFlag;

    @Schema(description = "是否填充店铺分类")
    private Boolean fillStoreCate;

    /**
     * 商品类型
     */
    @Schema(description = "商品类型0：普通商品 1：跨境 2：O2O")
    private Integer pluginType;

    /**
     * 是否屏蔽跨境商品
     */
    @Schema(description = "是否屏蔽跨境商品")
    private Boolean notShowCrossGoodsFlag;

    /**
     * 备案状态 0：待备案，1：备案中,2：备案成功，3：备案失败
     */
    @Schema(description = "备案状态,0：待备案，1：备案中,2：备案成功，3：备案失败")
    private List<Integer> recordStatus;

    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;
}
