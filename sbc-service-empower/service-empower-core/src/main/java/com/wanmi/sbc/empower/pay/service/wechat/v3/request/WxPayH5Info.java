package com.wanmi.sbc.empower.pay.service.wechat.v3.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wur
 * @className WxPayPare
 * @description 微信H5支付场景信息
 * @date 2022/11/28 18:43
 **/
@Data
public class WxPayH5Info implements Serializable {

    private static final long serialVersionUID = 7736763604536062258L;
    /**
     * 场景类型  示例值：iOS, Android, Wap
     */
    private String type;

    /**
     * 商户端设备号
     */
    private String device_id;
    

}
