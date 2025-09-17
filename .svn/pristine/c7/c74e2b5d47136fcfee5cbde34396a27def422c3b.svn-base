package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author houshuai
 * @date 2021/3/1 14:56
 * @description <p> 待还款订单参数 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class RepayOrderPageRequest extends BaseQueryRequest implements Serializable {
    private static final long serialVersionUID = 3542275307681801517L;
    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 是否当前周期 0否 1是
     */
    @Schema(description = "是否当前周期 0否 1是")
    private Integer isCurrent;

    /**
     * 还款单号
     */
    @Schema(description = "还款单号")
    private String repayOrderCode;
}
