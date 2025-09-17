package com.wanmi.sbc.drawrecord.request;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：
 *
 * @ClassName RedeemPrizeRequest
 * @Description 领取奖品Request
 * @Author ghj
 * @Date 4/16/21 10:25 AM
 * @Version 1.0
 */
@Schema
@Data
public class RedeemPrizeRequest implements Serializable {

    private static final long serialVersionUID = -5513227067125147002L;


    /**
     * 抽奖记录主键
     */
    @Schema(description = "抽奖记录主键")
    @Max(9223372036854775807L)
    private Long id;

    /**
     * 详细收货地址
     */
    @Schema(description = "详细收货地址")
    private String deliveryAddress;

    /**
     * 收货人姓名
     */
    @Schema(description = "收货人姓名")
    private String consigneeName;

    /**
     * 收货人电话
     */
    @Schema(description = "收货人电话")
    private String consigneeNumber;


}
