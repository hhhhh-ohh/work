package com.wanmi.sbc.setting.bean.vo;

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


/**
 * <p>热门搜索VO</p>
 * @author weiwenhao
 * @date 2020-04-17
 */
@Schema
@Data
public class PopularSearchTermsVO extends BasicResponse {

    /**
     * 主键id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 热门搜索词
     */
    @Schema(description = "热门搜索词")
    private String popularSearchKeyword;

    /**
     * 移动端落地页
     */
    @Schema(description = "移动端落地页")
    private String relatedLandingPage;

    /**
     * PC落地页
     */
    @Schema(description = "PC落地页")
    private String pcLandingPage;

    /**
     * 排序号
     */
    @Schema(description ="排序号")
    private Long sortNumber;

    /**
     * 是否删除 0 否  1 是
     */
    @Schema(description = "是否删除")
    private DeleteFlag delFlag;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

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
