package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.account.bean.enums.FundsStatus;
import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 会员资金明细-更新对象
 * @createDate: 2019/2/19 11:06
 * @version: 1.0
 */
@Schema
@Data
public class CustomerFundsDetailModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 2798653895804228579L;
    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 业务编号
     */
    @Schema(description = "业务编号")
    private String businessId;

    /**
     * 资金状态
     */
    @Schema(description = "资金状态")
    private FundsStatus fundsStatus;

    /**
     * 佣金提现id
     */
    @Schema(description = "佣金提现id")
    private String drawCashId;

    /**
     * Tab类型 0: 全部, 1: 收入, 2: 支出, 3:分销佣金&邀新记录
     */
    @Schema(description = "ab类型 0: 全部, 1: 收入, 2: 支出, 3:分销佣金&邀新记录")
    private Integer tabType;
}
