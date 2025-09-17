package com.wanmi.sbc.account.api.response.credit.repay;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>客户授信还款修改结果</p>
 * @author chenli
 * @date 2021-03-15 16:21:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditRepayModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的客户授信还款信息
     */
    @Schema(description = "已修改的客户授信还款信息")
    private CustomerCreditRepayVO customerCreditRepayVO;
}
