package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName WXPaySUpplierTransferBatchResponse
 * @Description TODO
 * @Author qiyuanzhao
 * @Date 2022/8/18 9:50
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WXPaySUpplierTransferBatchResponse {

    /**
     * 商户号,微信支付分配的商户号
     **/
    @Schema(description = "商户号")
    private String mchid;

    /**
     * 商家批次单号,商户系统内部的商家批次单号，在商户系统内部唯一
     **/
    @Schema(description = "商家批次单号")
    private String out_batch_no;

    /**
     * 微信批次单号,微信批次单号，微信商家转账系统返回的唯一标识
     **/
    @Schema(description = "微信批次单号")
    private String batch_id;

    /**
     * 直连商户的appid,申请商户号的appid或商户号绑定的appid（企业号corpid即为此appid）
     **/
    @Schema(description = "直连商户的appid")
    private String appid;

    /**
     * 批次状态
     * WAIT_PAY：待付款，商户员工确认付款阶段
     * ACCEPTED:已受理。批次已受理成功，若发起批量转账的30分钟后，转账批次单仍处于该状态，可能原因是商户账户余额不足等。商户可查询账户资金流水，若该笔转账批次单的扣款已经发生，则表示批次已经进入转账中，请再次查单确认
     * PROCESSING:转账中。已开始处理批次内的转账明细单
     * FINISHED：已完成。批次内的所有转账明细单都已处理完成
     * CLOSED：已关闭。可查询具体的批次关闭原因确认
     **/
    @Schema(description = "批次状态")
    private String batch_status;

    /**
     * 批次类型
     * 枚举值：
     * API：API方式发起
     * WEB：页面方式发起
     **/
    @Schema(description = "批次类型")
    private String batch_type;

    /**
     * 批次名称,该笔批量转账的名称
     **/
    @Schema(description = "批次名称")
    private String batch_name;

    /**
     * 批次备注,	转账说明，UTF8编码，最多允许32个字符
     **/
    @Schema(description = "批次备注")
    private String batch_remark;

    /**
     * 批次关闭原因
     *
     * 如果批次单状态为“CLOSED”（已关闭），则有关闭原因
     * MERCHANT_REVOCATION：商户主动撤销
     * OVERDUE_CLOSE：系统超时关闭
     **/
    @Schema(description = "批次关闭原因")
    private String close_reason;

    /**
     * 转账总金额,转账金额单位为分
     **/
    @Schema(description = "转账总金额")
    private Integer total_amount;

    /**
     * 转账总笔数,一个转账批次单最多发起三千笔转账
     **/
    @Schema(description = "转账总笔数")
    private Integer total_num;

    /**
     * 批次创建时间,示例值：2015-05-20T13:29:35.120+08:00
     **/
    @Schema(description = "批次创建时间")
    private String create_time;

    /**
     * 批次更新时间,示例值：2015-05-20T13:29:35.120+08:00
     **/
    @Schema(description = "批次更新时间")
    private String update_time;


    /**
     * 转账成功金额,转账成功的金额，单位为分。当批次状态为“PROCESSING”（转账中）时，转账成功金额随时可能变化
     **/
    @Schema(description = "转账成功金额")
    private Integer success_amount;


    /**
     * 转账成功笔数,	转账成功的笔数。当批次状态为“PROCESSING”（转账中）时，转账成功笔数随时可能变化
     **/
    @Schema(description = "转账成功笔数")
    private Integer success_num;


    /**
     * 转账失败金额,转账失败的金额，单位为分
     **/
    @Schema(description = "转账失败金额")
    private Integer fail_amount;

    /**
     * 转账失败笔数,转账失败的笔数
     **/
    @Schema(description = "转账失败笔数")
    private Integer fail_num;


}
