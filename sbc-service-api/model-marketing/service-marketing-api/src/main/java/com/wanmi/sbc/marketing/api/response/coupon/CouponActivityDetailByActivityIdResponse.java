package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityConfigVO;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponActivityDetailByActivityIdResponse extends BasicResponse {

    private static final long serialVersionUID = -8639204400803957647L;

    private CouponActivityVO couponActivity;

    private List<CouponActivityConfigVO> couponActivityConfigList;

    private List<CouponInfoVO> couponInfoList;

}
