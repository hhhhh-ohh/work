package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author hejiawen<br>
 * @version 1.0<br>
 * @taskId <br>
 * @createTime 2018-09-12 10:26 <br>
 * @see com.wanmi.sbc.customer.api.request.store <br>
 * @since V1.0<br>
 */

@Schema
@Data
public class StoreCustomerQueryByEmployeeRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String customerAccount;

    /**
     * 要查询的条数
     */
    @Schema(description = "要查询的条数")
    private Integer pageSize;

    /**
     * 员工ID
     */
    @Schema(description = "员工ID")
    private String employeeId;

    /**
     * 员工ID集合
     */
    @Schema(description = "员工ID集合")
    private List<String> employeeIds;
}
