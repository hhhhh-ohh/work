package com.wanmi.sbc.goods.api.request.suppliercommissiongoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author wur
 * @className SupplierCommissionGoodsSynRequest
 * @description
 * @date 2021/9/14 11:02
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCommissionGoodsSynRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 商品Id
     */
    @Schema(description = "商品Id")
    private List<String> idList;

}