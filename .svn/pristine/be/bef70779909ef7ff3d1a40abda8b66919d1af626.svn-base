package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Schema
public class PurchaseGetStoreCouponExistResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺是否有优惠券活动map ,key为店铺id，value为是否存在优惠券活动")
    private HashMap<Long, Boolean> map = new HashMap<>();
}
