package com.wanmi.sbc.marketing.coupon.request;

import com.wanmi.sbc.common.enums.TriggerNodeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
*
 * @description  初始化小程序订阅消息模板
 * @author  xufeng
 * @date: 2022/8/22 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class CouponSendMiniMsgRequest {

    private static final long serialVersionUID = 7801604113101604423L;

    /**
     * 用户记录主键id
     */
    @Schema(description = "用户记录主键id")
    private Long customerRecordId;

    /**
     * 推送活动id
     */
    @Schema(description = "推送活动id")
    private Long activityId;

    @Schema(description = "接收者（用户）的 openid")
    private List<String> toUsers;

    @Schema(description = "所需下发的订阅模板id")
    private String templateId;

    @Schema(description = "点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转")
    private String page;

    @Schema(description = "模板内容，格式形如 { \"key1\": { \"value\": any }, \"key2\": { \"value\": any } }")
    private Map<String, Object> data;

    @Schema(description = "触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时")
    private TriggerNodeType triggerNodeId;

    /**
     * 优惠券名称
     */
    @Schema(description = "优惠券名称")
    private String couponName;

    /**
     * 优惠券面值
     */
    @Schema(description = "优惠券面值")
    private String denomination;

    /**
     * 营销范围类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    @Schema(description = "营销范围类型")
    private String scopeTypeDec;

    /**
     * 有效期
     */
    @Schema(description = "有效期")
    private String effectiveDate;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private String couponNum;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

}
