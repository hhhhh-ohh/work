package com.wanmi.sbc.customer.api.request.company;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerType;

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
 * @createTime 2018-09-13 14:36 <br>
 * @see com.wanmi.sbc.customer.api.request.company <br>
 * @since V1.0<br>
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyInfoQueryRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String customerId;

    /**
     * 客户类型（0:平台客户,1:商家客户,2：门店）
     */
    @Schema(description = "客户类型")
    CustomerType customerType;
}
