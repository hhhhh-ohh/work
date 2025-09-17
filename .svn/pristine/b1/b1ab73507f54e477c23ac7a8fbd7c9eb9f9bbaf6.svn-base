package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * @description 礼品卡发卡DTO
 * @author malianfeng
 * @date 2022/12/9 13:48
 */
@Schema
@Data
public class GiftCardSendCustomerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    @Schema(description = "用户Id")
    @NotNull
    private String customerId;

    /**
     * 发卡数量
     */
    @Schema(description = "发卡数量")
    @NotNull
    @Max(10000)
    @Min(1)
    private Long sendNum;
}

