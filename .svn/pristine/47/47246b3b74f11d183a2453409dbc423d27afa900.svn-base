package com.wanmi.sbc.customer;

import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerByAccountRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerCheckPayPasswordRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerModifyRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerByAccountResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.request.*;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.util.CommonUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by feitingting on 2019/3/1.
 */
@RestController
@Validated
@Slf4j
@Tag(name = "CustomerBalanceBaseController", description = "余额提现相关")
public class CustomerBalanceBaseController {
    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private CustomerSiteProvider customerSiteProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private CustomerSiteQueryProvider customerSiteQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * H5 - 设置支付密码或忘记支付密码时发送验证码
     *
     * @param request
     * @return
     */
    @Operation(summary = "H5 - 设置支付密码或忘记支付密码时发送验证码")
    @RequestMapping(value = "/balancePayPassword", method = RequestMethod.POST)
    public BaseResponse balancePayPassword(@Valid @RequestBody BalancePayPasswordSendCodeRequest request) {
        return this.sendVerifyCode(request.getCustomerAccount(), request.getIsForgetPassword(),
                CacheKeyConstant.FIND_BALANCE_PAY_PASSWORD, CacheKeyConstant.FIND_BALANCE_PAY_PASSWORD_NUM,
                CacheKeyConstant.BALANCE_PAY_PASSWORD, CacheKeyConstant.BALANCE_PAY_PASSWORD_NUM);
    }

    /**
     * H5 - 设置支付密码--验证验证码
     *
     * @param balancePayPasswordRequest
     * @return
     */
    @Operation(summary = "H5 - 设置支付秘密时验证,进入下一步")
    @RequestMapping(value = "/validatePayPassword", method = RequestMethod.POST)
    public BaseResponse<String> validatePayCode(@Valid @RequestBody BalancePayPasswordValidateRequest balancePayPasswordRequest) {
        return this.validatePayPassword(balancePayPasswordRequest.getCustomerAccount(), balancePayPasswordRequest.getVerifyCode(),
                balancePayPasswordRequest.getIsForgetPassword(), CacheKeyConstant.FIND_BALANCE_PAY_PASSWORD,
                CacheKeyConstant.FIND_BALANCE_PAY_PASSWORD_NUM, CacheKeyConstant.BALANCE_PAY_PASSWORD,
                CacheKeyConstant.BALANCE_PAY_PASSWORD_NUM);
    }

    /**
     * H5 - 设置/忘记支付密码
     *
     * @param balancePayPasswordRequest
     * @return
     */
    @Operation(summary = "H5 - 设置/忘记支付密码")
    @RequestMapping(value = "/payPasswordByForgot", method = RequestMethod.POST)
    public BaseResponse passwordByForgot(@Valid @RequestBody BalancePayPasswordRequest balancePayPasswordRequest) {

        if(!balancePayPasswordRequest.getCustomerId().equals(commonUtil.getOperatorId())){
            return BaseResponse.FAILED();
        }

        return this.setPayPassword(
                balancePayPasswordRequest.getCustomerId(),
                balancePayPasswordRequest.getVerifyCode(),
                balancePayPasswordRequest.getCustomerPayPassword(),
                balancePayPasswordRequest.getIsForgetPassword(),
                CacheKeyConstant.FIND_BALANCE_PAY_PASSWORD,
                CacheKeyConstant.FIND_BALANCE_PAY_PASSWORD_NUM,
                CacheKeyConstant.BALANCE_PAY_PASSWORD,
                CacheKeyConstant.BALANCE_PAY_PASSWORD_NUM);
    }

    /**
     * APP - 设置支付密码或忘记支付密码时发送验证码
     *
     * @param request
     * @return
     */
    @Operation(summary = "APP - 设置支付密码或忘记支付密码时发送验证码")
    @RequestMapping(value = "/app/verify/code", method = RequestMethod.POST)
    public BaseResponse sendPayVerifyCode(@Valid @RequestBody BalancePayPasswordVerifyCodeRequest request) {
        return this.sendVerifyCode(request.getCustomerAccount(), request.getIsForgetPassword(),
                CacheKeyConstant.YZM_APP_FIND_BALANCE_PAY_PASSWORD, CacheKeyConstant.YZM_APP_FIND_BALANCE_PAY_PASSWORD_NUM,
                CacheKeyConstant.YZM_APP_BALANCE_PAY_PASSWORD, CacheKeyConstant.YZM_APP_BALANCE_PAY_PASSWORD_NUM);
    }

    /**
     * APP - 设置支付秘密时验证,进入下一步
     *
     * @param balancePayPasswordRequest
     * @return
     */
    @Operation(summary = "APP - 设置支付秘密时验证,进入下一步")
    @RequestMapping(value = "/app/validate/code", method = RequestMethod.POST)
    public BaseResponse<String> validateCode(@Valid @RequestBody BalancePayPasswordValidateCodeRequest balancePayPasswordRequest) {
        return this.validatePayPassword(balancePayPasswordRequest.getCustomerAccount(), balancePayPasswordRequest.getVerifyCode(),
                balancePayPasswordRequest.getIsForgetPassword(), CacheKeyConstant.YZM_APP_FIND_BALANCE_PAY_PASSWORD,
                CacheKeyConstant.YZM_APP_FIND_BALANCE_PAY_PASSWORD_NUM, CacheKeyConstant.YZM_APP_BALANCE_PAY_PASSWORD,
                CacheKeyConstant.YZM_APP_BALANCE_PAY_PASSWORD_NUM);
    }

    /**
     * APP - 设置/忘记支付密码
     *
     * @param balancePayPasswordRequest
     * @return
     */
    @Operation(summary = "APP - 设置/忘记支付密码")
    @RequestMapping(value = "/app/pay/pwd", method = RequestMethod.POST)
    public BaseResponse setPayPassword(@Valid @RequestBody BalancePayPasswordRequest balancePayPasswordRequest) {
        return this.setPayPassword(balancePayPasswordRequest.getCustomerId(), balancePayPasswordRequest.getVerifyCode(), balancePayPasswordRequest.getCustomerPayPassword(),
                balancePayPasswordRequest.getIsForgetPassword(), CacheKeyConstant.YZM_APP_FIND_BALANCE_PAY_PASSWORD,
                CacheKeyConstant.YZM_APP_FIND_BALANCE_PAY_PASSWORD_NUM, CacheKeyConstant.YZM_APP_BALANCE_PAY_PASSWORD,
                CacheKeyConstant.YZM_APP_BALANCE_PAY_PASSWORD_NUM);
    }


    /**
     * 设置密码或忘记密码发送验证码
     *
     * @param customerAccount 发送验证码手机号
     * @param isForgetPwd     是否是忘记密码需求
     * @param findPassword    忘记密码的Redis key 前缀
     * @param setPassword     设置支付密码的Redis key 前缀
     * @return
     */
    private BaseResponse sendVerifyCode(String customerAccount, Boolean isForgetPwd, String findPassword,
                                        String findPasswordNum, String setPassword, String setPasswordNum) {
        //是否可以发送
        if (!customerCacheService.validateSendMobileCode(customerAccount)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }

        CustomerByAccountRequest customerByAccountRequest = new CustomerByAccountRequest();
        customerByAccountRequest.setCustomerAccount(customerAccount);
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(customerByAccountRequest);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        if (response == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        CustomerDetailVO customerDetail = this.findAnyCustomerDetailByCustomerId(response.getCustomerId());
        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        //删除验证错误次数
        redisService.delete(Objects.nonNull(isForgetPwd) && isForgetPwd ? findPasswordNum.concat(customerAccount) :
                setPasswordNum.concat(customerAccount));
        //发送验证码
        String redisKey = Objects.nonNull(isForgetPwd) && isForgetPwd ? findPassword : setPassword;
        if (Constants.yes.equals(customerCacheService.sendMobileCode(redisKey, customerAccount, SmsTemplate.VERIFICATION_CODE))) {
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }

    /**
     * 验证发送的验证码
     *
     * @param customerAccount 发送验证吗的手机号
     * @param verifyCode      前端传入的验证码
     * @param isForgetPwd     是否是忘记密码需求
     * @param findPassword    忘记支付密码Redis key前缀
     * @param setPassword     设置支付密码Redis key前缀
     * @return
     */
    private BaseResponse<String> validatePayPassword(String customerAccount, String verifyCode, Boolean isForgetPwd,
                                                     String findPassword, String findPasswordNum, String setPassword,
                                                     String setPasswordNum) {
        //累计验证错误次数
        String errKey = Objects.nonNull(isForgetPwd) && isForgetPwd ? findPasswordNum.concat(customerAccount) : setPasswordNum.concat(customerAccount);
        // 校验及封装校验后请求参数
        String smsKey = Objects.nonNull(isForgetPwd) && isForgetPwd ? findPassword.concat(customerAccount) : setPassword.concat(customerAccount);
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, verifyCode);

        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(customerAccount);
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        if (Objects.isNull(response)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }
        CustomerDetailVO customerDetail = findAnyCustomerDetailByCustomerId(response.getCustomerId());
        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }
        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        return BaseResponse.success(response.getCustomerId());
    }

    /**
     * 验证完验证码后，设置或修改支付密码
     *
     * @param customerId   客户ID
     * @param verifyCode   前端传入的验证码
     * @param payPassword  支付密码
     * @param isForgetPwd  是否是忘记密码需求
     * @param findPassword 忘记支付密码Redis key前缀
     * @param setPassword  设置支付密码Redis key前缀
     * @return
     */
    private BaseResponse setPayPassword(String customerId, String verifyCode, String payPassword, Boolean isForgetPwd
            , String findPassword, String findPasswordNum, String setPassword, String setPasswordNum) {
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();
        if (customer == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }
        CustomerDetailVO customerDetail = this.findAnyCustomerDetailByCustomerId(customerId);
        if (customerDetail == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010008);
        }

        //累计验证错误次数
        String errKey = Objects.nonNull(isForgetPwd) && isForgetPwd ?
                findPasswordNum.concat(customer.getCustomerAccount()) : setPasswordNum.concat(customer.getCustomerAccount());
        // 校验及封装校验后请求参数
        String smsKey = Objects.nonNull(isForgetPwd) && isForgetPwd ?
                findPassword.concat(customer.getCustomerAccount()) : setPassword.concat(customer.getCustomerAccount());
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, verifyCode);

        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010004, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }
        CustomerModifyRequest customerModifyRequest = new CustomerModifyRequest();
        customerModifyRequest.setCustomerPayPassword(payPassword);
        customerModifyRequest.setCustomerId(customerId);
        customerSiteProvider.modifyCustomerPayPwd(customerModifyRequest);
        //删除验证码缓存
        redisService.delete(Objects.nonNull(isForgetPwd) && isForgetPwd ?
                findPassword.concat(customer.getCustomerAccount()) : setPassword.concat(customer.getCustomerAccount()));
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 根据会员获取
     *
     * @param customerId
     * @return
     */
    private CustomerDetailVO findAnyCustomerDetailByCustomerId(String customerId) {
        return customerDetailQueryProvider.getCustomerDetailByCustomerId(
                CustomerDetailByCustomerIdRequest.builder().customerId(customerId).build()).getContext();
    }

    /**
     * 校验会员支付密码是否可用
     * 是否存在 账户是否被冻结
     *
     * @return
     */
    @Operation(summary = "校验会员支付密码是否可用")
    @RequestMapping(value = "/isPayPwdValid", method = RequestMethod.POST)
    public BaseResponse isPayPwdValid() {
        CustomerGetByIdResponse customerGetByIdResponse = customerQueryProvider.getCustomerById(new
                CustomerGetByIdRequest(commonUtil.getOperatorId())).getContext();
        if (StringUtils.isBlank(customerGetByIdResponse.getCustomerPayPassword())) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020023);
        }
        if (customerGetByIdResponse.getPayErrorTime() != null && customerGetByIdResponse.getPayErrorTime() == Constants.THREE) {
            Duration duration = Duration.between(customerGetByIdResponse.getPayLockTime(), LocalDateTime.now());
            if (duration.toMinutes() < Constants.NUM_30) {
                //支付密码输错三次，并且锁定时间还未超过30分钟，返回账户冻结错误信息
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010032, new Object[]{30 - duration.toMinutes()});
            }
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验会员输入支付密码是否正确
     *
     * @param request
     * @return
     */
    @Operation(summary = "校验会员输入支付密码是否正确")
    @RequestMapping(value = "/checkCustomerPayPwd", method = RequestMethod.POST)
    public BaseResponse checkCustomerPayPwd(@RequestBody @Valid CustomerCheckPayPasswordRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        return customerSiteProvider.checkCustomerPayPwd(request);
    }
}
