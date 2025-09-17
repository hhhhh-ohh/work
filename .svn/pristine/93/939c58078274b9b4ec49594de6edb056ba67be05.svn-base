package com.wanmi.sbc.elastic.provider.impl.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityPageRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsMagicCouponActivityPageRequest;
import com.wanmi.sbc.elastic.api.response.coupon.EsCouponActivityPageResponse;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponActivityVO;
import com.wanmi.sbc.elastic.coupon.model.root.EsCouponActivity;
import com.wanmi.sbc.elastic.coupon.service.EsCouponActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@Validated
public class EsCouponActivityQueryController implements EsCouponActivityQueryProvider {

    @Autowired
    private EsCouponActivityService esCouponActivityService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Override
    public BaseResponse<EsCouponActivityPageResponse> page(@RequestBody @Valid EsCouponActivityPageRequest request) {
        Page<EsCouponActivity> esCouponInfoPage = esCouponActivityService.page(request);
        MicroServicePage<EsCouponActivityVO> esCouponInfoVOS = KsBeanUtil.convertPage(esCouponInfoPage, EsCouponActivityVO.class);
        return BaseResponse.success(new EsCouponActivityPageResponse(esCouponInfoVOS));
    }

    @Override
    public BaseResponse<EsCouponActivityPageResponse> magicPage(@RequestBody EsMagicCouponActivityPageRequest request) {
        Page<EsCouponActivity> esCouponInfoPage = esCouponActivityService.magicPage(request);
        MicroServicePage<EsCouponActivityVO> esCouponInfoVOS =
                KsBeanUtil.convertPage(esCouponInfoPage, EsCouponActivityVO.class);
        if (!CollectionUtils.isEmpty(esCouponInfoVOS.getContent())) {
            List<Long> storeIds = esCouponInfoVOS.getContent().stream()
                    .map(EsCouponActivityVO::getStoreId).collect(Collectors.toList());
            List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build())
                    .getContext().getStoreVOList();
            // 构建店铺信息Map
            Map<Long, StoreVO> storeVOMap = storeVOList.stream()
                    .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
            esCouponInfoVOS.forEach(esCouponActivityVO -> {
                StoreVO storeVO = storeVOMap.get(esCouponActivityVO.getStoreId());
                if (Objects.nonNull(storeVO)) {
                    // 填充商家名称
                    esCouponActivityVO.setStoreName(storeVO.getStoreName());
                    // 填充是否自营
                    esCouponActivityVO.setCompanyType(storeVO.getCompanyType());
                }
            });
        }
        return BaseResponse.success(new EsCouponActivityPageResponse(esCouponInfoVOS));
    }
}
