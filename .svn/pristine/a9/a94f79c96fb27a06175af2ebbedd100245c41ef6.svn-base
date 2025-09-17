package com.wanmi.sbc.common.exception;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * 统一异常处理
 * Created by jinwei on 31/3/2017.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SbcRuntimeException extends RuntimeException {

    private String errorCode = "";

    private Object data;

    private String result = "fail";

    private Object[] params;

    /**
     * 默认构造，展示系统异常
     */
    public SbcRuntimeException() {
        super();
        this.errorCode = CommonErrorCodeEnum.K000000.getCode();
    }

    /**
     * 默认errorCode为 空字符串
     *
     * @param cause
     */
    public SbcRuntimeException(Throwable cause) {
        this("", cause);
    }

    /**
     * 只有出错信息
     * 多用于系统自身发生的异常，此时没有上级异常
     *
     * @param errorCode 错误码
     * @param result    出错信息
     */
    protected SbcRuntimeException(String errorCode, String result) {
        super(result);
        this.result = result;
        this.errorCode = errorCode;
    }

    public <E extends ErrorCode> SbcRuntimeException(E e){
        super(e.getMsg());
        this.result = e.getMsg();
        this.errorCode = e.getCode();
    }


    /**
     * 只有出错信息
     * 多用于系统自身发生的异常，此时没有上级异常
     *
     * @param errorCode 异常码 异常码的错误信息会被messageSource读取
     */
    protected SbcRuntimeException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }


    /**
     * 只有出错信息
     * 多用于系统自身发生的异常，此时没有上级异常
     *
     * @param errorCode
     * @param params
     */
    protected SbcRuntimeException(String errorCode, Object[] params) {
        super(String.valueOf(params != null && params.length > 0 ? params[0] : errorCode));
        this.errorCode = errorCode;
        this.params = params;
    }

    public <E extends ErrorCode> SbcRuntimeException(E e,Object[] params){
        super(String.valueOf(params != null && params.length > 0 ? params[0] :e.getCode()));
        this.params = params;
        this.result = MessageFormat.format(e.getMsg(),params);
        this.errorCode = e.getCode();
    }

    /**
     * 错误码 + 上级异常
     *
     * @param errorCode
     * @param cause
     */
    protected SbcRuntimeException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public <E extends ErrorCode> SbcRuntimeException(E e,Throwable cause){
        this(e.getCode(),cause);
        this.result = e.getMsg();
    }
    /**
     * 返回数据 + 异常码
     *
     * @param data
     * @param errorCode
     */
    protected SbcRuntimeException(Object data, String errorCode) {
        this.data = data;
        this.errorCode = errorCode;
    }

    public <E extends ErrorCode> SbcRuntimeException(Object data,E e){
        this(data,e.getCode());
        this.result = e.getMsg();
    }


    /**
     * 返回数据 + 异常码 + 异常信息
     *
     * @param data
     * @param errorCode
     */
    protected SbcRuntimeException(Object data, String errorCode, String errMsg) {
        super(errMsg);
        this.data = data;
        this.errorCode = errorCode;
        this.result = errMsg;
    }

    /**
     * 返回数据 + 异常码 + 异常信息所用参数
     *
     * @param data
     * @param errorCode
     */
    protected SbcRuntimeException(String errorCode, Object[] params, Object data) {
        this.errorCode = errorCode;
        this.params = params;
        this.data = data;
    }

    public <E extends ErrorCode> SbcRuntimeException(E e,Object[] params, Object data){
        this(e.getCode(),params,data);
        this.result = MessageFormat.format(e.getMsg(),params);
    }


    /**
     * 只有K999999允许设置错误信息，其他的不允许这么操作
     * @param e
     * @param result
     * @param
     */
    public SbcRuntimeException(CommonErrorCodeEnum e,String result){
        this(e);
        if(e.equals(CommonErrorCodeEnum.K999999)){
            this.result = result;
        }

    }
    /**
     * 返回值 + 异常链
     *
     * @param data
     * @param cause
     */
    public SbcRuntimeException( Throwable cause,Object data) {
        super(cause);
        this.data = data;
    }
}
