package com.wanmi.sbc.customer.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>客户等级</p>
 * author: sunkun
 * Date: 2018-11-19
 */
@Schema
@Data
public class CustomerLevelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    private String customerLevelName;

    /**
     * 客户等级折扣
     */
    @Schema(description = "客户等级折扣")
    private BigDecimal customerLevelDiscount;

    /**
     * 是否是默认 0：否 1：是
     */
    @Schema(description = "是否是默认")
    private DefaultFlag isDefalt;

    /**
     * 删除标志 0未删除 1已删除
     */
    @Schema(description = "删除标志")
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

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
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deletePerson;
}
