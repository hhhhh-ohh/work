package com.wanmi.sbc.marketing.api.request.bargain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.BargainStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>砍价通用查询请求参数</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainQueryRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-bargainIdList
     */
    @Schema(description = "批量查询-bargainIdList")
    private List<Long> bargainIdList;

    /**
     * bargainId
     */
    @Schema(description = "bargainId")
    private Long bargainId;

    private String goodsInfoName;

    private String goodsInfoNo;

    private BargainStatus bargainStatus;

    /**
     * 砍价编号
     */
    @Schema(description = "砍价编号")
    private Long bargainNo;

    /**
     * 砍价商品id
     */
    @Schema(description = "砍价商品id")
    private Long bargainGoodsId;

    private List<Long> bargainGoodsIds;

    /**
     * goodsInfoId
     */
    @Schema(description = "goodsInfoId")
    private String goodsInfoId;

    /**
     * goodsInfoIds
     */
    @Schema(description = "goodsInfoIds")
    private List<String> goodsInfoIds;

    /**
     * 搜索条件:发起时间开始
     */
    @Schema(description = "搜索条件:发起时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTimeBegin;
    /**
     * 搜索条件:发起时间截止
     */
    @Schema(description = "搜索条件:发起时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTimeEnd;

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
     * 发起人id
     */
    @Schema(description = "发起人id")
    private String customerId;


    private List<String> customerIds;

    @Schema(description = "发起人账号")
    private String customerAccount;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderId;

    private Long storeId;

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

}