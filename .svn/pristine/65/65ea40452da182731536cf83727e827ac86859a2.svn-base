package com.wanmi.sbc.marketing.api.request.bargaingoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.BargainActivityState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>砍价商品通用查询请求参数</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainGoodsQueryRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-bargainGoodsIdList
     */
    @Schema(description = "批量查询-bargainGoodsIdList")
    private List<Long> bargainGoodsIdList;

    /**
     * bargainGoodsId
     */
    @Schema(description = "bargainGoodsId")
    private Long bargainGoodsId;

    /**
     * 商品SKU编号
     */
    @Schema(description = "商品SKU编号")
    private String goodsInfoId;

    /**
     * 多个商品SKU编号
     */
    @Schema(description = "多个商品SKU编号")
    private List<String> goodsInfoIds;

    /**
     * SKU 编码
     */
    @Schema(description = "SKU 编码")
    private String goodsInfoNo;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsInfoName;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 平台类目
     */
    @Schema(description = "平台类目")
    private Long cateId;

    /**
     * 多个平台类目
     */
    @Schema(description = "多个平台类目")
    private List<Long> cateIds;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private List<Long> storeIds;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private Long storeId;

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
    private Boolean leaveStock;

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

    /**
     * 商品状态
     */
    @Schema(description = "商品状态")
    private DeleteFlag goodsStatus;

    /**
     * 搜索条件:活动开始时间开始
     */
    @Schema(description = "搜索条件:活动开始时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTimeBegin;
    /**
     * 搜索条件:活动开始时间截止
     */
    @Schema(description = "搜索条件:活动开始时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTimeEnd;

    /**
     * 搜索条件:活动结束时间开始
     */
    @Schema(description = "搜索条件:活动结束时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTimeBegin;
    /**
     * 搜索条件:活动结束时间截止
     */
    @Schema(description = "搜索条件:活动结束时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTimeEnd;

    /**
     * 搜索条件:createTime开始
     */
    @Schema(description = "搜索条件:createTime开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;
    /**
     * 搜索条件:createTime截止
     */
    @Schema(description = "搜索条件:createTime截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;

    /**
     * 搜索条件:updateTime开始
     */
    @Schema(description = "搜索条件:updateTime开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeBegin;
    /**
     * 搜索条件:updateTime截止
     */
    @Schema(description = "搜索条件:updateTime截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeEnd;

    /**
     * delFlag
     */
    @Schema(description = "delFlag")
    private DeleteFlag delFlag;

    /**
     * 0：未开始 1：活动已结束  2：活动进行中
     */
    @Schema(description = "0：未开始 1：活动已结束  2：活动进行中")
    private BargainActivityState bargainActivityState;

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

    @Schema(description = "前端标识 - 查询优惠券使用")
    private String terminalSource;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    private BigDecimal linePrice;


    /**
     * 审核状态，0：待审核，1：已审核，2：审核失败
     */
    @Schema(description = "审核状态，0：待审核，1：已审核，2：审核失败")
    private List<AuditStatus> auditStatusList;

    /**
     * 非自身id
     */
    @Schema(description = "非自身id")
    private Long notId;
}