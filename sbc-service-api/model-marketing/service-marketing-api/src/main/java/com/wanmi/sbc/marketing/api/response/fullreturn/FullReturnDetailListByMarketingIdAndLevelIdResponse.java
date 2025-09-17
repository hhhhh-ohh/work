package com.wanmi.sbc.marketing.api.response.fullreturn;

import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnDetailVO;

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
public class FullReturnDetailListByMarketingIdAndLevelIdResponse {

    /**
     * 营销详情列表
     */
    @Schema(description = "营销满赠详情列表")
    private List<MarketingFullReturnDetailVO> fullReturnDetailVOList;


}
