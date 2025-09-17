package com.wanmi.sbc.company;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.CharArrayUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByAccountNameRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeLoginRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeMobileSmsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeRegisterRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeSmsRequest;
import com.wanmi.sbc.customer.api.response.employee.StoreInformationResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInformationRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * Created by hht on 2017/11/23.
 */
@Tag(name = "CompanyController", description = "商家注册API")
@RestController
@Validated
@RequestMapping("/company")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private WeakPasswordsCheckUtil weakPasswordsCheckUtil;

    /**
     * 商家注册时发送验证码
     *
     * @param account
     * @return BaseResponse
     */
    @Operation(summary = "商家注册时发送验证码")
    @Parameter(name = "account", description = "手机号", required = true)
    @RequestMapping(value = "/verify-code/{account}", method = RequestMethod.POST)
    public BaseResponse sendVerifyCode(@PathVariable String account) {
        //发送验证码，验证手机号
        if(!ValidateUtil.isPhone(account)){
            logger.error("手机号码:{}格式错误", account);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //该手机号是否已注册
        if (employeeQueryProvider.getByAccountName(EmployeeByAccountNameRequest.builder()
                .accountName(account).accountType(AccountType.s2bSupplier).build()).getContext().getEmployee() != null){
            logger.error("手机号码:{}已注册", account);
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010076);
        }
        //同一个手机是否操作频繁
        boolean isSendSms = employeeQueryProvider.mobileIsSms(
                EmployeeMobileSmsRequest.builder().mobile(account).build()
        ).getContext().isSendSms();
        if(!isSendSms){
            logger.error("手机号码:{}操作频繁", account);
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010088);
        }
        //删除验证错误次数
        redisService.delete(CacheKeyConstant.YZM_SUPPLIER_REGISTER_NUM.concat(account));
        //发送验证码
        return employeeProvider.sms(
                EmployeeSmsRequest.builder().redisKey(CacheKeyConstant.YZM_SUPPLIER_REGISTER)
                .mobile(account)
                .smsTemplate(SmsTemplate.VERIFICATION_CODE).build()
        );
    }

    /**
     * 商家注册
     * 验证手机号
     * 验证验证码
     * @param loginRequest
     * @return
     */
    @Operation(summary = "商家注册验证验证码")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse register(@Valid @RequestBody EmployeeLoginRequest loginRequest){
        //验证手机号
        if(!ValidateUtil.isPhone(loginRequest.getAccount())){
            logger.error("手机号码:{}格式错误", loginRequest.getAccount());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //验证验证码
        if(StringUtils.isBlank(loginRequest.getVerifyCode())){
            logger.error("手机验证码为空");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //该手机号是否已注册
        if (employeeQueryProvider.getByAccountName(EmployeeByAccountNameRequest.builder()
                .accountName(loginRequest.getAccount()).accountType(AccountType.s2bSupplier).build()).getContext().getEmployee() != null){
            logger.error("手机号码:{}已注册", loginRequest.getAccount());
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010076);
        }
        //累计验证错误次数
        String errKey = CacheKeyConstant.YZM_SUPPLIER_REGISTER_NUM.concat(loginRequest.getAccount());
        // 校验及封装校验后请求参数
        String smsKey = CacheKeyConstant.YZM_SUPPLIER_REGISTER.concat(loginRequest.getAccount());
        // 校验短信验证码
        commonUtil.verifyCodeMsg(errKey, smsKey, loginRequest.getVerifyCode());
        //弱密码校验
        //弱密码校验
        byte[] pwdBytes = CharArrayUtils.toBytes(loginRequest.getPassword());
        weakPasswordsCheckUtil.weakPasswordsCheck(new String(pwdBytes));
        EmployeeRegisterRequest registerRequest = new EmployeeRegisterRequest();
        KsBeanUtil.copyPropertiesThird(loginRequest, registerRequest);
        registerRequest.setAccountType(2);
        registerRequest.setStoreType(1);
        StoreInformationResponse response = employeeProvider.registerGetStoreInfo(registerRequest).getContext();
        if (Objects.nonNull(response)
        ){
            //删除验证码缓存
            redisService.delete(CacheKeyConstant.YZM_SUPPLIER_REGISTER.concat(loginRequest.getAccount()));

            StoreInformationRequest storeInformationRequest = new StoreInformationRequest();
            //存储到es中
            KsBeanUtil.copyPropertiesThird(response, storeInformationRequest);
            esStoreInformationProvider.initStoreInformation(storeInformationRequest);
            return BaseResponse.SUCCESSFUL();
        }


        return BaseResponse.FAILED();
    }


}
