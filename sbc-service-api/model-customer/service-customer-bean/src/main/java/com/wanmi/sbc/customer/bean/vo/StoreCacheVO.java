package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CompanySourceType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.StoreState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 店铺信息
 * Created by CHENLI on 2017/11/2.
 */
@Schema
@Data
public class StoreCacheVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 公司信息编号
     */
    @Schema(description = "公司信息编号")
    private Long companyInfoId;


    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 省
     */
    @Schema(description = "省")
    private Long provinceId;

    /**
     * 市
     */
    @Schema(description = "市")
    private Long cityId;

    /**
     * 区
     */
    @Schema(description = "区")
    private Long areaId;

    /**
     * 街道
     */
    @Schema(description = "街道")
    private Long streetId;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String addressDetail;


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
     * 店铺小程序码
     */
    @Schema(description = "店铺小程序码")
    private String smallProgramCode;

    /**
     * 商家类型0品牌商城，1商家
     */
    @Schema(description = "商家类型0品牌商城，1商家")
    private StoreType storeType;

    /**
     * 商家来源类型 0:商城入驻 1:linkMall初始化
     */
    @Schema(description = "商家来源类型 0:商城入驻 1:linkMall初始化")
    private CompanySourceType companySourceType;

//    /**
//     * 店铺状态 0、开启 1、关店
//     */
//    @Schema(description = "店铺状态 0、开启 1、关店")
//    private StoreState storeState;
//
//    /**
//     * 签约结束日期
//     */
//    @Schema(description = "签约结束日期")
//    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
//    private LocalDateTime contractEndDate;
}
