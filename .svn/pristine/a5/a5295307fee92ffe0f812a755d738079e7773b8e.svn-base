package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品规格实体类
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class GoodsSpecSaveVO extends BasicResponse {

    /**
     * 规格ID
     */
    @Schema(description = "规格ID")
    private Long specId;

    /**
     * 规格ID
     */
    @Schema(description = "规格ID")
    private Long oldSpecId;

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
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
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
    @Schema(description = "多个规格值ID")
    private List<Long> specDetailIds;

    @Schema(description = "规格详情")
    private List<GoodsSpecDetailSaveVO> goodsSpecDetails;

}
