package com.wanmi.sbc.order.api.request.payingmemberrecord;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除付费记录表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecordDelByIdRequest extends OrderBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    @Schema(description = "记录id")
    @NotNull
    private String recordId;
}
