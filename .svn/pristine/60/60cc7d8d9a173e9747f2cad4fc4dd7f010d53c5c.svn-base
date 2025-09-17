package com.wanmi.sbc.ares;

import com.wanmi.ares.provider.FlowServerProvider;
import com.wanmi.ares.request.flow.FlowRequest;
import com.wanmi.ares.view.flow.FlowPageView;
import com.wanmi.ares.view.flow.FlowReportView;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

/**
 * Created by sunkun on 2017/10/16.
 */
@Tag(name = "FlowController", description = "流量统计 Api")
@Slf4j
@RestController
@Validated
@RequestMapping("/flow")
public class FlowController {

    @Autowired
    private FlowServerProvider flowServerProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "流量统计列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResponse<FlowReportView> list(@RequestBody FlowRequest request) {
        try {
            request.populateRequestCompanyId(commonUtil.getCompanyInfoId());
            FlowReportView flowReportView = flowServerProvider.getFlowList(request);
            return BaseResponse.success(flowReportView);
        } catch (Exception ex) {
            log.error("流量统计列表异常,", ex);
            return BaseResponse.FAILED();
        }

    }

    @Operation(summary = "流量统计列表分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<FlowPageView> page(@RequestBody FlowRequest request) {
        try {
            request.populateRequestCompanyId(commonUtil.getCompanyInfoId());
            FlowPageView flowPageView = flowServerProvider.getFlowPage(request);
            return BaseResponse.success(flowPageView);
        } catch (Exception ex) {
            log.error("流量统计列表分页异常,", ex);
            return BaseResponse.FAILED();
        }
    }

    @Operation(summary = "店铺流量统计")
    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public BaseResponse<FlowPageView> storePage(@RequestBody FlowRequest request) {
        try {
            FlowPageView flowPageView = flowServerProvider.getStoreList(request);
            return BaseResponse.success(flowPageView);
        } catch (Exception ex) {
            log.error("店铺流量统计,", ex);
            return BaseResponse.FAILED();
        }
    }
}
