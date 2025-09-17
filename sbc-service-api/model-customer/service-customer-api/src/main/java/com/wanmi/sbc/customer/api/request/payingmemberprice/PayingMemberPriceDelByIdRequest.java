package com.wanmi.sbc.customer.api.request.payingmemberprice;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除付费设置表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPriceDelByIdRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 付费设置id
     */
    @Schema(description = "付费设置id")
    @NotNull
    private Integer priceId;
}
