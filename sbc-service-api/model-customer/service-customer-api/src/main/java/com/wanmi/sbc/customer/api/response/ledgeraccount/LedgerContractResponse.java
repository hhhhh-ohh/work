package com.wanmi.sbc.customer.api.response.ledgeraccount;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className LedgerContractResponse
 * @description
 * @date 2022/7/21 6:17 PM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContractResponse implements Serializable {
    private static final long serialVersionUID = 908452676413359124L;

    @Schema(description = "合同内容")
    private String contract;
}
