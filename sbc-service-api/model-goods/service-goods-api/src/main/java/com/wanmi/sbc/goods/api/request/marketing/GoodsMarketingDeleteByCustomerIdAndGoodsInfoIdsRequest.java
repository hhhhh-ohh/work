package com.wanmi.sbc.goods.api.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>根据用户编号和商品编号列表删除商品使用的营销请求</p>
 * author: sunkun
 * Date: 2018-11-02
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsMarketingDeleteByCustomerIdAndGoodsInfoIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -5595551471557582468L;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    @NotBlank
    private String customerId;

    /**
     * sku编号
     */
    @Schema(description = "sku编号")
    @NotNull
    @Size(min = 1)
    private List<String> goodsInfoIds;
}
