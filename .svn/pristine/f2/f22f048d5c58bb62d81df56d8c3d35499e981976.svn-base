package com.wanmi.sbc.customer.api.request.level;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 客户等级查询请求参数
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLevelMapByCustomerIdAndStoreIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @NotNull
    @Schema(description = "客户ID")
    private String customerId;

    @NotEmpty
    @Schema(description = "店铺ID")
    private List<Long> storeIds;
}
