package com.wanmi.sbc.login;

import com.google.common.collect.Maps;
import com.wanmi.sbc.base.verifycode.VerifyCodeService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.SecurityUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByMobileRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeePasswordModifyByIdRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByMobileResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeePasswordModifyByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.password.UpdatePasswordRequest;
import com.wanmi.sbc.setting.bean.enums.VerifyType;
import com.wanmi.sbc.util.OperateLogMQUtil;
import com.wanmi.sbc.util.WeakPasswordsCheckUtil;
import com.wanmi.sbc.util.sms.SmsSendUtil;
import io.jsonwebtoken.impl.DefaultClaims;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: songhanlin
 * @Date: Created In 下午9:42 2017/11/2
 * @Description: 密码Controller
 */
@RestController("supplierPasswordController")
@Validated
@RequestMapping("/password")
@Tag(name =  "商品分类服务",description = "PasswordController")
public class PasswordController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private SmsSendUtil smsSendUtil;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private WeakPasswordsCheckUtil weakPasswordsCheckUtil;

    /**
     * 平台找回密码/重置密码
     *
     * @param phone
     * @return
     */
    @Operation(summary = "平台找回密码/重置密码")
    @Parameter(description = "手机号", required = true, example = "1393521586")
    @RequestMapping(value = "/sms/{phone}", method = RequestMethod.POST)
    public BaseResponse sendSmsVerifyCode(@PathVariable String phone) {
        if (!ValidateUtil.isPhone(phone)) {
            logger.error("手机号:{}不符合要求", phone);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return Optional.ofNullable(employeeQueryProvider.getByMobile(
                    EmployeeByMobileRequest.builder()
                            .mobile(phone).accountType(AccountType.s2bBoss).build()
                ).getContext())
                .map(EmployeeByMobileResponse::getEmployeeMobile)
                .map(mobile -> {
                    if (verifyCodeService.validSmsCertificate(mobile, VerifyType.S2B_BOSS_SEND)) {
                        verifyCodeService.generateSmsCertificate(mobile, VerifyType.S2B_BOSS_SEND, 1, TimeUnit.MINUTES);
                        String verifyCode = verifyCodeService.generateSmsVerifyCode(mobile, VerifyType.S2B_BOSS_CHANGE_PASSWORD, 5, TimeUnit.MINUTES);
                        smsSendUtil.send(SmsTemplate.VERIFICATION_CODE, new String[]{mobile}, verifyCode);
                    } else {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000012);
                    }
                    return BaseResponse.SUCCESSFUL();
                })
                .orElseThrow(() -> new SbcRuntimeException(CustomerErrorCodeEnum.K010135));
    }


    /**
     * 平台验证验证码
     *
     * @param phone 手机号码
     * @param code  验证码
     * @return BaseResponse
     */
    @Operation(summary = "平台验证验证码")
    @Parameters({
            @Parameter(name = "phone",
                    description = "手机号", required = true, example = "1393521586"),
            @Parameter(name = "code",
                    description = "验证码", required = true, example = "000000")
    })
    @RequestMapping(value = "/sms/{phone}/{code}", method = RequestMethod.POST)
    public BaseResponse<String> validateSmsVerifyCode(@PathVariable("phone") String phone, @PathVariable("code") String code) {
        if (!ValidateUtil.isPhone(phone)) {
            logger.error("手机号:{}不符合要求", phone);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        boolean verifyResult = verifyCodeService.validateSmsVerifyCode(phone, code, VerifyType.S2B_BOSS_CHANGE_PASSWORD, 1, TimeUnit.DAYS);
        if (verifyResult) {
            return BaseResponse.success(code);
        } else {
            verifyCodeService.addVerifyCodeErrorCount(phone);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000010);
        }
    }


    /**
     * 平台设置手机密码
     *
     * @return BaseResponse
     */
    @Operation(summary = "平台设置手机密码")
    @RequestMapping(value = "/boss", method = RequestMethod.POST)
    public BaseResponse<EmployeePasswordModifyByIdResponse> setPassword(@RequestBody UpdatePasswordRequest request) {
        if (!ValidateUtil.isPhone(request.getPhone())) {
            logger.error("手机号:{}不符合要求", request.getPhone());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (!ValidateUtil.isBetweenLen(request.getPassword(), Constants.SIX, Constants.NUM_32)) {
            logger.error("密码:{}不符合要求", request.getPassword());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //弱密码校验
        weakPasswordsCheckUtil.weakPasswordsCheck(request.getPassword());

        boolean hasCert = verifyCodeService.validateSmsVerifyCodeAgain(request.getPhone(), request.getCode(), VerifyType.S2B_BOSS_CHANGE_PASSWORD);
        if (hasCert) {
            verifyCodeService.deletePhoneCertificate(request.getPhone(), VerifyType.S2B_BOSS_CHANGE_PASSWORD);
            return Optional.ofNullable(employeeQueryProvider.getByMobile(
                        EmployeeByMobileRequest.builder()
                                .mobile(request.getPhone()).accountType(AccountType.s2bBoss).build()
                    ).getContext())
                    .map(employee -> {
                        String encodePwd = SecurityUtil.getStoreLogpwd(String.valueOf(employee.getEmployeeId()), request.getPassword(), employee.getEmployeeSaltVal());

                        Map<String, Object> claims = Maps.newHashMap();
                        claims.put("employeeId", employee.getEmployeeId());
                        claims.put("EmployeeName", employee.getAccountName());
                        claims.put("adminId", 0);
                        claims.put("storeId", 0);
                        claims.put("platform", Platform.PLATFORM.toValue());
                        claims.put("ip", HttpUtil.getIpAddr());

                        operateLogMQUtil.convertAndSend("账户管理", "账号管理", "修改密码", new DefaultClaims(claims));

                        return employeeProvider.modifyPasswordById(
                                EmployeePasswordModifyByIdRequest.builder()
                                        .employeeId(employee.getEmployeeId()).encodePwd(encodePwd).build()
                        );
                    }
            ).orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

}
