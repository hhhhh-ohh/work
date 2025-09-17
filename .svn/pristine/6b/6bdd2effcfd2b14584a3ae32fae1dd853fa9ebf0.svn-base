package com.wanmi.sbc.mq.configuration.support;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author zhanggaolei
 */
@Component
@Slf4j
public class GlobalValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BaseRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BaseRequest request = (BaseRequest) target;
        if (errors.hasErrors()) {
            return;
        }

        try {
            request.checkParam();
        } catch (Exception ex) {
            log.error("Parameter verification failure:the params:{}", request);
            if (ex instanceof SbcRuntimeException) {
                throw ex;
            } else {
                log.error("error message: {}", ex.getMessage());
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
    }
}
