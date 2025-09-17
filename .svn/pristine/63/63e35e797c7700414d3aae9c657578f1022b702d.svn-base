package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSelectStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 商品查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  GoodsByConditionRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 7979034129957350808L;

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
     * 精准条件-SPU编码
     */
    @Schema(description = "精准条件-SPU编码")
    private String goodsNo;

    /**
     * 精准条件-批量SPU编码
     */
    @Schema(description = "精准条件-批量SPU编码")
    private List<String> goodsNos;

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
     * 模糊条件-商品名称
     */
    @Schema(description = "模糊条件-商品名称")
    private String likeGoodsName;

    /**
     * 模糊条件-关键词（商品名称、SPU编码）
     */
    @Schema(description = "模糊条件-关键词（商品名称、SPU编码）")
    private String keyword;

    /**
     * 商品分类
     */
    @Schema(description = "商品分类")
    private Long cateId;

    /**
     * 批量商品分类
     */
    @Schema(description = "批量商品分类")
    private List<Long> cateIds;

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
     * 上下架状态
     */
    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private Integer addedFlag;

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
     * 非GoodsId
     */
    @Schema(description = "非GoodsId")
    private String notGoodsId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String likeSupplierName;

    /**
     * 供应商商品Ids
     */
    @Schema(description = "批量供应商商品Ids")
    private List<String> providerGoodsIds;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态，0：待审核 1：已审核 2：审核失败 3：禁售中")
    private CheckStatus auditStatus;

    /**
     * 批量审核状态
     */
    @Schema(description = "批量审核状态")
    private List<CheckStatus> auditStatusList;

    /**
     * 店铺分类Id
     */
    @Schema(description = "店铺分类Id")
    private Long storeCateId;

    /**
     * 店铺分类所关联的SpuIds
     */
    @Schema(description = "店铺分类所关联的SpuIds")
    private List<String> storeCateGoodsIds;

    /**
     * 运费模板ID
     */
    @Schema(description = "运费模板ID")
    private Long freightTempId;

    /**
     * 商品状态筛选
     */
    @Schema(description = "商品状态筛选")
    private List<GoodsSelectStatus> goodsSelectStatuses;

    /**
     * 商品来源，0供应商，1商家
     */
    private Integer goodsSource;

    /**
     * 店铺IDs
     */
    @Schema(description = "店铺IDs")
    private List<Long> storeIds;


    /**
     * 三方spuid
     */
    @Schema(description = "三方spuid")
    private String thirdPlatformSpuId;

    /**
     * 三方skuid
     */
    @Schema(description = "三方skuid")
    private String thirdPlatformSkuId;

}
