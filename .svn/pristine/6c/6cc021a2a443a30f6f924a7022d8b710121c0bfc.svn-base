package com.wanmi.sbc.goods.api.request.adjustprice;

import static com.wanmi.sbc.common.util.ValidateUtil.NULL_EX_MESSAGE;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsLevelPriceDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * <p>客户等级价批量调价详情编辑请求参数</p>
 * Created by of628-wenzhi on 2020-12-21-8:46 下午.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLevelPriceAdjustDetailModifyRequest extends GoodsBaseRequest {
    private static final long serialVersionUID = 8907180245177802986L;

    /**
     * 调价单号
     */
    @Schema(description = "调价单号")
    @NotBlank
    private String adjustNo;

    /**
     * 调价明细ID
     */
    @Schema(description = "调价明细ID")
    @NotNull
    private Long adjustDetailId;

    /**
     * 更新后的市场价
     */
    @Schema(description = "更新后的市场价")
    private BigDecimal marketingPrice;

    /**
     * Sku ID
     */
    @Schema(description = "Sku ID")
    @NotBlank
    private String goodsInfoId;

    /**
     * 客户级别价列表
     */
    @Schema(description = "客户级别价列表")
    private List<GoodsLevelPriceDTO> levelPriceList;

    @Override
    public void checkParam() {
        BigDecimal marketingPrice = this.marketingPrice;
        Validate.isTrue(Objects.nonNull(marketingPrice) || CollectionUtils.isNotEmpty(levelPriceList), NULL_EX_MESSAGE,
                "marketPrice & levelPriceList");
        checkPrice(marketingPrice, "市场价");
        levelPriceList.forEach(i -> {
            if (Objects.nonNull(i.getPrice())) {
                checkPrice(i.getPrice(), "等级价");
            }
        });
    }

    private void checkPrice(BigDecimal price, String priceText) {
        if (Objects.nonNull(price)) {
            if (!ValidateUtil.isNum(price + "") && !ValidateUtil.isFloatNum(price + "")) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030118, new Object[]{priceText, "0和正数，允许两位小数，不超过9999999.99！"});
            }
            if (price.compareTo(new BigDecimal("9999999.99")) > 0) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030118, new Object[]{priceText, "0和正数，允许两位小数，不超过9999999.99！"});
            }
        }
    }

}
