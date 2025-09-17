package com.wanmi.sbc.marketing.drawactivity.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.marketing.api.request.coupon.CouponFetchRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityGetByIdResponse;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.coupon.service.CouponActivityService;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeService;
import com.wanmi.sbc.marketing.drawprize.model.root.DrawPrize;
import com.wanmi.sbc.marketing.drawprize.repository.DrawPrizeRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>抽奖活动表业务逻辑</p>
 *
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Service("DrawGiveOutPrizesService")
@Slf4j
public class DrawGiveOutPrizesService {

    @Autowired
    private DrawPrizeRepository drawPrizeRepository;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private CouponActivityService couponActivityService;

    @Async
    public void giveOutPrizes(Long prizeId, String customerId) {
        try {
            Thread.sleep(Constants.NUM_5000);
            if (prizeId == null){
                return;
            }
            DrawPrize drawPrize = drawPrizeRepository
                    .findById(prizeId)
                    .orElseThrow(() -> new SbcRuntimeException(MarketingErrorCodeEnum.K080075));
            if (Objects.equals(DrawPrizeType.POINTS,drawPrize.getPrizeType())){
                //积分中奖
                customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                        .customerId(customerId)
                        .type(OperateType.GROWTH)
                        .serviceType(PointsServiceType.DRAW_AWARD)
                        .points(drawPrize.getPointsNum())
                        .content(JSONObject.toJSONString(Collections.singletonMap("drawActivityId", drawPrize.getActivityId())))
                        .opTime(LocalDateTime.now())
                        .build());
            }else if (Objects.equals(DrawPrizeType.COUPON,drawPrize.getPrizeType())){
                //优惠券中奖
                //查询对应优惠券活动
                CouponActivityGetByIdResponse response = couponActivityService.getByDrawActivityId(drawPrize.getActivityId());
                CouponFetchRequest couponFetchRequest = new CouponFetchRequest();
                couponFetchRequest.setCustomerId(customerId);
                couponFetchRequest.setCouponInfoId(drawPrize.getCouponCodeId());
                couponFetchRequest.setCouponActivityId(response.getActivityId());
                couponFetchRequest.setStoreId(response.getStoreId());
                couponCodeService.customerFetchCoupon(couponFetchRequest);
            }
        }catch (Exception e){
            log.error("抽奖活动，发放奖品失败，giveOutPrizes error, prizeId={}, customerId={}", prizeId, customerId);
        }
    }

}
