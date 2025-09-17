package com.wanmi.sbc.empower.api.request.pay.weixin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edy
 * @className UploadShippingInfoPayerRequest
 * @description TODO
 * @date 2023/7/19 下午3:24
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadShippingInfoPayerRequest implements Serializable {

    private static final long serialVersionUID = -1118086622387930857L;
    /**
     * 用户标识，用户在小程序appid下的唯一标识。 下单前需获取到用户的Openid 示例值: oUpF8uMuAJO_M2pxb1Q9zNjWeS6o 字符字节限制: [1, 128]
     */
    private String openid;
}
