package com.wanmi.sbc.vas.bean.vo.sellplatform;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class SellPlatformReturnItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "skuId")
    private String skuId;

    @Schema(description = "sku 名称")
    private String skuName;

    @Schema(description = "sku 编号")
    private String skuNo;

    @Schema(description = "商品类型")
    private PluginType pluginType;

    /**
     * 规格信息
     */
    @Schema(description = "规格信息")
    private String specDetails;

    /**
     * 退货商品单价 = 商品原单价 - 商品优惠单价
     */
    @Schema(description = "退货商品单价 = 商品原单价 - 商品优惠单价")
    private BigDecimal price;

    /**
     * 平摊价格
     */
    @Schema(description = "平摊价格")
    private BigDecimal splitPrice;

    /**
     * 供货价
     */
    @Schema(description = "供货价")
    private BigDecimal supplyPrice;

    /**
     * 供货价小计
     */
    @Schema(description = "供货价小计")
    private BigDecimal providerPrice;

    @Schema(description = "供应商ID")
    private Long providerId;

    /**
     * 订单平摊价格
     */
    @Schema(description = "订单平摊价格")
    private BigDecimal orderSplitPrice;

    /**
     * 申请退货数量
     */
    @Schema(description = "申请退货数量")
    private Integer num;

    /**
     * 退货商品图片路径
     */
    @Schema(description = "退货商品图片路径")
    private String pic;

    /**
     * 单位
     */
    @Schema(description = "单位")
    private String unit;

    /**
     * 仍可退数量
     */
    @Schema(description = "仍可退数量")
    private Integer canReturnNum;

    /**
     * 购买积分，被用于普通订单的积分+金额混合商品
     */
    @Schema(description = "购买积分")
    private Long buyPoint;



    /**
     * 第三方平台的spuId
     */
    private String thirdPlatformSpuId;

    /**
     * 第三方平台的skuId
     */
    private String thirdPlatformSkuId;

    /**
     * 商品来源，0供应商，1商家 2linkedMall
     */
    private Integer goodsSource;

    /**
     *第三方平台类型，0，linkedmall
     */
    private ThirdPlatformType thirdPlatformType;


    /**
     * 第三方平台-明细子订单id
     */
    private String thirdPlatformSubOrderId;

    /**
     * 应退积分
     */
    @Schema(description = "应退积分")
    private Long splitPoint;
    /**
     * 跨境订单商品的扩展字段
     */
    @Schema(description = "跨境订单商品的扩展字段")
    private Object extendedAttributes;

    /**
     * 已退数量
     */
    private Integer alreadyReturnNum;

    /**
     * 商品spuId
     */
    private String goodsId;

    /**
     * 商品spuId
     */
    private String spuId;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * @param returnItem
     * @return
     */
    public DiffResult<SellPlatformReturnItemVO> diff(SellPlatformReturnItemVO returnItem) {
        return new DiffBuilder<>(this, returnItem, ToStringStyle.JSON_STYLE)
                .append("num", num, returnItem.getNum())
                .build();
    }

    public void merge(SellPlatformReturnItemVO newReturnItem) {
        DiffResult<SellPlatformReturnItemVO> diffResult = this.diff(newReturnItem);
        diffResult.getDiffs().forEach(
                diff -> {
                    String fieldName = diff.getFieldName();
                    switch (fieldName) {
                        case "num":
                            mergeSimple(fieldName, diff.getRight());
                            break;
                        default:
                            break;
                    }

                }
        );
    }

    private void mergeSimple(String fieldName, Object right) {
        Field field = ReflectionUtils.findField(SellPlatformReturnItemVO.class, fieldName);
        try {
            field.setAccessible(true);
            field.set(this, right);
        } catch (IllegalAccessException e) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050023, new Object[]{SellPlatformReturnItemVO.class, fieldName});
        }
    }
}
