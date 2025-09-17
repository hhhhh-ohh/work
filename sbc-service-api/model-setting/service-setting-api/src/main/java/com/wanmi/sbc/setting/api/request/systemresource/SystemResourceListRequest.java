package com.wanmi.sbc.setting.api.request.systemresource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>平台素材资源列表查询请求参数</p>
 *
 * @author lq
 * @date 2019-11-05 16:14:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceListRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-素材资源IDList
     */
    @Schema(description = "批量查询-素材资源IDList")
    private List<Long> resourceIdList;

    /**
     * 素材资源ID
     */
    @Schema(description = "素材资源ID")
    private Long resourceId;

    /**
     * 资源类型(0:图片,1:视频)
     */
    @Schema(description = "资源类型(0:图片,1:视频)")
    private ResourceType resourceType;

    /**
     * 素材分类ID
     */
    @Schema(description = "素材分类ID")
    private Long cateId;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识")
    private Long storeId;

    /**
     * 商家标识
     */
    @Schema(description = "商家标识")
    private Long companyInfoId;

    /**
     * 素材KEY
     */
    @Schema(description = "素材KEY")
    private String resourceKey;

    /**
     * 素材名称
     */
    @Schema(description = "素材名称")
    private String resourceName;

    /**
     * 素材地址
     */
    @Schema(description = "素材地址")
    private String artworkUrl;

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
     * 搜索条件:更新时间开始
     */
    @Schema(description = "搜索条件:更新时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeBegin;
    /**
     * 搜索条件:更新时间截止
     */
    @Schema(description = "搜索条件:更新时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeEnd;

    /**
     * 删除标识,0:未删除1:已删除
     */
    @Schema(description = "删除标识,0:未删除1:已删除")
    private DeleteFlag delFlag;

    /**
     * oss服务器类型，对应system_config的config_type
     */
    @Schema(description = "oss服务器类型，对应system_config的config_type")
    private String serverType;


    @Schema(description = "批量查询-素材分类id")
    private List<Long> cateIds;

}