package com.wanmi.sbc.elastic.bean.vo.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class GoodsLabelNestVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long goodsLabelId;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String labelName;

    /**
     * 排序规则
     */
    @Schema(description = "排序规则")
    private Integer labelSort;

    /**
     * 前端是否展示 0: 关闭 1:开启
     */
    @Schema(description = "前端是否展示 0: 关闭 1:开启")
    private Boolean labelVisible;

    /**
     * 删除标识 0:未删除1:已删除
     */
    @Schema(description = "删除标识 0:未删除1:已删除")
    private DeleteFlag delFlag;
}
