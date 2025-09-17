package com.wanmi.sbc.customer.api.request.print;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>根据id查询单个打印设置request</p>
 * Created by daiyitian on 2018-08-13-下午3:47.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintSettingByStoreIdRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

}