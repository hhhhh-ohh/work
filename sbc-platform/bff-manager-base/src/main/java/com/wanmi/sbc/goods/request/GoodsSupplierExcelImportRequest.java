package com.wanmi.sbc.goods.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 导入商品信息请求对象
 * @author lipeng
 * @dateTime 2018/11/2 上午9:54
 */
@Schema
@Data
public class GoodsSupplierExcelImportRequest extends BaseRequest {

    @Schema(description = "用户Id")
    private String userId;

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
    @Schema(description = "商家类型")
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
     * 文件后缀
     */
    @Schema(description = "文件后缀")
    private String ext;

    /**
     * Excel模板类型 0:商家,1:供应商
     */
    @Schema(description = "Excel模板类型")
    private StoreType type;

    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;


    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;
}
