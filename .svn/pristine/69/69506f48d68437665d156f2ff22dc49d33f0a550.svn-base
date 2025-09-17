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

/**
 * ${DESCRIPTION}
 *
 * @auther ruilinxin
 * @create 2018/03/20 10:04
 */
@Schema
@Data
public class GoodsPropDetailRelVO extends BasicResponse {

    private static final long serialVersionUID = -6278619735661165272L;

    /**
     * 编号，
     */
    @Schema(description = "编号")
    private Long relId;

    /**
     * SPU标识
     */
    @Schema(description = "SPU标识")
    private String goodsId;

    /**
     * 属性值id
     */
    @Schema(description = "属性值id")
    private Long detailId;

    /**
     * 属性id
     */
    @Schema(description = "属性id")
    private Long propId;

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
}
