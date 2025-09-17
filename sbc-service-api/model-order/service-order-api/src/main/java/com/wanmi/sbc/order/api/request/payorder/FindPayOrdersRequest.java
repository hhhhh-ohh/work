package com.wanmi.sbc.order.api.request.payorder;


import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.QueryPayType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.order.bean.enums.OrderDeductionType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 收款单查询条件
 * Created by zhangjin on 2017/4/20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class FindPayOrdersRequest extends BaseQueryRequest {

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

    @Schema(description = "员工相关会员detailId列表")
    private List<String> employeeCustomerDetailIds;

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

    /**
     * 订单号-批量
     */
    private List<String> orderNoList;

    /**
     * 门店/店铺名称
     */
    @Schema(description = "门店/店铺名称")
    private String storeName;

    /**
     * 筛选支付方式
     */
    @Schema(description = "筛选支付方式")
    private QueryPayType queryPayType;

    /**
     * 支付渠道
     */
    @Schema(description = "支付渠道，0：微信 1：支付宝 5：企业银联 6：云闪付 8：余额 9：授信 10：拉卡拉")
    private PayWay payWay;

    /**
     * 抵扣方式
     */
    @Schema(description = "支付渠道，0：积分 1：礼品卡")
    private OrderDeductionType orderDeductionType;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型")
    private StoreType storeType;

    /**
     * 负责业务员集合
     */
    @Schema(description = "负责业务员集合")
    private List<String> employeeIds;
}
