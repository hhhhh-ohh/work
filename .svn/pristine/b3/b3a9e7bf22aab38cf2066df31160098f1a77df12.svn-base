package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-30
 */
@Data
@Schema
public class PurchaseClearLoseGoodsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "客户id")
    @NotBlank
    private String userId;


    /**
     * 分销渠道信息-会员id
     */
    @Schema(description = "分销渠道信息")
    DistributeChannel distributeChannel;

    @Schema(description = "用户对象")
    CustomerVO customerVO;

    @Schema(description = "门店id")
    private Long storeId;
}
