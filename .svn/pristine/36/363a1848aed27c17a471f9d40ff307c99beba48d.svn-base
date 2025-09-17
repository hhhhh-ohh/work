package com.wanmi.sbc.account.api.response.ledgerfunds;

import com.wanmi.sbc.account.bean.vo.LedgerFundsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）会员分账资金信息response</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFundsByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会员分账资金信息
     */
    @Schema(description = "会员分账资金信息")
    private LedgerFundsVO ledgerFundsVO;
}
