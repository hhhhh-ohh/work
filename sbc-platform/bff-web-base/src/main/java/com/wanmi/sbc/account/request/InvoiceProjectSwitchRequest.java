package com.wanmi.sbc.account.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Created by CHENLI on 2017/12/12.
 */
@Schema
@Data
public class InvoiceProjectSwitchRequest extends BaseRequest {
    /**
     * 商家id集合
     */
    @Schema(description = "商家id集合")
    private List<Long> companyInfoIds;
}
