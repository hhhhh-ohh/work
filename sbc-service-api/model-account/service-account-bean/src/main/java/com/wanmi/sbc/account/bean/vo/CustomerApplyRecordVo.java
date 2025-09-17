package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CustomerApplyRecordVo extends BasicResponse {

    @Schema(description = "主键")
    private String id;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态")
    private CreditAuditStatus auditStatus;

    /**
     * 申请原因
     */
    @Schema(description = "申请原因")
    private String applyNotes;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String rejectReason;

    /**
     * 生效状态
     */
    @Schema(description = "生效状态")
    private Integer effectStatus;

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
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;

    /**
     * 是否删除标志
     */
    @Schema(description = "是否删除标志")
    private Integer delFlag;


    /**
     * 客户主键
     */
    @Schema(description = "客户主键")
    private String customerId;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String customerAccount;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 申请记录ID
     */
    @Schema(description = "申请记录ID")
    private String creditRecordId;

    /**
     * 申请记录ID
     */
    @Schema(description = "申请记录ID")
    private String applyRecordId;

    /**
     * 恢复次数
     */
    @Schema(description = "恢复次数")
    private Integer creditNum;

    /**
     * 授信额度
     */
    @Schema(description = "授信额度")
    private BigDecimal creditAmount;

    /**
     * 当前周期待还款金额
     */
    @Schema(description = "当前周期待还款金额")
    private BigDecimal repayAmount;

    /**
     * 当前周期已还款额度
     */
    @Schema(description = "当前周期已还款额度")
    private BigDecimal hasRepaidAmount;

    /**
     * 授信开始时间
     */
    @Schema(description = "授信开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 授信截止时间
     */
    @Schema(description = "授信截止时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 使用状态
     */
    @Schema(description = "使用状态")
    private Integer usedStatus;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;
}
