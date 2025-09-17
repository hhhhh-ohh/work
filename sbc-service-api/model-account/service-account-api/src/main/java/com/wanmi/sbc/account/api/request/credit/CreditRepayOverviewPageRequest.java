package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.*;

/***
 * 授信概览查询请求参数
 * @author zhengyang
 * @date 2021-03-11
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditRepayOverviewPageRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = 3073399382743811637L;
    /**
     * 还款状态
     */
    @Schema(description = "还款状态")
    private CreditRepayStatus repayStatus;

    /**
     * 还款单号
     */
    @Schema(description = "还款单号")
    private String repayOrderCode;

    /**
     * 会员账户
     */
    @Schema(description = "会员账户")
    private String customerAccount;

    /**
     * 会员名
     */
    @Schema(description = "会员名")
    private String customerName;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;
}
