package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品库SKU VO
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class StandardSkuVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 商品SKU编号
     */
    @Schema(description = "商品SKU编号")
    private String goodsInfoId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * 商品SKU编码
     */
    @Schema(description = "商品SKU编码")
    private String goodsInfoNo;

    /**
     * 商品SKU名称
     */
    @Schema(description = "商品SKU名称")
    private String goodsInfoName;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    @CanEmpty
    private String goodsInfoImg;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    private BigDecimal marketPrice;

    /**
     * 商品成本价
     */
    @CanEmpty
    @Schema(description = "商品成本价")
    private BigDecimal costPrice;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private DeleteFlag delFlag;

    /**
     * 来源
     */
    @Schema(description = "来源")
    private Integer goodsSource;

    /**
     * 新增时，模拟多个规格ID
     * 查询详情返回响应，扁平化多个规格ID
     */
    @Schema(description = "新增时，模拟多个规格ID，查询详情返回响应，扁平化多个规格ID")
    private List<Long> mockSpecIds;

    /**
     * 新增时，模拟多个规格值 ID
     * 查询详情返回响应，扁平化多个规格值ID
     */
    @Schema(description = "新增时，模拟多个规格值 ID，查询详情返回响应，扁平化多个规格值ID")
    private  List<Long> mockSpecDetailIds;

    /**
     * 商品分页，扁平化多个商品规格值ID
     */
    @Schema(description = "商品分页，扁平化多个商品规格值ID")
    private List<Long> specDetailRelIds;

    /**
     * 规格名称规格值 颜色:红色;大小:16G
     */
    @Schema(description = "规格名称规格值，颜色:红色;大小:16G")
    private String specText;

    /**
     * 供应商商品SKU编号
     */
    @Schema(description = "供应商商品SKU编号")
    private String providerGoodsInfoId;

    /**
     * 商品供货价
     */
    @Schema(description = "商品供货价")
    private BigDecimal supplyPrice;

    @Schema(description = "库存")
    private Long stock;

    /**
     * 商品参数
     */
    @CanEmpty
    @Schema(description = "商品参数")
    private String goodsParam;

    /**
     * 条形码
     */
    @Schema(description = "条形码")
    private String goodsInfoBarcode;


    /**
     * 第三方平台的skuId
     */
    @Schema(description = "第三方平台的skuId")
    private String thirdPlatformSkuId;

    /**
     * 第三方平台的skuId
     */
    @Schema(description = "第三方平台的spuId")
    private String thirdPlatformSpuId;

    /**
     * 第三方卖家id
     */
    @Schema(description = "第三方卖家id")
    private Long sellerId;

    /**
     * 三方渠道类目id
     */
    @Schema(description = "三方渠道类目id")
    private Long thirdCateId;

    @Schema(description = "上下架状态，0下架，1上架，2部分上架")
    private Integer addedFlag;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal goodsWeight;

    /**
     * 商品体积 单位：m3
     */
    @Schema(description = "商品体积 单位：m3")
    private BigDecimal goodsCubage;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0:实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    private StandardGoodsVO standardGoods;

    /**
     * 商品状态 0：正常 1：缺货 2：失效
     */
    @Schema(description = "商品状态，0：正常 1：缺货 2：失效")
    private GoodsStatus goodsStatus = GoodsStatus.OK;

    /**
     * 订货号
     */
    @Schema(description = "订货号")
    private String quickOrderNo;
}
