package com.wanmi.sbc.goods.api.request.pointsgoodscate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>积分商品分类表新增参数</p>
 *
 * @author yang
 * @date 2019-05-13 09:50:07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsCateAddRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    @NotBlank
    private String cateName;

    /**
     * 排序 默认0
     */
    @Schema(description = "排序 默认0")
    private Integer sort;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 删除标识,0: 未删除 1: 已删除
     */
    @Schema(description = "删除标识,0: 未删除 1: 已删除")
    private DeleteFlag delFlag;

}