package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class CouponActivityGetByIdResponse extends CouponActivityVO {

    private static final long serialVersionUID = -2674325443889414889L;
}
