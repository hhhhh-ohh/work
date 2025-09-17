package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺信息
 * Created by CHENLI on 2017/11/2.
 */
@Schema
@Data
public class MiniStoreVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型(0、平台自营 1、第三方商家)")
    private BoolFlag companyType;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 一对多关系，多个SPU编号
     */
    @Schema(description = "多个SPU编号")
    private List<String> goodsIds = new ArrayList<>();

    /**
     * 一对多关系，多个SKU编号
     */
    @Schema(description = "多个SKU编号")
    private List<String> goodsInfoIds = new ArrayList<>();

    /**
     * 商家类型0品牌商城，1商家
     */
    @Schema(description = "商家类型0品牌商城，1商家")
    private StoreType storeType;

    @Schema(description = "门店与地址之间是否支持配送")
    private Boolean deliveryFlag;

    /**
     * 是否有券活动
     */
    @Schema(description = "是否有券活动")
    private Boolean isStoreCoupon;
}
