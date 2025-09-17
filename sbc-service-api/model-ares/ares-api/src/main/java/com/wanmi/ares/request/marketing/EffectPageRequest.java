package com.wanmi.ares.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.MarketingTypeBase;
import com.wanmi.ares.enums.StoreSelectType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author：zhangwenchang
 * @Date：2021/1/19 16:14
 * @Description：营销效果请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EffectPageRequest extends BaseRequest {
    /**
     * 页码
     */
    @Schema(description = "页码")
    private int pageNum = 0;

    /**
     * 页面大小
     */
    @Schema(description = "页面大小")
    private int pageSize = 10;

    /**
     * 营销类型
     */
    @Schema(description = "营销类型", required = true)
    private MarketingType marketingType;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsInfoName;


     @Schema(hidden=true)
    private String queryDate;

    /**
     * 营销ID
     */
    @Schema(description = "营销ID")
    private List<String> marketingIds;

    /**
     * 拼团营销ID
     */
    @Schema(description = "拼团营销ID")
    private List<String> grouponMarketingIds;

    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段: payROI:支付ROI, payMoney:营销支付金额, discountMoney:营销优惠金额, payGoodsCount:营销支付件数, " +
            "payTradeCount:营销支付订单数, jointRate:连带率, newCustomerCount:新用户, oldCustomerCount:老用户, payCustomerCount:营销支付人数, " +
            "customerPrice:客单价, shareCount:发起分享人数, shareVisitorsCount:分享访客人数, shareGrouponCount:分享参团数, grouponTradeCount:拼团订单数, " +
            "grouponPersonCount:拼团人数, alreadyGrouponTradeCount:成团订单数, alreadyGrouponPersonCount:成团人数, grouponRoi:拼团-成团转化率," +
            " appointmentCount:预约人数, appointmentPayRate:预约支付转换率, payDepositCount：订金支付人数, payTailCount：尾款支付人数, " +
            "conversionRates：订金-尾款转化率")
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
        "pay_roi",
        "pay_money",
        "discount_money",
        "pay_goods_count",
        "pay_trade_count",
        "joint_rate",
        "new_customer",
        "old_customer",
        "pay_customer_count",
        "customer_price",
        "share_count",
        "share_visitors_count",
        "share_groupon_count",
        "groupon_trade_count",
        "groupon_person_count",
        "already_groupon_trade_count",
        "already_groupon_person_count",
        "groupon_roi",
        "appointmentCount",
        "appointmentPayRate",
        "pay_deposit_count",
        "pay_tail_count",
        "conversion_rates",
        "pv",
        "uv",
        "uVPayRate");

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortName() {
        if (StringUtils.isEmpty(this.sortName)
                || columnNameList.contains(this.sortName)) {
            return sortName;
        }
        switch (this.sortName) {
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
                this.sortName = "joint_rate";
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
            case "shareCount":
                this.sortName = "share_count";
                break;
            case "shareVisitorsCount":
                this.sortName = "share_visitors_count";
                break;
            case "shareGrouponCount":
                this.sortName = "share_groupon_count";
                break;
            case "grouponTradeCount":
                this.sortName = "groupon_trade_count";
                break;
            case "grouponPersonCount":
                this.sortName = "groupon_person_count";
                break;
            case "alreadyGrouponTradeCount":
                this.sortName = "already_groupon_trade_count";
                break;
            case "alreadyGrouponPersonCount":
                this.sortName = "already_groupon_person_count";
                break;
            case "grouponRoi":
                this.sortName = "groupon_roi";
                break;
            case "appointmentCount":
                this.sortName = "appointmentCount";
                break;
            case "appointmentPayRate":
                this.sortName = "appointmentPayRate";
                break;
            case "payDepositCount":
                this.sortName = "pay_deposit_count";
                break;
            case "payTailCount":
                this.sortName = "pay_tail_count";
                break;
            case "conversionRates":
                this.sortName = "conversion_rates";
                break;
            case "uvPayRate":
                this.sortName = "uVPayRate";
                break;
            case "supplyPrice":
                this.sortName = "supply_price";
                break;
            default:
                this.sortName = "";
                break;
        }
        return sortName;
    }

    /**
     * 分页起始值
     */
    @Schema(description = "分页起始值", hidden = true)
    private Long beginIndex;

    /**
     * 营销类型集合
     */
    @Schema(description = "营销类型集合")
    private List<Integer> marketingTypeList;

    public List<Integer> getMarketingTypeList() {
        if (MarketingType.REDUCTION_DISCOUNT_GIFT.equals(marketingType)){
            marketingTypeList = Arrays.asList(MarketingTypeBase.REDUCTION.toValue(),MarketingTypeBase.DISCOUNT.toValue(),MarketingTypeBase.GIFT.toValue());
        } else if (MarketingType.HALF_PRICE_SECOND_PIECE.equals(marketingType)){
            marketingTypeList = Arrays.asList(MarketingTypeBase.HALF_PRICE_SECOND_PIECE.toValue());
        } else if (MarketingType.BUYOUT_PRICE.equals(marketingType)){
            marketingTypeList = Arrays.asList(MarketingTypeBase.BUYOUT_PRICE.toValue());
        } else if (MarketingType.SUITS.equals(marketingType)){
            marketingTypeList = Arrays.asList(MarketingTypeBase.SUITS.toValue());
        } else if (MarketingType.PREFERENTIAL.equals(marketingType)) {
            marketingTypeList = Arrays.asList(MarketingTypeBase.PREFERENTIAL.toValue());
        }
        return marketingTypeList;
    }

    /**
     * 获取分页参数对象
     *
     * @return
     */
    public PageRequest getPageable() {
        return PageRequest.of(pageNum, pageSize);
    }

    @Schema(description = "0全部，1商家，2门店")
    private StoreSelectType storeSelectType;
}
