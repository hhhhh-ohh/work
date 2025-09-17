package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Schema
public class PurchaseVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 采购单编号
     */
    @Schema(description = "采购单编号")
    private Long purchaseId;

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String goodsInfoId;

    /**
     * 全局购买数
     */
    @Schema(description = "全局购买数")
    private Long goodsNum;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 采购创建时间
     */
    @Schema(description = "采购创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "类型： 0-正常商品 1-跨境商品 2-o2o商品")
    private PluginType pluginType;
}
