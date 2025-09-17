package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Schema
@Data
public class GoodsPropVO extends BasicResponse {

    private static final long serialVersionUID = -7284798055659119876L;

    @Schema(description = "属性Id")
    private Long propId;

    @Schema(description = "分类Id")
    private Long cateId;

    @Schema(description = "属性名称")
    private String propName;

    @Schema(description = "是否开启索引，0: 否, 1: 是")
    private DefaultFlag indexFlag;

    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "删除标识，0: 否, 1: 是")
    private DeleteFlag delFlag;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "商品属性详情")
    private List<GoodsPropDetailVO> goodsPropDetails;

    @Schema(description = "商品属性详情字符串")
    private String propDetailStr;
}
