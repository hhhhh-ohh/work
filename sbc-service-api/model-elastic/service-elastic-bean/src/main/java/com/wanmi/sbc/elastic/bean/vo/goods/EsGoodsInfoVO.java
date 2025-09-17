package com.wanmi.sbc.elastic.bean.vo.goods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.vo.GoodsPropRelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ES商品实体类
 * 以SKU维度
 * Created by dyt on 2017/4/21.
 */
@Data
@Schema
public class EsGoodsInfoVO extends BasicResponse {

    @Schema(description = "id")
    private String id;

    @Schema(description = "goodsId")
    private String goodsId;

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
     * SKU信息
     */
    @Schema(description = "SKU信息")
    private GoodsInfoNestVO goodsInfo;

    /**
     * 商品副标题
     */
    @Schema(description = "商品副标题")
    private String goodsSubtitle;

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

    @Schema(description = "属性id")
    private List<Long> propDetailIds;

    /**
     * 等级价数据
     */
    @Schema(description = "等级价数据")
    private List<GoodsLevelPriceNestVO> goodsLevelPrices;

    /**
     * 客户价数据
     */
    @Schema(description = "客户价数据")
    private List<GoodsCustomerPriceNestVO> customerPrices;

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
     * 可售状态 0不可售 1可售
     */
    @Schema(description = "可售状态 0不可售 1可售")
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
    @Schema(description = " 多对多关系，多个店铺分类编号")
    private List<Long> storeCateIds;

    /**
     * 分销商品状态，配合分销开关使用
     */
    @Schema(description = "分销商品状态，配合分销开关使用")
    private Integer distributionGoodsStatus;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Long sortNo;

    @Schema(description = "计算单位")
    private String goodsUnit;

    @Schema(description = "划线价格")
    private BigDecimal linePrice;

    /**
     * 商品标签集合
     */
    @Schema(description = "商品标签集合")
    private List<GoodsLabelNestVO> goodsLabelList;

    /**
     * 商品类型，普通商品/跨境商品
     */
    @Schema(description = "商品类型")
    private Integer pluginType;

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

    @Schema(description = "运费模板")
    public Long freightTemplateId;

    /**
     * 分账绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
     */
    @Schema(description = "绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败")
    private Integer bindState;


    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Schema(description = "是否参与周期购 0 否， 1 是")
    private Integer isBuyCycle;

    @Schema(description = "商品属性")
    List<GoodsPropRelVO> goodsPropRelNests;
}
