package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.account.bean.enums.AccountRecordType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>根据退单号和对账类型删除参数结构</p>
 * Created by daiyitian on 2018-10-25-下午7:39.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountRecordDeleteByReturnOrderCodeAndTypeRequest extends BasePageRequest {

    private static final long serialVersionUID = -8240842777552836494L;
    /**
     * 退单号
     */
    @Schema(description = "退单号")
    @NotBlank
    private String returnOrderCode;


    /**
     * 需要查询的对账记录类型 {@link AccountRecordType}
     */
    @Schema(description = "需要查询的对账记录类型")
    @NotNull
    private AccountRecordType accountRecordType;

}
