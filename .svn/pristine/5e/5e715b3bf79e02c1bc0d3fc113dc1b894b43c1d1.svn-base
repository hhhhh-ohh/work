package com.wanmi.sbc.empower.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 物流记录-简化版
 * Created by dyt on 2020/4/17.
 */
@Data
public class LogisticsLogSimpleVO implements Serializable {

    private static final long serialVersionUID = -6414564526969459575L;

    //1
    @Schema(description = "id")
    private String id;

    //2
    @Schema(description = "商品图片")
    private String goodsImg;

    //
    @Schema(description = "商品名称")
    private String goodsName;

    //
    @Schema(description = "快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态")
    private String state;

    //
    @Schema(description = "内容")
    private String context;

    //
    @Schema(description = "时间")
    private String time;

    //
    @Schema(description = "订单号")
    private String orderNo;

    //
    @Schema(description = "本地发货单号")
    private String deliverId;
}
