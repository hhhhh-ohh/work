package com.wanmi.sbc.customer.api.request.follow;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.customer.api.request.follow.validGroups.StoreFollowAdd;
import com.wanmi.sbc.customer.api.request.follow.validGroups.StoreFollowDelete;
import com.wanmi.sbc.customer.api.request.follow.validGroups.StoreFollowFilter;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 店铺收藏
 * Created by daiyitian on 2017/11/6.
 */
@Schema
@Data
public class StoreCustomerFollowRequest extends BaseQueryRequest {

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    @NotNull(groups = {StoreFollowAdd.class})
    private Long storeId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    @NotEmpty(groups = {StoreFollowDelete.class, StoreFollowFilter.class})
    private List<Long> storeIds;


    /**
     * 终端
     */
    @Schema(description = "终端")
    private TerminalSource terminalSource;
}
