package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class PayingMemberLevelBaseVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 客户付费会员等级ID
     */

    @Schema(description = "客户付费会员等级ID")
    private Integer levelId;

    /**
     * 客户付费会员等级名称
     */
    @Schema(description = "客户付费会员等级名称")
    private String payingMemberLevelName;
}
