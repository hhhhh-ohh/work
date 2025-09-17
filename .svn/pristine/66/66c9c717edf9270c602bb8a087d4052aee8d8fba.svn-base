package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-05
 */
@Data
@Schema
public class PurchaseFillBuyCountRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品信息")
    @NotNull
    @Size(min = 1)
    private List<GoodsInfoVO> goodsInfoList;

    @Schema(description = "客户id")
    @NotBlank
    private String customerId;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;
}
