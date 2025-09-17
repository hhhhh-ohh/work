package com.wanmi.sbc.marketing.api.request.plugin;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>会员等级插件公共Request</p>
 * @author: sunkun
 * Date: 2018-11-19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingLevelGoodsListFilterRequest extends BaseRequest {

    private static final long serialVersionUID = -4516497403355733680L;

    /**
     * 商品列表
     */
    @Schema(description = "商品信息列表")
    @NotNull
    private List<GoodsInfoDTO> goodsInfos;

    /**
     * 当前客户
     */
    @Schema(description = "客户信息")
    private String customerId;
//    private CustomerDTO customerDTO;
}
