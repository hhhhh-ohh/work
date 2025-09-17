package com.wanmi.sbc.crm.customer;

import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsByCustomerIdRequest;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsByCustomerIdResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.crm.customer.response.CustomerGetForCrmResponse;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.enterpriseinfo.EnterpriseInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeOptionalByIdRequest;
import com.wanmi.sbc.customer.api.request.enterpriseinfo.EnterpriseInfoByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelWithDefaultByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelQueryRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeOptionalByIdResponse;
import com.wanmi.sbc.customer.api.response.enterpriseinfo.EnterpriseInfoByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.PayingMemberCustomerRelByIdResponse;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodePageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardBalanceQueryRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodePageResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardBalanceQueryResponse;
import com.wanmi.sbc.marketing.bean.enums.GiftCardStatus;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.CustomerTradeListRequest;
import com.wanmi.sbc.order.api.response.trade.TradeListAllResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 会员
 * Created by hht on 2017/4/19.
 */
@Tag(name =  "CRM会员API", description =  "CrmCustomerController")
@RestController
@Validated
@RequestMapping(value = "/customer")
public class CrmCustomerController {

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private CustomerFundsQueryProvider customerFundsQueryProvider;

    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private CouponCodeQueryProvider couponCodeQueryProvider;

    @Autowired
    private EnterpriseInfoQueryProvider enterpriseInfoQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PayingMemberCustomerRelQueryProvider payingMemberCustomerRelQueryProvider;

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    @Autowired private TradeQueryProvider tradeQueryProvider;

    /**
     * 查询单条会员信息
     *
     * @param customerId
     * @return
     */
    @Operation(summary = "查询会员详情")
    @Parameter(name = "customerId", description = "会员ID", required = true)
    @RequestMapping(value = "/crm/{customerId}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_boss_crm_find_customer_by_id_sign_word")
    public BaseResponse<CustomerGetForCrmResponse> findById(@PathVariable String customerId) {
        CustomerGetForCrmResponse response = new CustomerGetForCrmResponse();
        CustomerGetByIdResponse customer =
                customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();
        KsBeanUtil.copyPropertiesThird(customer, response);
        KsBeanUtil.copyPropertiesThird(customer.getCustomerDetail(), response);
        if(Objects.nonNull(customer.getCustomerLevelId())) {
            CustomerLevelVO customerLevel = customerLevelQueryProvider.getCustomerLevelWithDefaultById(
                    CustomerLevelWithDefaultByIdRequest.builder().customerLevelId(customer.getCustomerLevelId()).build())
                    .getContext();
            response.setCustomerLevelName(customerLevel.getCustomerLevelName());
        }

        PayingMemberCustomerRelByIdResponse time=payingMemberCustomerRelQueryProvider
                .findMostByCustomerId(PayingMemberCustomerRelQueryRequest.builder()
                .customerId(customer.getCustomerId()).build())
                .getContext();


        if (time != null) {
            response.setExpirationDate(time.getPayingMemberCustomerRelVO().getExpirationDate());
            response.setLevelName(time.getPayingMemberCustomerRelVO().getLevelName());
            //查询用户升级还差金额
            CustomerTradeListRequest tradeListAllRequest = CustomerTradeListRequest.builder()
                    .customerId(customerId)
                    .build();
            BaseResponse<TradeListAllResponse> tradeListAllResponse =
                    tradeQueryProvider.getListByCustomerId(tradeListAllRequest);
            BigDecimal totalPay = BigDecimal.ZERO;
            tradeListAllResponse.getContext().getTradeVOList().stream()
                    .forEach(tradeVO -> {
                        BigDecimal payCash = tradeVO.getTradePrice().getTotalPayCash();
                        totalPay.add(payCash);
                    });
            String levelName = time.getPayingMemberCustomerRelVO().getLevelName();
            BigDecimal upgradePay = BigDecimal.ZERO;
            if(levelName.equals("V1")){
                upgradePay = BigDecimal.valueOf(1000);
            } else if(levelName.equals("V2")){
                upgradePay = BigDecimal.valueOf(2000);
            }
            time.getPayingMemberCustomerRelVO().setRemainingAmount(upgradePay.subtract(totalPay));

            response.setRemainingAmount(time.getPayingMemberCustomerRelVO().getRemainingAmount());

        }




        CustomerFundsByCustomerIdRequest fundRequest = new CustomerFundsByCustomerIdRequest();
        fundRequest.setCustomerId(customerId);
        CustomerFundsByCustomerIdResponse fundsResponse = customerFundsQueryProvider.getByCustomerId(fundRequest).getContext();
        if(Objects.nonNull(fundsResponse)){
            response.setAccountBalance(fundsResponse.getAccountBalance());
        }
        DistributionCustomerVO distributionVO = distributionCustomerQueryProvider.getByCustomerIdAndDistributorFlagAndDelFlag(
                DistributionCustomerByCustomerIdRequest.builder().customerId(customerId).build()).getContext().getDistributionCustomerVO();
        if(Objects.nonNull(distributionVO)){
            response.setDistributorFlag(distributionVO.getDistributorFlag());
            response.setInviteCount(distributionVO.getInviteCount());
            response.setInviteAvailableCount(distributionVO.getInviteAvailableCount());
            response.setRewardCash(distributionVO.getRewardCash());
            response.setRewardCashNotRecorded(distributionVO.getRewardCashNotRecorded());
            response.setDistributorLevelName(distributionVO.getDistributorLevelName());
        }

        if(Objects.nonNull(response.getEmployeeId())){
            EmployeeOptionalByIdResponse employee = employeeQueryProvider.getOptionalById(EmployeeOptionalByIdRequest.builder()
                    .employeeId(response.getEmployeeId()).build()).getContext();
            if(Objects.nonNull(employee)){
                response.setEmployeeName(employee.getEmployeeName());
                response.setEmployeeMobile(employee.getEmployeeMobile());
            }
        }

        if(!Objects.equals(EnterpriseCheckState.INIT,customer.getEnterpriseCheckState())){
            BaseResponse<EnterpriseInfoByCustomerIdResponse> enterpriseInfo = enterpriseInfoQueryProvider.getByCustomerId(EnterpriseInfoByCustomerIdRequest.builder()
                    .customerId(customerId)
                    .build());
            if(Objects.nonNull(enterpriseInfo.getContext())){
                response.setEnterpriseInfo(enterpriseInfo.getContext().getEnterpriseInfoVO());
            }
            response.setEnterpriseCustomerName(commonUtil.getIepSettingInfo().getEnterpriseCustomerName());
        }
        //查询会员礼品卡余额
        UserGiftCardBalanceQueryResponse userGiftCardBalanceQueryResponse = userGiftCardProvider.getGiftCardBalance(
                UserGiftCardBalanceQueryRequest.builder().customerId(customerId).giftCardStatus(GiftCardStatus.ACTIVATED).build()).getContext();
        if(Objects.nonNull(userGiftCardBalanceQueryResponse.getGiftCardBalance())){
            response.setGiftCardBalance(userGiftCardBalanceQueryResponse.getGiftCardBalance());
        }
        return BaseResponse.success(response);
    }

    /**
     * 客户优惠券列表
     * @param request
     * @return
     */
    @Operation(summary = "客户优惠券列表")
    @RequestMapping(value = "/coupons", method = RequestMethod.POST)
    public BaseResponse<CouponCodePageResponse> listMyCouponList(@RequestBody CouponCodePageRequest request){
        return couponCodeQueryProvider.page(request);
    }
}
