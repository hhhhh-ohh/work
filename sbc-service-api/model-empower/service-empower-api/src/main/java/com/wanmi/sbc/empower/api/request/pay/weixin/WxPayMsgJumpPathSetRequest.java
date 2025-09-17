package com.wanmi.sbc.empower.api.request.pay.weixin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description
 * @author
 * @date
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WxPayMsgJumpPathSetRequest implements Serializable {

    private static final long serialVersionUID = -7235036131607693930L;

    /**
     * 商户自定义跳转路径
     */
    private String path;
}
