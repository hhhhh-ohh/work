package com.wanmi.sbc.account.api.response.credit.account;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CreditAccountForRepayResponse
 * @Description 在线还款查询授信账户和还款详情
 * @author chenli
 * @date 2021/3/3 16:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditAccountForRepayResponse extends BasicResponse {

    private static final long serialVersionUID = -7825916480457709685L;
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
     * 还款单关联订单列表
     */
    @Schema(description = "还款单关联订单列表")
    private List<CustomerCreditOrderVO> customerCreditOrderVOList;
}
