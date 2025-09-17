package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailWithImgVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class CustomerIdsListResponse extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "会员信息列表")
    private List<CustomerDetailWithImgVO> customerVOList;
}
