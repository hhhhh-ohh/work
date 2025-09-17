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
 * @className PayMemberGrowthNewTrendView
 * @description
 * @date 2022/5/26 5:57 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PayMemberGrowthNewTrendView implements Serializable {

    private static final long serialVersionUID = 658812888981392352L;
    /**
     * x轴数据
     */
    @JsonProperty(value = "xValue")
    private String xValue;

    /**
     * 付费会员总数
     */
    @Schema(description = "付费会员总数")
    private Long allCount;

    /**
     * 新增付费会员数
     */
    @Schema(description = "新增付费会员数")
    private Long dayGrowthCount;
}
