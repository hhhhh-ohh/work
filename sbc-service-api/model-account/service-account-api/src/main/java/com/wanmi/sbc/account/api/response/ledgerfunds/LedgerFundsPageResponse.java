package com.wanmi.sbc.account.api.response.ledgerfunds;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.account.bean.vo.LedgerFundsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>会员分账资金分页结果</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFundsPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会员分账资金分页结果
     */
    @Schema(description = "会员分账资金分页结果")
    private MicroServicePage<LedgerFundsVO> ledgerFundsVOPage;
}
