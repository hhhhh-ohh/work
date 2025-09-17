package com.wanmi.sbc.customer.api.request.level;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 会员等级参数
 * Created by CHENLI on 2017/4/17.
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerLevelModifyRequest extends CustomerLevelAddRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    @NotNull
    private Long customerLevelId;

}
