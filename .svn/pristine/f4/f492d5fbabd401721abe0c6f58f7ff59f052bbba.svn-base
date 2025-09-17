package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.Data;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;

/**
 * <p>店铺分销设置DTO</p>
 *
 * @author gaomuwei
 * @date 2019-02-19 15:46:27
 */
@Schema
@Data
public class DistributionStoreSettingDTO {

    /**
     * 主键Id
     */
    @Schema(description = "主键Id")
    private String settingId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private String storeId;

    /**
     * 是否开启社交分销 0：关闭，1：开启
     */
    @Schema(description = "是否开启社交分销")
    @NotNull
    private DefaultFlag openFlag;

    /**
     * 是否开启通用分销佣金 0：关闭，1：开启
     */
    @Schema(description = "是否开启通用分销佣金")
    @NotNull
    private DefaultFlag commissionFlag;

    /**
     * 通用分销佣金比例
     */
    @Schema(description = "通用分销佣金比例")
    private BigDecimal commissionRate;

}