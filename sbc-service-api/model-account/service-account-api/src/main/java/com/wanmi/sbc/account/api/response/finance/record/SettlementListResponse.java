package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.account.bean.vo.LakalaSettlementVO;
import com.wanmi.sbc.account.bean.vo.LakalaSettlementViewVO;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 结算单列表
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementListResponse extends BasicResponse {

    private static final long serialVersionUID = -4070934459053403658L;
    /**
     * 待结算数量
     */
    @Schema(description = "结算单列表")
    private List<SettlementViewVO> lists;

    @Schema(description = "拉卡拉结算单列表")
    private List<LakalaSettlementViewVO> lakalaSettlementViewVOS;
}
