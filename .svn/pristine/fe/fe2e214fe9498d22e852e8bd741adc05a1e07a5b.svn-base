package com.wanmi.sbc.empower.pay.service.wechat.v3.request;

import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForMWebRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @description  微信扫码支付下单请求
 * @author  wur
 * @date: 2022/11/28 13:50
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WxPayV3ForH5Request extends WxPayBaseRequest {

    private static final long serialVersionUID = 7736763604536062258L;

    /**
     * 用户信息
     */
    private WxPayAmount amount;

    /**
     * 场景信息
     */
    private WxPaySceneInfo scene_info;

    public WxPayV3ForH5Request(WxPayForMWebRequest request) {
        this.setAppid(request.getAppid());
        this.setDescription(request.getBody());
        this.setOut_trade_no(request.getOut_trade_no());
        this.amount = new WxPayAmount();
        amount.setTotal(Integer.valueOf(request.getTotal_fee()));
        this.setAmount(amount);
        WxPaySceneInfo sceneInfo = new WxPaySceneInfo();
        WxPayH5Info h5Info = new WxPayH5Info();
        h5Info.setType("Wap");
        sceneInfo.setH5_info(h5Info);
        sceneInfo.setPayer_client_ip(request.getSpbill_create_ip());
        this.setScene_info(sceneInfo);
        this.setTime_expire(request.getTime_expire());
    }

}
