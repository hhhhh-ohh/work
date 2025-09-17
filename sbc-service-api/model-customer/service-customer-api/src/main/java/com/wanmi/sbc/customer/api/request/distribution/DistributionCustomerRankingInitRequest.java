package com.wanmi.sbc.customer.api.request.distribution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * <p>用户分销排行榜初始化数据入参</p>
 * @author lq
 * @date 2019-04-19 10:05:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerRankingInitRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 目标日期
     */
    @Schema(description = "目标日期")
    @NotNull
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    LocalDate targetDate;
}
