package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goodscate.vo.GoodsPropListVO
 *
 * @author lipeng
 * @dateTime 2018/11/1 下午4:30
 */
@Schema
@Data
public class GoodsCatePropVO extends BasicResponse {

    private static final long serialVersionUID = 2844745534045429715L;

    @Schema(description = "属性id")
    private Long propId;

    @Schema(description = "分类id")
    private Long cateId;

    @Schema(description = "属性名")
    private String propName;

    @Schema(description = "默认标识")
    private DefaultFlag indexFlag;

    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "是否删除")
    private DeleteFlag delFlag;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "商品属性明细")
    private List<GoodsPropDetailVO> goodsPropDetails;

    @Schema(description = "属性明细")
    private String propDetailStr;
}
