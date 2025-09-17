package com.wanmi.sbc.marketing.api.response.market;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 14:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingStartResponse extends BasicResponse {

    /**
     * 数据操作结果
     */
    @Schema(description = "数据操作结果")
    private Integer resultNum;

}
