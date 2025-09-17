package com.wanmi.sbc.setting.api.response.companyinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.CompanyInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>公司信息分页结果</p>
 * @author lq
 * @date 2019-11-05 16:09:36
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfoPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 公司信息分页结果
     */
    @Schema(description = "公司信息分页结果")
    private MicroServicePage<CompanyInfoVO> companyInfoVOPage;
}
