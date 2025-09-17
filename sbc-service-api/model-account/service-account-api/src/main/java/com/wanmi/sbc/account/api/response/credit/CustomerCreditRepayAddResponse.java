package com.wanmi.sbc.account.api.response.credit;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>新增客户授信还款信息response</p>
 *
 * @author chenli
 * @date 2020-03-24 16:21:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditRepayAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 客户授信还款信息
     */
    @Schema(description = "客户授信还款信息")
    private CustomerCreditRepayVO customerCreditRepayVO;

}