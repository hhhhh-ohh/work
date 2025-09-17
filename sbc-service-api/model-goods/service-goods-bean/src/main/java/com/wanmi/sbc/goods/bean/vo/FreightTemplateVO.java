package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 运费模板
 * Created by sunkun on 2018/5/2.
 */
@Schema
@Data
public class FreightTemplateVO extends BasicResponse {

    private static final long serialVersionUID = -2243723680863364216L;

    /**
     * 运费模板id
     */
    @Schema(description = "运费模板id")
    private Long freightTempId;

    /**
     * 运费模板名称
     */
    @Schema(description = "运费模板名称")
    private String freightTempName;

    /**
     * 运送方式(1:快递配送) {@link DeliverWay}
     */
    @Schema(description = "运送方式，0: 默认方式, 1: 快递配送")
    private DeliverWay deliverWay;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 默认标识
     */
    @Schema(description = "默认标识，0: 否, 1: 是")
    private DefaultFlag defaultFlag;

    /**
     * 删除标识
     */
    @Schema(description = "删除标识，0: 否, 1: 是")
    private DeleteFlag delFlag;

}
