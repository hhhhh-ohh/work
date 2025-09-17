package com.wanmi.sbc.order.api.response.purchase;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @ClassName PurchaseGoodsInfoSelectionResponse
 * @Description TODO
 * @Author qiyuanzhao
 * @Date 2022/5/26 17:39
 **/
@Data
@Schema
@Builder
public class PurchaseGoodsInfoSelectionResponse {


    private Set<Object> goodsInfoIds;


}
