package com.wanmi.sbc.account.credit.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * 授信账户信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer_credit_account")
@Entity
@Accessors(chain = true)
public class CustomerCreditAccount extends BaseEntity {

    private static final long serialVersionUID = -1653595116489628996L;
    /**
     * 客户授信信息主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 客户主键（母账户）
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 客户账号
     */
    @Column(name = "customer_account")
    private String customerAccount;

    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 申请记录id
     */
    @Column(name = "apply_record_id")
    private String applyRecordId;

    /**
     * 变更额度记录ID
     */
    @Column(name = "change_record_id")
    private String changeRecordId;

    /**
     * 授信记录id
     */
    @Column(name = "credit_record_id")
    private String creditRecordId;

    /**
     * 授信次数
     */
    @Column(name = "credit_num")
    private Integer creditNum;

    /**
     * 授信额度
     */
    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    /**
     * 当前周期待还款金额
     */
    @Column(name = "repay_amount")
    private BigDecimal repayAmount;

    /**
     * 当前周期已还款额度
     */
    @Column(name = "has_repaid_amount")
    private BigDecimal hasRepaidAmount;

    /**
     * 授信开始时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "start_time")
    private LocalDateTime startTime;

    /**
     * 授信截止时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * 使用状态 0未使用 1已使用
     * 额度是否使用
     */
    @Column(name = "used_status")
    @Enumerated
    private BoolFlag usedStatus;

    /**
     * 启用状态 0未启用 1已启用
     * 当前账户是否可用
     */
    @Column(name = "enabled")
    @Enumerated
    private BoolFlag enabled;

    /**
     * 是否删除标志 0：否，1：是
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    @OneToOne
    @JoinColumn(name = "apply_record_id", insertable = false, updatable = false)
    private CustomerApplyRecord customerApplyRecord;

    @OneToOne
    @JoinColumn(name = "credit_record_id", insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    private CustomerCreditRecord customerCreditRecord;

    /**
     * 当前周期已使用额度
     */
    @Column(name = "used_amount")
    private BigDecimal usedAmount;

    /**
     * 当前周期剩余额度(可用额度)
     */
    @Column(name = "usable_amount")
    private BigDecimal usableAmount;


    /**
     * 是否已经扣减可用额度 0未扣减 1已扣减
     */
    @Column(name = "expired_change_flag")
    @Enumerated
    private BoolFlag expiredChangeFlag;

}