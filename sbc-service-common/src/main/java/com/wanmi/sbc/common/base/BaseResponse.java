package com.wanmi.sbc.common.base;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ErrorCode;
import com.wanmi.sbc.common.util.MessageSourceUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 响应基类
 * Created by aqlu on 15/11/30.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> implements Serializable {

    public static BaseResponse SUCCESSFUL() {
        return new BaseResponse(CommonErrorCodeEnum.K000000);
    }

    public static BaseResponse FAILED() {
        return new BaseResponse(CommonErrorCodeEnum.K000001);
    }

    public <E extends ErrorCode> BaseResponse(E e) {
        this.code = e.getCode();
        this.message = MessageSourceUtil.getMessage(code, null, LocaleContextHolder.getLocale());
        if(StringUtils.isEmpty(message)){
            this.message = e.getMsg();
        }
    }

    public <E extends ErrorCode>  BaseResponse(E e, Object[] args) {
        this.code = e.getCode();
        this.message = MessageSourceUtil.getMessage(code, args, LocaleContextHolder
                .getLocale());
        if(StringUtils.isEmpty(message)){
            this.message = MessageFormat.format(e.getMsg(),args);;
        }
    }

    /**
     * 结果码
     */
    @Schema(description = "结果码", required = true)
    private String code;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String message;

    /**
     * 错误内容
     */
    @Schema(description = "错误内容")
    private Object errorData;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private T context;

    /**
     * 特殊提示
     *
     * @param errorCode 异常码
     * @param message   消息
     * @param obj       业务错误的时候，但是依旧要返回数据
     * @return
     */
    public static BaseResponse info(String errorCode, String message, Object obj) {
        return new BaseResponse<>(errorCode, message, obj, null);
    }

    /**
     * 特殊提示
     *
     * @param errorCode 异常码
     * @param message   消息
     * @param obj       业务错误的时候，但是依旧要返回数据
     * @return
     */
    public static BaseResponse info(String errorCode, String message, Object obj, String context) {
        return new BaseResponse<>(errorCode, message, obj, context);
    }

    /**
     * 特殊提示
     *
     * @param errorCode 异常码
     * @param message   消息
     * @return
     */
    public static <T> BaseResponse<T> info(String errorCode, String message) {
        return new BaseResponse<>(errorCode, message, null, null);
    }

    /**
     * 失败
     *
     * @param message 消息
     * @return
     */
    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(CommonErrorCodeEnum.K000001.getCode(), message, null, null);
    }

    /**
     * 成功
     *
     * @param context 内容
     * @return
     */
    public static <T> BaseResponse<T> success(T context) {
        return new BaseResponse<>(CommonErrorCodeEnum.K000000.getCode(), CommonErrorCodeEnum.K000000.getMsg(), null, context);
    }

    public Boolean isSuccess(){
        return CommonErrorCodeEnum.K000000.getCode().equals(this.code);
    }
}
