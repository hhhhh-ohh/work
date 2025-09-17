package com.wanmi.sbc.account.credit.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2021/3/1 09:39
 * @description <p> 授信申请记录 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer_apply_record")
@Entity
public class CustomerApplyRecord extends BaseEntity {
    private static final long serialVersionUID = -3726052700047596249L;

    /**
     * 客户授信申请记录主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 审核状态 0待审核 1拒绝 2通过 3变更额度审核
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 审核状态 0待审核 1拒绝 2通过 3变更额度审核
     */
    @Column(name = "audit_status")
    @Enumerated
    private CreditAuditStatus auditStatus;

    /**
     * 申请原因
     */
    @Column(name = "apply_notes")
    private String applyNotes;

    /**
     * 驳回原因
     */
    @Column(name = "reject_reason")
    private String rejectReason;

    /**
     * 是否生效 0 否 1是
     */
    @Column(name = "effect_status")
    @Enumerated
    private BoolFlag effectStatus;

    /**
     * 审批人
     */
    @Column(name = "audit_person")
    private String auditPerson;

    /**
     * 审批时间
     */
    @Column(name = "audit_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime auditTime;

    /**
     * 删除标识
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag deleteFlag;
}
