package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>店铺分销设置VO</p>
 *
 * @author gaomuwei
 * @date 2019-02-19 15:46:27
 */
@Schema
@Data
public class DistributionStoreSettingVO extends BasicResponse {

    private static final long serialVersionUID = -2471219162878883946L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private String storeId;

    /**
     * 是否开启社交分销 0：关闭，1：开启
     */
    @Schema(description = "是否开启社交分销")
    private DefaultFlag openFlag;

    /**
     * 是否开启通用分销佣金 0：关闭，1：开启
     */
    @Schema(description = "是否开启通用分销佣金")
    private DefaultFlag commissionFlag;

    /**
     * 通用分销佣金
     */
    @Schema(description = "通用分销佣金")
    private BigDecimal commission;

    /**
     * 通用佣金比例
     */
    @Schema(description = "通用佣金比例")
    private BigDecimal commissionRate;



}