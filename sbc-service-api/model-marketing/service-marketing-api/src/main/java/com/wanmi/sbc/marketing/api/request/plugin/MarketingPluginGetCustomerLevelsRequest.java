package com.wanmi.sbc.marketing.api.request.plugin;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-27
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingPluginGetCustomerLevelsRequest extends BaseRequest {

    private static final long serialVersionUID = 4325393427789438480L;

    @Schema(description = "商品信息列表")
    @NotNull
    @Size(min = 1)
    private List<GoodsInfoDTO> goodsInfoList;

    @Schema(description = "客户信息")
    @NotNull
    private String customerId;
//    private CustomerDTO customer;

}
