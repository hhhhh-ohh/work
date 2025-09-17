package com.wanmi.sbc.account.credit.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerApplyRecordWithAccount {
    
    @Column(name="id")
    private String id;

    /**
     * 审核状态
     */
    @Column(name="audit_status")
    @Enumerated(EnumType.ORDINAL)
    private CreditAuditStatus auditStatus;

    /**
     * 申请原因
     */
    @Column(name="apply_notes")
    private String applyNotes;

    /**
     * 驳回原因
     */
    @Column(name="reject_reason")
    private String rejectReason;

    /**
     * 生效状态
     */
    @Column(name="effect_status")
    private BoolFlag effectStatus;

    /**
     * 创建时间
     */
    @Column(name="create_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name="update_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 客户主键
     */
    @Column(name="customer_id")
    private String customerId;

    /**
     * 客户账号
     */
    @Column(name="customer_account")
    private String customerAccount;

    /**
     * 客户名称
     */
    @Column(name="customer_name")
    private String customerName;

    /**
     * 申请记录ID
     */
    @Column(name="credit_record_id")
    private String creditRecordId;

    /**
     * 申请记录ID
     */
    @Column(name="apply_record_id")
    private String applyRecordId;

    /**
     * 恢复次数
     */
    @Column(name="credit_num")
    private Integer creditNum;

    /**
     * 授信额度
     */
    @Column(name="credit_amount")
    private BigDecimal creditAmount;

    /**
     * 当前周期待还款金额
     */
    @Column(name="repay_amount")
    private BigDecimal repayAmount;

    /**
     * 当前周期已还款额度
     */
    @Column(name="has_repaid_amount")
    private BigDecimal hasRepaidAmount;

    /**
     * 授信开始时间
     */
    @Column(name="start_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 授信截止时间
     */
    @Column(name="end_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 使用状态
     */
    @Column(name="used_status")
    private BoolFlag usedStatus;
}
