package com.wanmi.sbc.common.handler;

import com.google.common.base.Joiner;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import feign.FeignException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * 异常统一处理
 */
@ControllerAdvice
@Slf4j
public class SbcExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Value("#{'${cors.allowedOrigins:*}'.split(',')}")
    private List<String> allowedOrigins;

    private static final String LOGGER_FORMAT = "操作执行异常：异常编码{},异常信息：{},堆栈信息：{}";

    @ExceptionHandler(SbcRuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse SbcRuntimeExceptionHandler(SbcRuntimeException ex,HttpServletRequest request) {
        //1、异常链的errorMessage
        Throwable cause = ex.getCause();
        String msg = "";
        if (cause != null) {
            msg = cause.getMessage();
        }
        Locale locale = LocaleContextHolder.getLocale();
//        String language = request.getHeader("Accept-Language");
//        Locale locale = Locale.CHINA;
//        if(StringUtils.isNotBlank(language)){
//            locale = Locale.forLanguageTag(language);
//            if(locale==null){
//                locale = Locale.CHINA;
//            }
//        }
        String errorCode = ex.getErrorCode();
        if (StringUtils.isNotEmpty(errorCode)) {
            if(CommonErrorCodeEnum.K999999.getCode().equals(errorCode) && ex.getParams() != null && ex.getParams().length>0){
                msg = Objects.toString(ex.getParams()[0]);
            }else {
                msg = this.getMessage(ex,locale);
            }

            // 如果异常码在本系统中有对应异常信息，以异常码对应的提示信息为准
            if (!errorCode.equals(msg)) {
                ex.setResult(msg);
            }

            if (StringUtils.isNotBlank(ex.getResult()) && !"fail".equals(ex.getResult())) {
                log.error(LOGGER_FORMAT, ex.getErrorCode(), ex.getResult(), ex);
                return BaseResponse.info(errorCode, ex.getResult(), ex.getData());
            }
            // 2、如果有异常码，以异常码对应的提示信息为准
            // msg = this.getMessage(errorCode, ex.getParams());
        } else if (StringUtils.isEmpty(msg)) {
            // 3、异常码为空 & msg为空，提示系统异常
            msg = this.getMessage(new SbcRuntimeException(CommonErrorCodeEnum.K000001, ex.getParams()),locale);
        }

        if(StringUtils.isEmpty(errorCode)) {
            errorCode = CommonErrorCodeEnum.K000001.getCode();
        }
        log.error(LOGGER_FORMAT, errorCode, msg, ex);

        return new BaseResponse(errorCode,ex.getMessage(), ex.getParams(),null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse validationExceptionHandle(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return new BaseResponse(CommonErrorCodeEnum.K000009);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse constraintViolationExceptionHandle(ConstraintViolationException ex) {
        final StringBuilder sb = new StringBuilder();
        ex.getConstraintViolations().forEach(
                i -> sb
                        .append(i.getRootBeanClass().getName())
                        .append('.')
                        .append(i.getPropertyPath())
                        .append(i.getMessage()).append("\r\n")
        );
        log.error("{}", sb);
        return new BaseResponse(CommonErrorCodeEnum.K000009);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse illegalStateExceptionHandle(IllegalStateException ex) {
        log.error("{}", ex.getMessage());
        return new BaseResponse(CommonErrorCodeEnum.K000009);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse defaultExceptionHandler(Throwable ex) throws Exception {
        log.error(LOGGER_FORMAT, "", ex.getMessage(), ex);
        if (ex.getCause() instanceof GenericJDBCException) {
            if (1366 == ((GenericJDBCException) ex.getCause()).getSQLException().getErrorCode()) {
                return new BaseResponse(CommonErrorCodeEnum.K000017);
            }
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return new BaseResponse(CommonErrorCodeEnum.K000009);
        }
        if (ex instanceof FeignException && ((FeignException) ex).status()==400 ) {

            return new BaseResponse(CommonErrorCodeEnum.K000009);
        }
        return BaseResponse.FAILED();
    }

    @ExceptionHandler(InvalidFileNameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse defaultServerExceptionHandler(Throwable ex)  {
        log.error(LOGGER_FORMAT, "", ex.getMessage(), ex);
        return BaseResponse.FAILED();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse httpMessageNotReadableExceptionHandle(HttpMessageNotReadableException ex) {
        log.error("{}", ex.getMessage());
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof SbcRuntimeException) {
            SbcRuntimeException sbcRuntimeException = (SbcRuntimeException) rootCause;
            return BaseResponse.info(sbcRuntimeException.getErrorCode(),sbcRuntimeException.getMessage(),sbcRuntimeException.getData());
        }
        return new BaseResponse(CommonErrorCodeEnum.K000009);
    }

    /**
     * jwt异常处理
     *
     * @param sx
     * @return
     */
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse jwtExceptionHandler(SignatureException sx, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msg = sx.getMessage();
        response.setStatus(200);
        response.addHeader("Access-Control-Allow-Origin", Joiner.on(",").join(allowedOrigins));
        response.addHeader("Access-Control-Allow-Headers", "authorization,content-type,x-requested-with");
        response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Max-Age", "1800");
        response.addHeader("Allow", "Allow:GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.addHeader("Vary", "Origin");
        if ("Invalid jwtToken.".equals(msg) || "Expired jwtToken.".equals(msg) || "Missing jwtToken.".equals(msg)) {
            return new BaseResponse(CommonErrorCodeEnum.K000015);
        } else {
            return new BaseResponse(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 获取错误码描述
     *
     * @param exception 异常类
     * @param locale 语言类型
     * @return 错误信息
     */
    protected String getMessage(SbcRuntimeException exception,Locale locale) {
        try {
            if (messageSource != null) {
                return messageSource.getMessage(
                        exception.getErrorCode(), exception.getParams(), locale);
            }else{
                return exception.getMessage();
            }
        } catch (NoSuchMessageException e) {
            return exception.getErrorCode();
        }
    }
}
