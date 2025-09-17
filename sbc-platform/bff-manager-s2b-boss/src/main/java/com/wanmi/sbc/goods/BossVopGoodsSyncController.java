package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CompanySourceType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreBycompanySourceType;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.provider.mqconsumer.EmpowerMqConsumerProvider;
import com.wanmi.sbc.empower.api.request.mqconsumer.EmpowerMqConsumerRequest;
import com.wanmi.sbc.goods.request.VopGoodsSyncRequest;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelSyncGoodsProvider;
import com.wanmi.sbc.vas.api.provider.mqconsumer.VasMqConsumerProvider;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySkuVasRequest;
import com.wanmi.sbc.vas.api.request.mqconsumer.VasMqConsumerRequest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * @author houshuai
 * @date 2021/4/21 19:20
 * @description <p> VOP商品手动同步 </p>
 */
@RestController
@Validated
@RequestMapping("/vop/goods")
@Tag(name =  "VOP商品手动同步", description =  "BossVopGoodsSyncController")
public class BossVopGoodsSyncController {

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private ChannelSyncGoodsProvider channelSyncGoodsProvider;

    /**
     * @param jdSkuId
     * @return
     */
    @Operation(summary = "商品同步")
    @GetMapping(value = "/sync/{jdSkuId}")
    public BaseResponse findGoodsPropertyPage(@PathVariable("jdSkuId") String jdSkuId) {
        //同步商品
        StoreVO storeVO = storeQueryProvider.getBycompanySourceType(new StoreBycompanySourceType(CompanySourceType.JD_VOP)).getContext();
        List<Long> skuList = Collections.singletonList(Long.valueOf(jdSkuId));
        return channelSyncGoodsProvider.syncSkuList(ChannelGoodsSyncBySkuVasRequest.builder()
                .skuIds(skuList)
                .thirdPlatformType(ThirdPlatformType.VOP)
                .storeId(storeVO.getStoreId())
                .companyInfoId(storeVO.getCompanyInfoId())
                .companyName(storeVO.getCompanyInfo() == null ? null : storeVO.getCompanyInfo().getCompanyName())
                .build());
    }

    @Operation(summary = "商品同步")
    @PostMapping(value = "/sync/skuIds")
    public BaseResponse findGoodsPropertyPage(@RequestBody @Valid VopGoodsSyncRequest request) {
        //同步商品
        if (CollectionUtils.isEmpty(request.getVopSkuIds())) {
            return BaseResponse.SUCCESSFUL();
        }
        StoreVO storeVO = storeQueryProvider.getBycompanySourceType(new StoreBycompanySourceType(CompanySourceType.JD_VOP)).getContext();
        return channelSyncGoodsProvider.syncSkuList(ChannelGoodsSyncBySkuVasRequest.builder()
                .skuIds(request.getVopSkuIds())
                .thirdPlatformType(ThirdPlatformType.VOP)
                .storeId(storeVO.getStoreId())
                .companyInfoId(storeVO.getCompanyInfoId())
                .companyName(storeVO.getCompanyInfo() == null ? null : storeVO.getCompanyInfo().getCompanyName())
                .build());
    }

    @Autowired private EmpowerMqConsumerProvider empowerMqConsumerProvider;

    @Operation(summary = "商品同步")
    @PostMapping(value = "/sync/goodsTempSyncOver")
    public BaseResponse findGoodsPropertyPage() {
        EmpowerMqConsumerRequest empowerMqConsumerRequest = new EmpowerMqConsumerRequest();
        empowerMqConsumerRequest.setMqContentJson("Boos test");
        empowerMqConsumerProvider.goodsTempSyncOver(empowerMqConsumerRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
