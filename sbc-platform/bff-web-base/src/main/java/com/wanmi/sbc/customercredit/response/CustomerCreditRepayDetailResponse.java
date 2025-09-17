package com.wanmi.sbc.customercredit.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditRepayDetailResponse extends BasicResponse {

    /**
     * 是否还款中 true 还款中 false 否
     */
    @Schema(description = "是否还款中 true 还款中 false 否")
    private Boolean waitRepay;

    /**
     * 还款账号和还款信息
     */
    @Schema(description = "还款账号和还款信息")
    private CustomerCreditRepayVO customerCreditRepayVO;

    /**
     * 交易单列表
     */
    @Schema(description = "交易单列表")
    private List<TradeVO> tradeVOList;
}
