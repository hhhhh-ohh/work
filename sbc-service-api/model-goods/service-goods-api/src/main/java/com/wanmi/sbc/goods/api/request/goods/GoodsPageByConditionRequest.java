package com.wanmi.sbc.goods.api.request.goods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSelectStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsPageRequest
 * 商品分页请求对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午9:33
 */
@Schema
@Data
public class GoodsPageByConditionRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -7972557462976673056L;

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
    @Schema(description = "审核状态", contentSchema = com.wanmi.sbc.goods.bean.enums.CheckStatus.class)
    private CheckStatus auditStatus;

    /**
     * 批量审核状态
     */
    @Schema(description = "批量审核状态", contentSchema = com.wanmi.sbc.goods.bean.enums.CheckStatus.class)
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
     * 销售类别
     */
    @Schema(description = "销售类别", contentSchema = com.wanmi.sbc.goods.bean.enums.SaleType.class)
    private Integer saleType;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型")
    private Integer goodsType;

    /**
     * 商品来源，0品牌商城，1商家
     */
    @Schema(description = "商品来源，0品牌商城，1商家")
    private Integer goodsSource;

    /**
     * 创建开始时间
     */
    @Schema(description = "创建开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;

    /**
     * 创建结束时间
     */
    @Schema(description = "创建结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;

    /***
     * 是否展示O2O商品
     */
    private Boolean isShowO2oFlag;

    /***
     * 仅展示O2O商品
     */
    private Boolean onlyShowO2oFlag;
}
