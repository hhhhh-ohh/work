package com.wanmi.sbc.goodscommission;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.provider.goodscommission.GoodsCommissionConfigProvider;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionConfigUpdateRequest;
import com.wanmi.sbc.goods.api.response.goodscommission.GoodsCommissionConfigQueryResponse;
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
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author wur
 * @className GoodsCommissionConfigController
 * @description 代销设置服务
 * @date 2021/9/13 9:42
 **/
@Slf4j
@Tag(name = "GoodsCommissionConfigController", description = "代销设置服务 Api")
@RestController
@Validated
@RequestMapping("/goods/commissionConfig")
public class GoodsCommissionConfigController {

    @Autowired private GoodsCommissionConfigProvider goodsCommissionConfigProvider;

    @Autowired private CommonUtil commonUtil;

    /**
     * @description 商品代销配置
     * @author  wur
     * @date: 2021/9/13 9:47
     * @param queryRequest   查询条件
     * @return  返回查询结果
     **/
    @Operation(summary = "商品代销配置")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public BaseResponse<GoodsCommissionConfigQueryResponse> query(@RequestBody @Valid GoodsCommissionConfigQueryRequest queryRequest) {
        queryRequest.setUserId(commonUtil.getOperatorId());
        queryRequest.setBaseStoreId(commonUtil.getStoreId());
        return goodsCommissionConfigProvider.query(queryRequest);
    }

    /**
     * @description 更新商品代销配置
     * @author  wur
     * @date: 2021/9/13 9:47
     * @param updateRequest
     * @return  返回更新状态
     **/
    @MultiSubmit
    @Operation(summary = "更新商品代销配置")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BaseResponse update(@RequestBody @Valid GoodsCommissionConfigUpdateRequest updateRequest) {
        // 如果是智能设价验证加价比例
        if (Objects.isNull(updateRequest.getSynPriceType()) || Objects.isNull(updateRequest.getInfoSynFlag())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        updateRequest.setUserId(commonUtil.getOperatorId());
        updateRequest.setBaseStoreId(commonUtil.getStoreId());
        return goodsCommissionConfigProvider.update(updateRequest);
    }

    /**
     * @description 更新商品代销配置
     * @author  wur
     * @date: 2021/9/13 9:47
     * @param updateRequest
     * @return  返回更新状态
     **/
    @MultiSubmit
    @Operation(summary = "修改默认加价比例")
    @RequestMapping(value = "/updateAddRate", method = RequestMethod.POST)
    public BaseResponse updateAddRate(@RequestBody @Valid GoodsCommissionConfigUpdateRequest updateRequest) {
        // 如果是智能设价验证加价比例
        if (Objects.isNull(updateRequest.getAddRate())
                || updateRequest.getAddRate().compareTo(BigDecimal.ZERO) < 0 || updateRequest.getAddRate().compareTo(new BigDecimal(300)) > 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        updateRequest.setUserId(commonUtil.getOperatorId());
        updateRequest.setBaseStoreId(commonUtil.getStoreId());
        return goodsCommissionConfigProvider.updateAddRate(updateRequest);
    }
}