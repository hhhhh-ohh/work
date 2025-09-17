package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 第三方订单的子订单商品信息
 * @author  wur
 * @date: 2021/5/18 11:36
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradePlatformSuborderItemVO extends BasicResponse {

    @Schema(description = "第三方商品名")
    private String name;

    @Schema(description = "第三方商品ID")
    private String skuId;

    @Schema(description = "商品数量")
    private Integer num;

}
