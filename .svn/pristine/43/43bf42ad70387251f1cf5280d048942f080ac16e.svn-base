package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询退单列表请求结构(不包含已作废状态以及拒绝收货的退货单与拒绝退款的退款单)
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderNotVoidByTidRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @NotBlank
    private String tid;

}
