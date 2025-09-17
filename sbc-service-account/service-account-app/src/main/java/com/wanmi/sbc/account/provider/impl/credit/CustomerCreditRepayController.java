package com.wanmi.sbc.account.provider.impl.credit;

import com.wanmi.sbc.account.api.provider.credit.CustomerCreditRepayProvider;
import com.wanmi.sbc.account.api.request.credit.*;
import com.wanmi.sbc.account.api.response.credit.CustomerCreditRepayAddResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayModifyResponse;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRepay;
import com.wanmi.sbc.account.credit.service.CreditRepayQueryService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>授信订单信息服务接口实现</p>
 *
 * @author chenli
 * @date 2021-03-04 16:21:28
 */
@RestController
@Validated
public class CustomerCreditRepayController implements CustomerCreditRepayProvider {

    @Autowired
    private CreditRepayQueryService customerCreditRepayService;

    @Override
    public BaseResponse<CustomerCreditRepayAddResponse> add(@RequestBody @Valid CustomerCreditRepayAddRequest request) {
        return BaseResponse.success(new CustomerCreditRepayAddResponse(
                customerCreditRepayService.wrapperVo(customerCreditRepayService.addCreditRepay(request))));
    }

    @Override
    public BaseResponse cancel(@RequestBody @Valid CustomerCreditRepayCancelRequest request) {
        List<CustomerCreditRepay> list = customerCreditRepayService.list(CustomerCreditRepayQueryRequest.builder()
                .customerId(request.getUserId())
                .repayStatus(CreditRepayStatus.WAIT)
                .build());
        list.forEach(customerCreditRepay -> {
            customerCreditRepay.setRepayStatus(CreditRepayStatus.VOID);
            customerCreditRepay.setUpdatePerson(request.getUserId());
            customerCreditRepay.setUpdateTime(LocalDateTime.now());
            customerCreditRepayService.modify(customerCreditRepay);
        });
        return BaseResponse.SUCCESSFUL();
    }

	@Override
	public BaseResponse<CustomerCreditRepayModifyResponse> modify(@RequestBody @Valid CustomerCreditRepayModifyRequest customerCreditRepayModifyRequest) {
		CustomerCreditRepay customerCreditRepay = KsBeanUtil.convert(customerCreditRepayModifyRequest, CustomerCreditRepay.class);
		return BaseResponse.success(new CustomerCreditRepayModifyResponse(
				customerCreditRepayService.wrapperVo(customerCreditRepayService.modify(customerCreditRepay))));
	}


	@Override
	public BaseResponse<CustomerCreditRepayModifyResponse> modifyByPaySuccess(@RequestBody @Valid CustomerCreditRepayModifyRequest request) {
		return BaseResponse.success(new CustomerCreditRepayModifyResponse(
				customerCreditRepayService.wrapperVo(customerCreditRepayService.modifyByPaySuccess(request))));
	}

    @Override
    public BaseResponse checkCreditRepay(CheckCreditRepayRequest request) {
        customerCreditRepayService.checkCreditRepay(request);
        return BaseResponse.SUCCESSFUL();
    }
}

