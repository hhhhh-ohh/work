package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>B2B场景下，根据商家初始化店铺信息request</p>
 * Created by of628-wenzhi on 2018-09-12-下午2:46.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitStoreByCompanyRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    @NotNull
    private Long companyInfoId;
}
