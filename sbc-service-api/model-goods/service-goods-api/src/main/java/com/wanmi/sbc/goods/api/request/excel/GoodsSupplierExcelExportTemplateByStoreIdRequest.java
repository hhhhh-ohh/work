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
public class GoodsSupplierExcelExportTemplateByStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = 4832634860033968684L;

    @Schema(description = "店铺Id")
    @NotNull
    private Long storeId;

    @Schema(description = "商品类型")
    @NotNull
    private Integer goodsType;
}
