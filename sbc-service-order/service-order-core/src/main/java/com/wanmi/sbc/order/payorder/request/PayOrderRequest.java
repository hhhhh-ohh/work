package com.wanmi.sbc.order.payorder.request;


import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
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
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderRequest extends BaseQueryRequest {

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 收款流水号
     */
    private String payBillNo;

    /**
     * 支付方式 0线上 1线下
     */
    private Integer payType;

    /**
     * 在支付渠道id
     */
    private Integer payChannelId;


    /**
     * 在支付渠道ids(用于查询)
     */
    private List<Long> payChannelIds;

    /**
     * 付款状态
     */
    private PayOrderStatus payOrderStatus;

    /**
     * 筛选支付方式
     */
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
     * 收款开始时间
     */
    private String startTime;

    /**
     * 收款结束时间
     */
    private String endTime;

    /**
     * 收款单主键
     */
    private List<String> payOrderIds;

    /**
     * 收款账户id
     */
    private String accountId;

    private String token;

    /**
     * 是否根据收款时间排序
     */
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
    private String supplierName;

    /**
     * 商家id
     */
    private String companyInfoId;

    /**
     * 多个商家ids
     */
    private List<Long> companyInfoIds;

    /**
     * 多个会员详细ids
     */
    private List<String> customerDetailIds;

    private List<String> employeeCustomerDetailIds;

    /**
     * 收款账号账户名称
     */
    private String account;

    /**
     * 多个收款账户id
     */
    private List<Long> accountIds;

    /**
     * 模糊查询order字段
     */
    private String orderCode;


    /**
     * 订单号-批量
     */
    private List<String> orderNoList;

    /**
     * 门店/店铺名称
     */
    private String storeName;

    /**
     * 店铺类型
     */
    private StoreType storeType;

    /**
     * 负责业务员集合
     */
    private List<String> employeeIds;
}
