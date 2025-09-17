package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankVO {


    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long bankId;

    /**
     * 银行名称
     */
    @Schema(description = "银行名称")
    private String bankName;

    /**
     * 银行code
     */
    @Schema(description = "银行code")
    private String bankCode;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private DeleteFlag deleteFlag;
}
