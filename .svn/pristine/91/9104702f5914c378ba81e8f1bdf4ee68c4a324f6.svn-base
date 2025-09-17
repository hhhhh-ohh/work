package com.wanmi.sbc.elastic.api.request.sensitivewords;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author houshuai
 * 敏感词查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsSensitiveWordsQueryRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-敏感词id 主键List
     */
    @Schema(description = "批量查询-敏感词id 主键List")
    private List<Long> sensitiveIdList;

    /**
     * 敏感词id 主键
     */
    @Schema(description = "敏感词id 主键")
    private Long sensitiveId;

    /**
     * 敏感词内容
     */
    @Schema(description = "敏感词内容")
    private String sensitiveWords;

    /**
     * 敏感词内容
     */
    @Schema(description = "敏感词内容")
    private String likeSensitiveWords;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除")
    private DeleteFlag delFlag;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createUser;

    /**
     * 搜索条件:创建时间开始
     */
    @Schema(description = "搜索条件:创建时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;
    /**
     * 搜索条件:创建时间截止
     */
    @Schema(description = "搜索条件:创建时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updateUser;

    /**
     * 搜索条件:修改时间开始
     */
    @Schema(description = "修改时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeBegin;
    /**
     * 搜索条件:修改时间截止
     */
    @Schema(description = "修改时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeEnd;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deleteUser;

    /**
     * 搜索条件:删除时间开始
     */
    @Schema(description = "删除时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTimeBegin;
    /**
     * 搜索条件:删除时间截止
     */
    @Schema(description = "删除时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTimeEnd;

    @Schema(description = "批量敏感词id")
    private List<Long> idList;
}