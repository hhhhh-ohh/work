package com.wanmi.sbc.empower.api.request.sellplatform.returnorder;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.bean.vo.sellplatform.returnorder.PlatformDefaultAddressVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
*
 * @description WxChannelsAcceptReturnRequest  同意退单
 * @author  wur
 * @date: 2022/4/8 17:14
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatformAcceptReturnRequest extends ThirdBaseRequest {

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
     * 商家收货地址
     * 必传
     */
    private PlatformDefaultAddressVO address_info;

}
