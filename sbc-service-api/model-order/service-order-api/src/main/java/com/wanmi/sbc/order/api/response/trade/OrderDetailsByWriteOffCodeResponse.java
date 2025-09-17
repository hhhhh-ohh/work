package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className OrderDetailsByWriteOffCodeResponse
 * @description TODO
 * @author 黄昭
 * @date 2021/9/10 15:59
 **/
@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsByWriteOffCodeResponse extends BasicResponse {
    private static final long serialVersionUID = 7757750618379573140L;

    private TradeVO tradeVO;
}
