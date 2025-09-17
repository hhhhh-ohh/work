package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.account.bean.enums.FundsStatus;
import com.wanmi.sbc.account.bean.enums.FundsType;
import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 会员资金明细-导出查询对象
 * @author: of2975
 * @createDate: 2019/4/30 11:06
 * @version: 1.0
 */
@Schema
@Data
public class CustomerFundsDetailExportRequest extends BaseQueryRequest implements Serializable {

    /**
     * jwt token
     */
    @Schema(description = "token")
    private String token;

    /**
     * 批量查询-明细主键List
     */
    @Schema(description = "批量查询-明细主键List")
    private List<String> customerFundsDetailIdList;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * Tab类型 0: 全部, 1: 收入, 2: 支出, 3:分销佣金&邀新记录
     */
    @Schema(description = "Tab类型 0: 全部, 1: 收入, 2: 支出, 3:分销佣金&邀新记录")
    private Integer tabType;

    /**
     * 资金类型集合
     */
    @Schema(description = "资金类型集合")
    private List<Integer> fundsTypeList;

    /**
     * 资金类型
     */
    @Schema(description = "资金类型")
    private FundsType fundsType;

    /**
     * 资金状态
     */
    @Schema(description = "资金状态")
    private FundsStatus fundsStatus;

    /**
     * 业务编号
     */
    @Schema(description = "业务编号")
    private String businessId;

    /**
     * 入账开始时间
     */
    @Schema(description = "入账开始时间")
    private String startTime;

    /**
     * 入账结束时间
     */
    @Schema(description = "入账结束时间")
    private String endTime;

    /**
     * 佣金提现id
     */
    @Schema(description = "佣金提现id")
    private String drawCashId;
}
