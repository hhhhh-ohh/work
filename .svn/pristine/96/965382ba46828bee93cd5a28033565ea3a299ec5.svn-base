package com.wanmi.sbc.account.api.request.credit;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wanmi.sbc.common.enums.CreditStateChangeType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * * 授信账户状态变更事件
 *
 * @author zhengyang
 * @since 2021-03-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditStateChangeEvent implements Serializable {

    private static final long serialVersionUID = 6326365796842633371L;

    @Schema(description = "变更类型")
    private CreditStateChangeType creditStateChangeType;

    @Schema(description = "变更金额")
    private BigDecimal amount;

    @Schema(description = "还款时授信账户是否过期")
    private Boolean expiredFlag;

    @Schema(description = "会员id")
    private String customerId;
}
