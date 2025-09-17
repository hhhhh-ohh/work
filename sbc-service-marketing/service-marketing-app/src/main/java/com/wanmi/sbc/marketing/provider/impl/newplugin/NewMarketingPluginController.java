package com.wanmi.sbc.marketing.provider.impl.newplugin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.newplugin.*;
import com.wanmi.sbc.marketing.api.response.info.GoodsInfoListByGoodsInfoResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsCartPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListCacheMarketingResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginAdapter;
import com.wanmi.sbc.marketing.newplugin.service.SkuCacheMarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className NewMarketingPluginController
 * @description TODO
 * @date 2021/7/26 10:44 上午
 **/
@RestController
@Validated
@Scope("prototype")
public class NewMarketingPluginController implements NewMarketingPluginProvider {
    @Autowired private SkuCacheMarketingService skuCacheMarketingService;

    @Autowired private MarketingPluginAdapter marketingPluginAdapter;

    @Override
    public BaseResponse<GoodsInfoDetailPluginResponse> goodsInfoDetailPlugin(@Valid GoodsInfoPluginRequest request) {
        return BaseResponse.success(this.marketingPluginAdapter.goodsDetail(request));
    }

    @Override
    public BaseResponse<GoodsListPluginResponse> goodsListPlugin(@Valid GoodsListPluginRequest request) {

        return BaseResponse.success(this.marketingPluginAdapter.goodsList(request));
    }

    @Override
    public BaseResponse<GoodsCartPluginResponse> cartListPlugin(@Valid GoodsListPluginRequest request) {

        return BaseResponse.success(this.marketingPluginAdapter.cartList(request));
    }

    @Override
    public BaseResponse<GoodsInfoDetailPluginResponse> goodsInfoDetailPrePlugin(@RequestBody  @Valid MarketingPluginPreRequest request) {
        return BaseResponse.success(this.skuCacheMarketingService.getSkuCachePreMarketing(request));
    }


    @Override
    public BaseResponse<GoodsListCacheMarketingResponse> goodsListCacheMarketing(@RequestBody  @Valid GoodsListCacheMarketingRequest request) {
        Map<String, Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>>> retMap = this.skuCacheMarketingService.getSkuCacheMarketing(request.getGoodsInfoIdList(), Boolean.TRUE);
        return BaseResponse.success(GoodsListCacheMarketingResponse.builder().retMap(retMap).build());
    }
    @Override
    public BaseResponse<GoodsTradePluginResponse> immediateBuyPlugin(@Valid GoodsListPluginRequest request) {

        return BaseResponse.success(this.marketingPluginAdapter.immediateBuy(request));
    }

    @Override
    public BaseResponse<GoodsTradePluginResponse> confirmPlugin(@Valid GoodsListPluginRequest request) {

        return BaseResponse.success(this.marketingPluginAdapter.confirm(request));
    }

    @Override
    public BaseResponse<GoodsTradePluginResponse> commitPlugin(@Valid GoodsListPluginRequest request) {

        return BaseResponse.success(this.marketingPluginAdapter.commit(request));
    }

    @Override
    public BaseResponse<GoodsInfoListByGoodsInfoResponse> flushCache(@Valid MarketingPluginFlushCacheRequest request) {
        boolean flag;
        if(request.getDeleteFlag() == DeleteFlag.NO){
            flag = skuCacheMarketingService.setSkuCacheMarketing(request.getGoodsInfoMarketingCacheDTOS());
        }else{
            flag = skuCacheMarketingService.delSkuCacheMarketing(request.getGoodsInfoMarketingCacheDTOS());
        }
        //根据redis返回值判断是否刷新缓存成功
        if (!flag) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        return BaseResponse.SUCCESSFUL();
    }
}
