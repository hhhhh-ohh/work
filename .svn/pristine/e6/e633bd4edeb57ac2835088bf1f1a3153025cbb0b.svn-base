package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>客户授信还款取消参数</p>
 *
 * @author chenli
 * @date 2021-03-09 16:21:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditRepayCancelRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 退款记录id
     */
    @Schema(description = "退款记录id")
    private String id;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String customerId;

}