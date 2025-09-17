package com.wanmi.sbc.empower.bean.vo.channel.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 渠道配置
 * @author wur
 * @date 2021/5/20 10:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelConfigVO implements Serializable {

    private static final long serialVersionUID = 6246961037582549416L;

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "渠道标识")
    private ThirdPlatformType channelType;

    @Schema(description = "渠道名称")
    private String channelName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "状态")
    private EnableStatus status;

    @Schema(description = "内容")
    private String context;

    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    @NotNull
    private DeleteFlag delFlag;
}
