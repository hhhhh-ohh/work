package com.wanmi.ares.response;

import com.wanmi.ares.view.paymember.PayMemberGrowthNewTrendView;
import com.wanmi.ares.view.paymember.PayMemberGrowthRenewalTrendView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className PayMemberGrowthRenewalTrendResponse
 * @description
 * @date 2022/5/26 6:01 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayMemberGrowthRenewalTrendResponse implements Serializable {
    private static final long serialVersionUID = -1851731004841780070L;

    /**
     * 会员续费趋势数据
     */
    @Schema(description = "会员续费趋势数据")
    private List<PayMemberGrowthRenewalTrendView> viewList;
}
