package com.wanmi.sbc.elastic.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
@Data
public class EsStoreCustomerRelaUpdateRequest extends BaseRequest {

    private static final long serialVersionUID = 6908227868404162376L;

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
     * 商家标识
     */
    @Schema(description = "商家标识")
    private Long companyInfoId;

    /**
     * 店铺等级标识
     */
    @Schema(description = "店铺等级标识")
    private Long storeLevelId;
}
