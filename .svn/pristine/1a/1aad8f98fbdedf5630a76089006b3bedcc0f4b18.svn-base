package com.wanmi.sbc.order.api.response.settlement;

import com.wanmi.sbc.account.bean.vo.LakalaSettlementViewVO;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;
import com.wanmi.sbc.order.bean.vo.SettlementDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 返回用于刷入es
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettlementForEsResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 结算单 {@link SettlementDetailVO}
     */
    @Schema(description = "结算单")
    private List<SettlementViewVO> lists;

    @Schema(description = "拉卡拉结算单")
    private List<LakalaSettlementViewVO> lakalaLists;
}
