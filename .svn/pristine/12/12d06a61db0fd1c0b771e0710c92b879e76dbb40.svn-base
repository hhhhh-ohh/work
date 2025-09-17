package com.wanmi.sbc.customer.api.response.enterpriseinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.EnterpriseInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>企业信息表列表结果</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 企业信息表列表结果
     */
    @Schema(description = "企业信息表列表结果")
    private List<EnterpriseInfoVO> enterpriseInfoVOList;
}
