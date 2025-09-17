package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品实体类
 *
 * @author dyt
 * @date 2017/4/11
 */
@Data
@Schema
public class GoodsAuditSaveDTO implements Serializable {

    /**
     * 商品编号，采用UUID
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * 旧商品Id
     */
    @Schema(description = "旧商品Id")
    private String oldGoodsId;

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 销售类别
     */
    @Schema(description = "销售类别")
    private Integer saleType;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    @CanEmpty
    private Long brandId;

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
    @Schema(description = "商品主图")
    @CanEmpty
    private String goodsImg;

    /**
     * 商品视频地址
     */
    @Schema(description = "商品视频地址")
    @CanEmpty
    private String goodsVideo;

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
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0:实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 划线价格
     */
    @Schema(description = "划线价格")
    @CanEmpty
    private BigDecimal linePrice;

    /**
     * 成本价
     */
    @Schema(description = "成本价")
    @CanEmpty
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
     * 上下架时间
     */
    @Schema(description = "上下架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTime;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall
     */
    @Schema(description = "商品来源，0供应商，1商家,2 linkedmall")
    private Integer goodsSource;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private DeleteFlag delFlag;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态")
    private Integer addedFlag;

    /**
     * 是否定时上架
     */
    @Schema(description = "是否定时上架")
    private Boolean addedTimingFlag;

    /**
     * 定时上架时间
     */
    @Schema(description = "定时上架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTimingTime;

    /**
     * 是否定时下架
     */
    @Schema(description = "是否定时下架 true:是,false:否")
    private Boolean takedownTimeFlag;

    /**
     * 定时下架时间
     */
    @Schema(description = "定时下架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime takedownTime;

    /**
     * 是否多规格标记
     */
    @Schema(description = "是否多规格标记")
    private Integer moreSpecFlag;

    /**
     * 设价类型 0:按客户 1:按订货量 2:按市场价
     */
    @Schema(description = "设价类型 0:按客户 1:按订货量 2:按市场价")
    private Integer priceType;

    /**
     * 是否按客户单独定价
     */
    @Schema(description = "是否按客户单独定价")
    private Integer customFlag;

    /**
     * 订货量设价时,是否允许sku独立设阶梯价(0:不允许,1:允许)
     */
    @Schema(description = "是否允许sku独立设阶梯价")
    private Integer allowPriceSet;

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
    @Schema(description = "审核状态")
    private CheckStatus auditStatus;

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
     * 商品移动端详情
     */
    @Schema(description = "商品移动端详情")
    private String goodsMobileDetail;

    /**
     * 库存，根据相关所有SKU库存来合计
     */
    @Schema(description = "库存")
    private Long stock;

    /**
     * 一对多关系，多个SKU编号
     */
    @Schema(description = "一对多关系")
    private List<String> goodsInfoIds;

    /**
     * 一对多关系，多个SKU
     */
    @Schema(description = "一对多关系，多个SKU")
    private List<GoodsInfoSaveDTO> goodsInfoList;

    /**
     * 多对多关系，多个店铺分类编号
     */
    @Schema(description = "多对多关系，多个店铺分类编号")
    private List<Long> storeCateIds;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型 0、平台自营 1、第三方商家")
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
     * 商品评论数
     */
    @Schema(description = "商品评论数")
    private Long goodsEvaluateNum;

    /**
     * 商品收藏量
     */
    @Schema(description = "商品收藏量")
    private Long goodsCollectNum;

    /**
     * 商品销量
     */
    @Schema(description = "商品销量")
    private Long goodsSalesNum;

    /**
     * 商品好评数量
     */
    @Schema(description = "商品好评数量")
    private Long goodsFavorableCommentNum;

    /**
     * 注水销量
     */
    @Schema(description = "注水销量")
    private Long shamSalesNum;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Long sortNo;

    /**
     * 是否单规格
     */
    @Schema(description = "是否单规格")
    private Boolean singleSpecFlag;

    /**
     * 最小市场价（取自SKU中最小的价格）
     */
    @Schema(description = "最小市场价")
    private BigDecimal skuMinMarketPrice;

    /**
     * 购买方式 0立即购买,1购物车,内容以,相隔
     */
    @Schema(description = "购买方式 0立即购买,1购物车,内容以,相隔")
    private String goodsBuyTypes;

    /**
     * 购买积分
     */
    @Schema(description = "购买积分")
    private Long buyPoint;

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
     * 是否需要同步 0：不需要同步 1：需要同步
     */
    @Schema(description = "是否需要同步 0：不需要同步 1：需要同步")
    private BoolFlag needSynchronize;

    /**
     * 删除原因
     */
    @Schema(description = "删除原因")
    private String deleteReason;

    /**
     * 下架原因
     */
    @Schema(description = "下架原因")
    private String addFalseReason;

    /**
     * 是否可售
     */
    @Schema(description = "是否可售")
    private Integer vendibility;
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
     * 三方平台类型，0，linkedmall
     */
    @Schema(description = "三方平台类型，0，linkedmall")
    private ThirdPlatformType thirdPlatformType;

    /**
     * 虚拟字段，好评率
     */
    @Schema(description = "虚拟字段，好评率")
    private BigDecimal feedbackRate;

    /**
     * 虚拟字段，全销售量 （真实销售量 + 注水销量）
     */
    @Schema(description = "虚拟字段，全销售量 （真实销售量 + 注水销量）")
    private Long allSalesNum;

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
     * 商品一级分类
     */
    @Schema(description = "商品一级分类")
    private Long cateTopId;

    /**
     * 商品类型；0:一般商品 1:跨境商品
     */
    @Schema(description = "商品类型；0:一般商品 1:跨境商品")
    private PluginType pluginType = PluginType.NORMAL;

    /**
     * 扩展属性
     */
    @Schema(description = "扩展属性")
    private Object extendedAttributes;

    /**
     * 商家类型 0 普通商家 1 跨境商家
     */
    @Schema(description = "商家类型 0 普通商家 1 跨境商家")
    private SupplierType supplierType;

    /**
     * 商家类型,0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型,0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;

    /**
     * 在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置
     * 0 否， 1 是
     */
    @Schema(description = "标志加价比例是否独立设置")
    private EnableStatus isIndependent;

    /**
     * 审核类型 1:初次审核 2:二次审核
     */
    @Schema(description = "审核类型 1:初次审核 2:二次审核")
    private Integer auditType;

    public String getGoodsId() {
        if (StringUtils.isEmpty(this.goodsId)) {
            this.goodsId = UUIDUtil.getUUID();
        }
        return goodsId;
    }
}