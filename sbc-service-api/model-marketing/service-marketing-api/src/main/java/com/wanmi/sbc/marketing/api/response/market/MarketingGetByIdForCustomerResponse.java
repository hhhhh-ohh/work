package com.wanmi.sbc.marketing.api.response.market;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Description:
* @Author: ZhangLingKe
* @CreateDate: 2018/11/22 9:56
*/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingGetByIdForCustomerResponse extends BasicResponse {

    /**
     * 终端营销视图
     */
    @Schema(description = "终端营销视图")
    private MarketingForEndVO marketingForEndVO;

}
