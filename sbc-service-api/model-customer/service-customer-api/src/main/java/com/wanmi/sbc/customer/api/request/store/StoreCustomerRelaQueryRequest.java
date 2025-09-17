package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author hejiawen<br>
 * @version 1.0<br>
 * @taskId <br>
 * @createTime 2018-09-13 13:58 <br>
 * @see com.wanmi.sbc.customer.api.request.store <br>
 * @since V1.0<br>
 */

@Schema
@Data
public class StoreCustomerRelaQueryRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String customerId;

    /**
     * 商家ID
     */
    @Schema(description = "商家ID")
    private Long companyInfoId;

    /**
     * 是否查平台
     */
    @Schema(description = "是否查平台")
    private Boolean queryPlatform;
}
