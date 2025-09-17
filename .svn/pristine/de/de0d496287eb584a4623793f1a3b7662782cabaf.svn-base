package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商品模板配置实体类
 * Created by xiemengnan on 2018/10/12.
 */
@Data
@Schema
public class StoreGoodsTabSaveVO extends BasicResponse {

    /**
     * 模板标识
     */
    @Schema(description = "模板标识")
    private Long tabId;


    /**
     * 模板名称
     */
    @NotBlank
    @Schema(description = "模板名称")
    private String tabName;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识")
    private Long storeId;

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
     * 删除时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "删除时间")
    private LocalDateTime delTime;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private DeleteFlag delFlag;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;
    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String delPerson;
}

