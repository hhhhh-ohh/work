package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.SettlementTotalVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 结算单响应请求
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementTotalResponse extends BasicResponse {

    private static final long serialVersionUID = 247779612510131342L;
    /**
     * 结算单统计分组结果 {@link SettlementTotalVO}
     */
    @Schema(description = "结算单统计分组结果")
    private List<SettlementTotalVO> settlementTotalVOList;
}
