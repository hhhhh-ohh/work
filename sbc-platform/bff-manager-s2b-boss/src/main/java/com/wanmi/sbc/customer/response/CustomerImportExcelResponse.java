package com.wanmi.sbc.customer.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author minchen
 */
@Schema
@Data
public class CustomerImportExcelResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;

    /**
     * 短信发送成功条数
     */
    @Schema(description = "短信发送成功条数")
    private Integer sendMsgSuccessCount;

    /**
     * 短信发送失败条数
     */
    @Schema(description = "短信发送失败条数")
    private Integer sendMsgFailedCount;


}
