package com.wanmi.sbc.goods.standard.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.StandardSkuDTO;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 商品库Sku编辑请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class StandardSkuSaveRequest extends BaseRequest {

    /**
     * 商品SKU信息
     */
    @NotNull
    private StandardSkuDTO goodsInfo;

}
