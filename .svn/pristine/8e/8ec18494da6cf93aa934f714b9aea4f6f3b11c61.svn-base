package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.MatterType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema
public class DistributionGoodsMatterPageVO extends BasicResponse {

    @Schema(description = "id")
    private String id;

    @Schema(description = "商品sku的id")
    private String goodsInfoId;

    @Schema(description = "素材类型")
    private MatterType matterType;

    @Schema(description = "素材")
    private String matter;

    @Schema(description = "推荐语")
    private String recommend;

    @Schema(description = "推荐次数")
    private Integer recommendNum;

    @Schema(description = "发布者id")
    private String operatorId;

    @Schema(description = "发布者名称")
    private String name;

    @Schema(description = "发布者账号")
    private String account;

    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "商品信息")
    private GoodsInfoVO goodsInfo;

    @Schema(description = "店铺Id")
    private Long storeId;
}
