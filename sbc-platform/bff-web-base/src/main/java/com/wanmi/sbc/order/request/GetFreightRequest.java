package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.order.api.request.trade.TradeParamsRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className GetFreightRequest
 * @description TODO
 * @date 2022/8/3 15:00
 **/
@Schema
@Data
public class GetFreightRequest extends BaseRequest {

    @Schema(description = "订单商品信息")
    private List<TradeParamsRequest> tradeParams;

    @Schema(description = "积分兑换金额")
    private BigDecimal pointsPrice;

    @Schema(description = "砍价订单标识")
    private Boolean bargain;

    @Schema(description = "砍价记录Id")
    private Long bargainId;

    @Override
    public void checkParam(){
        if (CollectionUtils.isEmpty(this.getTradeParams())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if(Objects.equals(Boolean.TRUE, bargain) && Objects.isNull(bargainId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        tradeParams.forEach(tradeParamsRequest -> {
            tradeParamsRequest.setBargain(bargain);
            tradeParamsRequest.setBargainId(bargainId);
        });
    }

    /**
     * @description  处理积分兑换金额的抵扣均摊
     * @author  wur
     * @date: 2022/8/3 14:40
     * @return
     **/
    public void handlePointsPrice() {
        if (Objects.isNull(this.pointsPrice) || BigDecimal.ZERO.compareTo(this.pointsPrice) >= 0) {
            return;
        }
        //计算商品总金额
        List<TradeItemDTO> oldTradeItems = new ArrayList<>();
        List<TradeItemDTO> oldPreferentialTradeItems = new ArrayList<>();

        for(TradeParamsRequest tradeParams : this.tradeParams) {
            if (CollectionUtils.isNotEmpty(tradeParams.getOldTradeItems())) {
                oldTradeItems.addAll(tradeParams.getOldTradeItems().stream().filter(item->Objects.nonNull(item.getSplitPrice()) && BigDecimal.ZERO.compareTo(item.getSplitPrice())<0).collect(Collectors.toList()));
                oldPreferentialTradeItems.addAll(tradeParams.getOldPreferential().stream().filter(item->Objects.nonNull(item.getSplitPrice()) && BigDecimal.ZERO.compareTo(item.getSplitPrice())<0).collect(Collectors.toList()));
            }
        }

        BigDecimal amount = oldTradeItems.stream().map(TradeItemDTO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        amount = amount.add(oldPreferentialTradeItems.stream().map(TradeItemDTO::getSplitPrice).reduce(BigDecimal.ZERO,
                BigDecimal::add));

        //如果总金额小于等于抵扣金额 则商品金额全部置为0
        if(amount.compareTo(this.pointsPrice) <= 0) {
            this.tradeParams.forEach(tradeParams -> {
                tradeParams.getOldTradeItems().forEach(item -> {
                    item.setSplitPrice(BigDecimal.ZERO);
                });
                tradeParams.getOldPreferential().forEach(item -> {
                    item.setSplitPrice(BigDecimal.ZERO);
                });
            });
            return;
        }

        //已均摊金额
        BigDecimal alreadyDivideAmount = BigDecimal.ZERO;
        oldTradeItems = oldTradeItems.stream().sorted(Comparator.comparing(TradeItemDTO::getSplitPrice)).collect(Collectors.toList());
        oldPreferentialTradeItems = oldPreferentialTradeItems.stream().sorted(Comparator.comparing(TradeItemDTO::getSplitPrice)).collect(Collectors.toList());

        for (TradeItemDTO item : oldPreferentialTradeItems){
            if (item.getSplitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            //计算
            BigDecimal ratio = item.getSplitPrice().divide(amount, 8, RoundingMode.HALF_UP);
            BigDecimal dividePrice = ratio.multiply(this.pointsPrice).setScale(2, RoundingMode.HALF_UP);
            dividePrice = dividePrice.compareTo(item.getSplitPrice()) <= 0 ? dividePrice : item.getSplitPrice();
            item.setSplitPrice(item.getSplitPrice().subtract(dividePrice));
            alreadyDivideAmount = alreadyDivideAmount.add(dividePrice);
        }
        for(int index = 0; index < oldTradeItems.size(); index++) {
            TradeItemDTO item = oldTradeItems.get(index);
            if (item.getSplitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            //抵扣金额
            BigDecimal dividePrice = BigDecimal.ZERO;
            //验证是否是最后一个商品
            if(index == oldTradeItems.size()-1) {
                dividePrice = this.pointsPrice.subtract(alreadyDivideAmount);
            } else {
                //计算
                BigDecimal ratio = item.getSplitPrice().divide(amount, 8, RoundingMode.HALF_UP);
                dividePrice = ratio.multiply(this.pointsPrice).setScale(2, RoundingMode.HALF_UP);
            }
            dividePrice = dividePrice.compareTo(item.getSplitPrice()) <= 0 ? dividePrice : item.getSplitPrice();
            item.setSplitPrice(item.getSplitPrice().subtract(dividePrice));
            alreadyDivideAmount = alreadyDivideAmount.add(dividePrice);
        }

        //处理未均摊完的优惠金额 不再使用均摊
        if (alreadyDivideAmount.compareTo(this.pointsPrice) < 0) {
            BigDecimal surplus = this.pointsPrice.subtract(alreadyDivideAmount);
            for (TradeItemDTO item : oldTradeItems) {
                if (item.getSplitPrice().compareTo(BigDecimal.ZERO) > 0
                        && surplus.compareTo(BigDecimal.ZERO) > 0) {
                    if (item.getSplitPrice().compareTo(surplus) >= 0) {
                        item.setSplitPrice(item.getSplitPrice().subtract(surplus));
                        break;
                    } else {
                        item.setSplitPrice(BigDecimal.ZERO);
                    }
                }
            }
        }
    }
}