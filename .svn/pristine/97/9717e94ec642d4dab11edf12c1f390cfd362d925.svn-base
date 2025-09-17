package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCustomerRelaListCustomerIdByStoreIdRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺等级ID
     */
    @Schema(description = "店铺等级ID")
    private List<Long> storeLevelIds;
}
