package com.wanmi.sbc.marketing.coupon.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CouponMarketingCustomerScopeVO;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityConfigVO;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoSaveVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CouponActivityDetailResponse extends BasicResponse {


    private static final long serialVersionUID = 7266785219929124711L;

    CouponActivityVO couponActivity;

    List<CouponActivityConfigVO> couponActivityConfigList;

    List<CouponInfoSaveVO> couponInfoList;

    //会员等级
    List<CustomerLevelVO> customerLevelList;
    //活动目标客户范围
    List<CouponMarketingCustomerScopeVO> couponMarketingCustomerScope;
    //目标客户信息
    List<CustomerVO>  customerDetailVOS;

}
