package com.wanmi.ares.view.paymember;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className PayMemberGrowthPageView
 * @description
 * @date 2022/5/26 3:42 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PayMemberGrowthRenewalView implements Serializable {

    private static final long serialVersionUID = -1960139928949106826L;

    /**
     * 日期
     */
    @Schema(description = "统计日期")
    private String baseDate;

    /**
     * 续费会员数
     */
    @Schema(description = "续费会员数")
    private Long dayRenewalCount;

    /**
     * 到期未续费会员数
     */
    @Schema(description = "到期未续费会员数")
    private Long dayOvertimeCount;
}
