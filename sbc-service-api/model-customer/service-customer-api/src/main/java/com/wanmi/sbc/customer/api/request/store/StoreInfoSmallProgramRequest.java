package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author feitingting
 * Created by feitingting on 2019/1/9.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfoSmallProgramRequest extends BaseRequest {
    /**
     * 店铺ID
     */
    @Schema(description = "店铺id")
    private  Long storeId;
    /**
     * 店铺码地址
     */
    @Schema(description = "店铺码地址")
    private  String codeUrl;
}
