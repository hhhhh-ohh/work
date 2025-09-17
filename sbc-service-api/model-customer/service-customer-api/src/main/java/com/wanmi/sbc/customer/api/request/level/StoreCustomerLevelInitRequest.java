package com.wanmi.sbc.customer.api.request.level;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 店铺等级初始化请求参数
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreCustomerLevelInitRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    @NotNull
    private Long storeId;

}
