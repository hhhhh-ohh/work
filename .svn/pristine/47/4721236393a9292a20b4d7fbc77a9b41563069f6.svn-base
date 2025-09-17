package com.wanmi.sbc.goods.api.provider.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goods.*;
import com.wanmi.sbc.goods.api.response.goods.*;
import com.wanmi.sbc.goods.api.response.linkedmall.ThirdPlatformGoodsDelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

/**
 * com.wanmi.sbc.goods.api.provider.goods.GoodsProvider
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午9:30
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsProvider")
public interface GoodsProvider {

    /**
     * 新增商品
     *
     * @param request {@link GoodsAddRequest}
     * @return 新增结果 {@link GoodsAddResponse}
     */
    @PostMapping("/goods/${application.goods.version}/add")
    BaseResponse<GoodsAddResponse> add(@RequestBody @Valid GoodsAddRequest request);
    /**
     * linkedmall删除商品
     *
     */
    @PostMapping("/goods/${application.goods.version}/thirdplatform/del")
    BaseResponse<ThirdPlatformGoodsDelResponse> deleteThirdPlatformGoods(@RequestBody @Valid ThirdPlatformGoodsDelRequest request);
    /**
     * 修改所有商家三方渠道商品可售状态
     *
     */
    @PostMapping("/goods/${application.goods.version}/vendibility/thirdplatform/goods")
    BaseResponse vendibilityThirdGoods(@RequestBody @Valid ThirdGoodsVendibilityRequest request);

    /**
     * 修改商品
     *
     * @param request {@link GoodsModifyRequest}
     * @return 修改结果 {@link GoodsModifyResponse}
     */
    @PostMapping("/goods/${application.goods.version}/modify")
    BaseResponse<GoodsModifyResponse> modify(@RequestBody @Valid GoodsModifyRequest request);

    /**
     * 新增商品定价
     *
     * @param request {@link GoodsAddPriceRequest}
     */
    @PostMapping("/goods/${application.goods.version}/add-price")
    BaseResponse addPrice(@RequestBody @Valid GoodsAddPriceRequest request);

    /**
     * 新增商品基本信息、基价
     *
     * @param request {@link GoodsAddAllRequest}
     * @return 商品编号 {@link GoodsAddAllResponse}
     */
    @PostMapping("/goods/${application.goods.version}/add-all")
    BaseResponse<GoodsAddAllResponse> addAll(@RequestBody @Valid GoodsAddAllRequest request);

    /**
     * 修改商品基本信息、基价
     *
     * @param request {@link GoodsModifyAllRequest}
     * @return 修改结果 {@link GoodsModifyInfoResponse}
     */
    @PostMapping("/goods/${application.goods.version}/modify-all")
    BaseResponse<GoodsModifyInfoResponse> modifyAll(@RequestBody @Valid GoodsModifyAllRequest request);

    /**
     * 修改商品基本信息、基价
     *
     * @param request {@link GoodsDeleteByIdsRequest}
     */
    @PostMapping("/goods/${application.goods.version}/delete-by-ids")
    BaseResponse<GoodsDeleteResponse> deleteByIds(@RequestBody @Valid GoodsDeleteByIdsRequest request);

    /**
     * 删除供应商商品
     *
     * @param request {@link GoodsDeleteByIdsRequest}
     */
    @PostMapping("/goods/${application.goods.version}/provider/delete-by-ids")
    BaseResponse<ProviderGoodsDelResponse> deleteProviderGoodsByIds(@RequestBody @Valid GoodsDeleteByIdsRequest request);

    /**
     * 修改商品上下架状态
     *
     * @param request {@link GoodsModifyAddedStatusRequest}
     */
    @PostMapping("/goods/${application.goods.version}/modify-added-status")
    BaseResponse modifyAddedStatus(@RequestBody @Valid GoodsModifyAddedStatusRequest request);

    /**
     * 修改商品上下架状态
     *
     * @param request {@link GoodsModifyAddedStatusRequest}
     */
    @PostMapping("/goods/${application.goods.version}/provider-modify-added-status")
    BaseResponse providerModifyAddedStatus(@RequestBody @Valid GoodsModifyAddedStatusRequest request);

    /**
     * 定时任务修改商品上下架状态
     *
     * @param request {@link GoodsModifyAddedStatusRequest}
     */
    @PostMapping("/goods/${application.goods.version}/provider-modify-added-status-by-timing")
    BaseResponse providerModifyAddedStatusByTiming(@RequestBody @Valid GoodsModifyAddedStatusRequest request);


    /**
     * 修改商品分类
     *
     * @param request {@link GoodsModifyCateRequest}
     */
    @PostMapping("/goods/${application.goods.version}/modify-cate")
    BaseResponse modifyCate(@RequestBody @Valid GoodsModifyCateRequest request);

    /**
     * 修改商品商家名称
     *
     * @param request {@link GoodsModifySupplierNameRequest}
     */
    @PostMapping("/goods/${application.goods.version}/modify-supplier-name")
    BaseResponse modifySupplierName(@RequestBody @Valid GoodsModifySupplierNameRequest request);

    /**
     * 修改商品运费模板
     *
     * @param request {@link GoodsModifyFreightTempRequest}
     */
    @PostMapping("/goods/${application.goods.version}/modify-freight-temp")
    BaseResponse modifyFreightTemp(@RequestBody @Valid GoodsModifyFreightTempRequest request);

    /**
     * 商品审核
     *
     * @param request {@link GoodsCheckRequest}
     */
    @PostMapping("/goods/${application.goods.version}/check")
    BaseResponse<GoodsCheckResponse> checkGoods(@RequestBody @Valid GoodsCheckRequest request);

    /**
     * @Author lvzhenwei
     * @Description 更新商品收藏量
     * @Date 16:04 2019/4/11
     * @Param [goodsModifyCollectNumRequest]
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/update-goods-collect-num")
    BaseResponse updateGoodsCollectNum(@RequestBody @Valid GoodsModifyCollectNumRequest goodsModifyCollectNumRequest);

    /**
     * @Author lvzhenwei
     * @Description 更新商品销量数据
     * @Date 16:08 2019/4/11
     * @Param [goodsModifySalesNumRequest]
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/update-goods-sales-num")
    BaseResponse updateGoodsSalesNum(@RequestBody @Valid GoodsModifySalesNumRequest goodsModifySalesNumRequest);

    /**
     * @Author lvzhenwei
     * @Description 更新商品评论数量
     * @Date 15:21 2019/4/12
     * @Param [request]
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/update-goods-favorable-comment-num")
    BaseResponse updateGoodsFavorableCommentNum(@RequestBody @Valid GoodsModifyEvaluateNumRequest request);

    /**
     * 修改商品注水销量
     *
     * @param request {@link GoodsModifyShamSalesNumRequest}
     */
    @PostMapping("/goods/${application.goods.version}/modify-sham-sales-num")
    BaseResponse modifyShamSalesNum(@RequestBody @Valid GoodsModifyShamSalesNumRequest request);

    /**
     * 修改商品序列号
     *
     * @param request {@link GoodsModifySortNoRequest}
     */
    @PostMapping("/goods/${application.goods.version}/modify-sort-no")
    BaseResponse modifySortNo(@RequestBody @Valid GoodsModifySortNoRequest request);

    /**
     * @Author dkx
     * @Description 同步供应商商品库最新商品
     * @Param request {GoodsSynRequest}
     **/
    @PostMapping("/goods/${application.goods.version}/update-goods-syn")
    BaseResponse<GoodsSynResponse> synGoods(@RequestBody @Valid GoodsSynRequest request);

    /**
     * 更新代销商品供应商店铺状态
     */
    @PostMapping("/goods/${application.goods.version}/update-provider-status")
    BaseResponse updateProviderStatus(@RequestBody @Valid GoodsProviderStatusRequest request);

    /**
     * 更新代销商品供应商店铺状态
     */
    @PostMapping("/goods/${application.goods.version}/update-store-goods-status")
    BaseResponse updateStoreGoodsStatus(@RequestBody @Valid StoreGoodsStatusRequest request);

    /**
     * 将商品关联到店铺的默认分类
     * @param goodsId 是goods表的goodsId
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/store/cate/addGoodsToDefaultStoreCateRel")
    BaseResponse addGoodsToDefaultStoreCateRel(@RequestParam("goodsId") String goodsId);

    /**
     * 将商品关联到店铺的默认分类
     * @param
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/store/cate/change-added-flag-by-bind-state")
    BaseResponse changeAddedFlagByLedgerBindState();

    /**
     *  删除商家，商品库所有linkedmall商品
     */
//    @PostMapping("/goods/${application.goods.version}/del-all-linkedmall-goods")
//    BaseResponse delAllLinkedMallGoods();
}
