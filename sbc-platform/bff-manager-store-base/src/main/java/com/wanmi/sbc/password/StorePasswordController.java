package com.wanmi.sbc.password;

import com.wanmi.sbc.base.verifycode.VerifyCodeService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByIdRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.setting.bean.enums.VerifyType;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.sms.SmsSendUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: songhanlin
 * @Date: Created In 下午9:42 2017/11/2
 * @Description: 密码Controller
 */
@Tag(name = "StorePasswordController", description = "密码服务API")
@RestController("StorePasswordController")
@Validated
@RequestMapping("/store/password")
public class StorePasswordController {

    private static final Logger logger = LoggerFactory.getLogger(StorePasswordController.class);

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private SmsSendUtil smsSendUtil;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired private CommonUtil commonUtil;


    /**
     * 商家找重置密码
     *
     * @return
     */
    @Operation(summary = "商家找重置密码")
    @Parameter(name = "phone", description = "手机号码", required = true)
    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    public BaseResponse sendSmsVerifyCode() {
        return handleOption(AccountType.s2bSupplier);
    }

    /**
     *供应商重置密码
     *
     * @return
     */
    @Operation(summary = "供应商重置密码")
    @Parameter(name = "phone", description = "手机号码", required = true)
    @RequestMapping(value = "/provider/sms", method = RequestMethod.POST)
    public BaseResponse sendSmsProviderVerifyCode() {
        return handleOption(AccountType.s2bProvider);
    }


    /**
     * 统一处理
     * @param type
     * @return
     */
    public BaseResponse handleOption(AccountType type){

        String userId = commonUtil.getOperatorId();

        Optional<EmployeeByIdResponse> optional = Optional.ofNullable(employeeQueryProvider.getById(
                EmployeeByIdRequest.builder().employeeId(userId).build()).getContext());

        if (!optional.isPresent()) {
            logger.error("会员账号:{}不存在", userId);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000002);
        }
        EmployeeByIdResponse response = optional.get();

        Long companyInfoId = commonUtil.getCompanyInfoId();
        if (Objects.isNull(companyInfoId) || !Objects.equals(companyInfoId, response.getCompanyInfoId())) {
            logger.error("手机号:{}商家Id不一致", userId);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        Optional<EmployeeByIdResponse> finalOptional = optional;
        return optional.map(EmployeeByIdResponse::getEmployeeMobile)
                .map(mobile -> {
                    //商家
                    if ((AccountType.s2bSupplier.equals(type)
                            || finalOptional.isPresent() && finalOptional.get().getAccountType() == AccountType.O2O)
                            && verifyCodeService.validSmsCertificate(mobile, VerifyType.SUPPLIER_SEND)) {
                        verifyCodeService.generateSmsCertificate(mobile, VerifyType.SUPPLIER_SEND, 1, TimeUnit.MINUTES);
                        String verifyCode = verifyCodeService.generateSmsVerifyCode(mobile,
                                VerifyType.SUPPLIER_CHANGE_PASSWORD, 5, TimeUnit.MINUTES);
                        smsSendUtil.send(SmsTemplate.VERIFICATION_CODE, new String[]{mobile}, verifyCode);
                    }
                    //供应商
                    else if (AccountType.s2bProvider.equals(type) && verifyCodeService.validSmsCertificate(mobile, VerifyType.PROVIDER_SEND)) {
                        verifyCodeService.generateSmsCertificate(mobile, VerifyType.PROVIDER_SEND, 1, TimeUnit.MINUTES);
                        String verifyCode = verifyCodeService.generateSmsVerifyCode(mobile,
                                VerifyType.PROVIDER_CHANGE_PASSWORD, 5, TimeUnit.MINUTES);
                        smsSendUtil.send(SmsTemplate.VERIFICATION_CODE, new String[]{mobile}, verifyCode);
                    }else {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
                    }
                    return BaseResponse.SUCCESSFUL();
                })
                .orElseThrow(() -> new SbcRuntimeException(CustomerErrorCodeEnum.K010135));
    }
}
