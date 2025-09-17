package com.wanmi.ares.response;

import com.wanmi.ares.view.paymember.PayMemberGrowthNewTrendView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className PayMemberGrowthNewTrendResponse
 * @description
 * @date 2022/5/26 6:00 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayMemberGrowthNewTrendResponse implements Serializable {
    private static final long serialVersionUID = 3838426908225123159L;

    /**
     * 会员新增趋势数据
     */
    @Schema(description = "会员新增趋势数据")
    private List<PayMemberGrowthNewTrendView> viewList;
}
