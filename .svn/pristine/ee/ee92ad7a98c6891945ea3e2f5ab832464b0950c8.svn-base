package com.wanmi.sbc.goods.api.request.distributionmatter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.goods.bean.enums.MatterType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema
public class DistributionGoodsMatterModifyRequest extends BaseRequest {

    @Schema(description = "id")
    @NotBlank
    private String id;


    @Schema(description = "商品sku的id")
    private String goodsInfoId;

    @Schema(description = "素材类型")
    @NotNull
    private MatterType matterType;

    /**
     * 素材
     */
    @Schema(description = "素材")
    @NotBlank
    private String matter;

    /**
     * 推荐语
     */
    @Schema(description = "推荐语")
    @NotBlank
    private String recommend;

    /**
     * 发布者id
     */
    @Schema(description = "发布者id")
    private String operatorId;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 是否开启社交分销 0：关闭，1：开启
     */
    @Schema(description = "是否开启社交分销 0：关闭，1：开启")
    private DefaultFlag openFlag;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态 0、开启 1、关店")
    private StoreState storeState;

    /**
     * 签约结束日期
     */
    @Schema(description = "签约结束日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;
}
