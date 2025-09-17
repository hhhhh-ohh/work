package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>单个查询分销员请求参数</p>
 *
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerByInviteCustomerIdRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 被邀请会员id
     */
    private String inviteCustomerId;

}