package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCompanyVO extends BasicResponse {

    private static final long serialVersionUID = -2310808028751350150L;


    @Schema(description = "商家列表")
    private List<CompanyInfoVO> companyInfoLis;


}
