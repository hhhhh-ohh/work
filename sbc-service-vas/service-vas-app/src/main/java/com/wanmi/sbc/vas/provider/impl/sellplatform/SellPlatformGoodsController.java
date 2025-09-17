package com.wanmi.sbc.vas.provider.impl.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformGoodsProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.goods.*;
import com.wanmi.sbc.vas.api.response.sellplatform.goods.SellPlatformAddGoodsResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.goods.SellPlatformGetGoodsResponse;
import com.wanmi.sbc.vas.sellplatform.SellPlatformContext;
import com.wanmi.sbc.vas.sellplatform.SellPlatformGoodsService;
import com.wanmi.sbc.vas.sellplatform.SellPlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class SellPlatformGoodsController implements SellPlatformGoodsProvider {

    @Autowired private SellPlatformContext sellPlatformContext;

    @Override
    public BaseResponse<SellPlatformAddGoodsResponse> addGoods(@RequestBody @Valid SellPlatformAddGoodsRequest request) {
        SellPlatformGoodsService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_GOODS_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        return BaseResponse.success(applyService.addGoods(request));
    }

    @Override
    public BaseResponse<SellPlatformGetGoodsResponse> delAuditGoods(@RequestBody @Valid SellPlatformGoodsBaseRequest request) {
        SellPlatformGoodsService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_GOODS_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        return BaseResponse.success(applyService.delAuditGoods(request));
    }

    @Override
    public BaseResponse delGoods(@Valid SellPlatformDeleteGoodsRequest request) {
        SellPlatformGoodsService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_GOODS_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        applyService.delGoods(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateGoods(@RequestBody @Valid SellPlatformUpdateGoodsRequest request) {
        SellPlatformGoodsService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_GOODS_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        return applyService.updateGoods(request);
    }

    @Override
    public BaseResponse syncStock(@RequestBody @Valid List<SellPlatformSyncStockRequest> request) {
        SellPlatformGoodsService applyService = sellPlatformContext.getPlatformService(request.get(0).getSellPlatformType(), SellPlatformServiceType.SELL_GOODS_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        applyService.syncStock(request);
        return BaseResponse.SUCCESSFUL();
    }
    @Override
    public BaseResponse listingGoods(@RequestBody @Valid SellPlatformGoodsBaseRequest request) {
        SellPlatformGoodsService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_GOODS_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        applyService.listingGoods(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse delistingGoods(@RequestBody @Valid SellPlatformGoodsBaseRequest request) {
        SellPlatformGoodsService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_GOODS_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        applyService.delistingGoods(request);
        return BaseResponse.SUCCESSFUL();
    }

}
