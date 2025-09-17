package com.wanmi.sbc.customer.api.request.follow;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* *
 * @Description:  店铺
 * @Author: Bob
 * @Date: 2019-04-02 18:30
*/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreFollowBystoreIdRequest extends BaseRequest {


    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    @NotNull
    private Long storeId;

}
