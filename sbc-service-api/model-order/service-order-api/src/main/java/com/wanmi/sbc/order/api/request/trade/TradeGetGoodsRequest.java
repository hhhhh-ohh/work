package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 17:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeGetGoodsRequest extends BaseRequest {

    /**
     *
     */
    @Schema(description = "skuIds")
    private List<String> skuIds;
}
