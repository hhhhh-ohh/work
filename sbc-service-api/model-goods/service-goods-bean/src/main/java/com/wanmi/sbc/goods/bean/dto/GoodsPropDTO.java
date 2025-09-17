package com.wanmi.sbc.goods.bean.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Schema
@Data
public class GoodsPropDTO implements Serializable {

    private static final long serialVersionUID = 3308356983745670262L;

    @Schema(description = "属性Id")
    private Long propId;

    @Schema(description = "分类Id")
    private Long cateId;

    @Schema(description = "属性名")
    private String propName;

    @Schema(description = "是否开启索引")
    private DefaultFlag indexFlag;

    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "删除标记")
    private DeleteFlag delFlag;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "商品属性详情")
    private List<GoodsPropDetailDTO> goodsPropDetails;

    @Schema(description = "商品属性详情字符串")
    private String propDetailStr;
}
