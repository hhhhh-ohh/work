package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSelectStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsUnAuditCountRequest
 * 待审核商品统计请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午11:21
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsUnAuditCountRequest extends BaseRequest {

    private static final long serialVersionUID = 6395373997205751361L;

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
    @Schema(description = "模糊条件-关键词，商品名称、SPU编码")
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
     * 审核状态
     */
    @Schema(description = "审核状态，0：待审核 1：已审核 2：审核失败 3：禁售中")
    private CheckStatus auditStatus;

    /**
     * 批量审核状态
     */
    @Schema(description = "批量审核状态，0：待审核 1：已审核 2：审核失败 3：禁售中")
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
    @Schema(description = "商品状态筛选，0：上架中 1：下架中 2：部分上架中 3：待审核及其他")
    private List<GoodsSelectStatus> goodsSelectStatuses;
}
