package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.order.bean.vo.TradeGoodsListVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 获取订单商品详情响应
 * Created by daiyitian on 2017/3/24.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class TradeGetGoodsResponse extends TradeGoodsListVO {

    private static final long serialVersionUID = 1L;
}
