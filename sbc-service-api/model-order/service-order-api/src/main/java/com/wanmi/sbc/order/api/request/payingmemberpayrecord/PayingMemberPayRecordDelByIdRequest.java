package com.wanmi.sbc.order.api.request.payingmemberpayrecord;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除付费会员支付记录表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPayRecordDelByIdRequest extends OrderBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private String id;
}
