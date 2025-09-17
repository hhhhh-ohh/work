package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CouponMarketingCustomerScopeVO;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityConfigVO;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
public class CouponActivityDetailResponse extends BasicResponse {

    private static final long serialVersionUID = -2335845421602812039L;

    @Schema(description = "优惠券活动")
    private CouponActivityVO couponActivity;

    @Schema(description = "优惠券活动配置列表")
    private List<CouponActivityConfigVO> couponActivityConfigList;

    @Schema(description = "优惠券信息")
    private List<CouponInfoVO> couponInfoList;

    @Schema(description = "客户等级列表")
    private List<CustomerLevelVO> customerLevelList;

    @Schema(description = "活动目标客户范围")
    List<CouponMarketingCustomerScopeVO> couponMarketingCustomerScope;

    @Schema(description = "目标客户信息")
    List<CustomerVO>  customerDetailVOS;

}
