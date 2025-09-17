package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.account.bean.vo.LakalaSettlementViewVO;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;
import com.wanmi.sbc.common.base.MicroServicePage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 结算新增响应请求
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementPageResponse extends BasicResponse {

    private static final long serialVersionUID = -5372681048101615320L;
    /**
     * 结算分页数据 {@link SettlementViewVO}
     */
    @Schema(description = "结算分页数据")
    private MicroServicePage<SettlementViewVO> settlementViewVOPage;

    @Schema(description = "拉卡拉分账结算分页数据")
    private MicroServicePage<LakalaSettlementViewVO> lakalaSettlementViewVOPage;
}
