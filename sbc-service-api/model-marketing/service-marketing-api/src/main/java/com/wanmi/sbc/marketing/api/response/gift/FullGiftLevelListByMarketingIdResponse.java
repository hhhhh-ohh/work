package com.wanmi.sbc.marketing.api.response.gift;

import com.wanmi.sbc.common.base.BasicResponse;
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
 * @Date: 2018-11-20 14:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullGiftLevelListByMarketingIdResponse extends BasicResponse {

    /**
     * 活动规则列表
     */
    @Schema(description = "活动规则列表")
    private List<MarketingFullGiftLevelVO> fullGiftLevelVOList;

}
