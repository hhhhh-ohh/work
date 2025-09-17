package com.wanmi.sbc.marketing.api.request.countprice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.CountCouponPriceGoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @description   商品算价请求类
 * @author  wur
 * @date: 2022/2/23 16:31
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeCountCouponPriceRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description ="用户编号")
    @NotEmpty
    private String customerId;

    @Schema(description ="是否强制下单，true：任何异常都以无营销处理")
    @NotNull
    private Boolean forceCommit = Boolean.FALSE;

    @Schema(description ="优惠券码Id")
    @NotEmpty
    private String couponCodeId;

    @Schema(description ="订单运费")
    private BigDecimal freightPrice;

    @Schema(description ="目标商品总金额  如果不为空优先使用此字段来验证券的使用门槛  目前只用与运费券")
    private BigDecimal totalPrice;

    /**
     * 订单快照信息
     */
    @Schema(description = "订单快照信息")
    private List<CountCouponPriceGoodsInfoDTO> countPriceGoodsInfoDTOList = new ArrayList<>();
}