package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午5:57 2019/6/26
 * @Description:
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalDistributorLevelNameUpdateRequest extends BaseRequest {

    /**
     * 分销员等级名称
     */
    @Schema(description = "分销员等级名称")
    @NotNull
    private String distributorLevelName;

}
