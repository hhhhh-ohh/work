package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.account.bean.enums.ReturnStatus;
import com.wanmi.sbc.account.bean.enums.SettleCouponType;
import com.wanmi.sbc.account.bean.enums.SettleMarketingType;
import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hht on 2017/12/7.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LakalaSettleGoodVO extends SettleGoodVO {

    @Schema(description = "供货商")
    private String providerName;

    @Schema(description = "供货商ID")
    private String providerCompanyInfoId;

    @Schema(description = "分销员会员ID")
    private String distributorCustomerId;

    @Schema(description = "分销员")
    private String distributorName;

}
