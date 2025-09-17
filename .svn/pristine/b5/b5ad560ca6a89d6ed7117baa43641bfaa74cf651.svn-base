package com.wanmi.sbc.customer.agent.model.root;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代理商刷海报权限实体类
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agent_update_poster_auth")
public class AgentUpdatePosterAuth implements Serializable {

    @Schema(description = "自增主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "联系电话")
    @Column(name = "contact_phone")
    private String contactPhone;

    @Schema(description = "城市ID")
    @Column(name = "city_id")
    private Long cityId;

    @Serial
    private static final long serialVersionUID = 1L;
}
