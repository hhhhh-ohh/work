package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * @description 礼品卡生成DTO
 * @author malianfeng
 * @date 2022/12/9 13:48
 */
@Schema
@Data
public class GiftCardBatchCreateDTO implements Serializable {

    /**
     * 礼品卡Id
     */
    @Schema(description = "礼品卡Id")
    @NotNull
    private Long giftCardId;

    /**
     * 制卡数量
     */
    @Schema(description = "生成数量")
    @NotNull
    @Max(10000)
    @Min(1)
    private Long createNum;

}

