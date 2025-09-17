package com.wanmi.sbc.customer.bean.dto;

import com.wanmi.sbc.customer.bean.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author hejiawen<br>
 * @version 1.0<br>
 * @taskId <br>
 * @createTime 2018-09-12 14:44 <br>
 * @see com.wanmi.sbc.customer.api.request.store.dto <br>
 * @since V1.0<br>
 */
@Schema
@Data
public class StoreCustomerRelaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private String id;

    /**
     * 用户标识
     */
    @Schema(description = "用户标识")
    private String customerId;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识")
    private Long storeId;

    /**
     * 商家标识
     */
    @Schema(description = "商家标识")
    private Long companyInfoId;

    /**
     * 客户等级标识
     */
    @Schema(description = "客户等级标识")
    private Long customerLevelId;

    /**
     * 店铺等级标识
     */
    @Schema(description = "店铺等级标识")
    private Long storeLevelId;

    /**
     * 负责的业务员标识
     */
    @Schema(description = "负责的业务员标识")
    private String employeeId;

    /**
     * 关系类型(0:店铺关联的客户,1:店铺发展的客户)
     */
    @Schema(description = "关系类型")
    private CustomerType customerType;

}
