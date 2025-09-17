package com.wanmi.sbc.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditOrderQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CustomerCreditRepayProvider;
import com.wanmi.sbc.account.api.request.credit.CheckCreditRepayRequest;
import com.wanmi.sbc.account.api.request.credit.CreditRepayOverviewPageRequest;
import com.wanmi.sbc.account.api.request.credit.RepayOrderPageRequest;
import com.wanmi.sbc.account.api.response.credit.repay.CreditRepayOverviewPageResponse;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.request.trade.TradeCreditHasRepaidRequest;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 授信还款api
 *
 * @author zhengyang
 * @since 2021/03/11 15:46
 */
@RestController
@Validated
@Tag(name =  "CreditRepayController", description =  "S2B 平台端-授信还款API")
@RequestMapping("/credit/repay")
public class CreditRepayController {

    @Resource
    private CreditRepayQueryProvider creditRepayQueryProvider;

    @Resource
    private CustomerCacheService customerCacheService;

    @Resource
    private CustomerCreditRepayProvider customerCreditRepayProvider;

    @Resource
    private CommonUtil commonUtil;

    @Resource
    private CreditOrderQueryProvider creditOrderQueryProvider;

    @Resource
    private TradeProvider tradeProvider;

    /**
     * 分页查询授信还款概览列表
     *
     * @return
     */
    @PostMapping("/findRepayOrderPage")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @Operation(summary = "分页查询授信还款概览列表")
    @ReturnSensitiveWords(functionName = "f_boss_find_repay_order_page_sign_word")
    public BaseResponse<MicroServicePage<CreditRepayOverviewPageResponse>>
                                        findRepayOrderPage(@RequestBody @Validated CreditRepayOverviewPageRequest request) {
        BaseResponse<MicroServicePage<CreditRepayOverviewPageResponse>> response = creditRepayQueryProvider.findRepayOrderPage(request);
        //获取用户注销状态
        List<String> customerIds = response.getContext().getContent()
                .stream()
                .map(CreditRepayOverviewPageResponse::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        response.getContext().getContent().forEach(v ->v.setLogOutStatus(map.get(v.getCustomerId())));
        return response;
    }

    /**
     * 线下还款授信审核
     *
     * @return
     */
    @PutMapping("/check")
    @Operation(summary = "线下还款授信审核")
    public BaseResponse checkCreditRepay(@RequestBody @Valid CheckCreditRepayRequest request) {
        request.setUserId(commonUtil.getOperatorId());
        customerCreditRepayProvider.checkCreditRepay(request);
        if(Objects.equals(Constants.ZERO,request.getAuditStatus())){
            List<CustomerCreditOrderVO> customerCreditOrderVOList = creditOrderQueryProvider.list(RepayOrderPageRequest
                            .builder()
                            .repayOrderCode(request.getRepayOrderCode())
                            .build())
                    .getContext()
                    .getCustomerCreditOrderVOList();
            if (CollectionUtils.isNotEmpty(customerCreditOrderVOList)){
                customerCreditOrderVOList.forEach(v -> {
                    tradeProvider.updateCreditHasRepaid(TradeCreditHasRepaidRequest
                            .builder()
                            .tid(v.getOrderId())
                            .hasRepaid(Boolean.TRUE)
                            .build());
                });
            }
        }
        return BaseResponse.SUCCESSFUL();
    }
}
