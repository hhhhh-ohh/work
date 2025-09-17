package com.wanmi.sbc.customer.api.response.ledgeraccount;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>清分账户新增结果</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountSaveResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 待签约的商户合同链接
     */
    @Schema(description = "待签约的商户合同链接")
    private String url;
}
