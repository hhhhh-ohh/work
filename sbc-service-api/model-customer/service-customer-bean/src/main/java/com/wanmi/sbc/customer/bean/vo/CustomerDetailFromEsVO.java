package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerDetailFromEsVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @Schema(description = "客户ID")
    private String customerId;


    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;


    /**
     * 负责业务员
     */
    @Schema(description = "业务员ID")
    private String employeeId;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    private String customerLevelName;

    /**
     * 成长值
     */
    @Schema(description = "成长值")
    private Long growthValue;

    /**
     * 业务员名称
     */
    @Schema(description = "业务员名称")
    private String employeeName;

    /**
     * 是否是我的客户（S2b-Supplier使用）
     */
    @Schema(description = "是否是我的客户（S2b-Supplier使用")
    private Boolean myCustomer;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private CustomerType customerType;

}
