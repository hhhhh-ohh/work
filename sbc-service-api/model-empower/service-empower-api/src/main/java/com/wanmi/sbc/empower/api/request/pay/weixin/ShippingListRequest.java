package com.wanmi.sbc.empower.api.request.pay.weixin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvzhenwei
 * @className ShippingListRequest
 * @description TODO
 * @date 2023/7/19 下午3:16
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingListRequest implements Serializable {

    private static final long serialVersionUID = -6167520408993398920L;
    /**
     * 物流单号，物流快递发货时必填，示例值: 323244567777 字符字节限制: [1, 128]
     */
    private String tracking_no;

    /**
     * 物流公司编码，快递公司ID，参见「查询物流公司编码列表」，物流快递发货时必填， 示例值: DHL 字符字节限制: [1, 128]
     */
    private String express_company;

    /**
     * 商品信息，例如：微信红包抱枕*1个，限120个字以内
     */
    private String item_desc;

    /**
     * 联系方式，当发货的物流公司为顺丰时，联系方式为必填，收件人或寄件人联系方式二选一
     */
    private ShippingListContactRequest contact;

}
