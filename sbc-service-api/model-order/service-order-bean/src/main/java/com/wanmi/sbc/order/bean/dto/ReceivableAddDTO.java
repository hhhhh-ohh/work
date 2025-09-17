package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 新增收款单参数
 * Created by zhangjin on 2017/4/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ReceivableAddDTO implements Serializable {

    /**
     * 支付单id
     */
    @Schema(description = "支付单id", required = true)
    @NotEmpty
    private String payOrderId;

    /**
     * 收款单时间
     */
    @Schema(description = "收款单时间", required = true)
    @NotEmpty
    private String createTime;

    /**
     * 评价
     */
    @Schema(description = "评价")
    private String comment;

    /**
     * 收款账号
     */
    @Schema(description = "收款账号")
    private Long accountId;

    /**
     * 线上支付渠道描述，在线支付必传
     */
    @Schema(description = "线上支付渠道描述，在线支付必传")
    private String payChannel;

    /**
     * 线上支付渠道id，在线支付必传
     */
    @Schema(description = "线上支付渠道id，在线支付必传")
    private Long payChannelId;

    /**
     * 附件
     */
    @Schema(description = "附件")
    private String encloses;
}
