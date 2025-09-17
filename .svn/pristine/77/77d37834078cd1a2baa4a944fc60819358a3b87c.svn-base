package com.wanmi.sbc.goods.api.provider.suppliercommissiongoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.suppliercommissiongoods.SupplierCommissionGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.suppliercommissiongoods.SupplierCommissionGoodsSynRequest;
import com.wanmi.sbc.goods.api.response.suppliercommissiongoods.SupplierCommissionGoodsPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: wur
 * @Date: 2021/9/14 19:22
 */
@FeignClient(value = "${application.goods.name}", contextId = "SupplierCommissionGoodsProvider")
public interface SupplierCommissionGoodsProvider {

    /**
     * @description  代销商品变更记录
     * @author  wur
     * @date: 2021/9/14 19:41
     * @param queryRequest
     * @return
     **/
    @PostMapping("/goods/${application.goods.version}/supplierCommissionGoods/page")
    BaseResponse<SupplierCommissionGoodsPageResponse> page(@RequestBody @Valid SupplierCommissionGoodsQueryRequest queryRequest);

    /**
     * @description  商家同步供应商商品信息
     * @author  wur
     * @date: 2021/9/16 15:40
     * @param synRequest
     * @return
     **/
    @PostMapping("/goods/${application.goods.version}/supplierCommissionGoods/synGoodsInfo")
    BaseResponse synGoodsInfo(@RequestBody @Valid SupplierCommissionGoodsSynRequest synRequest);

}
