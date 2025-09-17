package com.wanmi.sbc.crm.api.request.recommendcatemanage;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.bean.enums.NoPushType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName RecommendCateManageInfoListRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/19 14:41
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateManageInfoListRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-主键List
     */
    @Schema(description = "批量查询-主键List")
    private List<Long> idList;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 类目id
     */
    @Schema(description = "类目id")
    private Long cateId;

    /**
     * 类目idList
     */
    @Schema(description = "类目idList")
    List<Long> cateIds;

    /**
     * 权重
     */
    @Schema(description = "权重")
    private Integer weight;

    /**
     * 禁推标识 0：可推送；1:禁推
     */
    @Schema(description = "禁推标识 0：可推送；1:禁推")
    private NoPushType noPushType;

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
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

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
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortColumn;

    /**
     * 排序方式
     */
    @Schema(description = "排序方式")
    private String sortRole;

}
