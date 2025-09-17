package com.wanmi.sbc.customer.api.response.enterpriseinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.EnterpriseInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>企业信息表修改结果</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的企业信息表信息
     */
    @Schema(description = "已修改的企业信息表信息")
    private EnterpriseInfoVO enterpriseInfoVO;
}
