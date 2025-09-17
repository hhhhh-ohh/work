package com.wanmi.sbc.customer.api.response.ledgeraccount;

import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）清分账户信息response</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 清分账户信息
     */
    @Schema(description = "清分账户信息")
    private LedgerAccountVO ledgerAccountVO;
}
