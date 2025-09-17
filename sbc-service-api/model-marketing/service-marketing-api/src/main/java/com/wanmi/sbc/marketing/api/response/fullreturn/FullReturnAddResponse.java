package com.wanmi.sbc.marketing.api.response.fullreturn;

import com.wanmi.sbc.marketing.bean.vo.MarketingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xufeng
 * @Description:
 * @Date: 2022-04-06 14:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullReturnAddResponse {

    /**
     * 营销视图对象
     */
    @Schema(description = "营销视图对象")
    private MarketingVO marketingVO;

}
