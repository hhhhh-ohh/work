package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xuyunpeng
 * @className OrderReturnItemNumResponse
 * @description 售后商品数量
 * @date 2021/11/17 3:05 下午
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReturnItemNumResponse extends BasicResponse {

    private static final long serialVersionUID = 7255298393721247546L;

    @Schema(description = "退货商品数量，skuId:num")
    private Map<String, Integer> returnItemMap;

    @Schema(description = "退货赠品数量，skuId:num")
    private Map<Long, Map<String, Integer>> returnGiftsMap;
}
