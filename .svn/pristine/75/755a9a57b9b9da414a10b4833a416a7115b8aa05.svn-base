package com.wanmi.sbc.empower.bean.dto.channel.base.goods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 渠道商品实体类
 * @author daiyitian
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class ChannelGoodsDto implements Serializable {

    private static final long serialVersionUID = -2318316623301334744L;

    /**
     * 商品编号，采用UUID
     */
    @Schema(description = "商品编号，采用UUID")
    private String goodsId;

    /**
     * 分类一级编号
     */
    @Schema(description = "分类一级编号")
    private Long cateTopId;

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
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

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
     * SPU编码
     */
    @Schema(description = "SPU编码")
    private String goodsNo;

    /**
     * 计量单位
     */
    @Schema(description = "计量单位")
    @CanEmpty
    private String goodsUnit;

    /**
     * 商品主图
     */
    @IsImage
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
    @CanEmpty
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
     * 上下架时间
     */
    @Schema(description = "上下架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTime;

    /**
     * 是否可售
     */
    @Schema(description = "是否可售")
    private Integer vendibility;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private DeleteFlag delFlag;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态", contentMediaType = "com.wanmi.sbc.goods.bean.enums.AddedFlag.class")
    private Integer addedFlag;

    /**
     * 是否定时上架
     */
    @Schema(description = "是否定时上架 true:是,false:否")
    private Boolean addedTimingFlag;

    /**
     * 定时上架时间
     */
    @Schema(description = "定时上架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTimingTime;

    /**
     * 是否多规格标记
     */
    @Schema(description = "是否多规格标记")
    private Integer moreSpecFlag;

    /**
     * 设价类型 0:客户,1:订货
     */
    @Schema(description = "设价类型")
    private Integer priceType;

    /**
     * 是否按客户单独定价
     */
    @Schema(description = "是否按客户单独定价")
    private Integer customFlag;

    /**
     * 是否叠加客户等级折扣
     */
    @Schema(description = "是否叠加客户等级折扣")
    private Integer levelDiscountFlag;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String supplierName;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

    /**
     * 供应商id
     */
    @Schema(description = "供应商id")
    private Long providerId;

    /**
     * 所属供应商商品Id
     */
    @Schema(description = "所属供应商商品Id")
    private String providerGoodsId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 提交审核时间
     */
    @Schema(description = "提交审核时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime submitTime;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态，0：待审核 1：已审核 2：审核失败 3：禁售中")
    private Integer auditStatus;

    /**
     * 审核驳回原因
     */
    @Schema(description = "审核驳回原因")
    private String auditReason;

    /**
     * 商品详情
     */
    @Schema(description = "商品详情")
    private String goodsDetail;

    /**
     * 库存，根据相关所有SKU库存来合计
     */
    @Schema(description = "库存，根据相关所有SKU库存来合计")
    private Long stock;

    /**
     * 一对多关系，多个SKU编号
     */
    @Schema(description = "一对多关系，多个SKU编号")
    private List<String> goodsInfoIds;

    /**
     * 多对多关系，多个店铺分类编号
     */
    @Schema(description = "多对多关系，多个店铺分类编号")
    private List<Long> storeCateIds;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型，0、平台自营 1、第三方商家")
    private BoolFlag companyType;

    /**
     * 商品体积 单位：m3
     */
    @Schema(description = "商品体积 单位：m3")
    private BigDecimal goodsCubage;

    /**
     * 运费模板ID
     */
    @Schema(description = "运费模板ID")
    private Long freightTempId;

    /**
     * 运费模板名称
     */
    @Schema(description = "运费模板名称")
    private String freightTempName;

    /**
     * 销售类别
     */
    @Schema(description = "销售类别", contentMediaType = "com.wanmi.sbc.goods.bean.enums.SaleType.class")
    private Integer saleType;

    /**
     * 商品视频地址
     */
    @Schema(description = "商品视频地址")
    private String goodsVideo;

    /**
     * 划线价格
     */
    @Schema(description = "划线价格")
    private BigDecimal linePrice;

    /**
     * 订货量设价时,是否允许sku独立设阶梯价(0:不允许,1:允许)
     */
    @Schema(description = "订货量设价时,是否允许sku独立设阶梯价，0:不允许,1:允许")
    private Integer allowPriceSet;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;

    /**
     * 商品来源，0品牌商城，1商家,2linkedmall
     */
    private Integer goodsSource;

    /**
     * 购买方式 0立即购买,1购物车,内容以,相隔
     */
    @Schema(description = "购买方式 0立即购买,1购物车,内容以,相隔")
    private String goodsBuyTypes;

    /**
     * 是否单规格
     */
    @Schema(description = "是否单规格")
    private Boolean singleSpecFlag;


    /**
     * 是否需要同步 0：不需要同步 1：需要同步
     */
    @Schema(description = "是否需要同步 0：不需要同步 1：需要同步")
    private BoolFlag needSynchronize;

    /**
     * 删除原因
     */
    @Schema(description = "删除原因")
    private String deleteReason;

    @Schema(description = "第三方平台的spuId")
    private String thirdPlatformSpuId;

    @Schema(description = "三方渠道的类型,0 linkedmall")
    private ThirdPlatformType thirdPlatformType;

    @Schema(description = "linkedmall卖家id")
    private Long sellerId;

    /**
     * 三方渠道类目id
     */
    @Schema(description = "三方渠道类目id")
    private Long thirdCateId;

    /***
     * 类目
     */
    private String category;

    /**
     * 供应商店铺状态 0：关店 1：开店
     */
    @Schema(description = "供应商店铺状态 0：关店 1：开店")
    private Integer providerStatus;

    /**
     * 标签id，以逗号拼凑
     */
    @Schema(description = "标签id，以逗号拼凑")
    private String labelIdStr;

    /**
     * 标签名称，用于敏感词验证
     */
    @Schema(description = "标签名称，用于敏感词验证", hidden = true)
    private String labelName;

    /**
     * 商品标签列表
     */
    @Schema(description = "商品标签列表", hidden = true)
    private List<ChannelGoodsLabelDto> goodsLabelList;
}
