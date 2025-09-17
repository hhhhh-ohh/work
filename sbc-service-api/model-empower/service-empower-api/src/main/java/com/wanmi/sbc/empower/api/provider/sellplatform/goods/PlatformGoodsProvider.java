package com.wanmi.sbc.empower.api.provider.sellplatform.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.*;
import com.wanmi.sbc.empower.api.response.sellplatform.goods.PlatformAddGoodsResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.goods.PlatformGetGoodsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


/**
 * @author wur
 * @className PlatformGoodsProvider
 * @description 微信视频商品处理
 * @date 2022/4/11 10:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "PlatformGoodsProvider")
public interface PlatformGoodsProvider {

    /**
     * @description  新增商品
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/add_goods/")
    BaseResponse<PlatformAddGoodsResponse> addGoods(@RequestBody @Valid PlatformAddGoodsRequest request);

    /**
    *
     * @description 查询商品信息
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/get_goods/")
    BaseResponse<PlatformGetGoodsResponse> getGoods(@RequestBody PlatformGetGoodsRequest request);


    /**
    *
     * @description 撤回商品审核
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/del_audit_goods/")
    BaseResponse<PlatformGetGoodsResponse> delAuditGoods(@RequestBody @Valid PlatformGoodsBaseRequest request);

    /**
     * @description 删除商品
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/del_goods/")
    BaseResponse delGoods(@RequestBody @Valid PlatformDeleteGoodsRequest request);

    /**
    *
     * @description 修改商品
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/update_goods/")
    BaseResponse<PlatformAddGoodsResponse> updateGoods(@RequestBody @Valid PlatformUpdateGoodsRequest request);

    /**
    *
     * @description 商品上架
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/listing_goods/")
    BaseResponse listingGoods(@RequestBody @Valid PlatformGoodsBaseRequest request);

    /**
    *
     * @description 商品下架
     * @author  wur
     * @date: 2022/4/11 17:15
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/delisting_goods/")
    BaseResponse delistingGoods(@RequestBody @Valid PlatformGoodsBaseRequest request);

    /**
     * @description
     * @author  wur
     * @date: 2022/4/19 9:36
     * @return
     **/
    @PostMapping("/empower/${application.empower.version}/third_platform/update_noaudit_goods/")
    BaseResponse updateNoAuditGoods(@RequestBody @Valid PlatformUpdateNoAuditRequest request);

}
