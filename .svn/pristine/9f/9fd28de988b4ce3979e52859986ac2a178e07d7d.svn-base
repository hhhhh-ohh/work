package com.wanmi.sbc.elastic.coupon.mapper;

import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponInfoDTO;
import com.wanmi.sbc.elastic.coupon.model.root.EsCouponInfo;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface EsCouponInfoMapper {

    @Mappings({})
    EsCouponInfo couponInfoToEsCouponInfo(EsCouponInfoDTO couponInfo);


    @Mappings({})
    List<EsCouponInfo> couponInfoToEsCouponInfo(List<CouponInfoVO> couponInfo);
}
