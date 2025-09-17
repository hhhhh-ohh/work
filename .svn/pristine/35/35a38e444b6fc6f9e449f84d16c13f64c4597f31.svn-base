package com.wanmi.sbc.setting.refundcause.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2021/11/16 10:57
 * @description
 *     <p>退单原因表
 */
@Data
@Entity
@Table(name = "refund_cause_tbl")
public class RefundCause implements Serializable {

    private static final long serialVersionUID = -20929569370786627L;

    /** id 主键 */
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /** 退款原因 */
    @Column(name = "cause")
    private String cause;

    /** 排序 */
    @Column(name = "sort")
    private Long sort;

    /** 删除标识 */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /** 删除时间 */
    @CreatedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "delete_time")
    private LocalDateTime deleteTime;

    /** 删除人 */
    @Column(name = "delete_person")
    private String deletePerson;

    /** 创建时间 */
    @CreatedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /** 创建人 */
    @Column(name = "create_person")
    private String createPerson;

    /** 更新时间 */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /** 更新人 */
    @Column(name = "update_person")
    private String updatePerson;
}
