package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>商品库 spu dto</p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardGoodsDTO implements Serializable {

    private static final long serialVersionUID = -1268391019065068593L;

    /**
     * 商品编号，采用UUID
     */
    @Schema(description = "商品编号，采用UUID")
    private String goodsId;

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    @CanEmpty
    private Long brandId;

    /**
     * SPU编码
     */
    @Schema(description = "SPU编码")
    private String goodsNo;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 商品副标题
     */
    @Schema(description = "商品副标题")
    private String goodsSubtitle;

    /**
     * 计量单位
     */
    @Schema(description = "计量单位")
    @CanEmpty
    private String goodsUnit;

    /**
     * 商品主图
     */
    @Schema(description = "商品主图")
    @CanEmpty
    private String goodsImg;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal goodsWeight;

    /**
     * 市场价
     */
    @Schema(description = "市场价")
    private BigDecimal marketPrice;

    /**
     * 成本价
     */
    @Schema(description = "成本价")
    @CanEmpty
    private BigDecimal costPrice;

    /**
     * 供货价
     */
    @Schema(description = "供货价")
    @CanEmpty
    private BigDecimal supplyPrice;

    /**
     * 建议零售价
     */
    @Schema(description = "建议零售价")
    @CanEmpty
    private BigDecimal recommendedRetailPrice;

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
     * 是否多规格标记
     */
    @Schema(description = "是否多规格标记", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer moreSpecFlag;

    /**
     * 商品详情
     */
    @Schema(description = "商品详情")
    private String goodsDetail;

    /**
     * 商品移动端详情
     */
    @Schema(description = "商品移动端详情")
    private String goodsMobileDetail;

    /**
     * 一对多关系，多个SKU编号
     */
    @Schema(description = "一对多关系，多个SKU编号")
    private List<String> goodsInfoIds;

    /**
     * 商品体积 单位：m3
     */
    @Schema(description = "商品体积 单位：m3")
    private BigDecimal goodsCubage;

    /**
     * 商品视频链接
     */
    @Schema(description = "商品视频链接")
    @CanEmpty
    private String goodsVideo;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    @CanEmpty
    private String providerName;

    /**
     * 商品来源，0供应商，1商家
     */
    @Schema(description = "商品来源，0供应商，1商家")
    private Integer goodsSource;

    /**
     * 删除原因
     */
    @Schema(description = "删除原因")
    private String deleteReason;

    /**
     * 第三方平台的spuId
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

    /**
     * 上下架状态,0:下架1:上架2:部分上架
     */
    @Schema(description = "上下架状态")
    private Integer addedFlag;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商品类型 0、实物商品 1、虚拟商品 2、电子卡券
     */
    @Schema(description = "商品类型 0、实物商品 1、虚拟商品 2、电子卡券")
    private Integer goodsType;

    @Schema(description = "库存")
    private long stock;

    @Schema(description = "供应商商品id")
    private String providerGoodsId;

    /**
     * 运费模板名称
     */
    @Schema(description = "运费模板名称")
    private String freightTempName;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    private BigDecimal linePrice;
}
