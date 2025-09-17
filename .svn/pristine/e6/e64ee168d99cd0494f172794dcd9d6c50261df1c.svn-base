package com.wanmi.sbc.account.credit.model.root;

import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2021/3/2 16:50
 * @description <p> 授信还款表 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer_credit_repay")
@Entity
public class CustomerCreditRepay extends BaseEntity {

    private static final long serialVersionUID = -7447393195132886914L;
    /**
     * 客户授信还款表主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 会员id
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 还款记录关联主键
     */
    @Column(name = "credit_record_id")
    private String creditRecordId;

    /**
     * 还款单号
     */
    @Column(name = "repay_order_code")
    private String repayOrderCode;

    /**
     * 还款金额
     */
    @Column(name = "repay_amount")
    private BigDecimal repayAmount;

    /**
     * 还款说明
     */
    @Column(name = "repay_notes")
    private String repayNotes;

    /**
     * 还款方式 0:线上 1:线下
     */
    @Column(name = "repay_way")
    private Integer repayWay;

    /**
     * 还款附件 repay_way为1时有值
     */
    @Column(name = "repay_file")
    private String repayFile;

    /**
     * 驳回理由 audit_status为2时有值
     */
    @Column(name = "audit_remark")
    private String auditRemark;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime auditTime;

    /**
     * 审核人
     */
    @Column(name = "audit_person")
    private String auditPerson;

    /**
     * 还款状态 0待还款 1还款成功 2 已作废
     */
    @Column(name = "repay_status")
    @Enumerated
    private CreditRepayStatus repayStatus;

    /**
     * 还款方式 0银联，1微信，2支付宝
     */
    @Column(name = "repay_type")
    @Enumerated
    private CreditRepayTypeEnum repayType;

    /**
     * 还款时间
     */
    @Column(name = "repay_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime repayTime;

    /**
     * 是否删除标志 0：否，1：是
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 关联账户表
     */
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private CustomerCreditAccount customerCreditAccount;
}
