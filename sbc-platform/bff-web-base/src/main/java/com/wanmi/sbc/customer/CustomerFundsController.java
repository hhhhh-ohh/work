package com.wanmi.sbc.customer;

import com.wanmi.sbc.account.api.provider.funds.CustomerFundsDetailQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsDetailPageRequest;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsStatisticsResponse;
import com.wanmi.sbc.account.bean.enums.FundsStatus;
import com.wanmi.sbc.account.bean.vo.CustomerFundsDetailVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.service.CustomerAccountBaseService;
import com.wanmi.sbc.util.CommonUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * 会员资金页面控制类
 *
 * @author chenyufei
 */
@RestController("CustomerFundsController")
@Validated
@RequestMapping("/customer/funds")
@Tag(name = "CustomerFundsController", description = "S2B 会员端-会员资金API")
public class CustomerFundsController {

    @Autowired
    private CustomerFundsDetailQueryProvider customerFundsDetailQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerAccountBaseService customerAccountBaseService;

    /**
     * 获取会员资金统计（会员余额总额、冻结余额总额、可提现余额总额）
     *
     * @return
     */
    @Operation(summary = "S2B 会员端-获取会员资金统计（会员余额总额、冻结余额总额、可提现余额总额）")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public BaseResponse<CustomerFundsStatisticsResponse> statistics() {
        return customerAccountBaseService.getCustomerFundsStatisticsResponseBaseResponse();
    }

    /**
     * 获取余额明细分页列表
     *
     * @param request
     * @return
     */
    @Operation(summary = "S2B web公用-余额账单明细分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<CustomerFundsDetailVO>> page(@RequestBody CustomerFundsDetailPageRequest request) {
        String customerId = commonUtil.getOperatorId();
        request.setCustomerId(customerId);
        request.setFundsStatus(FundsStatus.YES);
        return BaseResponse.success(customerFundsDetailQueryProvider.page(request).getContext().getMicroServicePage());
    }


}
