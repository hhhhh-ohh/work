package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeGetByIdAndPidRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 供应商id
     */
    private String providerId;

}
