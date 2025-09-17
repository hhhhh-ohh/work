package com.wanmi.sbc.marketing.api.response.gift;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullGiftLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-20 16:44
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullGiftLevelListByMarketingIdAndCustomerResponse extends BasicResponse {

    /**
     * 活动规则列表
     */
    @Schema(description = "活动规则列表")
    private List<MarketingFullGiftLevelVO> levelList;

    /**
     * 赠品列表
     */
    @Schema(description = "赠品列表")
    private List<GoodsInfoVO> giftList;

    /**
     * 活动子类型
     */
    @Schema(description = "活动子类型")
    private MarketingSubType marketingSubType;

}
