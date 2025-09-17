package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 客户信息主表
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
public class CustomerSimplifyVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 可用积分
     */
    @Schema(description = "可用积分")
    private Long pointsAvailable;

    /**
     * 商家和客户的关联关系
     */
    @Schema(description = "商家和客户的关联关系")
    private List<StoreCustomerRelaVO> storeCustomerRelaListByAll;

}
