package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class StoreCustomerRelaListByConditionRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 客户Id
     */
    @Schema(description = "客户Id")
    private String customerId;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    private Long companyInfoId;

    /**
     * 商铺Id
     */
    @Schema(description = "商铺Id")
    private Long storeId;

    /**
     * 店铺等级标识
     */
    @Schema(description = "店铺等级标识")
    private Long storeLevelId;

    /**
     * 批量商铺Id
     */
    @Schema(description = "批量商铺Id")
    private List<Long> storeIds;

    /**
     * 客户类型
     */
    @Schema(description = "客户类型")
    private CustomerType customerType;

}
