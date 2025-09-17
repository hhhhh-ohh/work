package com.wanmi.sbc.marketing.api.response.market;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 16:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingQueryStartingByIdsResponse extends BasicResponse {

    /**
     * 营销id字符形式列表
     */
    @Schema(description = "营销id列表")
    private List<String> marketingIdList;

}
