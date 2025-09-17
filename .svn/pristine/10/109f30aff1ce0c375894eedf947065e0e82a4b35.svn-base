package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.CustomerDetailFromEsDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 会员详情查询参数
 * Created by CHENLI on 2017/4/19.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailListByCustomerIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 会员详细信息标识UUID
     */
    @Schema(description = "会员详细信息标识UUID")
    private List<CustomerDetailFromEsDTO> dtoList;

    @Schema(description = "公司ID")
    private Long companyInfoId;
}
