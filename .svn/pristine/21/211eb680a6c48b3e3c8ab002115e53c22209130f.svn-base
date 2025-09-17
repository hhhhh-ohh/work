package com.wanmi.sbc.customer.api.request.distribution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDate;

/**
 * <p>用户分销排行榜分页查询请求参数</p>
 * @author lq
 * @date 2019-04-19 10:05:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerRankingPageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 排行类型
     */
    @Schema(description = "排行类型")
    @NotNull
    private String type;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 我的会员ID
     */
    @Schema(description = "我的会员ID")
    private String myCustomerId;
    /**
     * 查询日期
     */
    @Schema(description = "查询日期")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate targetDate;
}