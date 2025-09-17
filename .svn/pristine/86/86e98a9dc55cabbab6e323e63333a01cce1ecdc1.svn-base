package com.wanmi.sbc.customer.api.request.payingmemberlevel;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除付费会员等级表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberLevelDelByIdRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 付费会员等级id
     */
    @Schema(description = "付费会员等级id")
    @NotNull
    private Integer levelId;
}
