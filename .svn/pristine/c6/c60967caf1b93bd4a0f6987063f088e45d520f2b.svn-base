package com.wanmi.sbc.customer.api.response.enterpriseinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.EnterpriseInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）企业信息表信息response</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoByCustomerIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 企业信息表信息
     */
    @Schema(description = "企业信息表信息")
    private EnterpriseInfoVO enterpriseInfoVO;
}
