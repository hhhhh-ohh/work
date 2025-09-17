package com.wanmi.sbc.account.credit.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2021/3/1 15:58
 * @description <p> 还款订单关联表 </p>
 */
@Data
@Table(name = "customer_credit_order")
@Entity
public class CustomerCreditOrder {

    private static final long serialVersionUID = -7123126469416144293L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 还款单号
     */
    @Column(name = "repay_order_code")
    private String repayOrderCode;

    /**
     * 订单编号
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 客户id
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 是否删除标志 0：否，1：是
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Column(name = "create_person")
    private String createPerson;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Column(name = "update_person")
    private String updatePerson;
}
