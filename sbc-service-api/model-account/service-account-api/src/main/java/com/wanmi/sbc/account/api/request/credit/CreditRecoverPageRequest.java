package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.io.Serializable;

/**
 * @author houshuai
 * @date 2021/3/3 15:16
 * @description <p> 额度恢复历史记录查询 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditRecoverPageRequest  extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -3574129845967424131L;
    /**
     * 会员id
     */
    @NotBlank
    @Schema(description = "会员id")
    private String customerId;
}
