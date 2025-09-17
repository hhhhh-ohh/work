package com.wanmi.sbc.setting.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统配置实体类
 * Created by dyt on 2017/4/23.
 */
@Schema
@Data
public class ConfigVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long id;

    /**
     * 配置键
     */
    @Schema(description = "配置键")
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
     * 状态
     */
    @Schema(description = "状态")
    private Integer status;

    /**
     * 内容
     */
    @Schema(description = "内容")
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
     * 删除标记
     */
    @Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    @NotNull
    private DeleteFlag delFlag;

    /**
     * 是否展示
     */
    @Schema(description = "是否展示")
    private Integer isShow;


}
