package com.wanmi.sbc.elastic.bean.vo.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ES商品实体类
 * 以SKU维度
 * Created by dyt on 2017/4/21.
 */
@Data
@Schema
public class EsGoodsVO extends BasicResponse {

    @Schema(description = "id")
    private String id;

    /**
     * 转化为小写
     */
    @Schema(description = "转化为小写")
    private String lowGoodsName;

    /**
     * 转化为小写
     */
    @Schema(description = "转化为小写")
    private String pinyinGoodsName;

    /**
     * 商品副标题
     */
    @Schema(description = "商品副标题")
    private String goodsSubtitle;

    /**
     * SKU信息auditStatus
     */
    @Schema(description = "SKU信息auditStatus")
    private List<GoodsInfoNestVO> goodsInfos;

    /**
     * 商品分类信息
     */
    @Schema(description = "商品分类信息")
    private GoodsCateNestVO goodsCate;

    /**
     * 商品品牌信息
     */
    @Schema(description = "商品品牌信息")
    private GoodsBrandNestVO goodsBrand;

    /**
     * 上下架时间
     */
    @Schema(description = "上下架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTime;

    /**
     * SKU相关规格
     */
    @Schema(description = "SKU相关规格")
    private List<GoodsInfoSpecDetailRelNestVO> specDetails;

    @Schema(description = "属性id")
    private List<Long> propDetailIds;

    /**
     * 等级价数据
     */
    @Schema(description = "等级价数据")
    private List<GoodsLevelPriceNestVO> goodsLevelPrices = new ArrayList<>();

    /**
     * 客户价数据
     */
    @Schema(description = "客户价数据")
    private List<GoodsCustomerPriceNestVO> customerPrices = new ArrayList<>();

    /**
     * 签约开始日期
     */
    @Schema(description = "签约开始日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;

    /**
     * 签约结束日期
     */
    @Schema(description = "签约结束日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态 0、开启 1、关店")
    private Integer storeState;

    /**
     * 可售状态 0不可收 1可售
     */
    @Schema(description = "可售状态 0不可收 1可售")
    private Integer vendibilityStatus;

    /**
     * 禁售状态
     */
    @Schema(description = "禁售状态")
    private Integer forbidStatus;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态")
    private Integer auditStatus;

    /**
     * 多对多关系，多个店铺分类编号
     */
    @Schema(description = "多对多关系，多个店铺分类编号")
    private List<Long> storeCateIds;

    /**
     * 营销信息
     */
    @Schema(description = "营销信息")
    private List<MarketingForEndVO> marketingList = new ArrayList<>();

    /**
     * 分销商品状态，配合分销开关使用
     */
    @Schema(description = "分销商品状态，配合分销开关使用")
    private Integer distributionGoodsStatus;

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
     * 真实的商品销量
     */
    @Schema(description = "真实商品销量")
    private Long realGoodsSalesNum;

    /**
     * 商品好评数量
     */
    @Schema(description = "商品好评数量")
    private Long goodsFavorableCommentNum;

    /**
     * 商品好评率
     */
    @Schema(description = "商品好评率")
    private Long goodsFeedbackRate;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Long sortNo;

    /**
     * 购买积分
     */
    @Schema(description = "购买积分")
    private Long buyPoint;

    /**
     * 排序的价格
     */
    @Schema(description = "排序的价格")
    private BigDecimal esSortPrice;

    @Schema(description = "计算单位")
    private String goodsUnit;

    @Schema(description = "划线价格")
    private BigDecimal linePrice;

    /**
     * 三方渠道类型，0 linkedmall
     */
    @Schema(description = "三方渠道类型，0 linkedmall")
    private ThirdPlatformType thirdPlatformType;

    /**
     * 商品标签集合
     */
    @Schema(description = "商品标签集合")
    private List<GoodsLabelNestVO> goodsLabelList;

    /**
     * 供应商id
     */
    @Schema(description = "供应商id")
    private Long providerId;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

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
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商品编码
     */
    @Schema(description = "商品编码")
    private String goodsNo;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态")
    private Integer addedFlag;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall
     */
    @Schema(description = "商品来源，0供应商，1商家,2 linkedmall")
    private Integer goodsSource;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0:实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 商品库存
     */
    @Schema(description = "商品库存")
    private Long stock;


    /**
     * 创建日期
     */
    @Schema(description = "创建日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "类目名称")
    private String storeCateName;

    @Schema(description = "商品类型")
    private Integer pluginType;

    @Schema(description = "运费模板")
    public Long freightTemplateId;

    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Schema(description = "是否参与周期购，0 否， 1 是")
    private Integer isBuyCycle;
}
