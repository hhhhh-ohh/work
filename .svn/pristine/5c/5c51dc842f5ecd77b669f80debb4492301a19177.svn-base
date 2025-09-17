package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.account.api.request.finance.record.BasePageRequest;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.*;

/**
 * 授信审核查询参数
 * @author zhegnyang
 * @since  2021-03-01 10:22:24
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditAuditQueryRequest extends BasePageRequest implements Serializable {

    @Schema(description = "用户账号")
    private String customerAccount;

    @Schema(description = "用户姓名")
    private String customerName;

    @Schema(description = "审核状态")
    private CreditAuditStatus auditStatus;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;
}
