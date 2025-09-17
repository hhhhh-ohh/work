package com.wanmi.sbc.returnorder.request;

import com.wanmi.sbc.order.bean.vo.CrossGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 导出退单如餐
 * Created by jinwei on 6/5/2017.
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class ReturnExportRequest extends ReturnQueryRequest {

    // jwt token
    @Schema(description = "jwt token")
    private String token;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

    /**
     * 供应商编码
     */
    @Schema(description = "供应商编码")
    private String providerCode;

    @Schema(description = "跨境商品信息")
    private CrossGoodsInfoVO crossGoodsInfo;

    @Schema(description = "是否O2O")
    private Boolean isO2O;

}
