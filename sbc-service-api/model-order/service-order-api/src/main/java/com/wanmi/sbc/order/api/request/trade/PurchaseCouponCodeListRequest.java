package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;
import com.wanmi.sbc.marketing.bean.enums.QueryCouponType;
import com.wanmi.sbc.marketing.bean.vo.CheckGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-11
 */
@Data
@Schema
public class PurchaseCouponCodeListRequest implements Serializable {

    private static final long serialVersionUID = 3106550472717776197L;

    /**
     * 采购单价格
     */
    @Schema(description = "采购单价格")
    private BigDecimal purchasePrice;

    /**
     * 可用状态
     */
    @Schema(description = "可用状态")
    private DefaultFlag useStatus;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String tid;

    /**
     * 是否预售订单
     */
    @Schema(description = "是否预售订单")
    private Boolean bookingSaleFlag = Boolean.FALSE;

    /**
     * 已选优惠券 codeIds 列表
     * 因为系统按照 店铺券 -> 平台券 -> 店铺券 的顺序判断使用门槛和算价，前者的时候可能会导致后者不满足门槛，
     * 这里通过传入已选券 codeIds，二次算价判断门槛，对不可用的后者，进行过滤
     */
    @Schema(description = "已选优惠券 codeIds 列表")
    private List<String> selectedCouponCodeIds;

    /**
     * 店铺运费列表
     */
    @Valid
    @Schema(description = "店铺运费列表")
    private List<StoreFreightDTO> storeFreights;

    /**
     * 优惠券列表类型，0满系券（满减、满折） 1运费券
     */
    @Schema(description = "优惠券列表类型，0满系券（满减、满折） 1运费券")
    private BoolFlag couponListType;


    /**
     * 优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券
     */
    @Schema(description = "优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券")
    private QueryCouponType queryCouponType;
}
