package com.wanmi.sbc.customer.api.response.company;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class CompanyInfoQueryByIdsResponse extends BasicResponse {

   @Schema(description = "公司信息列表")
   private List<CompanyInfoVO> companyInfoList;
}
