package com.wanmi.sbc.customer.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:25 2019/6/13
 * @Description:
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorLevelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分销员等级id
     */
    @Schema(description = "分销员等级id")
    private String distributorLevelId;

    /**
     * 分销员等级名称
     */
    @Schema(description = "分销员等级名称")
    private String distributorLevelName;

    /**
     * 佣金比例
     */
    @Schema(description = "佣金比例")
    @NotNull
    private BigDecimal commissionRate;

    /**
     * 佣金提成比例
     */
    @Schema(description = "佣金提成比例")
    @NotNull
    private BigDecimal percentageRate;

    /**
     * 销售额门槛是否开启
     */
    @Schema(description = "销售额门槛是否开启")
    @NotNull
    private DefaultFlag salesFlag;

    /**
     * 销售额门槛
     */
    @Schema(description = "销售额门槛")
    private BigDecimal salesThreshold;

    /**
     * 到账收益额门槛是否开启
     */
    @Schema(description = "到账收益额门槛是否开启")
    @NotNull
    private DefaultFlag recordFlag;

    /**
     * 到账收益额门槛
     */
    @Schema(description = "到账收益额门槛")
    private BigDecimal recordThreshold;

    /**
     * 邀请人数门槛是否开启
     */
    @Schema(description = "邀请人数门槛是否开启")
    @NotNull
    private DefaultFlag inviteFlag;

    /**
     * 邀请人数门槛
     */
    @Schema(description = "邀请人数门槛")
    private Integer inviteThreshold;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 等级排序
     */
    @Schema(description = "等级排序")
    @NotNull
    private Integer sort;

}
