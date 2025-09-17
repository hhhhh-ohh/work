package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量更新账户状态和禁用原因request</p>
 * Created by daiyitian on 2018-08-13-下午3:47.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistributionCustomeffBatchModifyRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 分销员ID
     */
    @Schema(description = "分销员ID列表")
    @NotEmpty
    private List<String> distributionIds;

    /**
     * 是否禁止分销 0: 启用中  1：禁用中
     */
    @Schema(description = "是否禁止分销 0: 启用中  1：禁用中")
    @NotNull
    private DefaultFlag forbiddenFlag;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    private String forbiddenReason;

}
