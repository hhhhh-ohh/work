package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对账数据导出Excel 返回对象
 * @author CHENLI
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRecordToExcelResponse extends BasicResponse {
    /**
     * base64位字符串形式的文件流
     */
    @Schema(description = "base64位字符串形式的文件流")
    private String file;
}
