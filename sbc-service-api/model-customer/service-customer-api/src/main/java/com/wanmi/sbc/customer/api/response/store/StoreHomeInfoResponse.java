package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.enums.StoreResponseState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 店铺主页基本信息
 * Created by chenli on 2017/12/18.
 */
@Schema
@Data
public class StoreHomeInfoResponse extends BasicResponse {
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
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String contactMobile;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

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
