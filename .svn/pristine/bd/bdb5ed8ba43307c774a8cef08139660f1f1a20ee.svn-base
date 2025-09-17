package com.wanmi.sbc.customer.api.response.follow;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺收藏批量关注响应
 * Created by bail on 2017/11/29.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreCustomerFollowExistsBatchResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;

    /**
     * 已关注的店铺ID
     */
    @Schema(description = "已关注的店铺ID")
    private List<Long> storeIds;
}
