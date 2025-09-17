package com.wanmi.sbc.order.api.request.payorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class FindPayOrdersWithNoPageRequest extends BaseRequest {

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;

    /**
     * 收款流水号
     */
    @Schema(description = "收款流水号")
    private String payBillNo;

    /**
     * 支付方式 0线上 1线下
     */
    @Schema(description = "支付方式" ,contentSchema = com.wanmi.sbc.account.bean.enums.PayType.class)
    private Integer payType;

    /**
     * 在支付渠道id
     */
    @Schema(description = "在支付渠道id",contentMediaType = "com.wanmi.sbc.pay.api.enums.PayGatewayEnum.class")
    private Integer payChannelId;

    /**
     * 付款状态
     */
    @Schema(description = "支付状态")
    private PayOrderStatus payOrderStatus;

    /**
     * 收款开始时间
     */
    @Schema(description = "收款开始时间")
    private String startTime;

    /**
     * 收款结束时间
     */
    @Schema(description = "收款结束时间")
    private String endTime;

    /**
     * 收款单主键
     */
    @Schema(description = "收款单主键")
    private List<String> payOrderIds;

    /**
     * 收款账户id
     */
    @Schema(description = "收款账户id")
    private String accountId;

    @Schema(description = "token")
    private String token;

    /**
     * 是否根据收款时间排序
     */
    @Schema(description = "是否根据收款时间排序")
    private Boolean sortByReceiveTime = Boolean.FALSE;

    /**
     * 解决默认值为null导致空指针
     * @return
     */
    public Boolean getSortByReceiveTime() {
        if(this.sortByReceiveTime == null){
            return Boolean.FALSE;
        }
        return sortByReceiveTime;
    }

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private String companyInfoId;

    /**
     * 多个商家ids
     */
    @Schema(description = "多个商家ids")
    private List<Long> companyInfoIds;

    /**
     * 多个会员详细ids
     */
    @Schema(description = "多个会员详细ids")
    private List<String> customerDetailIds;

    /**
     * 收款账号账户名称
     */
    @Schema(description = "收款账号账户名称")
    private String account;

    /**
     * 多个收款账户id
     */
    @Schema(description = "多个收款账户id")
    private List<Long> accountIds;

    /**
     * 模糊查询order字段
     */
    @Schema(description = "模糊查询order字段")
    private String orderCode;
}
