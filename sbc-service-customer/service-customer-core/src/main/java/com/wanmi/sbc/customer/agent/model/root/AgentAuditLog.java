package com.wanmi.sbc.customer.agent.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代理商审核记录实体类
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agent_audit_log")
public class AgentAuditLog implements Serializable {

    @Schema(description = "审核记录主键ID（UUID）")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "log_id")
    private String logId;

    @Schema(description = "代理商ID（agent.agent_id）")
    @Column(name = "agent_id")
    private String agentId;

    @Schema(description = "审核状态 0已创建 1待审核 2通过 3驳回")
    @Column(name = "audit_status")
    private Integer auditStatus;

    @Schema(description = "审核意见")
    @Column(name = "audit_opinion")
    private String auditOpinion;

    @Schema(description = "驳回原因（audit_status=3时填写）")
    @Column(name = "reject_reason")
    private String rejectReason;

    @Schema(description = "审核人用户ID")
    @Column(name = "auditor_id")
    private String auditorId;

    @Schema(description = "审核人姓名")
    @Column(name = "auditor_name")
    private String auditorName;

    @Schema(description = "审核时间")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "audit_time")
    private LocalDateTime auditTime;

    @Schema(description = "删除标志 0正常 1已删除")
    @Enumerated
    @Column(name = "del_flag")
    private DeleteFlag delFlag;

    @Serial
    private static final long serialVersionUID = 1L;
}
