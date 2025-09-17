package com.wanmi.sbc.supplier.register.controller;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.CharArrayUtils;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByAccountNameRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeLoginRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeSmsRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.employee.EmployeeCacheService;
import com.wanmi.sbc.supplier.register.service.SupplierCompanyInfoService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.WeakPasswordsCheckUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @Author: songhanlin
 * @Date: Created In 下午7:03 2017/11/1
 * @Description: 公司信息Controller
 */
@Tag(name = "SupplierCompanyInfoController", description = "公司信息 API")
@RestController("SupplierCompanyInfoController")
@Validated
@RequestMapping("/company")
public class SupplierCompanyInfoController {

    private static final Logger logger = LoggerFactory.getLogger(SupplierCompanyInfoController.class);

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;
    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private SupplierCompanyInfoService supplierCompanyInfoService;

    @Autowired
    private EmployeeCacheService employeeCacheService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private WeakPasswordsCheckUtil weakPasswordsCheckUtil;

    /**
     * 商家注册时发送验证码
     *
     * @param account
     * @return BaseResponse
     */
    @MultiSubmit
    @Operation(summary = "商家注册时发送验证码")
    @Parameter(name = "account", description = "手机号码", required = true)
    @RequestMapping(value = "/verify-code/{account}/{type}", method = RequestMethod.POST)
    public BaseResponse sendVerifyCode(@PathVariable String account,@PathVariable Integer type) {
        //发送验证码，验证手机号
        if(!ValidateUtil.isPhone(account)){
            logger.error("手机号码:{}格式错误", account);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        AccountType accountType = this.chgAccountType(type);

        //验证码sms
        String smsKey = this.chgSmsKey(accountType);

        //同一个手机是否操作频繁
        boolean isSendSms = employeeCacheService.isSendSms(smsKey, account);
        if(!isSendSms){
            logger.error("手机号码:{}操作频繁", account);
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010088);
        }

        //是否存在该手机号注册的商家
        if (employeeQueryProvider.getByAccountName(EmployeeByAccountNameRequest.builder()
                .accountName(account).accountType(accountType).build()).getContext().getEmployee() != null ){
            logger.error("手机号码:{}已注册", account);
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010076);
        }

        //删除验证错误次数
        redisUtil.delete(this.chgErrKey(accountType).concat(account));
        //发送验证码--商家
        return employeeProvider.sms(EmployeeSmsRequest.builder().redisKey(smsKey).mobile(account)
                        .smsTemplate(SmsTemplate.VERIFICATION_CODE).build());
    }

    /**
     * 商家注册 验证手机号 验证验证码
     *
     * @param loginRequest
     * @return
     */
    @Operation(summary = "商家注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @MultiSubmit
    @GlobalTransactional
    public BaseResponse register(@Valid @RequestBody EmployeeLoginRequest loginRequest) {
        if(!ValidateUtil.isPhone(loginRequest.getAccount())){
            logger.error("手机号码:{}格式错误", loginRequest.getAccount());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //验证，通过就删除key
        this.verifySmsCode(loginRequest);
        // 判断如果为空，走默认
        if (Objects.isNull(loginRequest.getPluginType())) {
            loginRequest.setPluginType(PluginType.NORMAL);
        }
        //弱密码校验
        byte[] pwdBytes = CharArrayUtils.toBytes(loginRequest.getPassword());
        weakPasswordsCheckUtil.weakPasswordsCheck(new String(pwdBytes));
        if (Objects.nonNull(supplierCompanyInfoService.register(loginRequest))) {
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }

    /**
     * 验证验证码
     * @param employeeLoginRequest 入参
     */
    private void verifySmsCode(EmployeeLoginRequest employeeLoginRequest){
        if(StringUtils.isBlank(employeeLoginRequest.getVerifyCode())){
            logger.error("手机号码:{}手机验证码为空", employeeLoginRequest.getAccount());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //累计验证错误次数
        String errKey =
                this.chgErrKey(this.chgAccountType(employeeLoginRequest.getAccountType())).concat(employeeLoginRequest.getAccount());
        // 校验及封装校验后请求参数
        String smsKey = this.chgSmsKey(this.chgAccountType(employeeLoginRequest.getAccountType())).concat(employeeLoginRequest.getAccount());
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, employeeLoginRequest.getVerifyCode());
        // 删除验证码缓存
        redisUtil.delete(smsKey);
    }

    /**
     * 类传转化AccountType
     * @param type int值
     * @return AccountType实例
     */
    private AccountType chgAccountType(Integer type){
        AccountType accountType = AccountType.s2bSupplier;
        if(Objects.nonNull(type)){
            accountType = AccountType.fromValue(type);
        }
        return accountType;
    }

    /**
     * 根据accountType采用相应的验证码KEY前缀
     * @param type int值
     * @return 验证码KEY前缀
     */
    private String chgSmsKey(AccountType type){
        String smsKey = CacheKeyConstant.YZM_SUPPLIER_REGISTER;
        if(AccountType.s2bProvider.equals(type)){
            smsKey = CacheKeyConstant.YZM_PROVIDER_REGISTER;
        }
        return smsKey;
    }

    /**
     * 根据accountType采用相应的验证码次数KEY前缀
     * @param type int值
     * @return 验证码KEY前缀
     */
    private String chgErrKey(AccountType type){
        String errKey = CacheKeyConstant.YZM_SUPPLIER_REGISTER_NUM;
        if(AccountType.s2bProvider.equals(type)){
            errKey = CacheKeyConstant.YZM_PROVIDER_REGISTER_NUM;
        }
        return errKey;
    }
}
