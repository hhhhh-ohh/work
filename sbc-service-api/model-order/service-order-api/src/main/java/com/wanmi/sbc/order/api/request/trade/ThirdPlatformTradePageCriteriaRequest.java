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
 * @Author: qiaokang
 * @Description:
 * @Date: 2020-03-27 13:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ThirdPlatformTradePageCriteriaRequest extends BaseRequest {

    /**
     * 是否是可退单查询
     */
    @Schema(description = "是否是可退单查询",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private boolean isReturn;

    /**
     * 分页参数
     */
    @Schema(description = "分页参数")
    private ThirdPlatformTradeQueryDTO tradePageDTO;
}
