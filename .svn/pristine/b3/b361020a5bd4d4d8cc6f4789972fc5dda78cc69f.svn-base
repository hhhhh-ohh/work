package com.wanmi.sbc.marketing.api.response.gift;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullGiftDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 根据营销id和等级id批量查询赠品明细的响应类
 * @author daiyitian
 * @date 2021/5/13 17:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullGiftDetailListByMarketingIdsAndLevelIdsResponse extends BasicResponse {

    @Schema(description = "营销满赠详情列表")
    private List<MarketingFullGiftDetailVO> fullGiftDetailVOList;
}
