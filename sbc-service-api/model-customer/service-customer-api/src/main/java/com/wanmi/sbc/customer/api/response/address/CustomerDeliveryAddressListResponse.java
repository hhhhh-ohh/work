package com.wanmi.sbc.customer.api.response.address;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDeliveryAddressVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDeliveryAddressListResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "会员收货地址")
    private List<CustomerDeliveryAddressVO> customerDeliveryAddressVOList = new ArrayList<>();
}
