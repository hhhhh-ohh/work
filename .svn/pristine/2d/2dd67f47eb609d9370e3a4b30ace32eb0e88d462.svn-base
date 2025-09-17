package com.wanmi.sbc.elastic.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>批量更新账户状态和禁用原因request</p>
 * Created by daiyitian on 2018-08-13-下午3:47.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsCustomerStateBatchModifyRequest extends BaseRequest {


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

}
