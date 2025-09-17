package com.wanmi.sbc.customer.api.response.enterpriseinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.EnterpriseInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>企业信息表分页结果</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 企业信息表分页结果
     */
    @Schema(description = "企业信息表分页结果")
    private MicroServicePage<EnterpriseInfoVO> enterpriseInfoVOPage;
}
