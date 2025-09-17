package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.enums.StoreResponseState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺基本信息
 * Created by bail on 2017/11/29.
 */
@Schema
@Data
public class StoreCustomerFollowVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
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
     * 店铺店招
     */
    @Schema(description = "店铺店招")
    private String storeSign;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型",contentSchema = com.wanmi.sbc.customer.bean.enums.CompanyType.class)
    private Integer companyType;

    /**
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    private String contactPerson;

    /**
     * 联系方式getCompanyType
     */
    @Schema(description = "联系方式")
    private String contactMobile;

    /**
     * 联系邮箱
     */
    @Schema(description = "联系邮箱")
    private String contactEmail;

    /**
     * 省份
     */
    @Schema(description = "省份")
    private Long provinceId;

    /**
     * 地市
     */
    @Schema(description = "地市")
    private Long cityId;

    /**
     * 区
     */
    @Schema(description = "区")
    private Long areaId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 扁平化商品信息，一对多关系
     */
    @Schema(description = "扁平化商品信息")
    private List<String> skuIds = new ArrayList<>();

    /**
     * 店铺响应状态
     */
    @Schema(description = "店铺响应状态")
    private StoreResponseState storeResponseState;

    /**
     * 店铺是否被关注
     */
    @Schema(description = "店铺是否被关注")
    private Boolean isFollowed = Boolean.FALSE;
}
