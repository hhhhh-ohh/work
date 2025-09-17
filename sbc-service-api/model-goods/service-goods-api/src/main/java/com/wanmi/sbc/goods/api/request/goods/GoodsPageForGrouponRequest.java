package com.wanmi.sbc.goods.api.request.goods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsSortType;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsPageRequest
 * 商品分页请求对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午9:33
 */
@Schema
@Data
public class GoodsPageForGrouponRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -7972557462976673056L;


    /**
     * 模糊条件-商品名称
     */
    @Schema(description = "模糊条件-商品名称")
    private String likeGoodsName;

    /**
     * 模糊条件-SPU编码
     */
    @Schema(description = "模糊条件-SPU编码")
    private String likeGoodsNo;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    private Long brandId;

    /**
     * 店铺分类
     */
    @Schema(description = "店铺分类")
    private Long storeCateId;

    /**
     * 店铺分类
     */
    @Schema(description = "平台类目")
    private Long cateId;

    /**
     * 活动开始时间
     */
    @Schema(description = "活动开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime activityStartTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime activityEndTime;

    /**
     * 标签ID
     */
    @Schema(description = "标签ID")
    private Long labelId;

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
     * 排序类型
     */
    @Schema(description = "排序类型")
    private GoodsSortType goodsSortType;

    /**
     * 模糊条件-SPU编码
     */
    @Schema(description = "模糊条件-SKU编码")
    private String likeGoodsInfoNo;

    /**
     * 商品类型 0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型 0:实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 商品来源，0品牌商城，1商家
     */
    @Schema(description = "商品来源，0品牌商城，1商家")
    private GoodsSource searchGoodsSource;

}
