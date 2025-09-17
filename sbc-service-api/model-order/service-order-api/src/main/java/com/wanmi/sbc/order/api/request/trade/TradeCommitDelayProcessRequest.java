package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>客户端提交订单参数结构，包含除商品信息外的其他必要参数</p>
 * Created by of628-wenzhi on 2017-07-18-下午3:40.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class TradeCommitDelayProcessRequest extends BaseRequest {

    private static final long serialVersionUID = -1555919128448507297L;

    /**
     * 用户终端token用于区分同一用户存在多端登陆的情况
     */
    private String terminalToken;

}
