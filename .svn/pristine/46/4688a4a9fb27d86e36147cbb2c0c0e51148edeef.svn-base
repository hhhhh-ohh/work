package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>商品库规格 dto</p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardSpecDTO implements Serializable {

    private static final long serialVersionUID = -5959508573359791034L;

    /**
     * 规格ID
     */
    @Schema(description = "规格ID")
    private Long specId;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String goodsId;

    /**
     * 规格名称
     */
    @Schema(description = "规格名称")
    private String specName;

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
    @Schema(description = "删除标记 0: 否, 1: 是")
    private DeleteFlag delFlag;

    /**
     * 新增时，模拟规格ID
     */
    @Schema(description = "新增时，模拟规格ID")
    private Long mockSpecId;

    /**
     * 多个规格值ID
     * 查询时，遍平化响应
     */
    @Schema(description = "多个规格值ID，查询时，遍平化响应")
    private List<Long> specDetailIds;
}
