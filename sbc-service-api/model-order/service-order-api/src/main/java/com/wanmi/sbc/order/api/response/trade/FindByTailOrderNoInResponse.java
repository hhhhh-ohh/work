package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className FindByParentIdResponse
 * @description TODO
 * @date 2022/10/14 22:07
 **/@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class FindByTailOrderNoInResponse implements Serializable {

     @Schema(description = "订单")
     private List<TradeVO> tradeVOList;
}
