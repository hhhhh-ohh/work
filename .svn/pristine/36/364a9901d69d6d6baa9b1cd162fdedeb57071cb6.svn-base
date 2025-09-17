package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 退货总金额
 * Created by jinwei on 19/4/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ReturnPriceVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 申请金额状态，是否启用
     */
    @Schema(description = "申请金额状态，是否启用")
    private Boolean applyStatus;

    /**
     * 申请金额
     */
    @Schema(description = "申请金额")
    private BigDecimal applyPrice;

    /**
     * 商品总金额
     */
    @Schema(description = "商品总金额")
    private BigDecimal totalPrice;

    /**
     * 实退金额，从退款流水中取的
     */
    @Schema(description = "实退金额")
    private BigDecimal actualReturnPrice;


    /**
     * 应退定金
     */
    private BigDecimal earnestPrice;

    /**
     * 应退尾款
     */
    private BigDecimal tailPrice;

    /**
     * 是否是尾款申请
     */
    private Boolean isTailApply;

    /**
     * 供货总额
     */
    @Schema(description = "供货总额")
    private BigDecimal providerTotalPrice;


    /**
     * 运费
     */
    @Schema(description = "运费")
    private BigDecimal fee;

    /**
     * 礼品卡应退金额
     */
    @Schema(description = "礼品卡应退金额")
    private BigDecimal giftCardPrice;

    /**
     *
     * @param returnPrice
     * @return
     */
    public DiffResult<ReturnPriceVO> diff(ReturnPriceVO returnPrice){
        return new DiffBuilder<>(this, returnPrice, ToStringStyle.JSON_STYLE)
            .append("applyStatus", applyStatus, returnPrice.getApplyStatus())
            .append("applyPrice", applyPrice, returnPrice.getApplyPrice())
            .append("totalPrice", totalPrice, returnPrice.getTotalPrice())
                .append("providerTotalPrice", providerTotalPrice, returnPrice.getProviderTotalPrice())
            .build();
    }

    /**
     * 合并
     * @param newPrice
     */
    public void merge(ReturnPriceVO newPrice){
        DiffResult<ReturnPriceVO> diffResult = this.diff(newPrice);
        diffResult.getDiffs().forEach(
            diff -> {
                String fieldName = diff.getFieldName();
                switch(fieldName){
                    case "applyStatus":
                        mergeSimple(fieldName, diff.getRight());
                        break;
                    case "applyPrice":
                        mergeSimple(fieldName, diff.getRight());
                        break;
                    case "totalPrice":
                        mergeSimple(fieldName, diff.getRight());
                        break;
                    case "providerTotalPrice":
                        mergeSimple(fieldName, diff.getRight());
                        break;
                    default:
                        break;
                }

            }
        );

    }

    private void mergeSimple(String fieldName, Object right){
        Field field = ReflectionUtils.findField(ReturnPriceVO.class, fieldName);
        try {
            field.setAccessible(true);
            field.set(this, right);
        } catch (IllegalAccessException e) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050023, new Object[]{ReturnPriceVO.class, fieldName });
        }
    }

    /**
     * 拼接所有diff
     * @param price
     * @return
     */
    public List<String> buildDiffStr(ReturnPriceVO price){
        Function<Object, String> f = (s) -> {
            if (s == null || StringUtils.isBlank(s.toString())) {
                return "空";
            } else {
                return s.toString().trim();
            }
        };
        DiffResult<ReturnPriceVO> diffResult = this.diff(price);
        return diffResult.getDiffs().stream().map(
            diff -> {
                String result = "";
                switch(diff.getFieldName()){
                    case "applyStatus":
                        if((Boolean)diff.getRight()){
                            result = "申请退款金额";
                        }else {
                            result = "取消申请退款金额";
                        }
                        break;
                    case "applyPrice":
                        result = String.format("申请退款金额 由 %s 变更为 %s",
                                f.apply(diff.getLeft().toString()),
                                f.apply(diff.getRight().toString())
                            );
                        break;
                    default:
                        break;
                }
                return result;
            }
        ).collect(Collectors.toList());

    }

}
