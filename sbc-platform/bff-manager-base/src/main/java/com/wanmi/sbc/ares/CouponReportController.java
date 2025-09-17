package com.wanmi.sbc.ares;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.enums.TrendType;
import com.wanmi.ares.provider.CouponProvider;
import com.wanmi.ares.request.coupon.CouponActivityEffectRequest;
import com.wanmi.ares.request.coupon.CouponEffectRequest;
import com.wanmi.ares.request.coupon.CouponQueryRequest;
import com.wanmi.ares.view.coupon.*;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @ClassName CouponController
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/1/15 14:51
 * @Version 1.0
 **/
@Slf4j
@Tag(name = "CouponReportController", description = "优惠券报表统计")
@RestController
@RequestMapping("/couponReport")
@Validated
public class CouponReportController {
    @Autowired
    private CouponProvider couponProvider;
    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "优惠券概况统计")
    @RequestMapping(value = "/overview", method = RequestMethod.POST)
    public BaseResponse<CouponOverviewView> overview(@RequestBody CouponQueryRequest request){
        Long storeId = commonUtil.getStoreId();
        if(request.getStoreId() == null){
            if(storeId==null){
                request.setStoreId(-1L);
            }else {
                if (this.buildRequest()){
                    request.setStoreSelectType(StoreSelectType.O2O);
                }
                request.setStoreId(storeId);
            }
        }
        return BaseResponse.success(couponProvider.overview(request));
    }

    @Operation(summary = "优惠券概况趋势统计")
    @RequestMapping(value = "/overview-list", method = RequestMethod.POST)
    public BaseResponse<List<CouponOverviewView>> overviewList(@RequestBody CouponQueryRequest request){
        Long storeId = commonUtil.getStoreId();
        if(request.getStoreId() == null){
            if(storeId==null){
                request.setStoreId(-1L);
            }else {
                if (this.buildRequest()){
                    request.setStoreSelectType(StoreSelectType.O2O);
                }
                request.setStoreId(storeId);
            }
        }

        return BaseResponse.success(couponProvider.overviewList(request));
    }

    @Operation(summary = "营销时间区间查询接口")
    @RequestMapping(value = "/data-period", method = RequestMethod.GET)
    public BaseResponse<DataPeriodView> dataPeriod(){
        return BaseResponse.success(couponProvider.dataPeriod());
    }

    @Operation(summary = "优惠券效果")
    @RequestMapping(value = "/coupon-info-effect", method = RequestMethod.POST)
    public BaseResponse<PageInfo<CouponInfoEffectView>> pageCouponInfoEffect(@RequestBody CouponEffectRequest request){
        Long storeId = commonUtil.getStoreId();
        if(request.getStoreId() == null){
            if(storeId==null){
                request.setStoreId(-1L);
            }else {
                if (this.buildRequest()){
                    request.setStoreSelectType(StoreSelectType.O2O);
                }
                request.setStoreId(storeId);
            }
        }
        return BaseResponse.success(couponProvider.pageCouponInfoEffect(request));
    }
    @Operation(summary = "优惠券活动详情效果")
    @RequestMapping(value = "/coupon-info-effect-activity", method = RequestMethod.POST)
    public BaseResponse<PageInfo<CouponInfoEffectView>> pageCouponInfoEffectByActivity(@RequestBody CouponEffectRequest request){
        StoreType storeType = commonUtil.getStoreType();
        if (StoreType.O2O == storeType){
            request.setStoreSelectType(StoreSelectType.O2O);
        }
        return BaseResponse.success(couponProvider.pageCouponInfoEffectByActivityId(request));
    }
    @Operation(summary = "优惠券活动效果")
    @RequestMapping(value = "/coupon-activity-effect", method = RequestMethod.POST)
    public BaseResponse<PageInfo<CouponActivityEffectView>> pageCouponActivityEffect(@RequestBody CouponActivityEffectRequest request){
        Long storeId = commonUtil.getStoreId();
        if(request.getStoreId() == null){
            if(storeId==null){
                request.setStoreId(-1L);
            }else {
                if (this.buildRequest()){
                    request.setStoreSelectType(StoreSelectType.O2O);
                }
                request.setStoreId(storeId);
            }
        }
        return BaseResponse.success(couponProvider.pageCouponActivityEffect(request));
    }

    @Operation(summary = "店铺效果")
    @RequestMapping(value = "/coupon-store-effect", method = RequestMethod.POST)
    public BaseResponse<PageInfo<CouponStoreEffectView>> pageCouponStoreEffect(@RequestBody CouponEffectRequest request){
        Long storeId = commonUtil.getStoreId();
        if(storeId==null || storeId == -1||(request.getStoreId()!=null && request.getStoreId() == -1)){
            request.setStoreId(null);
        }else {
            if (this.buildRequest()){
                request.setStoreSelectType(StoreSelectType.O2O);
            }
            request.setStoreId(storeId);
        }
        return BaseResponse.success(couponProvider.pageCouponStoreEffect(request));
    }

    private Boolean buildRequest(){
        StoreType storeType = commonUtil.getStoreType();
        if (StoreType.O2O==storeType){
            return true;
        }
        return false;
    }
}
