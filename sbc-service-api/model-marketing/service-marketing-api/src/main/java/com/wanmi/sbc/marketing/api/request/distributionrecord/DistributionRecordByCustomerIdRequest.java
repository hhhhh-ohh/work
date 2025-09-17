package com.wanmi.sbc.marketing.api.request.distributionrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>查询DistributionRecord列表请求参数</p>
 *
 * @author baijz
 * @date 2019-02-27 18:56:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class DistributionRecordByCustomerIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 会员的Id
     */
    @Schema(description = "会员的Id")
    @NotNull
    private String customerId;
}