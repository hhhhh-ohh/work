package com.wanmi.ares.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.DefaultFlag;
import com.wanmi.ares.enums.StatisticsDataType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Schema
public class MarketingOverviewRequest extends BaseRequest {
    @Schema(description = "店铺ID", hidden = true)
    private Long storeId;

    @Schema(description = "1：昨天，2：最近七天；3：最近30天；4：按月统计", required = true)
    private StatisticsDataType statisticsDataType;

    @Schema(description = "月份 格式：2021-01")
    private String month;

    @Schema(description = "0:天 1:周")
    private DefaultFlag week;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段:marketingActivityCount:活动数量, payROI:支付ROI, payMoney:营销支付金额, discountMoney:营销优惠金额, " +
            "payGoodsCount:营销支付件数, payTradeCount:营销支付订单数, jointRate:连带率, newCustomerCount:新用户, oldCustomerCount:老用户," +
            " payCustomerCount:营销支付人数, customerPrice:客单价")
    private String sortName;

    /**
     * 排序类型
     */
    @Schema(description = "排序类型 asc desc")
    private String sortOrder;

    /***
     * 字段列表
     */
    private List<String> columnNameList = Arrays.asList(
        "activity_num",
        "pay_roi",
        "pay_money",
        "discount_money",
        "pay_goods_count",
        "pay_trade_count",
        "join_rate",
        "new_customer",
        "old_customer",
        "pay_customer_count",
        "customer_price",
        "pv",
        "uv",
        "uvPayRate");

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortName() {
        if (StringUtils.isEmpty(this.sortName)
                || columnNameList.contains(this.sortName)) {
            return sortName;
        }
        switch (this.sortName) {
            case "marketingActivityCount":
                this.sortName = "activity_num";
                break;
            case "payROI":
                this.sortName = "pay_roi";
                break;
            case "payMoney":
                this.sortName = "pay_money";
                break;
            case "discountMoney":
                this.sortName = "discount_money";
                break;
            case "payGoodsCount":
                this.sortName = "pay_goods_count";
                break;
            case "payTradeCount":
                this.sortName = "pay_trade_count";
                break;
            case "jointRate":
                this.sortName = "join_rate";
                break;
            case "newCustomerCount":
                this.sortName = "new_customer";
                break;
            case "oldCustomerCount":
                this.sortName = "old_customer";
                break;
            case "payCustomerCount":
                this.sortName = "pay_customer_count";
                break;
            case "customerPrice":
                this.sortName = "customer_price";
                break;
            default:
                this.sortName = "";
                break;
        }
        return sortName;
    }
}
