package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>查询商家店铺和主账号信息request</p>
 * Created by of628-wenzhi on 2018-09-12-下午3:09.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInfoByIdRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
