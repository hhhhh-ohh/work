package com.wanmi.sbc.customer.api.request.follow;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.TerminalSource;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 店铺收藏的新增条件
 * Created by daiyitian on 2017/11/6.
 */
@Schema
@Data
public class StoreCustomerFollowAddRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @Schema(description = "客户ID", hidden = true)
    private String customerId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    @NotNull
    private Long storeId;


    /**
     * 终端
     */
    @Schema(description = "终端", hidden = true)
    private TerminalSource terminalSource;

}
