package com.wanmi.sbc.customer.api.response.company;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CompanyAccountVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商家账号分页响应
 * Created by sunkun on 2017/12/4.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAccountPageResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 公司信息列表
     */
    @Schema(description = "公司信息列表")
    private MicroServicePage<CompanyAccountVO> companyAccountVOPage;

}
