package com.wanmi.sbc.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodePageRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodePageResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@Tag(name = "CouponCodeController", description = "我的优惠券Api")
@RestController
@Validated
@RequestMapping("/coupon-code")
public class CouponCodeController {

    @Autowired
    private CouponCodeQueryProvider couponCodeQueryProvider;
//    private CouponCodeService couponCodeService;

    @Autowired
    private CommonUtil commonUtil;


    /**
     * PC 查询我的优惠券
     * @param request
     * @return
     */
    @Operation(summary = "查询我的优惠券")
    @RequestMapping(value = "/my-coupon-list", method = RequestMethod.POST)
    public BaseResponse<CouponCodePageResponse> myCouponList(@RequestBody CouponCodePageRequest request){
        request.setDefaultFlag(DefaultFlag.YES);
        request.setCustomerId(commonUtil.getOperatorId());
        return couponCodeQueryProvider.page(request);
    }
}
