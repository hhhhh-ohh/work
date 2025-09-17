package com.wanmi.sbc.flashsale.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @ClassName RushToBuyFlashSaleGoodsRequest
 * @Description 秒杀抢购活动Request请求类
 * @Author lvzhenwei
 * @Date 2019/6/14 9:38
 **/
@Schema
@Data
public class RushToBuyFlashSaleGoodsRequest extends BaseRequest {

    /**
     * 抢购商品Id
     */
    @Schema(description = "抢购商品Id")
    @NotNull
    private Long flashSaleGoodsId;

    /**
     * 抢购商品数量
     */
    @Schema(description = "抢购商品数量")
    private Integer flashSaleGoodsNum;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 秒杀商品会员抢购次数
     */
    @Schema(description = "秒杀商品会员抢购次数")
    private Integer flashSaleNum;

    @Schema(description = "终端登陆token")
    private String terminalToken;

    /**
     * skuID
     */
    @Schema(description = "skuID")
    private String goodsInfoId;

}
