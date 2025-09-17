package com.wanmi.sbc.coupon.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.coupon.dto.CouponCheckoutTradeParams;
import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 根据客户和券码id查询不可用的平台券以及优惠券实际优惠总额的请求结构
 * @Author: gaomuwei
 * @Date: Created In 上午9:27 2018/9/29
 */
@Schema
@Data
public class CouponCheckoutBaseRequest extends BaseRequest {

    /**
     * 已勾选的优惠券码id
     */
    @Schema(description = "已勾选的优惠券码id集合")
    @NotNull
    private List<String> couponCodeIds;

    /**
     * 店铺运费列表
     */
    @Valid
    @Schema(description = "店铺运费列表")
    private List<StoreFreightDTO> storeFreights;

    /**
     * 使用积分
     */
    @Schema(description = "使用积分")
    private Long points;


    @Schema(description = "门店Id")
    private Long storeId;

    @Schema(description = "订单商品信息")
    private List<CouponCheckoutTradeParams> tradeParams;
}
