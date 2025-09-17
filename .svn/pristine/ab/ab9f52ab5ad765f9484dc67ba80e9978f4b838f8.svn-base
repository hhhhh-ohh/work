package com.wanmi.sbc.setting.api.request.systemresourcecate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>平台素材资源分类新增参数</p>
 *
 * @author lq
 * @date 2019-11-05 16:14:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceCateAddRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;

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
     * 分类名称
     */
    @Schema(description = "分类名称")
    @Length(max = 45)
    @NotNull
    private String cateName;

    /**
     * 父分类ID
     */
    @Schema(description = "父分类ID")
    @Max(9223372036854775807L)
    private Long cateParentId;

    /**
     * 分类图片
     */
    @Schema(description = "分类图片")
    @Length(max = 255)
    private String cateImg;

    /**
     * 分类层次路径,例1|01|001
     */
    @Schema(description = "分类层次路径,例1|01|001")
    @Length(max = 1000)
    private String catePath;

    /**
     * 分类层级
     */
    @Schema(description = "分类层级")
    @Max(127)
    private Integer cateGrade;

    /**
     * 拼音
     */
    @Schema(description = "拼音")
    @Length(max = 45)
    private String pinYin;

    /**
     * 简拼
     */
    @Schema(description = "简拼")
    @Length(max = 45)
    private String spinYin;

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

    /**
     * 排序
     */
    @Schema(description = "排序")
    @Max(127)
    private Integer sort;

}