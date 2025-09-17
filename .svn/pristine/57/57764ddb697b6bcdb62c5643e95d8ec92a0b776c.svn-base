package com.wanmi.ares.view.paymember;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className PayMemberGrowthRenewalTrendView
 * @description
 * @date 2022/5/26 5:58 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PayMemberGrowthRenewalTrendView implements Serializable {
    private static final long serialVersionUID = 4058626195339170934L;

    /**
     * x轴数据
     */
    @JsonProperty(value = "xValue")
    private String xValue;

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
