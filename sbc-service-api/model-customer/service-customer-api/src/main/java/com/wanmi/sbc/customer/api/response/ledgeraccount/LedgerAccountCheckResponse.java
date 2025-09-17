package com.wanmi.sbc.customer.api.response.ledgeraccount;

import com.wanmi.sbc.customer.bean.enums.LedgerAccountState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className LedgerAccountCheckResponse
 * @description
 * @date 2022/7/12 7:15 PM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountCheckResponse implements Serializable {
    private static final long serialVersionUID = 6303072444004066799L;

    /**
     * 清分账户状态
     *
     * @see LedgerAccountState
     */
    @Schema(description = "清分账户状态 0、未审核 1、审核中 2、已审核 3、审核失败")
    private Integer state;
}
