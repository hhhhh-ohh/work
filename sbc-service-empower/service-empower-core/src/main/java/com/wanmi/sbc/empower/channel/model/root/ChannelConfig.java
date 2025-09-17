package com.wanmi.sbc.empower.channel.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 渠道配置实体
 * @author wur
 * @date: 2021/5/20 10:02
 */
@Data
@Entity
@Table(name = "channel_config")
public class ChannelConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 类型 */
    @Column(name = "channel_type")
    private ThirdPlatformType channelType;

    /** 名称 */
    @Column(name = "channel_name")
    private String channelName;

    /** 备注 */
    @Column(name = "remark")
    private String remark;

    /** 状态 */
    @Column(name = "status")
    private EnableStatus status;

    /** 内容 */
    @Column(name = "context")
    private String context;

    /** 创建时间 */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /** 更新时间 */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /** 删除标记 */
    @Column(name = "del_flag")
    @NotNull
    private DeleteFlag delFlag;
}
