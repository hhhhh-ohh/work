package com.wanmi.sbc.account.api.constant;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 基础银行信息
 * Created by sunkun on 2017/12/6.
 */
@Schema
@Data
@AllArgsConstructor
@Builder
public class BaseBank {

    /**
     * 银行名称
     */
    @Schema(description = "银行名称")
    private String bankName;

    /**
     * 银行编号
     */
    @Schema(description = "银行编号")
    private String bankCode;
}
