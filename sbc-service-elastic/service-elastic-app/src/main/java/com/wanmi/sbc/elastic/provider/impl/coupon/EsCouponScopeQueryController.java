package com.wanmi.sbc.elastic.provider.impl.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponScopeQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponScopePageRequest;
import com.wanmi.sbc.elastic.api.response.coupon.EsCouponScopePageResponse;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponScopeVO;
import com.wanmi.sbc.elastic.activitycoupon.root.EsCouponScope;
import com.wanmi.sbc.elastic.activitycoupon.service.EsCouponScopeService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Validated
public class EsCouponScopeQueryController implements EsCouponScopeQueryProvider {

    @Autowired
    private EsCouponScopeService esCouponScopeService;

    @Override
    public BaseResponse<EsCouponScopePageResponse> page(@RequestBody @Valid EsCouponScopePageRequest request) {
        // 只筛选出店铺用户等级，因为目前优惠券没有对用户的平台等级进行限定
        Map<Long, CommonLevelVO> storeLevelMap = new HashMap<>();
        if (MapUtils.isNotEmpty(request.getLevelMap())) {
            storeLevelMap = request.getLevelMap().entrySet().stream()
                    .filter(entry -> BoolFlag.YES == entry.getValue().getLevelType())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        // 重新填充店铺用户等级
        request.setLevelMap(storeLevelMap);
        Page<EsCouponScope> esCouponScopePage = esCouponScopeService.page(request);
        MicroServicePage<EsCouponScopeVO> esCouponScopeVOS = KsBeanUtil.convertPage(esCouponScopePage, EsCouponScopeVO.class);
        return BaseResponse.success(new EsCouponScopePageResponse(esCouponScopeVOS));
    }
}
