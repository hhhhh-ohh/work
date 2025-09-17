package com.wanmi.sbc.customer.api.response.ledgeraccount;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>清分账户分页结果</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 清分账户分页结果
     */
    @Schema(description = "清分账户分页结果")
    private MicroServicePage<LedgerAccountVO> ledgerAccountVOPage;
}
