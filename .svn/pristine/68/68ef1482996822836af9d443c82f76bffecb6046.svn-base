package com.wanmi.sbc.customer.api.request.detail;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;

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
public class CustomerStateBatchModifyRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = -9155234181833842753L;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID列表")
    @NotEmpty
    private List<String> customerIds;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态")
    @NotNull
    private CustomerStatus customerStatus;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    private String forbidReason;

    /**
     * 是否客户列表
     */
    @Schema(description = "是否客户列表")
    private Boolean customerListFlag;
}
