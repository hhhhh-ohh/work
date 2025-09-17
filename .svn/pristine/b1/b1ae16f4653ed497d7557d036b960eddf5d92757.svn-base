package com.wanmi.sbc.init;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CompanySourceType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreBycompanySourceType;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelConfigQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelConfigByTypeRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelConfigResponse;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByStoreIdRequest;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByStoreIdResponse;
import com.wanmi.sbc.goods.bean.vo.StoreCateResponseVO;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelSyncGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncVasRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Validated
public class InitLinkedMallGoodsController {
    @Autowired
    private ChannelConfigQueryProvider channelConfigQueryProvider;
    @Autowired
    private ChannelSyncGoodsProvider channelSyncGoodsProvider;
    @Autowired private StoreQueryProvider storeQueryProvider;
    @Autowired private StoreCateQueryProvider storeCateQueryProvider;

    @GetMapping("/init/linkedmall/goods")
    public BaseResponse initLinkedMallGoods() {
        ChannelConfigByTypeRequest channelConfigByTypeRequest = ChannelConfigByTypeRequest.builder().channelType(ThirdPlatformType.LINKED_MALL).build();
        ChannelConfigResponse response = channelConfigQueryProvider.getByType(channelConfigByTypeRequest).getContext();
        if (response != null && EnableStatus.ENABLE.equals(response.getStatus())) {
            //linkedmall供应商店铺
            StoreVO storeVO = storeQueryProvider.getBycompanySourceType(new StoreBycompanySourceType(CompanySourceType.LINKED_MALL)).getContext();
            //店铺默认分类
            StoreCateListByStoreIdResponse storeCateListResponse = storeCateQueryProvider.listByStoreId(new StoreCateListByStoreIdRequest(storeVO.getStoreId())).getContext();
            List<Long> storeCates = storeCateListResponse.getStoreCateResponseVOList().stream()
                    .filter(v -> DefaultFlag.YES == v.getIsDefault())
                    .map(StoreCateResponseVO::getStoreCateId)
                    .collect(Collectors.toList());

            channelSyncGoodsProvider.syncGoodsNotice(ChannelGoodsSyncVasRequest.builder()
                    .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                    .storeId(storeVO.getStoreId())
                    .storeName(storeVO.getStoreName())
                    .companyInfoId(storeVO.getCompanyInfoId())
                    .companyName(storeVO.getCompanyInfo().getCompanyName())
                    .storeCateIds(storeCates)
                    .build());
            return BaseResponse.SUCCESSFUL();
        } else {
            return BaseResponse.success("配置未启用");
        }
    }

}
