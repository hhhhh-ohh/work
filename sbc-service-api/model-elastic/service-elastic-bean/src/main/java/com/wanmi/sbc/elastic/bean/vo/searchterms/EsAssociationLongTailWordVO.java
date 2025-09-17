package com.wanmi.sbc.elastic.bean.vo.searchterms;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author houshuai
 */
@Data
@Schema
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsAssociationLongTailWordVO extends BasicResponse {

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
    private String longTailWord;

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

    /**
     * 是否删除 0 否  1 是
     */
    @Schema(description = "是否删除 0 否  1 是")
    private DeleteFlag delFlag;



    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 更新时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;


    /**
     * 删除时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "删除时间")
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deletePerson;

}