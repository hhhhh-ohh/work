package com.wanmi.sbc.empower.api.request.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description  微信视频号退款
 * @author  wur
 * @date: 2022/4/25 14:56
 **/
@Schema
@Data
public class WxChannelsPayRefundRequest {

    private static final long serialVersionUID = 8608617872604487533L;

    @Schema(description = "微信侧售后单号")
    private String aftersale_id;

    @Schema(description = "外部售后单号，和aftersale_id二选一")
    private String out_aftersale_id;

}
