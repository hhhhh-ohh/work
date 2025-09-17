package com.wanmi.sbc.customer.api.response.payingmembercustomerrel;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className PayingMemberCheckResponse
 * @description
 * @date 2022/6/8 3:26 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayingMemberCheckResponse implements Serializable {
    private static final long serialVersionUID = -60517036998211073L;

    /**
     * 是否为付费会员
     */
    @Schema(description = "是否为付费会员")
    private Boolean payMemberFlag;
}
