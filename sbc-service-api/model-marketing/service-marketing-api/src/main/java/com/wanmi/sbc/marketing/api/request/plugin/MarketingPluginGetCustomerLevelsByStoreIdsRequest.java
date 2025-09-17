package com.wanmi.sbc.marketing.api.request.plugin;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-27
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingPluginGetCustomerLevelsByStoreIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 5652085692573067665L;

    @Schema(description = "店铺id列表")
    @NotNull
    @Size(min = 1)
    private List<Long> storeIds;

    @Schema(description = "客户信息")
    private CustomerDTO customer;
}
