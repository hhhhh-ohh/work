package com.wanmi.sbc.customer.api.request.quicklogin;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>根据用户Id&第三方登录方式查询第三方登录关系request</p>
 * Created by daiyitian on 2018-08-13-下午3:47.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdLoginRelationByCustomerRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    @Schema(description = "用户Id")
    @NotNull
    private String customerId;

    /**
     * 第三方类型 0:wechat
     */
    @Schema(description = "第三方类型")
    @NotNull
    private ThirdLoginType thirdLoginType;

    @Schema(description = "是否被删除")
    private DeleteFlag delFlag;

    @Schema(description = "店铺Id")
    private Long storeId;
}
