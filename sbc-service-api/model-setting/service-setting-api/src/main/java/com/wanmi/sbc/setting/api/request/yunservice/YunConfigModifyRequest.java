package com.wanmi.sbc.setting.api.request.yunservice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>云配置修改参数</p>
 *
 * @author yang
 * @date 2019-11-05 18:33:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YunConfigModifyRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = " 编号")
    private Long id;

    /**
     * 键
     */
    @Schema(description = "键")
    private String configKey;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private String configType;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String configName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 状态,0:未启用1:已启用
     */
    @Schema(description = "状态,0:未启用1:已启用")
    private Integer status;

    /**
     * 配置内容，如JSON内容
     */
    @Schema(description = "配置内容，如JSON内容")
    private String context;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除标识,0:未删除1:已删除
     */
    @Schema(description = "删除标识,0:未删除1:已删除")
    private DeleteFlag delFlag;

}