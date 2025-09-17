package com.wanmi.sbc.returnorder;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderAddRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.bean.dto.CompanyDTO;
import com.wanmi.sbc.order.bean.dto.ReturnOrderDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.OrderTagVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by sunkun on 2017/8/18.
 */
@Tag(name = "ReturnOrderController", description = "退单API")
@RestController
@Validated
@RequestMapping("/return")
@Slf4j
public class ReturnOrderController {

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    /**
     * 创建退单
     *
     * @param returnOrder
     * @return
     */
    @Operation(summary = "创建退单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse<String> create(@RequestBody @Valid ReturnOrderDTO returnOrder) {
        verifyIsReturnable(returnOrder.getTid());
        returnOrder.setTerminalSource(commonUtil.getTerminal());
        if (!verifyTradeByCustomerId(returnOrder.getTid(), commonUtil.getOperatorId())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050066);
        }

        TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(returnOrder.getTid()).build()).getContext().getTradeVO();
        OrderTagVO orderTag = trade.getOrderTag();
        //是否是虚拟订单或者卡券订单
        boolean isVirtual = Objects.nonNull(orderTag) && (orderTag.getVirtualFlag() || orderTag.getElectronicCouponFlag());
        if (isVirtual) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999998);
        }
        returnOrder.setCompany(CompanyDTO.builder().companyInfoId(trade.getSupplier().getSupplierId())
                .companyCode(trade.getSupplier().getSupplierCode()).supplierName(trade.getSupplier().getSupplierName())
                .storeId(trade.getSupplier().getStoreId()).storeName(trade.getSupplier().getStoreName()).companyType(trade.getSupplier().getIsSelf() ? BoolFlag.NO : BoolFlag.YES).build());

        String rid = returnOrderProvider.add(ReturnOrderAddRequest.builder().returnOrder(returnOrder)
                .operator(commonUtil.getOperator()).build()).getContext().getReturnOrderId();

        return BaseResponse.success(rid);
    }


    /**
     * 是否可创建退单
     *
     * @return
     */
    @Operation(summary = "是否可创建退单")
    @Parameter(name = "tid", description = "订单id",
            required = true)
    @RequestMapping(value = "/returnable/{tid}", method = RequestMethod.GET)
    public BaseResponse isReturnable(@PathVariable String tid) {
        verifyIsReturnable(tid);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验是否可退
     *
     * @param tid
     */
    private void verifyIsReturnable(String tid) {
        TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
        //部分发货或者待确认收货
        if(trade.getTradeState().getFlowState() == FlowState.DELIVERED_PART || trade.getTradeState().getFlowState() == FlowState.DELIVERED) {
            Boolean transitReturn = trade.getTransitReturn();
            if (Boolean.FALSE.equals(transitReturn)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
        } else if (Objects.nonNull(trade.getTradeState().getDeliverStatus()) && (trade.getTradeState().getDeliverStatus() == DeliverStatus.SHIPPED)) {
            TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
            request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
            TradeConfigGetByTypeResponse config = auditQueryProvider.getTradeConfigByType(request).getContext();
            // 是否支持退货
            boolean flag = config.getStatus() == 0;
            //申请退单状态数据库状态优先
            if (Objects.nonNull(trade.getTradeState().getRefundStatus())){
                flag = trade.getTradeState().getRefundStatus() == 0;
            }
            if (flag) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
            JSONObject content = JSON.parseObject(config.getContext());
            Integer day = content.getObject("day", Integer.class);

            if (Objects.isNull(trade.getTradeState().getEndTime())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050012);
            }
            // 是否在可退时间内
            Duration duration = Duration.between(trade.getTradeState().getEndTime(), LocalDateTime.now());
            if (duration.toDays() > day) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
        }
    }


    private boolean verifyTradeByCustomerId(String tid, String customerId) {
        TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
        return trade.getBuyer().getId().equals(customerId);
    }
}
