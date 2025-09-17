package com.wanmi.sbc.goods.standard.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 商品库导入商品请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class StandardImportRequest extends BaseRequest {

    /**
     * 商品库编号
     */
    @NotNull
    private List<String> goodsIds;

    /**
     * 公司信息编号
     */
    private Long companyInfoId;

    /**
     * 商户类型
     */
    private BoolFlag companyType;

    /**
     * 店铺编号
     */
    private Long storeId;

    /**
     * 商家名称
     */
    private String supplierName;

    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;
}
