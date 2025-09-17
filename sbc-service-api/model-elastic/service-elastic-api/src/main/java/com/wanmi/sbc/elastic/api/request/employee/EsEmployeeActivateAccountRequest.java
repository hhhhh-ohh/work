package com.wanmi.sbc.elastic.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsEmployeeActivateAccountRequest extends BaseRequest {

    /**
     * 员工id列表
     */
    @Schema(description = "员工id列表")
    @NotNull
    private List<String> employeeIds;

    /**
     * 客户类型 0:平台客户,1:商家客户
     */
    @Schema(description = "客户类型")
    private CustomerType customerType;

    /**
     * 所属商家Id
     */
    @Schema(description = "所属商家Id")
    private Long companyInfoId;

    /**
     * 所属店铺Id
     */
    @Schema(description = "所属店铺Id")
    private Long storeId;

    /**
     * 是否商家端
     */
    @Schema(description = "是否商家端")
    private boolean s2bSupplier;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private String operator;


}
