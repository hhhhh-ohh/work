package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.ValuationType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 单品运费模板快递运送
 * Created by sunkun on 2018/5/3.
 */
@Schema
@Data
public class FreightTemplateGoodsExpressVO extends BasicResponse {

    private static final long serialVersionUID = 7745053035557404688L;

    /**
     * 主键标识
     */
    @Schema(description = "主键标识")
    private Long id;

    /**
     * 运费模板id
     */
    @Schema(description = "运费模板id")
    private Long freightTempId;

    /**
     * 配送地id(逗号分隔)
     */
    @Schema(description = "配送地id，逗号分隔")
    private String destinationArea;

    /**
     * 配送地名称(逗号分隔)
     */
    @Schema(description = "配送地名称，逗号分隔")
    private String destinationAreaName;

    /**
     * 计价方式(0:按件数,1:按重量,2:按体积) {@link ValuationType}
     */
    @Schema(description = "计价方式，0:按件数,1:按重量,2:按体积")
    private ValuationType valuationType;

    /**
     * 首件/重/体积
     */
    @Schema(description = "首件/重/体积")
    private BigDecimal freightStartNum;

    /**
     * 对应于首件/重/体积的起步价
     */
    @Schema(description = "对应于首件/重/体积的起步价")
    private BigDecimal freightStartPrice;

    /**
     * 续件/重/体积
     */
    @Schema(description = "续件/重/体积")
    private BigDecimal freightPlusNum;

    /**
     * 对应于续件/重/体积的价格
     */
    @Schema(description = "对应于续件/重/体积的价格")
    private BigDecimal freightPlusPrice;

    /**
     * 是否默认(0:否,1:是)
     */
    @Schema(description = "是否默认，0:否,1:是")
    private DefaultFlag defaultFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 是否删除(0:否,1:是)
     */
    @Schema(description = "是否删除，0:否,1:是")
    private DeleteFlag delFlag;

}
