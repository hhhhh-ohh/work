package com.wanmi.sbc.elastic.goods.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;
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
@Document(indexName = EsConstants.DOC_GOODS_TYPE)
@Data
public class EsGoods implements Serializable {

    @Id
    private String id;

    /**
     * 转化为小写
     */
    private String lowGoodsName;

    /**
     * 转化为小写
     */
    private String pinyinGoodsName;

    /**
     * SKU信息auditStatus
     */
    private List<GoodsInfoNest> goodsInfos;

    /**
     * 商品副标题
     */
    private String goodsSubtitle;

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

    /**
     * SKU相关规格
     */
    private List<GoodsInfoSpecDetailRelNest> specDetails;

    /**
     * 属性id
     */
    private List<Long> propDetailIds;

    /**
     * 商品属性对象
     */
    private List<GoodsPropRelNest> goodsPropRelNests = new ArrayList<>();

    /**
     * 等级价数据
     */
    private List<GoodsLevelPriceNest> goodsLevelPrices = new ArrayList<>();

    /**
     * 客户价数据
     */
    private List<GoodsCustomerPriceNest> customerPrices = new ArrayList<>();

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
     * 可售状态 0不可收 1可售
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
     * 营销信息
     */
    private List<MarketingForEndVO> marketingList = new ArrayList<>();

    /**
     * 分销商品状态，配合分销开关使用
     */
    private Integer distributionGoodsStatus;

    /**
     * 商品评论数
     */
    private Long goodsEvaluateNum;

    /**
     * 商品收藏量
     */
    private Long goodsCollectNum;

    /**
     * 商品销量
     */
    private Long goodsSalesNum;

    /**
     * 真实的商品销量
     */
    private Long realGoodsSalesNum;

    /**
     * 商品好评数量
     */
    private Long goodsFavorableCommentNum;

    /**
     * 商品好评率
     */
    private Long goodsFeedbackRate;

    /**
     * 排序号
     */
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
     * 运费模板ID
     */
    @Schema(description = "运费模板ID")
    private Long freightTempId;

    /**
     * 三方渠道类型，0 linkedmall
     */
    private ThirdPlatformType thirdPlatformType;

    /**
     * 商品类型；0:一般商品 1:跨境商品
     */
    private PluginType pluginType;

    /**
     * 商品标签集合
     */
    private List<GoodsLabelNest> goodsLabelList;

    /**
     * 供应商id
     */
    private Long providerId;

    /**
     * 供应商名称
     */
    private String providerName;

    /**
     * 公司信息ID
     */
    private Long companyInfoId;

    /**
     * 公司名称
     */
    private String supplierName;


    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 商品编码
     */
    private String goodsNo;

    /**
     * 上下架状态
     */
    private Integer addedFlag;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall
     */
    private Integer goodsSource;

    /**
     * 商品类型0：实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;

    /**
     * 商品库存
     */
    private Long stock;

    /**
     * 签约结束日期
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 模糊查询
     */
    private String goodsName;

    /**
     * 所属供应商商品spuId
     */
    private String providerGoodsId;

    @Schema(description = "扩展属性 pluginType=1时（tradeType:交易类型， electronPort:电子口岸）")
    private Object extendedAttributes;

    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;

    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Schema(description = "是否参与周期购，0 否， 1 是")
    private Integer isBuyCycle;
}
