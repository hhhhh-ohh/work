package com.wanmi.sbc.customer.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:25 2019/6/13
 * @Description:
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 提成
     */
    @Schema(description = "提成")
    @NotNull
    private BigDecimal commission;
}
