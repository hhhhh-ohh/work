package com.wanmi.ares.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanmi.ares.base.SortType;
import com.wanmi.ares.enums.StoreSelectType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @ClassName CouponInfoEffectRequest
 * @Description
 * @Author zhanggaolei
 * @Date 2021/1/20 19:57
 * @Version 1.0
 **/
@Data
@Schema
public class CouponEffectRequest extends BaseRequest {
    @Schema(description = "起始页码")
    private Integer pageNum=0;

    @Schema(description = "页面大小")
    private Integer pageSize = 10;

    @Schema(description = "店铺id")
    private Long    storeId;

    @Schema(description = "类型id，类如优惠券id、优惠券活动id")
    private String  id;

    @Schema(description = "类型名称，类如优惠券名称、优惠券活动名称、店铺名称")
    private String name;

    @Schema(description = "排序字段")
    private String sortName;

     @Schema(hidden=true)
    @JsonIgnore
    private  String sortColumn;

    @Schema(description = "排序类型 0-正序，1-倒序")
    private SortType sortType;

    @Schema(description = "时间选择 0-最近日期，1-30天，2-90天，不填默认0")
    private int statType=0;

     @Schema(hidden=true)
    @JsonIgnore
    private   String sort;

    @Schema(description = "0全部，1商家，2门店")
    private StoreSelectType storeSelectType;

    public String getSort(){
        if(StringUtils.isEmpty(sort)) {
            if (sortType != null) {
                return sortType.name();
            } else {
                return SortType.DESC.name();
            }
        }else{
            String so = SortType.DESC.name();
            switch (sort.toLowerCase()) {
                case "asc":
                    so = SortType.ASC.name();
                    break;
                case "desc":
                    so = SortType.DESC.name();
                    break;
                default:
                    so = SortType.DESC.name();
                    break;
            }
            return so;
        }
    }

    public String getSortColumn(){
        String col = "pay_money";
        if(!StringUtils.isEmpty(sortName)) {
            switch (sortName) {
                case "payMoney":
                    col = "pay_money";
                    break;
                case "discountMoney":
                    col = "discount_money";
                    break;
                case "roi":
                    col = "roi";
                    break;
                case "payGoodsCount":
                    col = "pay_goods_count";
                    break;
                case "payTradeCount":
                    col = "pay_trade_count";
                    break;
                case "jointRate":
                    col = "joint_rate";
                    break;
                case "oldCustomerCount":
                    col = "old_customer_count";
                    break;
                case "newCustomerCount":
                    col = "new_customer_count";
                    break;
                case "payCustomerCount":
                    col = "pay_customer_count";
                    break;
                case "acquireData":
                    col = "acquire_customer_count";
                    break;
                case "useData":
                    col = "use_customer_count";
                    break;
                case "useRate":
                    col = "use_rate";
                    break;
                case "customerPrice":
                    col = "customer_price";
                    break;
                case "denomination":
                    col = "denomination";
                    break;
                case "supplyPrice":
                    col = "supply_price";
                    break;
                default:
                    col = "pay_money";
                    break;
            }
        }
        return col;
    }
}
