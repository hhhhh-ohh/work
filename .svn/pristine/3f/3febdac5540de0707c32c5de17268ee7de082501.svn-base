package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailFromEsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 会员信息响应
 * Created by CHENLI on 2017/4/19.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailListByCustomerIdsResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "客户列表")
    private List<CustomerDetailFromEsVO> customerDetailFromEsVOS;
}

