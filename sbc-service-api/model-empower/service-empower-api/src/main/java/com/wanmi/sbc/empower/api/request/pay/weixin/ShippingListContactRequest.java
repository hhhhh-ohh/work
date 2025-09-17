package com.wanmi.sbc.empower.api.request.pay.weixin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edy
 * @className ShippingListContactRequest
 * @description TODO
 * @date 2023/7/19 下午3:19
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingListContactRequest implements Serializable {

    private static final long serialVersionUID = 812130764616462832L;
    /**
     * 寄件人联系方式，寄件人联系方式，采用掩码传输，最后4位数字不能打掩码 示例值: `189****1234, 021-****1234, ****1234, 0**2-***1234, 0**2-******23-10, ****123-8008` 值限制: 0 ≤ value ≤ 1024
     */
    private String consignor_contact;

    /**
     * 收件人联系方式，收件人联系方式为，采用掩码传输，最后4位数字不能打掩码 示例值: `189****1234, 021-****1234, ****1234, 0**2-***1234, 0**2-******23-10, ****123-8008` 值限制: 0 ≤ value ≤ 1024
     */
    private String receiver_contact;
}
