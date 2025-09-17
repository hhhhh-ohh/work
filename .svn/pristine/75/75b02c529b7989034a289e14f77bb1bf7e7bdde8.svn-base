package com.wanmi.sbc.account.api.request.funds;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.FundsStatus;
import com.wanmi.sbc.account.bean.enums.FundsSubType;
import com.wanmi.sbc.account.bean.enums.FundsType;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员资金明细-新增对象
 *
 * @author: Geek Wang
 * @createDate: 2019/2/19 11:06
 * @version: 1.0
 */
@Schema
@Data
public class CustomerFundsDetailAddRequest extends BaseRequest {

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
     * 资金类型
     */
    @Schema(description = "资金类型")
    private FundsType fundsType;

    /**
     * 佣金提现id
     */
    @Schema(description = "佣金提现id")
    private String drawCashId;

    /**
     * 收支金额
     */
    @Schema(description = "收支金额")
    private BigDecimal receiptPaymentAmount;

    /**
     * 资金是否成功入账 0:否，1：成功
     */
    @Schema(description = "资金是否成功入账 0:否，1：成功")
    private FundsStatus fundsStatus;

    /**
     * 账户余额
     */
    @Schema(description = "账户余额")
    private BigDecimal accountBalance;

    /**
     * 资金账务子类型
     */
    @Schema(description = "资金账务子类型")
    private FundsSubType subType;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

}
