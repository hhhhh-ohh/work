package com.wanmi.sbc.marketing.api.response.coupon;


import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 优惠券响应结构
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CouponInfoByIdResponse extends CouponInfoVO implements Serializable {

    private static final long serialVersionUID = -9028473330671730142L;

}
