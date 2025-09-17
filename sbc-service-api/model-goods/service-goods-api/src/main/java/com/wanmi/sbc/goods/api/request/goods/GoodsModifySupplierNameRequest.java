package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsModifySupplierNameREQUEST
 * 修改商品商家名称
 * @author lipeng
 * @dateTime 2018/11/5 上午10:58
 */
@Schema
@Data
public class GoodsModifySupplierNameRequest extends BaseRequest {

    @Schema(description = "商家名称")
    private String supplierName;

    @Schema(description = "公司Id")
    private Long companyInfoId;
}
