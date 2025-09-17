package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CompanyType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className LedgerRelMobileVO
 * @description
 * @date 2022/9/14 10:42 AM
 **/
@Schema
@Data
public class LedgerRelMobileVO implements Serializable {
    private static final long serialVersionUID = 870470782063188728L;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型 0、平台自营 1、第三方商家")
    private Integer companyType;

    /**
     * 审核状态 0、待审核 1、已审核 2、已驳回
     */
    @Schema(description = "审核状态 0、待审核 1、已审核 2、已驳回")
    private Integer checkState;

    /**
     * 绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
     */
    @Schema(description = "绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败")
    private Integer bindState;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String rejectReason;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    private Long supplierId;




}
