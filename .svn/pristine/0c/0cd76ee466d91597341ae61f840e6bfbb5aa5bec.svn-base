package com.wanmi.sbc.elastic.activitycoupon.mapper;

import com.wanmi.sbc.elastic.activitycoupon.root.EsCouponScope;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponScopeDTO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponScopeVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface EsCouponScopeMapper {

    @Mappings({})
    EsCouponScope couponInfoToEsCouponInfo(EsCouponScopeDTO esCouponScope);


    @Mappings({})
    List<EsCouponScope> couponInfoToEsCouponInfo(List<EsCouponScopeVO> couponScopeVOS);

    @Mappings({})
    List<EsCouponScope> couponInfoDTOToEsCouponInfo(List<EsCouponScopeDTO> couponInfoDTOS);
}
