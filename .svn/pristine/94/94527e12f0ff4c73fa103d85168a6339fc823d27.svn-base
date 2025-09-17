package com.wanmi.sbc.account.provider.impl.funds;

import com.wanmi.sbc.account.api.provider.funds.CustomerFundsDetailQueryProvider;
import com.wanmi.sbc.account.api.request.funds.*;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsDetailExportResponse;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsDetailPageResponse;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsTodayResponse;
import com.wanmi.sbc.account.bean.vo.CustomerFundsDetailVO;
import com.wanmi.sbc.account.funds.model.root.CustomerFundsDetail;
import com.wanmi.sbc.account.funds.service.CustomerFundsDetailService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员资金明细-数据转换层
 *
 * @author: Geek Wang
 * @createDate: 2019/2/19 14:33
 * @version: 1.0
 */
@RestController
@Validated
public class CustomerFundsDetailQueryController implements CustomerFundsDetailQueryProvider {

    @Autowired
    private CustomerFundsDetailService customerFundsDetailService;

    /**
     * 会员资金明细分页查询
     *
     * @param request 分页查询条件 {@link CustomerFundsDetailPageRequest}
     * @return {@link BaseResponse<CustomerFundsDetailPageResponse>}
     */
    @Override
    public BaseResponse<CustomerFundsDetailPageResponse> page(@RequestBody @Valid CustomerFundsDetailPageRequest request) {
        Page<CustomerFundsDetail> page = customerFundsDetailService.page(request);
        List<CustomerFundsDetailVO> customerFundsDetailVOList = getCustomerFundsDetailVOS(page);
        return BaseResponse.success(new CustomerFundsDetailPageResponse(new MicroServicePage<>(customerFundsDetailVOList, request.getPageRequest(), page.getTotalElements())));
    }

    /**
     * 会员资金明细导出查询
     *
     * @param request 导出查询条件 {@link CustomerFundsDetailExportRequest}
     * @return
     */
    @Override
    public BaseResponse<CustomerFundsDetailExportResponse> export(@RequestBody @Valid CustomerFundsDetailExportRequest request) {
        CustomerFundsDetailPageRequest queryReq = new CustomerFundsDetailPageRequest();
        KsBeanUtil.copyPropertiesThird(request, queryReq);

        List<CustomerFundsDetailVO> dataRecords = new ArrayList<>();
        Page<CustomerFundsDetail> page = customerFundsDetailService.page(queryReq);
        List<CustomerFundsDetailVO> data = getCustomerFundsDetailVOS(page);
        dataRecords.addAll(data);
        return BaseResponse.success(new CustomerFundsDetailExportResponse(dataRecords));
    }

    @Override
    public BaseResponse<Long> countForExport(@RequestBody @Valid CustomerFundsDetailExportRequest request) {
        CustomerFundsDetailPageRequest queryReq = new CustomerFundsDetailPageRequest();
        KsBeanUtil.copyPropertiesThird(request, queryReq);
        Long total = customerFundsDetailService.count(queryReq);
        return BaseResponse.success(total);
    }

    /**
     * Page转List
     *
     * @param page
     * @return
     */
    private List<CustomerFundsDetailVO> getCustomerFundsDetailVOS(Page<CustomerFundsDetail> page) {
        return page.getContent().stream().map(customerFundsDetail -> {
            CustomerFundsDetailVO vo = new CustomerFundsDetailVO();
            KsBeanUtil.copyPropertiesThird(customerFundsDetail, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 根据对应条件更新账户明细表数据信息
     *
     * @param customerFundsDetailModifyRequest
     * @return
     */
    @Override
    public BaseResponse modifyCustomerFundsDetail(@RequestBody CustomerFundsDetailModifyRequest customerFundsDetailModifyRequest) {
        customerFundsDetailService.modifyCustomerFundsDetail(customerFundsDetailModifyRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<CustomerFundsTodayResponse> getFundsChange(@RequestBody @Valid CustomerFundsAmountRequest request) {
        //计算收入金额
        request.setTabType(1);
        BigDecimal receiptAmount = customerFundsDetailService.getBalanceChange(request);
        if(receiptAmount == null){
            receiptAmount = BigDecimal.ZERO;
        }

        //计算支出金额
        request.setTabType(2);
        BigDecimal paymentAmount = customerFundsDetailService.getBalanceChange(request);
        if(paymentAmount == null){
            paymentAmount = BigDecimal.ZERO;
        }
        CustomerFundsTodayResponse response = CustomerFundsTodayResponse.builder()
                .receiptAmount(receiptAmount)
                .paymentAmount(paymentAmount).build();

        return BaseResponse.success(response);
    }
}
