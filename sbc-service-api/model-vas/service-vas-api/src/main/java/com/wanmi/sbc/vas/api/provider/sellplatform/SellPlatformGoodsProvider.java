package com.wanmi.sbc.vas.api.provider.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.goods.*;
import com.wanmi.sbc.vas.api.response.sellplatform.goods.SellPlatformAddGoodsResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.goods.SellPlatformGetGoodsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;


/**
 * @author wur
 * @className SellPlatformGoodsProvider
 * @description 微信视频商品处理
 * @date 2022/4/11 10:28
 */
@FeignClient(value = "${application.vas.name}", contextId = "SellPlatformGoodsProvider")
public interface SellPlatformGoodsProvider {

    /**
     * @description  新增商品
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/channel/${application.vas.version}/sell-platform/add_goods/")
    BaseResponse<SellPlatformAddGoodsResponse> addGoods(@RequestBody @Valid SellPlatformAddGoodsRequest request);


    /**
    *
     * @description 撤回商品审核
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/channel/${application.vas.version}/sell-platform/del_audit_goods/")
    BaseResponse<SellPlatformGetGoodsResponse> delAuditGoods(@RequestBody @Valid SellPlatformGoodsBaseRequest request);

    /**
     * @description 删除商品
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/channel/${application.vas.version}/sell-platform/del_goods/")
    BaseResponse delGoods(@RequestBody @Valid SellPlatformDeleteGoodsRequest request);

    /**
    *
     * @description 修改商品
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/channel/${application.vas.version}/sell-platform/update_goods/")
    BaseResponse<SellPlatformAddGoodsResponse> updateGoods(@RequestBody @Valid SellPlatformUpdateGoodsRequest request);


    @PostMapping("/channel/${application.vas.version}/sell-platform/syncStock")
    BaseResponse syncStock(@RequestBody @Valid List<SellPlatformSyncStockRequest> request);
    /**
    *
     * @description 商品上架
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/channel/${application.vas.version}/sell-platform/listing_goods/")
    BaseResponse listingGoods(@RequestBody @Valid SellPlatformGoodsBaseRequest request);

    /**
    *
     * @description 商品下架
     * @author  wur
     * @date: 2022/4/11 17:15
     * @param request
     **/
    @PostMapping("/channel/${application.vas.version}/sell-platform/delisting_goods/")
    BaseResponse delistingGoods(@RequestBody @Valid SellPlatformGoodsBaseRequest request);


}
