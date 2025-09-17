package com.wanmi.sbc.setting.api.response.companyinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.CompanyInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>公司信息列表结果</p>
 * @author lq
 * @date 2019-11-05 16:09:36
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfoListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 公司信息列表结果
     */
    @Schema(description = "公司信息列表结果")
    private List<CompanyInfoVO> companyInfoVOList;
}
