package com.wanmi.sbc.empower.api.response.pay.channelItem;

import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>支付渠道项response</p>
 * Created by of628-wenzhi on 2018-08-10-下午3:53.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class PayChannelItemResponse extends PayChannelItemVO implements Serializable{

    private static final long serialVersionUID = 8929875300784013687L;
}
