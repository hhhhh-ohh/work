package com.wanmi.sbc.account.credit.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.BoolFlag;
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
 * @date 2021/3/1 10:40
 * @description <p> 授信记录 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer_credit_record")
@Entity
public class CustomerCreditRecord  extends BaseEntity {
    private static final long serialVersionUID = -1007199712762568294L;

    /**
     * 客户授信申请记录主键
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
     * 授信金额
     */
    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    /**
     * 已使用额度
     */
    @Column(name = "used_amount")
    private BigDecimal usedAmount;

    /**
     * 生效天数
     */
    @Column(name = "effective_days")
    private Long effectiveDays;

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
     * 使用状态 0已结束 1已使用
     */
    @Column(name = "used_status")
    @Enumerated
    private BoolFlag usedStatus;

    /**
     * 是否删除标志 0：否，1：是
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;
}
