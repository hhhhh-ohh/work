package com.wanmi.sbc.ares;

import com.wanmi.ares.provider.TradeServiceProvider;
import com.wanmi.ares.request.flow.FlowRequest;
import com.wanmi.ares.view.trade.TradePageView;
import com.wanmi.ares.view.trade.TradeView;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sunkun on 2017/10/16.
 */
@Tag(name = "TradeReportController", description = "订单报表")
@RestController
@Validated
@Slf4j
@RequestMapping("/tradeReport")
public class TradeReportController {

    @Autowired
    private TradeServiceProvider tradeServiceProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "订单列表")
    @PostMapping("/list")
    public BaseResponse<List<TradeView>> list(@RequestBody FlowRequest request) {
        try {
            request.populateRequestCompanyId(commonUtil.getCompanyInfoId());
            List<TradeView> list = tradeServiceProvider.getTradeList(request);
            return BaseResponse.success(list);
        } catch (Exception ex) {
            log.error("查询订单列表异常,", ex);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"查询订单列表异常");
        }
    }

    @Operation(summary = "订单列表分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<TradePageView> page(@RequestBody FlowRequest request) {
        try {
            request.populateRequestCompanyId(commonUtil.getCompanyInfoId());
            TradePageView tradePageView = tradeServiceProvider.getTradePage(request);
            return BaseResponse.success(tradePageView);
        } catch (Exception ex) {
            log.error("查询订单列表分页异常,", ex);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"查询订单列表分页异常");
        }
    }

    @Operation(summary = "店铺订单列表分类")
    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public BaseResponse<TradePageView> storePage(@RequestBody FlowRequest request) {
        try {
            TradePageView tradePageView = tradeServiceProvider.getStoreTradePage(request);
            return BaseResponse.success(tradePageView);
        } catch (Exception ex) {
            log.error("店铺订单列表分类,", ex);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"店铺订单列表分类");
        }
    }

    @Operation(summary = "订单列表概览")
    @RequestMapping(value = "overview", method = RequestMethod.POST)
    public BaseResponse<TradeView> getOverview(@RequestBody FlowRequest request) {
        try {
            request.populateRequestCompanyId(commonUtil.getCompanyInfoId());
            TradeView tradeView = tradeServiceProvider.getOverview(request);
            return BaseResponse.success(tradeView);
        } catch (Exception ex) {
            log.error("订单列表概览,", ex);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"订单列表概览");
        }
    }
}
