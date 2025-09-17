package com.wanmi.sbc.empower.provider.impl.sellplatform.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.sellplatform.goods.PlatformGoodsProvider;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.*;
import com.wanmi.sbc.empower.api.response.sellplatform.goods.PlatformAddGoodsResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.goods.PlatformGetGoodsResponse;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformGoodsService;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
*
 * @description
 * @author  wur
 * @date: 2022/4/11 10:41
 **/
@RestController
public class PlatformGoodsController implements PlatformGoodsProvider {

    @Autowired private PlatformContext thirdPlatformContext;

    @Override
    public BaseResponse<PlatformAddGoodsResponse> addGoods(PlatformAddGoodsRequest request) {
        PlatformGoodsService goodsService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.GOODS_SERVICE);
        return goodsService.addGoods(request);
    }

    @Override
    public BaseResponse<PlatformGetGoodsResponse> getGoods(PlatformGetGoodsRequest request) {
        PlatformGoodsService goodsService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.GOODS_SERVICE);
        return goodsService.getSpu(request);
    }

    @Override
    public BaseResponse<PlatformGetGoodsResponse> delAuditGoods(PlatformGoodsBaseRequest request) {
        PlatformGoodsService goodsService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.GOODS_SERVICE);
        return goodsService.delAudit(request);
    }

    @Override
    public BaseResponse delGoods(PlatformDeleteGoodsRequest request) {
        PlatformGoodsService goodsService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.GOODS_SERVICE);
        return goodsService.delGoods(request);
    }

    @Override
    public BaseResponse<PlatformAddGoodsResponse> updateGoods(PlatformUpdateGoodsRequest request) {
        PlatformGoodsService goodsService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.GOODS_SERVICE);
        return goodsService.updateGoods(request);
    }

    @Override
    public BaseResponse listingGoods(PlatformGoodsBaseRequest request) {
        PlatformGoodsService goodsService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.GOODS_SERVICE);
        return goodsService.listing(request);
    }

    @Override
    public BaseResponse delistingGoods(PlatformGoodsBaseRequest request) {
        PlatformGoodsService goodsService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.GOODS_SERVICE);
        return goodsService.delisting(request);
    }

    @Override
    public BaseResponse updateNoAuditGoods(@Valid PlatformUpdateNoAuditRequest request) {
        PlatformGoodsService goodsService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.GOODS_SERVICE);
        return goodsService.updateNoAuditGoods(request);
    }
}
