package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotNull;


/**
 * <p>单个查询授信订单信息请求参数</p>
 *
 * @author chenli
 * @date 2020-03-10 16:21:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditRepayByRepayCodeRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 还款单号
     */
    @Schema(description = "还款单号")
    @NotNull
    private String repayCode;

}