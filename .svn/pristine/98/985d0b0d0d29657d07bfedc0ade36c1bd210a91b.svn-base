package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.goods.api.provider.suppliercommissiongoods.SupplierCommissionGoodsProvider;
import com.wanmi.sbc.goods.api.request.suppliercommissiongoods.SupplierCommissionGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.suppliercommissiongoods.SupplierCommissionGoodsSynRequest;
import com.wanmi.sbc.goods.api.response.suppliercommissiongoods.SupplierCommissionGoodsPageResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className ProviderCommissionGoodsController
 * @description 代销智能设价加价比例服务
 * @date 2021/9/13 9:42
 **/
@Slf4j
@Tag(name = "ProviderCommissionGoodsController", description = "代销商品服务 Api")
@RestController
@Validated
@RequestMapping("/goods/providerCommissionGoods")
public class ProviderCommissionGoodsController {

    @Autowired private SupplierCommissionGoodsProvider supplierCommissionGoodsProvider;

    @Autowired private CommonUtil commonUtil;

    @Operation(summary = "代销商品分页查询")
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public BaseResponse<SupplierCommissionGoodsPageResponse> queryPage(@RequestBody @Valid SupplierCommissionGoodsQueryRequest queryRequest) {
        queryRequest.setUserId(commonUtil.getOperatorId());
        queryRequest.setBaseStoreId(commonUtil.getStoreId());
        queryRequest.setUpdateFlag(DefaultFlag.YES);
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setSortColumn("updateTime");
        queryRequest.setSortRole(SortType.DESC.toValue());
        return supplierCommissionGoodsProvider.page(queryRequest);
    }

    @Operation(summary = "同步商品信息")
    @RequestMapping(value = "/synGoodsInfo", method = RequestMethod.POST)
    public BaseResponse<SupplierCommissionGoodsPageResponse> synGoodsInfo(@RequestBody @Valid SupplierCommissionGoodsSynRequest synRequest) {
        synRequest.setUserId(commonUtil.getOperatorId());
        synRequest.setBaseStoreId(commonUtil.getStoreId());
        return supplierCommissionGoodsProvider.synGoodsInfo(synRequest);
    }

}