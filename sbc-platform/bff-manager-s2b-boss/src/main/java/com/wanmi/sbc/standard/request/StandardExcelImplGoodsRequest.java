package com.wanmi.sbc.standard.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
public class StandardExcelImplGoodsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "后缀")
    @NotBlank
    private String ext;

    /**
     * 批量商品编号
     */
    @Schema(description = "批量商品编号")
    private List<String> goodsIds;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 公司信息Id
     */
    @Schema(description = "公司信息Id")
    private Long companyInfoId;

    /**
     * 客户等级
     */
    @Schema(description = "客户等级")
    private Long customerLevelId;

    /**
     * 客户等级折扣
     */
    @Schema(description = "客户等级折扣")
    private BigDecimal customerLevelDiscount;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型 0、平台自营 1、第三方商家")
    private BoolFlag companyType;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String supplierName;

    /**
     * 商品的店铺分类
     */
    @Schema(description = "商品的店铺分类")
    private List<Long> storeCateIds;

    /**
     * 运费模板ID
     */
    @Schema(description = "运费模板ID")
    private Long freightTempId;

    /**
     * 操作员id
     */
    @Schema(description = "操作员id")
    private String userId;

    /**
     * 商品类型 0、实物商品 1、虚拟商品 2、电子卡券
     */
    @Schema(description = "商品类型 0、实物商品 1、虚拟商品 2、电子卡券")
    private Integer goodsType;
}
