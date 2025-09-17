package com.wanmi.sbc.elastic.activitycoupon.mapper;

import com.wanmi.sbc.elastic.activitycoupon.root.EsActivityCoupon;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsActivityCouponDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface EsActivityCouponMapper {


    @Mappings({})
    List<EsActivityCoupon> couponInfoDTOToEsCouponInfo(List<EsActivityCouponDTO> activityCouponDTOS);
}
