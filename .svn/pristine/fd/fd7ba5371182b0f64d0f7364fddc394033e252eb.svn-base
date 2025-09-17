package com.wanmi.sbc.customer.api.request.follow;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺收藏批量已被关注条件
 * Created by daiyitian on 2017/11/6.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreCustomerFollowExistsBatchRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @Schema(description = "客户ID", hidden = true)
    private String customerId;

    /**
     * 多个店铺ID
     */
    @Schema(description = "多个店铺ID")
    @NotEmpty
    private List<Long> storeIds;

}
