package com.wanmi.sbc.empower.api.request.sellplatform.returnorder;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
*
 * @description  WxChannelsUpReturnInfoRequest 上传退单物流信息
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatformUpReturnInfoRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 微信侧售后单号
     */
    private String aftersale_id;

    /**
     * 外部售后单号，和aftersale_id二选一
     */
    private String out_aftersale_id;

    /**
     * 用户 openid
     * 必传
     */
    private String openid;

    /**
     * 物流单号
     * 必传
     */
    private String waybill_id;

    /**
     *  物流公司Id
     *  必传
     */
    private String delivery_id;

    /**
     *   物流公司名
     */
    private String delivery_name;

}
