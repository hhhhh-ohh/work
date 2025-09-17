package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>根据店铺名称查询未删除店铺request</p>
 * Created by of628-wenzhi on 2018-09-12-下午7:27.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListStoreByNameRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 店铺名称关键字
     */
    @Schema(description = "店铺名称关键字")
    private String storeName;

    @Schema(description = "店铺类型", contentSchema = StoreType.class)
    private StoreType storeType;

    /**
     * 商家名称关键字
     */
    @Schema(description = "商家名称关键字")
    private String supplierName;
}
