package com.wanmi.sbc.elastic.goods.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

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
@Document(indexName = EsConstants.DOC_GOODS_INFO_TYPE)
@Data
public class EsGoodsInfo implements Serializable {

    @Id
    private String id;

    private String goodsId;

    /**
     * 转化为小写
     */
    private String lowGoodsName;

    /**
     * 商品副标题
     */
    private String goodsSubtitle;

    /**
     * 转化为小写
     */
    private String pinyinGoodsName;

    /**
     * SKU信息
     */
    private GoodsInfoNest goodsInfo;

    /**
     * 商品分类信息
     */
    private GoodsCateNest goodsCate;

    /**
     * 商品品牌信息
     */
    private GoodsBrandNest goodsBrand;

    /**
     * 上下架时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTime;

    private List<Long> propDetailIds;

    /**
     * 商品属性对象
     */
    private List<GoodsPropRelNest> goodsPropRelNests = new ArrayList<>();

    /**
     * 等级价数据
     */
    private List<GoodsLevelPriceNest> goodsLevelPrices;

    /**
     * 客户价数据
     */
    private List<GoodsCustomerPriceNest> customerPrices;

    /**
     * 签约开始日期
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;

    /**
     * 签约结束日期
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /**
     * 店铺状态 0、开启 1、关店
     */
    private Integer storeState;

    /**
     * 可售状态 0不可售 1可售
     */
    private Integer vendibilityStatus;


    /**
     * 禁售状态
     */
    private Integer forbidStatus;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 多对多关系，多个店铺分类编号
     */
    private List<Long> storeCateIds;

    /**
     * 分销商品状态，配合分销开关使用
     */
    private Integer distributionGoodsStatus;

    /**
     * 排序号
     */
    private Long sortNo;

    @Schema(description = "计算单位")
    private String goodsUnit;

    @Schema(description = "划线价格")
    private BigDecimal linePrice;

    /**
     * 商品标签集合
     */
    private List<GoodsLabelNest> goodsLabelList;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall
     */
    private Integer goodsSource;

    /**
     * 商品SPU编码
     */
    @Schema(description = "商品SPU编码")
    private String goodsNo;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 电子卡券Id
     */
    @Schema(description = "电子卡券Id")
    private Long electronicCouponsId;

    /**
     * 电子卡券名称
     */
    @Schema(description = "电子卡券名称")
    private String electronicCouponsName;



    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Schema(description = "是否参与周期购，0 否， 1 是")
    private Integer isBuyCycle;

    /**
     * 订货号
     */
    @Schema(description = "订货号")
    private String quickOrderNo;

    /**
     * 供应商订货号
     */
    @Schema(description = "供应商订货号")
    private String providerQuickOrderNo;
    @Schema(description = "属性尺码")
    private String attributeSize;

    /**
     * 0-春秋装 1-夏装 2-冬装
     */
    @Schema(description = "款式季节")
    private Integer attributeSeason;

    /**
     * 0-校服服饰 1-非校服服饰 2-自营产品
     */
    @Schema(description = "是否校服")
    private Integer attributeGoodsType;

    /**
     * 0-老款 1-新款
     */
    @Schema(description = "新老款")
    private Integer attributeSaleType;

    /**
     * 售卖地区
     */
    @Schema(description = "售卖地区")
    private String attributeSaleRegion;

    /**
     * 学段
     */
    @Schema(description = "学段")
    private String attributeSchoolSection;

    /**
     * 银卡价格
     */
    @Schema(description = "银卡价格")
    private BigDecimal attributePriceSilver;

    /**
     * 金卡价格
     */
    @Schema(description = "金卡价格")
    private BigDecimal attributePriceGold;

    /**
     * 钻石卡价格
     */
    @Schema(description = "钻石卡价格")
    private BigDecimal attributePriceDiamond;
    @Schema(description = "折扣价格")
    private BigDecimal attributePriceDiscount;
}
