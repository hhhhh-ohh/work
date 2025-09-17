package com.wanmi.sbc.customer.api.request.payingmemberlevel;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className PayingMemberLevelCustomerRequest
 * @description
 * @date 2022/5/18 10:00 AM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberLevelCustomerRequest implements Serializable {
    private static final long serialVersionUID = -2634179912939489664L;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 当没拥有付费会员时，是否填充默认数据
     */
    @Schema(description = "是否填充默认数据")
    private Boolean defaultFlag;

}
