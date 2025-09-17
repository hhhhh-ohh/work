package com.wanmi.sbc.order.api.response.payingmemberrecord;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xuyunpeng
 * @className RightsCustomerIdsResponse
 * @description
 * @date 2022/5/14 5:49 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayingMemberRecordCustomerResponse implements Serializable {
    private static final long serialVersionUID = -6119473604453237067L;

    /**
     * "会员付费记录, key:customerId value:recordId
     */
    @Schema(description = "会员付费记录, key:customerId value:recordId")
    private Map<String, String> recordMap;
}
