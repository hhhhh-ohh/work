package com.wanmi.sbc.customer.api.response.address;

import com.wanmi.sbc.customer.bean.vo.CustomerDeliveryAddressVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 会员收货地址-修改Response
 */
@Schema
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerDeliveryAddressModifyResponse extends CustomerDeliveryAddressVO implements Serializable{

    private static final long serialVersionUID = 1L;
}
