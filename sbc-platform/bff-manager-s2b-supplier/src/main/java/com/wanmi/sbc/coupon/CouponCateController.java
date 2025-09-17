package com.wanmi.sbc.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCateQueryProvider;
import com.wanmi.sbc.marketing.bean.vo.CouponCateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @Author: songhanlin
 * @Date: Created In 10:39 AM 2018/9/14
 * @Description: 优惠券分类Controller
 */
@Tag(name = "CouponCateController", description = "优惠券分类 API")
@RestController
@Validated
@RequestMapping("/coupon-cate")
public class CouponCateController {

    @Resource
    private CouponCateQueryProvider couponCateQueryProvider;

    /**
     * 查询优惠券分类列表 (去掉平台专用分类)
     *
     * @return
     */
    @Operation(summary = "查询优惠券分类列表 (去掉平台专用分类)")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseResponse<List<CouponCateVO>> listCouponCate() {
        List<CouponCateVO> couponCateResponseList = couponCateQueryProvider.list().getContext().getList();
        return BaseResponse.success(couponCateResponseList);
    }

}
