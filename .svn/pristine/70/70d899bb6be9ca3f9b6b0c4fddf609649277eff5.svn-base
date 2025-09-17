package com.wanmi.sbc.account.api.response.ledgerfunds;

import com.wanmi.sbc.account.bean.vo.LedgerFundsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>会员分账资金列表结果</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFundsListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会员分账资金列表结果
     */
    @Schema(description = "会员分账资金列表结果")
    private List<LedgerFundsVO> ledgerFundsVOList;
}
