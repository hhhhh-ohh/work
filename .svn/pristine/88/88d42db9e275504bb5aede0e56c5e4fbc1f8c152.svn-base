package com.wanmi.sbc.customercredit;

import com.wanmi.sbc.account.api.provider.credit.CreditAccountQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditApplyQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAccountDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditApplyQueryRequest;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.bean.vo.CreditApplyRecordVo;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayByIdRequest;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayResponse;
import com.wanmi.sbc.util.CommonUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/***
 * 授信账户查询Controller
 * @author zhengyang
 * @since 2021-03-02
 */
@RestController
@Validated
@RequestMapping("/credit/query")
@Tag(name = "CustomerCreditQueryBaseController", description = "S2B web公用-授信账户查询API")
public class CustomerCreditQueryBaseController {
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private CreditApplyQueryProvider applyQueryProvider;
    @Resource
    private CreditAccountQueryProvider accountQueryProvider;
    @Resource
    private PaySettingQueryProvider paySettingQueryProvider;


    /***
     * 授信支付网关ID
     */
    private final static Long CREDIT_GATEWAY_ID = 16L;
    /***
     * 默认店铺ID
     */
    private final static Long DEFAULT_STORE_ID = -1L;

    /***
     * 授信账户申请记录查询
     * @return {@link CreditApplyRecordVo}
     */
    @RequestMapping(value = "/queryApplyInfo", method = RequestMethod.GET)
    public BaseResponse<CreditApplyRecordVo> queryApplyInfoByCustomerId() {
        // 请求对象
        CreditApplyQueryRequest request = CreditApplyQueryRequest
                .builder().customerId(commonUtil.getOperatorId()).build();
        return applyQueryProvider.queryApplyInfoByCustomerId(request);
    }

    /***
     * 授信账户变更额度申请记录查询
     * @return {@link CreditApplyRecordVo}
     */
    @RequestMapping(value = "/queryChangeInfo", method = RequestMethod.GET)
    public BaseResponse<CreditApplyRecordVo> queryChangeInfoByCustomerId() {
        // 请求对象
        CreditApplyQueryRequest request = CreditApplyQueryRequest
                .builder().customerId(commonUtil.getOperatorId()).build();
        return applyQueryProvider.queryChangeInfoByCustomerId(request);
    }

    /***
     * 根据登录用户查询授信账户
     * @return {@link CreditAccountDetailResponse}
     */
    @RequestMapping(value = "/getCreditAccount", method = RequestMethod.POST)
    public BaseResponse<CreditAccountDetailResponse> getCreditAccountByCustomerId() {
        // 请求对象
        CreditAccountDetailRequest request = CreditAccountDetailRequest
                .builder().customerId(commonUtil.getOperatorId()).build();
        BaseResponse<CreditAccountDetailResponse> accountRes = accountQueryProvider.getCreditAccountByCustomerId(request);
        if (Objects.nonNull(accountRes) && Objects.nonNull(accountRes.getContext())) {
            accountRes.getContext().setAlias(Nutils.getValFromResponse(paySettingQueryProvider
                    .getGatewayById(GatewayByIdRequest.builder().gatewayId(CREDIT_GATEWAY_ID).storeId(DEFAULT_STORE_ID).build()), "alias"));

            // 查询授信支付是否开启
            BaseResponse<PayGatewayResponse> payGatewayRes = paySettingQueryProvider
                    .getGatewayById(GatewayByIdRequest.builder().gatewayId(16L).storeId(-1L).build());
            if (Objects.nonNull(payGatewayRes)) {
                Optional.ofNullable(payGatewayRes.getContext())
                        .ifPresent(val -> {
                            accountRes.getContext().setIsOpen(val.getIsOpen().toValue());
                        });
            }
        }
        return accountRes;
    }
}
