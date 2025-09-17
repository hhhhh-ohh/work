package com.wanmi.sbc.order.api.response.settlement;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.LakalaSettlementDetailVO;
import com.wanmi.sbc.order.bean.vo.SettlementDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据计算单id查询结算明细列表返回结构</p>
 * Created by of628-wenzhi on 2018-10-13-下午7:14.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettlementDetailListBySettleUuidResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 结算明细列表 {@link SettlementDetailVO}
     */
    @Schema(description = "结算明细列表")
    private List<SettlementDetailVO> settlementDetailVOList;

    @Schema(description = "拉卡拉结算明细列表")
    private List<LakalaSettlementDetailVO> lakalaSettlementDetailVOList;
}
