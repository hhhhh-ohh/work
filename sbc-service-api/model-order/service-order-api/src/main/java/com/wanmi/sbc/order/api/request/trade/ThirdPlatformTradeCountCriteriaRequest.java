package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.ThirdPlatformTradeQueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 11:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ThirdPlatformTradeCountCriteriaRequest extends BaseRequest {

    /**
     * 分页参数
     */
    @Schema(description = "分页参数")
    private ThirdPlatformTradeQueryDTO tradePageDTO;
}
