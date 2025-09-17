package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品库SKU DTO
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class StandardSkuDTO implements Serializable {

    private static final long serialVersionUID = -6155314934602900190L;

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
    private String goodsInfoImg;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    private BigDecimal marketPrice;

    /**
     * 商品成本价
     */
    @Schema(description = "商品成本价")
    private BigDecimal costPrice;

    /**
     * 商品供货价
     */
    @Schema(description = "商品供货价")
    private BigDecimal supplyPrice;

    /**
     * 商品参数
     */
    @CanEmpty
	@Schema(description = "商品参数")
    private String goodsParam;

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
    @Schema(description = "删除标记 0: 否, 1: 是")
    private DeleteFlag delFlag;

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
    @Schema(description = "规格名称规格值", example = " 颜色:红色;大小:16G")
    private String specText;

    /**
     * 供应商商品SKU编号
     */
    @Schema(description = "供应商商品SKU编号")
    private String providerGoodsInfoId;

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
     * 第三方平台的spuId
     */
    @Schema(description = "第三方平台的spuId")
    private String thirdPlatformSpuId;

    /**
     * 第三方卖家id
     */
    @Schema(description = "seller_id")
    private Long sellerId;

    /**
     * 三方渠道类目id
     */
    @Schema(description = "third_cate_id")
    private Long thirdCateId;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall,3 Platform,4 VOP
     */
    @Schema(description = "商品来源，0供应商，1商家,3 Platform,4 VOP")
    private Integer goodsSource;

    /**
     * 商品库存
     */
    @Schema(description = "商品库存")
    private Long stock;

    /**
     * 上下架状态,0:下架1:上架2:部分上架
     */
    @Schema(description = "上下架状态,0:下架1:上架2:部分上架")
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
     * 商品类型 0、实物商品 1、虚拟商品 2、电子卡券
     */
    @Schema(description = "商品类型 0、实物商品 1、虚拟商品 2、电子卡券")
    private Integer goodsType;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    private BigDecimal linePrice;

    /**
     * 订货号
     */
    @Schema(description = "订货号")
    private String quickOrderNo;
}
