package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/4/23.
 */
@Schema
@Data
public class EmployeeResponse extends BasicResponse {

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String employeeId;

    /**
     * 会员名字
     */
    @Schema(description = "会员名字")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String employeeName;

    /**
     * 业务员手机号
     */
    @Schema(description = "业务员手机号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String employeeMobile;
}
