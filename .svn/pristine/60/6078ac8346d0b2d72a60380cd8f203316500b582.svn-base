package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Range;

/**
 * <p>查询账期内有效店铺request</p>
 * Created by of628-wenzhi on 2018-09-12-下午7:05.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListStoreForSettleRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "账期")
    @NotNull
    @Range(max = 31, min = 1)
    private Integer targetDay;

    @Schema(description = "商家类型")
    private StoreType storeType;
}
