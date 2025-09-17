package com.wanmi.sbc.empower.api.request.channel.vop;

import com.wanmi.sbc.empower.api.request.vop.base.VopBaseRequest;
import com.wanmi.sbc.empower.bean.dto.channel.vop.VopFreightSkuDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * @description VOP查询订单运费请求类
 * @author daiyitian
 * @date 2021/5/10 14:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VopOrderFreightQueryRequest extends VopBaseRequest {

    private static final long serialVersionUID = -1L;

    @Valid
    @NotEmpty
    @Schema(description = "商品和数量")
    private List<VopFreightSkuDTO> sku;

    @NotNull
    @Schema(description = "一级地址")
    private Integer province;

    @NotNull
    @Schema(description = "二级地址")
    private Integer city;

    @NotNull
    @Schema(description = "三级地址")
    private Integer county;

    @Schema(description = "四级地址")
    private Integer town;

    @Schema(description = "京东支付方式")
    private Integer paymentType;
}
