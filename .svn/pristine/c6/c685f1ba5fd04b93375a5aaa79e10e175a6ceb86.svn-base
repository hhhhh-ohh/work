package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.StoreCustomerRelaDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author hejiawen<br>
 * @version 1.0<br>
 * @taskId <br>
 * @createTime 2018-09-12 14:35 <br>
 * @see com.wanmi.sbc.customer.api.request.store <br>
 * @since V1.0<br>
 */

@Schema
@Data
public class StoreCustomerRelaUpdateRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String customerId;

    /**
     * 员工ID
     */
    @Schema(description = "员工ID")
    private String employeeId;

    /**
     * 店铺-用户关系 {@link StoreCustomerRelaDTO}
     */
    @Schema(description = "店铺-用户关系")
    private StoreCustomerRelaDTO storeCustomerRelaDTO;
}
