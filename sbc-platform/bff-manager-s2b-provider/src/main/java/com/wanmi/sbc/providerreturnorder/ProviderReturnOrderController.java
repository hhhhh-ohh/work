package com.wanmi.sbc.providerreturnorder;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.request.refund.RefundOrderByReturnOrderNoRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnCountByConditionRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderPageRequest;
import com.wanmi.sbc.order.api.response.refund.RefundOrderByReturnOrderNoResponse;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.returnorder.request.ReturnExportRequest;
import com.wanmi.sbc.todo.response.ReturnOrderTodoReponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @Description: 供应商退单处理
 * @Autho qiaokang
 * @Date：2020-03-29 14:39
 */
@Tag(name = "ProviderReturnOrderController", description = "退货服务API")
@RestController
@Validated
@RequestMapping("/providerReturn")
@Slf4j
public class ProviderReturnOrderController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private RefundOrderQueryProvider refundOrderQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    /**
     * 退单统计
     * @return
     */
    @Operation(summary = "退单todo")
    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public BaseResponse<ReturnOrderTodoReponse> returnOrderTodo() {
        ReturnOrderTodoReponse returnOrderTodoReponse = new ReturnOrderTodoReponse();
        ReturnCountByConditionRequest returnQueryRequest = new ReturnCountByConditionRequest();
        returnQueryRequest.setProviderCompanyInfoId(commonUtil.getCompanyInfoId());
        returnQueryRequest.setReturnFlowState(ReturnFlowState.INIT);
        returnOrderTodoReponse.setWaitAudit(returnOrderQueryProvider.countByCondition(returnQueryRequest).getContext().getCount());
        returnQueryRequest.setReturnFlowState(ReturnFlowState.AUDIT);
        returnOrderTodoReponse.setWaitFillLogistics(returnOrderQueryProvider.countByCondition(returnQueryRequest).getContext().getCount());
        returnQueryRequest.setReturnFlowState(ReturnFlowState.DELIVERED);
        returnOrderTodoReponse.setWaitReceiving(returnOrderQueryProvider.countByCondition(returnQueryRequest).getContext().getCount());
        returnQueryRequest.setReturnFlowState(ReturnFlowState.RECEIVED);
        returnOrderTodoReponse.setWaitRefund(returnOrderQueryProvider.countByCondition(returnQueryRequest).getContext().getCount());
        return BaseResponse.success(returnOrderTodoReponse);
    }

    /**
     * 供应商分页查询退单信息
     * @param request
     * @return
     */
    @Operation(summary = "供应商分页查询退单信息")
    @EmployeeCheck
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<ReturnOrderVO>> page(@RequestBody ReturnOrderPageRequest request) {
        request.setProviderCompanyInfoId(commonUtil.getCompanyInfoId());
        MicroServicePage<ReturnOrderVO> page = returnOrderQueryProvider.page(request).getContext().getReturnOrderPage();
        page.getContent().forEach(returnOrder -> {
            RefundOrderByReturnOrderNoResponse refundOrderByReturnCodeResponse = refundOrderQueryProvider.getByReturnOrderNo(new RefundOrderByReturnOrderNoRequest(returnOrder.getId())).getContext();
            if (Objects.nonNull(refundOrderByReturnCodeResponse)) {
                returnOrder.setRefundStatus(refundOrderByReturnCodeResponse.getRefundStatus());
            }
        });
        return BaseResponse.success(page);
    }

    /**
     * 导出退单
     *
     * @param encrypted
     */
    @EmployeeCheck
    @Operation(summary = "导出退单")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportByParams(@PathVariable String encrypted, ReturnOrderPageRequest request) {
        String decrypted = new String(Base64.getUrlDecoder()
                .decode(encrypted.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
        ReturnExportRequest returnExportRequest = JSON.parseObject(decrypted, ReturnExportRequest.class);
        returnExportRequest.setEmployeeIds(request.getEmployeeIds());

        Operator operator = commonUtil.getOperator();
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(JSONObject.toJSONString(returnExportRequest));
        exportDataRequest.setTypeCd(ReportType.BUSINESS_RETURN_ORDER);
        exportDataRequest.setBuyAnyThirdChannelOrNot(commonUtil.buyAnyThirdChannelOrNot());
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }


}
