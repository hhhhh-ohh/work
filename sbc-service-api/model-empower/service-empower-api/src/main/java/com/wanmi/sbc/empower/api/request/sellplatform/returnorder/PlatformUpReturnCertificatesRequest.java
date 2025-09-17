package com.wanmi.sbc.empower.api.request.sellplatform.returnorder;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
*
 * @description  WxChannelsUpReturnCertificatesRequest 上传退款凭证
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatformUpReturnCertificatesRequest extends ThirdBaseRequest {

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
     * 协商文本内容
     */
    private String refund_desc;

    /**
     *  凭证图片列表
     */
    private List<String> certificates;


}
