package com.wanmi.sbc.order.api.request.settlement;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>删除结算明细条件</p>
 * Created by of628-wenzhi on 2018-10-13-下午6:35.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementDetailDeleteRequest extends OrderBaseRequest {
    private static final long serialVersionUID = 6123758772470413034L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private String startDate;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private String endDate;
}
