package com.wanmi.sbc.setting.api.response.yunservice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>根据id查询云配置信息response</p>
 * @author yang
 * @date 2019-11-05 18:33:04
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YunConfigResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     *  编号
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

    /**
     * 请求路径
     */
    @Schema(description = "请求路径")
    private String endPoint;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String accessKeyId;

    /**
     * 密钥
     */
    @Schema(description = "密钥")
    private String accessKeySecret;

    /**
     * 存储空间名
     */
    @Schema(description = "存储空间名")
    private String bucketName;

    /**
     * 地区
     */
    @Schema(description = "地区")
    private String region;

    /**
     * 外部访问地址
     */
    @Schema(description = "外部访问地址")
    private String publicUrlPrefix;
}
