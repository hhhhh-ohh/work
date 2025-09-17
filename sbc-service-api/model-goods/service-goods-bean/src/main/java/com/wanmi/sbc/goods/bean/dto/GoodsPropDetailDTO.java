package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema
@Data
public class GoodsPropDetailDTO implements Serializable {

    private static final long serialVersionUID = 6619799018979251459L;

    @Schema(description = "详情Id")
    private Long detailId;

    @Schema(description = "属性Id")
    private Long propId;

    @Schema(description = "详情名字")
    private String detailName;

    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "删除标记 0: 否, 1: 是")
    private DeleteFlag delFlag;

    @Schema(description = "排序")
    private Integer sort;
}
