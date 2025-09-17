package com.wanmi.sbc.customer.api.request.level;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className CustomerLevelForMarketingRequest
 * @description 客户等级查询请求参数,包含校验会员信息
 * @author songhanlin
 * @date 2021/6/28 下午4:06
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLevelForMarketingRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    @NotNull
    private String customerId;

    @Schema(description = "店铺ID")
    @NotNull
    private Long storeId;

    @Schema(description = "营销参加的店铺等级")
    @NotNull
    private String joinLevel;
}
