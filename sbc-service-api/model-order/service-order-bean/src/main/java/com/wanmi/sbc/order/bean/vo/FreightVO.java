package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wur
 * @className FreightVO
 * @description TODO
 * @date 2021/10/9 10:12
 **/
@Data
@Schema
public class FreightVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 用户承担的运费  根据商家代销运费设置来计算
     */
    @Schema(description = "用户承担的运费  根据商家代销运费设置来计算")
    private BigDecimal freight;

    /**
     * 商家承担运费
     */
    @Schema(description = "商家承担运费")
    private BigDecimal supplierBearFreight;

    /**
     * 商家运费
     */
    @Schema(description = "商家运费")
    private BigDecimal supplierFreight;

    /**
     * 供应商运费
     */
    @Schema(description = "供应商运费")
    private BigDecimal providerFreight;

    /**
     * 供应商运费列表
     */
    @Schema(description = "供应商运费列表")
    private List<ProviderFreightVO> providerFreightList;
}