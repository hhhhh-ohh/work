package com.wanmi.sbc.customer.api.request.payingmemberstorerel;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除商家与付费会员等级关联表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberStoreRelDelByIdRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private Long id;
}
