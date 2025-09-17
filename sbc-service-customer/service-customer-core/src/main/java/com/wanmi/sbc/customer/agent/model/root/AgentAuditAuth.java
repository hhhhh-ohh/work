package com.wanmi.sbc.customer.agent.model.root;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代理商审核权限实体类
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agent_audit_auth")
public class AgentAuditAuth implements Serializable {

    @Schema(description = "主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "联系电话")
    @Column(name = "contact_phone")
    private String contactPhone;

    @Schema(description = "区ID")
    @Column(name = "area_id")
    private Long areaId;

    @Serial
    private static final long serialVersionUID = 1L;
}
