package com.wanmi.sbc.todo;

import com.wanmi.sbc.account.api.provider.finance.record.SettlementQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.SettlementCountRequest;
import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerCountByStateRequest;
import com.wanmi.sbc.customer.api.request.customer.InvoiceCountByStateRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByIdsRequest;
import com.wanmi.sbc.customer.api.response.CustomerTodoResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeListByIdsResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsUnAuditCountRequest;
import com.wanmi.sbc.order.api.provider.orderinvoice.OrderInvoiceQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.orderinvoice.OrderInvoiceCountByStateRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnCountByConditionRequest;
import com.wanmi.sbc.order.api.request.trade.TradeCountCriteriaRequest;
import com.wanmi.sbc.order.api.response.trade.TradeCountByFlowStateResponse;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingIdsRequest;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingIdsResponse;
import com.wanmi.sbc.todo.response.ReturnOrderTodoReponse;
import com.wanmi.sbc.todo.response.SupplierTodoResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Objects;

/**
 * Created by sunkun on 2017/9/16.
 */
@Tag(name = "TodoController", description = "待办事项服务 Api")
@RestController
@Validated
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private OrderInvoiceQueryProvider orderInvoiceQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private SettlementQueryProvider settlementQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private PickupSettingQueryProvider pickupSettingQueryProvider;

    /**
     * 订单todo
     *
     * @return
     */
    @Operation(summary = "订单todo")
    @RequestMapping(value = "trade", method = RequestMethod.GET)
    public BaseResponse<TradeCountByFlowStateResponse> TardeTodo() {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        TradeCountByFlowStateResponse tradeTodoReponse = new TradeCountByFlowStateResponse();
        TradeQueryDTO queryRequest = new TradeQueryDTO();
        if (companyInfoId != null) {
            queryRequest.setSupplierId(companyInfoId);
        }

        //判断当前登录人是否为自提员工
        PickupSettingIdsResponse response = pickupSettingQueryProvider
                .getPickupIdsByEmployeeId(PickupSettingIdsRequest.builder()
                        .employeeId(commonUtil.getOperator().getUserId())
                        .storeId(commonUtil.getStoreId()).build()).getContext();
        if (CollectionUtils.isNotEmpty(response.getPickupIds())){
            //自提员工
            queryRequest.setPickupIds(response.getPickupIds());
        }

        //判断当前登录人是否为业务员（不含主账号）
        BaseResponse<EmployeeListByIdsResponse> employeeResponse = employeeQueryProvider
                .listByIds(EmployeeListByIdsRequest.builder().employeeIds(Collections.singletonList(commonUtil.getOperatorId())).build());
        if (CollectionUtils.isNotEmpty(employeeResponse.getContext().getEmployeeList())
                //是否业务员 0：是   1：否
                && employeeResponse.getContext().getEmployeeList().get(0).getIsEmployee().equals(0)
                //是否主账号 0：否   1：是
                && employeeResponse.getContext().getEmployeeList().get(0).getIsMasterAccount().equals(0)){
            queryRequest.setEmployeeId(employeeResponse.getContext().getEmployeeList().get(0).getEmployeeId());
        }

        TradeStateDTO tradeState = new TradeStateDTO();
        //设置待审核状态
        tradeState.setFlowState(FlowState.INIT);
        queryRequest.setTradeState(tradeState);
        if(Objects.nonNull(commonUtil.getStoreType())){
            queryRequest.setStoreType(commonUtil.getStoreType());
        }

        tradeTodoReponse.setWaitAudit(tradeQueryProvider.countCriteria(TradeCountCriteriaRequest.builder()
                .tradePageDTO(queryRequest).build()).getContext().getCount());

        //设置待发货状态
        // 都未发货
        tradeState.setFlowState(FlowState.AUDIT);
        queryRequest.setTradeState(tradeState);
        Long noDeliveredCount = tradeQueryProvider.countCriteria(TradeCountCriteriaRequest.builder()
                .tradePageDTO(queryRequest).build()).getContext().getCount();
        // 部分发货
        tradeState.setFlowState(FlowState.DELIVERED_PART);
        queryRequest.setTradeState(tradeState);
        Long deliveredPartCount = tradeQueryProvider.countCriteria(TradeCountCriteriaRequest.builder()
                .tradePageDTO(queryRequest).build()).getContext().getCount();
        tradeTodoReponse.setWaitDeliver(noDeliveredCount + deliveredPartCount);

        //设置待付款订单
        tradeState.setFlowState(null);
        tradeState.setPayState(PayState.NOT_PAID);
        queryRequest.setTradeState(tradeState);
        tradeTodoReponse.setWaitPay(tradeQueryProvider.countCriteria(TradeCountCriteriaRequest.builder()
                .tradePageDTO(queryRequest).build()).getContext().getCount());

        //设置待收货订单
        tradeState.setPayState(null);
        tradeState.setFlowState(FlowState.DELIVERED);
        queryRequest.setTradeState(tradeState);
        tradeTodoReponse.setWaitReceiving(tradeQueryProvider.countCriteria(TradeCountCriteriaRequest.builder()
                .tradePageDTO(queryRequest).build()).getContext().getCount());
        return BaseResponse.success(tradeTodoReponse);
    }

    /**
     * 退单todo
     *
     * @return
     */
    @Operation(summary = "退单todo")
    @RequestMapping(value = "return", method = RequestMethod.GET)
    public BaseResponse<ReturnOrderTodoReponse> returnOrderTodo() {
        ReturnOrderTodoReponse returnOrderTodoReponse = new ReturnOrderTodoReponse();
        ReturnCountByConditionRequest returnQueryRequest = new ReturnCountByConditionRequest();
        returnQueryRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        returnQueryRequest.setStoreId(commonUtil.getStoreId());
        returnQueryRequest.setReturnFlowState(ReturnFlowState.INIT);
        if(Objects.nonNull(commonUtil.getStoreType())){
            returnQueryRequest.setStoreType(commonUtil.getStoreType());
        }
        //判断当前登录人是否为业务员(不含主账号)
        BaseResponse<EmployeeListByIdsResponse> employeeResponse = employeeQueryProvider
                .listByIds(EmployeeListByIdsRequest.builder().employeeIds(Collections.singletonList(commonUtil.getOperatorId())).build());
        if (CollectionUtils.isNotEmpty(employeeResponse.getContext().getEmployeeList())
                //是否业务员 0：是   1：否
                && employeeResponse.getContext().getEmployeeList().get(0).getIsEmployee().equals(0)
                //是否主账号 0：否   1：是
                && employeeResponse.getContext().getEmployeeList().get(0).getIsMasterAccount().equals(0)){
            returnQueryRequest.setEmployeeId(employeeResponse.getContext().getEmployeeList().get(0).getEmployeeId());
        }
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
     * B2B用户todo
     *
     * @return
     */
    @Operation(summary = "B2B用户todo")
    @RequestMapping(value = "customer", method = RequestMethod.GET)
    public BaseResponse<CustomerTodoResponse> customerTodo() {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        Long storeId = commonUtil.getStoreId();
        CustomerTodoResponse customerTodoResponse = new CustomerTodoResponse();
        customerTodoResponse.setWaitAuditCustomer(customerQueryProvider.countCustomerByState(new
                CustomerCountByStateRequest(CheckState.WAIT_CHECK, DeleteFlag.NO)).getContext().getCount());
        customerTodoResponse.setWaitAuditCustomerInvoice(customerQueryProvider.countInvoiceByState(new
                InvoiceCountByStateRequest(CheckState.WAIT_CHECK)).getContext().getCount());

        OrderInvoiceCountByStateRequest countByStateRequest =
                OrderInvoiceCountByStateRequest.builder().invoiceState(InvoiceState.WAIT)
                .companyInfoId(companyInfoId).storeId(storeId).build();


        customerTodoResponse.setWaitAuditOrderInvoice(orderInvoiceQueryProvider.countByState(countByStateRequest).getContext().getCount());
        return BaseResponse.success(customerTodoResponse);
    }

    /**
     * S2B商家todo
     *
     * @return
     */
    @Operation(summary = "S2B商家todo")
    @RequestMapping(value = "goods", method = RequestMethod.GET)
    public BaseResponse<SupplierTodoResponse> goodsTodo() {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        Long storeId = commonUtil.getStoreId();
        SupplierTodoResponse customerTodoResponse = new SupplierTodoResponse();

        OrderInvoiceCountByStateRequest countByStateRequest =
                OrderInvoiceCountByStateRequest.builder().invoiceState(InvoiceState.WAIT)
                .companyInfoId(companyInfoId).storeId(storeId).build();


        customerTodoResponse.setWaitInvoice(orderInvoiceQueryProvider.countByState(countByStateRequest).getContext().getCount());
        //待审核商品
        if (auditQueryProvider.isBossGoodsAudit().getContext().isAudit() || auditQueryProvider.isSupplierGoodsAudit().getContext().isAudit()) {
            customerTodoResponse.setCheckGoodsFlag(true);
            customerTodoResponse.setWaitGoods(goodsQueryProvider.countUnAudit(GoodsUnAuditCountRequest.builder().companyInfoId(companyInfoId).storeId
                    (storeId).build()).getContext().getUnAuditCount());
        }
        //查询未结算
        SettlementCountRequest request = new SettlementCountRequest();
        request.setStoreId(storeId);
        request.setSettleStatus(SettleStatus.NOT_SETTLED);
        customerTodoResponse.setWaitSettle(settlementQueryProvider.count(request).getContext().getCount());

        return BaseResponse.success(customerTodoResponse);
    }
}
