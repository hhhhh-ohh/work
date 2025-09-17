package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>联想词模糊实体类</p>
 * @author weiwenhao
 * @date 2020-04-16
 */
@Data
@Schema
public class AssociationLongTailLikeWordVO extends BasicResponse {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long associationLongTailWordId;

    /**
     * 联想词
     */
    @Schema(description = "联想词")
    private String associationalWord;

    /**
     * 长尾词
     */
    @Schema(description = "长尾词")
    private String[] longTailWord;

    /**
     * 关联搜索词id
     */
    @Schema(description = "关联搜索词id")
    private Long searchAssociationalWordId;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Long sortNumber;

}
