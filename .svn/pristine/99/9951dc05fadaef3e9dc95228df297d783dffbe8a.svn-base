package com.wanmi.sbc.account.api.request.credit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author houshuai
 * @date 2021/2/27 13:52
 * @description <p> 授信账户请求参数 </p>
 */
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreditAccountPageRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -2789079595826035596L;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 会员账号
     */
    @Schema(description = "会员账号")
    private String customerAccount;

    /**
     * 是否使用
     */
    @Schema(description = "是否使用 0未使用 1已使用")
    private BoolFlag usedStatus;

    /**
     * 是否过期
     */
    @Schema(description = "是否过期 0未过期 1已过期")
    private BoolFlag expireStatus;

    /**
     * 当前时间
     */
    @Schema(description = "当前时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime nowTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 当前周期待还款金额
     */
    @Schema(description = "当前周期待还款金额")
    private BigDecimal repayAmount;

    @Schema(description = "是否启用")
    private BoolFlag enabled;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;
}
