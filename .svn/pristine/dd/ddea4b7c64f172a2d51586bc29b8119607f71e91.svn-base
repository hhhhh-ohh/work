package com.wanmi.sbc.account.api.response.credit.repay;

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
 * <p>根据还款单号查询任意（包含已删除）客户授信还款信息response</p>
 *
 * @author chenli
 * @date 2021-03-15 16:21:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditRepayAndOrdersByRepayCodeResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 客户授信还款信息
     */
    @Schema(description = "客户授信还款信息")
    private CustomerCreditRepayVO customerCreditRepayVO;

    /**
     * 还款关联订单
     */
    @Schema(description = "客户授信还款信息")
    private List<CustomerCreditOrderVO> creditOrderVOList;

}