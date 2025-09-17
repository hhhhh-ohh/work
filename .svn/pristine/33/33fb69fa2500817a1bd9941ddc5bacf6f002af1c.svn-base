package com.wanmi.sbc.marketing.api.response.fullreturn;

import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xufeng
 * @Description:
 * @Date: 2022-04-11 16:44
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullReturnLevelListByMarketingIdAndCustomerResponse {

    /**
     * 活动规则列表
     */
    @Schema(description = "活动规则列表")
    private List<MarketingFullReturnLevelVO> levelList;

    /**
     * 赠券列表
     */
    @Schema(description = "赠券列表")
    private List<CouponInfoVO> returnList;

    /**
     * 活动子类型
     */
    @Schema(description = "活动子类型")
    private MarketingSubType marketingSubType;

}
