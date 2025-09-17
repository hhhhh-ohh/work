package com.wanmi.sbc.goods.api.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>根据用户编号删除商品使用的营销请求</p>
 * author: sunkun
 * Date: 2018-11-02
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsMarketingDeleteByCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = 288708864891866746L;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    @NotBlank
    private String customerId;
}
