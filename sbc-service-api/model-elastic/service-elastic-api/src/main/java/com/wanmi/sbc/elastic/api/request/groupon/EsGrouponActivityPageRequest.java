package com.wanmi.sbc.elastic.api.request.groupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import com.wanmi.sbc.marketing.bean.enums.GrouponTabTypeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author houshuai
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsGrouponActivityPageRequest extends BaseQueryRequest {


    private static final long serialVersionUID = 8805638492638567858L;
    /**
     * 批量查询-活动IDList
     */
    @Schema(description = "批量查询-活动IDList")
    private List<String> grouponActivityIdList;

    /**
     * 活动ID
     */
    @Schema(description = "是否包邮，0：否，1：是")
    private String grouponActivityId;

    /**
     * 拼团人数
     */
    @Schema(description = "拼团人数")
    private Integer grouponNum;

    /**
     * 搜索条件:开始时间开始
     */
    @Schema(description = "搜索条件:开始时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTimeBegin;
    /**
     * 搜索条件:开始时间截止
     */
    @Schema(description = "搜索条件:开始时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTimeEnd;

    /**
     * 搜索条件:结束时间开始
     */
    @Schema(description = "搜索条件:结束时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTimeBegin;
    /**
     * 搜索条件:结束时间截止
     */
    @Schema(description = "搜索条件:结束时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTimeEnd;


    /**
     * 搜索条件:开始时间开始
     */
    @Schema(description = "搜索条件:开始时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;
    /**
     * 搜索条件:开始时间截止
     */
    @Schema(description = "搜索条件:开始时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;
    /**
     * 拼团分类ID
     */
    @Schema(description = "拼团分类ID")
    private String grouponCateId;

    /**
     * 是否自动成团
     */
    @Schema(description = "是否自动成团")
    private Boolean autoGroupon;

    /**
     * 是否包邮
     */
    @Schema(description = "是否包邮")
    private Boolean freeDelivery;

    /**
     * spu编号
     */
    @Schema(description = "spu编号")
    private String goodsId;

    /**
     * spu编码
     */
    @Schema(description = "spu编码")
    private String goodsNo;

    /**
     * spu商品名称
     */
    @Schema(description = "spu商品名称")
    private String goodsName;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private String storeId;

    /**
     * 是否精选
     */
    @Schema(description = "是否精选")
    private Boolean sticky;

    /**
     * 是否审核通过，0：待审核，1：审核通过，2：审核不通过
     */
    @Schema(description = "是否审核通过")
    private AuditStatus auditStatus;


    /**
     * 删除标记 0未删除 1已删除
     */
    @Schema(description = "删除标记")
    private DeleteFlag delFlag = DeleteFlag.NO;


    /**
     *  页面tab类型，0: 即将开始, 1: 进行中, 2: 已结束，3：待审核，4：审核失败
     */
    @Schema(description = " 页面tab类型")
    private GrouponTabTypeStatus tabType;


    /**
     * 批量查询-spu
     */
    @Schema(description = " 批量查询-spu")
    private List<String> spuIdList;

    @Schema(description = "批量拼团活动id")
    private List<String> idList;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;
}