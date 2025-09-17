package com.wanmi.sbc.goods.api.request.excel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * com.wanmi.sbc.goods.api.request.goodsexcel.GoodsExcelExportTemplateByStoreIdRequest
 * 商家根据店铺编号导出模板请求对象
 * @author lipeng
 * @dateTime 2018/11/2 上午10:00
 */
@Schema
@Data
public class GoodsSupplierExcelExportTemplateIEPByStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = -6468908414646829604L;

    @Schema(description = "店铺Id")
    @NotNull
    private Long storeId;
}
