package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Schema
@Data
public class GoodsPropDetailVO extends BasicResponse {

    private static final long serialVersionUID = -8265917035237102678L;

    @Schema(description = "详情id")
    private Long detailId;

    @Schema(description = "属性id")
    private Long propId;

    @Schema(description = "详情名")
    private String detailName;

    @Schema(description = "属性名拼音")
    private String detailPinYin;

    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "是否删除，0: 否, 1: 是")
    private DeleteFlag delFlag;

    @Schema(description = "排序")
    private Integer sort;
}
