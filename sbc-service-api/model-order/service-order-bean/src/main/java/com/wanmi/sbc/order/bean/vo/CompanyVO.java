package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商家信息
 * Created by sunkun on 2017/11/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class CompanyVO extends BasicResponse {

    /**
     * 商家主键
     */
    @Schema(description = "商家主键")
    private Long companyInfoId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String companyCode;

    /**
     * 商家账号
     */
    @Schema(description = "商家账号")
    private String accountName;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private BoolFlag companyType;


    /**
     * 门店类型
     */
    @Schema(description = "门店类型")
    private StoreType storeType;
}
