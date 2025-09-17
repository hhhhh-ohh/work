package com.wanmi.sbc.customer.api.response.address;

import com.wanmi.sbc.customer.bean.vo.CustomerDeliveryAddressVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 会员收货地址-根据收货地址ID查询Response
 */
@Schema
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerDeliveryAddressByIdResponse extends CustomerDeliveryAddressVO {

    private static final long serialVersionUID = 1L;
}
