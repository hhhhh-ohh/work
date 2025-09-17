package com.wanmi.sbc.customercredit;

import com.wanmi.sbc.account.api.provider.credit.CreditAccountQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAccountDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditRecoverDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditRecoverPageRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRecoverPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.util.CommonUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author chenli
 * @ClassName CustomerCreditAccountBaseController
 * @Description 客户授信账户bff
 * @date 2021/3/3 13:59
 */
@Tag(name =  "客户授信账户API", description =  "CustomerCreditAccountBaseController")
@RestController
@Validated
@RequestMapping(value = "/credit/account")
public class CustomerCreditAccountBaseController {

    @Autowired
    private CreditAccountQueryProvider creditAccountQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Operation(summary = "我的授信")
    @GetMapping("/detail")
    public BaseResponse<CreditAccountDetailResponse> getCreditAccountDetail() {
        String operatorId = commonUtil.getOperatorId();
        CreditAccountDetailRequest request = CreditAccountDetailRequest.builder()
                .customerId(operatorId)
                .build();
        //获取授信名称
        BaseResponse<String> creditNameResp = paySettingQueryProvider.getCreditName();

        BaseResponse<CreditAccountDetailResponse> creditAccountDetail = creditAccountQueryProvider.getCreditAccountDetail(request);

        CreditAccountDetailResponse detailResponse = creditAccountDetail.getContext();
        detailResponse.setAlias(creditNameResp.getContext());
        return BaseResponse.success(detailResponse);
    }


    /**
     * 分页查询额度恢复记录
     *
     * @return
     */
    @PostMapping("/history-recover-list")
    @Operation(summary = "分页查询额度恢复记录")
    public BaseResponse<MicroServicePage<CreditRecoverPageResponse>> findCreditRecoverPage(@RequestBody CreditRecoverPageRequest request) {
        //排序
        String operatorId = commonUtil.getOperatorId();
        request.setCustomerId(operatorId);
        return creditAccountQueryProvider.findCreditRecoverPage(request);
    }

    /**
     * 分页查询额度恢复记录
     *
     * @return
     */
    @GetMapping("/history-recover-detail/{id}")
    @Operation(summary = "额度恢复记录详情")
    public BaseResponse<CreditRecoverPageResponse> getCreditRecoverDetail(@PathVariable("id") String id) {
        CreditRecoverDetailRequest request = CreditRecoverDetailRequest.builder()
                .id(id)
                .build();
        return creditAccountQueryProvider.getCreditRecoverDetail(request);
    }

    /**
     * 授信账户待还款订单
     *
     * @param request
     * @return
     */
   /* @PostMapping("/repay-list")
    @Operation(summary = "分页查询授信账户列表")
    public BaseResponse<MicroServicePage<CreditTradePageResponse>> findCreditRepayPage(@RequestBody CreditTradePageRequest request) {

        return tradeQueryProvider.findCreditRepayPage(request);
    }*/

}
