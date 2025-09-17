package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenli
 * @Date: Created In 上午9:40 2021/3/15
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeCreditHasRepaidRequest extends BaseRequest {

    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 是否已经授信还款
     */
    @Schema(description = "是否已经授信还款")
    private Boolean hasRepaid;

}
