package com.wanmi.sbc.marketing.api.response.preferential;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialGoodsDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author bob
 * @className DetailByMIdsAndLIdsResponse
 * @description TODO
 * @date 2022/12/4 00:29
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeveByMIdsResponse extends BasicResponse {

    @Schema(description = "营销加价购级别列表")
    private List<MarketingPreferentialLevelVO> levelVOList;
}
