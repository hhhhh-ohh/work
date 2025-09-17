package com.wanmi.sbc.account;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.request.refund.RefundOrderResponseByReturnOrderCodeRequest;
import com.wanmi.sbc.order.api.response.refund.RefundOrderListReponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderResponse;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 退款单
 * Created by zhangjin on 2017/4/21.
 */
@RestController
@Validated
@RequestMapping("/account")
@Tag(name = "RefundOrderController", description = "S2B web公用-退款单管理API")
public class RefundOrderController {

    @Autowired
    private RefundOrderQueryProvider refundOrderQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 根据退单号查询
     *
     * @return BaseResponse<RefundBill>
     */
    @Operation(summary = "根据退单编号查询退款单")
    @Parameter(name = "returnOrderNo", description = "returnOrderNo", required = true)
    @RequestMapping(value = "/refundOrders/{returnOrderNo}", method = RequestMethod.GET)
    public BaseResponse<RefundOrderListReponse> queryRefundByReturnOrderNo(@PathVariable("returnOrderNo") String returnOrderNo) {
        String customerId = commonUtil.getOperatorId();
        if (StringUtils.isBlank(customerId)) {
            return BaseResponse.success(new RefundOrderListReponse(Collections.emptyList()));
        }
        List<RefundOrderResponse> response = refundOrderQueryProvider.getRefundOrderRespByReturnOrderCode(new RefundOrderResponseByReturnOrderCodeRequest(returnOrderNo)).getContext().getRefundOrderResponseList();
        if (CollectionUtils.isNotEmpty(response) && response.stream().anyMatch(s -> !customerId.equals(s.getCustomerId()))) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
        return BaseResponse.success(new RefundOrderListReponse(response));
    }
}
