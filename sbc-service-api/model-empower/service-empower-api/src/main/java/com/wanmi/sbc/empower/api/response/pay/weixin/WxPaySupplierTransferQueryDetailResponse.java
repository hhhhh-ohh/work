package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 微信明细单号查询明细单返回值
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxPaySupplierTransferQueryDetailResponse implements Serializable {

    private static final long serialVersionUID = -6457221265995114893L;

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
     * 商家明细单号,商户系统内部区分转账批次单下不同转账明细单的唯一标识
     **/
    @Schema(description = "商家明细单号")
    private String out_detail_no;

    /**
     * 微信明细单号,微信支付系统内部区分转账批次单下不同转账明细单的唯一标识
     **/
    @Schema(description = "微信明细单号")
    private String detail_id;

    /**
     * 明细状态,
     * 枚举值：
     * PROCESSING：转账中。正在处理中，转账结果尚未明确
     * SUCCESS：转账成功
     * FAIL：转账失败。需要确认失败原因后，再决定是否重新发起对该笔明细单的转账（并非整个转账批次单）
     **/
    @Schema(description = "明细状态")
    private String detail_status;

    /**
     * 转账金额,	转账金额单位为分
     **/
    @Schema(description = "转账金额")
    private Integer transfer_amount;

    /**
     * 转账备注,单条转账备注（微信用户会收到该备注），UTF8编码，最多允许32个字符
     **/
    @Schema(description = "转账备注")
    private String transfer_remark;

    /**
     * 明细失败原因,
     * 如果转账失败则有失败原因
     * ACCOUNT_FROZEN：账户冻结
     * REAL_NAME_CHECK_FAIL：用户未实名
     * NAME_NOT_CORRECT：用户姓名校验失败
     * OPENID_INVALID：Openid校验失败
     * TRANSFER_QUOTA_EXCEED：超过用户单笔收款额度
     * DAY_RECEIVED_QUOTA_EXCEED：超过用户单日收款额度
     * MONTH_RECEIVED_QUOTA_EXCEED：超过用户单月收款额度
     * DAY_RECEIVED_COUNT_EXCEED：超过用户单日收款次数
     * PRODUCT_AUTH_CHECK_FAIL：产品权限校验失败
     * OVERDUE_CLOSE：转账关闭
     * ID_CARD_NOT_CORRECT：用户身份证校验失败
     * ACCOUNT_NOT_EXIST：用户账户不存在
     * TRANSFER_RISK：转账存在风险
     * REALNAME_ACCOUNT_RECEIVED_QUOTA_EXCEED：用户账户收款受限，请引导用户在微信支付查看详情
     * RECEIVE_ACCOUNT_NOT_PERMMIT：未配置该用户为转账收款人
     * PAYER_ACCOUNT_ABNORMAL：商户账户付款受限，可前往商户平台-违约记录获取解除功能限制指引
     * PAYEE_ACCOUNT_ABNORMAL：用户账户收款异常，请引导用户完善其在微信支付的身份信息以继续收款
     * TRANSFER_REMARK_SET_FAIL：转账备注设置失败，请调整对应文案后重新再试
     **/
    @Schema(description = "明细失败原因")
    private String fail_reason;

    /**
     * 用户在直连商户应用下的用户标示,用户在直连商户appid下的唯一标识
     **/
    @Schema(description = "用户在直连商户应用下的用户标示")
    private String openid;

    /**
     * 收款用户姓名,
     * 1、商户转账时传入了收款用户姓名、查询时会返回收款用户姓名；
     * 2、收款方姓名采用标准RSA算法，公钥由微信侧提供
     * 3、 该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     **/
    @Schema(description = "收款用户姓名")
    private String user_name;

    /**
     * 转账发起时间,示例值：2015-05-20T13:29:35.120+08:00
     **/
    @Schema(description = "转账发起时间")
    private String initiate_time;

    /**
     * 明细更新时间,示例值：2015-05-20T13:29:35.120+08:00
     **/
    @Schema(description = "明细更新时间")
    private String update_time;

    /**
     * 描述
     **/
    @Schema(description = "描述")
    private String message;
}
