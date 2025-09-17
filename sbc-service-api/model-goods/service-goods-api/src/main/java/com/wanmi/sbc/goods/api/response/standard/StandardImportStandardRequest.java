package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.standard.StandardImportStandardRequest
 *
 * @author lipeng
 * @dateTime 2018/11/9 下午2:53
 */
@Schema
@Data
public class StandardImportStandardRequest extends BaseRequest {

    private static final long serialVersionUID = 3640997975163669381L;

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
    @Schema(description = "商家类型，0:平台自营, 1: 第三方商家")
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
}
