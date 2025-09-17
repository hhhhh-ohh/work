package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>预约抢购VO</p>
 *
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleGoodsSimplifyVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * skuID
     */
    @Schema(description = "skuID")
    private String goodsInfoId;

    /**
     * 预约价
     */
    @Schema(description = "预约价")
    private BigDecimal price;
}