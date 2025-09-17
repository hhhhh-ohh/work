package com.wanmi.sbc.marketing.api.request.bargainjoin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>帮砍记录通用查询请求参数</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainJoinQueryRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-bargainJoinIdList
     */
    @Schema(description = "批量查询-bargainJoinIdList")
    private List<Long> bargainJoinIdList;

    /**
     * bargainJoinId
     */
    @Schema(description = "bargainJoinId")
    private Long bargainJoinId;

    /**
     * 砍价记录id
     */
    @Schema(description = "砍价记录id")
    private Long bargainId;

    /**
     * 砍价商品id
     */
    @Schema(description = "砍价商品id")
    private String goodsInfoId;

    /**
     * 砍价的发起人
     */
    @Schema(description = "砍价的发起人")
    private String customerId;

    /**
     * 帮砍人id
     */
    @Schema(description = "帮砍人id")
    private String joinCustomerId;

    /**
     * 帮砍金额
     */
    @Schema(description = "帮砍金额")
    private BigDecimal bargainAmount;

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

}