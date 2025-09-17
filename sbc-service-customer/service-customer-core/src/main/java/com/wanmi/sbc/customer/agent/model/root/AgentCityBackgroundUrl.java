package com.wanmi.sbc.customer.agent.model.root;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代理商区域背景图表
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agent_city_background_url")
public class AgentCityBackgroundUrl implements Serializable {

    @Schema(description = "自增主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "背景图 URL")
    @Column(name = "background_url", nullable = false, length = 1024)
    private String backgroundUrl;

    @Schema(description = "城市ID")
    @Column(name = "city_id", unique = true)
    private Long cityId;

    @Serial
    private static final long serialVersionUID = 1L;
}
