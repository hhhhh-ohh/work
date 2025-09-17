package com.wanmi.sbc.marketing.api.response.gift;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class FullGiftAddResponse extends BasicResponse {

    /**
     * 营销视图对象
     */
    @Schema(description = "营销视图对象")
    private MarketingVO marketingVO;

}
