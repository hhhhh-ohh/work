package com.wanmi.sbc.customer.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.util.LongStrictDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 批量调整积分
 */
@Schema
@Data
public class CustomerPointsAdjustDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 调整数量
     */
    @Schema(description = "调整数量")
    @JsonDeserialize(using = LongStrictDeserializer.class)
    @Length(min = 1,max = 99999)
    private Long adjustNum;

    /**
     * 操作类型 0:扣除 1:增长 2:覆盖'
     */
    @Schema(description = "操作类型 0:扣除 1:增长 2:覆盖'")
    private OperateType operateType;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    private String customerAccount;
}
