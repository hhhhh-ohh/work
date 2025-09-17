package com.wanmi.sbc.empower.api.response.pay.channelItem;

import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>支付渠道项response</p>
 * Created by of628-wenzhi on 2018-08-10-下午3:53.
 */
@Schema
@Data
public class PayChannelItemListResponse  implements Serializable{

    private static final long serialVersionUID = -3948305700240678166L;
    /**
     * 支付渠道项列表
     */
    @Schema(description = "支付渠道项列表")
    private List<PayChannelItemVO> payChannelItemVOList;
}
