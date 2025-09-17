package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>根据商户名称查询未删除店铺request</p>
 * Created by of628-wenzhi on 2018-09-12-下午7:27.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListStoreBySupplierNameRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 商户名称关键字
     */
    @Schema(description = "商户名称关键字")
    private String supplierName;

    @Schema(description = "店铺类型")
    private StoreType storeType;
}
