package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.bean.enums.BargainActivityState;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * <p>砍价商品VO</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Schema
@Data
public class BargainGoodsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * bargainGoodsId
     */
    @Schema(description = "bargainGoodsId")
    private Long bargainGoodsId;

    /**
     * goodsInfoId
     */
    @Schema(description = "goodsInfoId")
    private String goodsInfoId;

    @Schema(description = "商品信息")
    private GoodsInfoVO goodsInfoVO;

    /**
     * 市场价
     */
    @Schema(description = "市场价")
    private BigDecimal marketPrice;

    /**
     * 帮砍金额
     */
    @Schema(description = "帮砍金额")
    private BigDecimal bargainPrice;

    /**
     * 帮砍人数
     */
    @Schema(description = "帮砍人数")
    private Integer targetJoinNum;

    /**
     * 砍价库存
     */
    @Schema(description = "砍价库存")
    private Long bargainStock;

    /**
     * 剩余库存
     */
    @Schema(description = "剩余库存")
    private Long leaveStock;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String reasonForRejection;

    /**
     * 审核状态，0：待审核，1：已审核，2：审核失败
     */
    @Schema(description = "审核状态，0：待审核，1：已审核，2：审核失败")
    private AuditStatus auditStatus;

    /**
     * 是否手动停止，0，否，1，是
     */
    @Schema(description = "是否手动停止，0，否，1，是")
    private Boolean stoped;

    @Schema(description = "是否包邮，0，否 1，是")
    private DeleteFlag freightFreeFlag;

    @Schema(description = "商品可售状态，0： 不可售 1：可售")
    private DeleteFlag goodsStatus;

    /**
     * 活动开始时间
     */
    @Schema(description = "活动开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * createTime
     */
    @Schema(description = "createTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;


    /**
     * delFlag
     */
    @Schema(description = "delFlag")
    private DeleteFlag delFlag;

    /**
     * 发起的砍价记录
     */
    private BargainVO bargainVO;


    /**
     * 相关商品SPU信息
     */
    @Schema(description = "相关商品SPU信息")
    private GoodsDetailVO goods;

    /**
     * 商品属性列表
     */
    @Schema(description = "商品属性列表")
    private List<GoodsPropDetailRelVO> goodsPropDetailRels;


    /**
     * 商品相关图片
     */
    @Schema(description = "商品相关图片")
    private List<GoodsImageVO> images = new ArrayList<>();


    @Schema(description = "优惠券信息")
    private List<MarketingPluginLabelVO> marketingLabels;


    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String companyCode;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 商家类型
     */
    @Schema(description = "商家类型")
    private BoolFlag companyType;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 0：未开始 1：活动已结束  2：活动进行中
     */
    @Schema(description = "0：未开始 1：活动已结束  2：活动进行中")
    private BargainActivityState bargainActivityState;

    /**
     * 规格信息
     */
    @Schema(description = "规格信息")
    private String specText;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    private BigDecimal linePrice;

    /**
     * 新用户权重
     */
    @Schema(description = "新用户权重")
    private Double newUserWeight;

    /**
     * 老用户权重
     */
    @Schema(description = "老用户权重")
    private Double oldUserWeight;

}