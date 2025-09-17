package com.wanmi.sbc.goodscommission;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.goodscommission.GoodsCommissionPriceConfigProvider;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigStatusUpdateRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigUpdateRequest;
import com.wanmi.sbc.goods.api.response.goodscommission.GoodsCommissionPriceConfigQueryResponse;
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
 * @className GoodsCommissionPriceConfigController
 * @description 代销智能设价加价比例服务
 * @date 2021/9/13 9:42
 **/
@Slf4j
@Tag(name = "GoodsCommissionPriceConfigController", description = "代销智能设价加价比例服务 Api")
@RestController
@Validated
@RequestMapping("/goods/commissionPriceConfig")
public class GoodsCommissionPriceConfigController {

    @Autowired private GoodsCommissionPriceConfigProvider goodsCommissionPriceConfigProvider;

    @Autowired private CommonUtil commonUtil;

    /**
     * @description 代销智能设价加价比例查询
     * @author  wur
     * @date: 2021/9/13 9:47
     * @param queryRequest   查询条件
     * @return  返回查询结果
     **/
    @Operation(summary = "代销智能设价加价比例查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public BaseResponse<GoodsCommissionPriceConfigQueryResponse> query(@RequestBody @Valid GoodsCommissionPriceConfigQueryRequest queryRequest) {
        queryRequest.setUserId(commonUtil.getOperatorId());
        queryRequest.setBaseStoreId(commonUtil.getStoreId());
        return goodsCommissionPriceConfigProvider.query(queryRequest);
    }

    /**
     * @description 代销智能设价加价比例更新
     * @author  wur
     * @date: 2021/9/13 9:47
     * @param updateRequest
     * @return  返回更新状态
     **/
    @MultiSubmit
    @Operation(summary = "代销智能设价加价比例更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BaseResponse update(@Valid @RequestBody GoodsCommissionPriceConfigUpdateRequest updateRequest) {
        updateRequest.setUserId(commonUtil.getOperatorId());
        updateRequest.setBaseStoreId(commonUtil.getStoreId());
        return goodsCommissionPriceConfigProvider.update(updateRequest);
    }

    /**
     * @description 代销智能设价加价比例配置开启状态更新
     * @author  wur
     * @date: 2021/9/13 9:47
     * @param updateRequest
     * @return  返回更新状态
     **/
    @MultiSubmit
    @Operation(summary = "代销智能设价加价比例配置开启状态更新")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public BaseResponse updateStatus(@Valid @RequestBody GoodsCommissionPriceConfigStatusUpdateRequest updateRequest) {
        updateRequest.setUserId(commonUtil.getOperatorId());
        updateRequest.setBaseStoreId(commonUtil.getStoreId());
        return goodsCommissionPriceConfigProvider.updateStatus(updateRequest);
    }

}