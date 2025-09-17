package com.wanmi.sbc.order.api.request.paytraderecord;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>根据id查询单笔交易记录request</p>
 * Created by of628-wenzhi on 2018-08-13-下午3:47.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeRecordByIdRequest extends OrderBaseRequest {

    private static final long serialVersionUID = 4437841431786573922L;
    /**
     * 交易记录id
     */
    @Schema(description = "交易记录id")
    @NotBlank
    private String recodId;
}
