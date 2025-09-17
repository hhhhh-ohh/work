package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 退单账户DTO
 */
@Data
@Schema
public class ReturnCustomerAccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账户ID
     */
    @Schema(description = "账户ID")
    private String customerAccountId;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 账户名字
     */
    @Schema(description = "账户名字")
    private String customerAccountName;

    /**
     * 银行账号
     */
    @Schema(description = "银行账号")
    private String customerAccountNo;

    /**
     * 开户行
     */
    @Schema(description = "开户行")
    private String customerBankName;

    /**
     * 银行名称
     */
    @Schema(description = "银行名称")
    private String bankName;

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
