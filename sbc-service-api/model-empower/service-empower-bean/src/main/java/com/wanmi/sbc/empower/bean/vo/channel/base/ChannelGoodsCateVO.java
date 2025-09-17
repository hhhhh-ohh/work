package com.wanmi.sbc.empower.bean.vo.channel.base;

import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGoodsCateVO {
    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Long cateId;
    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String cateName;
    /**
     * 分类的父级id（一级分类的父级id为0）
     */
    @Schema(description = "分类的父级id（一级分类的父级id为0）")
    private Long cateParentId;
    /**
     * 当前分类的路径
     */
    @Schema(description = "当前分类的路径")
    private String catePath;
    /**
     * 当前分类的层级
     */
    @Schema(description = "当前分类的层级")
    private Integer cateGrade;

    @Schema(description = "来源")
    private ThirdPlatformType thirdPlatformType;
}
