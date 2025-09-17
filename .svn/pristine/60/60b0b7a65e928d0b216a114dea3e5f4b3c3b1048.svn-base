package com.wanmi.sbc.order.provider.impl.purchase;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseProvider;
import com.wanmi.sbc.order.api.request.purchase.*;
import com.wanmi.sbc.order.api.response.purchase.*;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.purchase.GoodsMarketingService;
import com.wanmi.sbc.order.purchase.PurchaseService;
import com.wanmi.sbc.order.purchase.request.PurchaseRequest;
import com.wanmi.sbc.order.purchase.service.CartAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import jakarta.annotation.Resource;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-04
 */
@Validated
@RestController
public class PurchaseController implements PurchaseProvider {

    @Autowired
    private PurchaseService purchaseService;
    @Resource
    private GoodsMarketingService goodsMarketingService;

    @Autowired
    private CartAdaptor cartAdaptor;

    /**
     * @param request 新增采购单请求结构 {@link PurchaseSaveRequest}
     * @return
     */
    @Override
    public BaseResponse save(@RequestBody @Valid PurchaseSaveRequest request) {
        purchaseService.save(KsBeanUtil.convert(request, PurchaseRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 批量加入采购单请求结构 {@link PurchaseBatchSaveRequest}
     * @return
     */
    @Override
    public BaseResponse batchSave(@RequestBody @Valid PurchaseBatchSaveRequest request) {
        purchaseService.batchSave(KsBeanUtil.convert(request, PurchaseRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description   批量加购支持容错
     * @author  wur
     * @date: 2022/10/22 13:09
     * @param request
     * @return
     **/
    @Override
    public BaseResponse batchSaveNew(@RequestBody @Valid PurchaseBatchSaveRequest request) {
        return purchaseService.batchSaveNew(KsBeanUtil.convert(request, PurchaseRequest.class));
    }

    /**
     * @param request 商品收藏调整商品数量请求结构 {@link PurchaseUpdateNumRequest}
     * @return
     */
    @Override
    public BaseResponse updateNum(@RequestBody @Valid PurchaseUpdateNumRequest request) {
        purchaseService.updateNum(KsBeanUtil.convert(request, PurchaseRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 删除采购单请求结构 {@link PurchaseDeleteRequest}
     * @return
     */
    @Override
    public BaseResponse delete(@RequestBody @Valid PurchaseDeleteRequest request) {
        int result = purchaseService.delete(KsBeanUtil.convert(request, PurchaseRequest.class));
        if (result < 1) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050172);
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 采购单商品移入收藏夹请求结构 {@link PurchaseAddFollowRequest}
     * @return
     */
    @Override
    public BaseResponse addFollow(@RequestBody @Valid PurchaseAddFollowRequest request) {
        purchaseService.addFollow(KsBeanUtil.convert(request, PurchaseRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 清除失效商品请求结构 {@link PurchaseClearLoseGoodsRequest}
     * @return
     */
    @Override
    public BaseResponse clearLoseGoods(@RequestBody @Valid PurchaseClearLoseGoodsRequest request) {
        purchaseService.clearLoseGoods(request.getCustomerVO(), request.getDistributeChannel(),request.getStoreId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 计算采购单金额请求结构 {@link PurchaseCalcAmountRequest}
     * @return
     */
    @Override
    public BaseResponse<PurchaseListResponse> calcAmount(@RequestBody @Valid PurchaseCalcAmountRequest request) {
        PurchaseListResponse purchaseListResponse = purchaseService.calcAmount(KsBeanUtil.convert(request.getPurchaseCalcAmount(), PurchaseListResponse.class), request.getGoodsInfoIds(), request.getCustomerId());
        return BaseResponse.success(purchaseListResponse);
    }

    /**
     * @param request 计算采购单中参加同种营销的商品列表/总额/优惠请求结构 {@link PurchaseCalcMarketingRequest}
     * @return
     */
    @Override
    public BaseResponse<PurchaseCalcMarketingResponse> calcMarketingByMarketingId(@RequestBody @Valid PurchaseCalcMarketingRequest request) {
        PurchaseMarketingCalcResponse purchaseMarketingCalcResponse = purchaseService.calcMarketingByMarketingIdBase(
                request.getMarketingId(),
                KsBeanUtil.convert(request.getCustomer(), CustomerVO.class),
                request.getFrontRequest(),
                request.getGoodsInfoIds(),
                request.getIsPurchase());
        return BaseResponse.success(KsBeanUtil.convert(purchaseMarketingCalcResponse, PurchaseCalcMarketingResponse.class));
    }

    /**
     * @param request 同步商品使用的营销请求结构 {@link PurchaseSyncGoodsMarketingsRequest}
     * @return
     */
    @Override
    public BaseResponse syncGoodsMarketings(@RequestBody @Valid PurchaseSyncGoodsMarketingsRequest request) {
        purchaseService.syncGoodsMarketings(request.getGoodsMarketingMap(), request.getCustomerId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 修改商品使用的营销请求结构 {@link PurchaseModifyGoodsMarketingRequest}
     * @return
     */
    @Override
    public BaseResponse modifyGoodsMarketing(@RequestBody @Valid PurchaseModifyGoodsMarketingRequest request) {
        goodsMarketingService.modifyGoodsMarketing(request.getGoodsInfoId(), request.getMarketingId(),
                KsBeanUtil.convert(request.getCustomer(), CustomerVO.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 填充客户购买数请求结构 {@link PurchaseFillBuyCountRequest}
     * @return
     */
    @Override
    public BaseResponse<PurchaseFillBuyCountResponse> fillBuyCount(@RequestBody @Valid PurchaseFillBuyCountRequest request) {
        List<GoodsInfoVO> goodsInfoList = purchaseService.fillBuyCount(request.getGoodsInfoList(), request.getCustomerId(), request.getInviteeId());
        PurchaseFillBuyCountResponse purchaseFillBuyCountResponse = new PurchaseFillBuyCountResponse();
        purchaseFillBuyCountResponse.setGoodsInfoList(goodsInfoList);
        return BaseResponse.success(purchaseFillBuyCountResponse);
    }

    /**
     * 合并登录前后采购单
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse mergePurchase(@RequestBody @Valid PurchaseMergeRequest request) {
        purchaseService.mergePurchase(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<GoodsCartListResponse> getCartList(@RequestBody @Valid PurchaseInfoRequest request){
        return BaseResponse.success(cartAdaptor.getCartList(request));
    }

    /**
     * 更新购物商品规格
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse replacePurchase(PurchaseReplaceRequest request) {
        // 删除购物车中原有规格商品信息
        PurchaseRequest purchaseRequest = KsBeanUtil.convert(request, PurchaseRequest.class);
        purchaseRequest.setGoodsInfoIds(Arrays.asList(request.getDeleteGoodsInfoId()));
        int result = purchaseService.delete(purchaseRequest);
        if (result < 1) {
            return BaseResponse.FAILED();
        }
        // 将新的规格的商品信息加入购物车中
        purchaseService.batchSave(KsBeanUtil.convert(request, PurchaseRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 快速下单商品移入收藏夹请求结构 {@link PurchaseAddFollowRequest}
     * @return
     */
    @Override
    public BaseResponse quickOrderAddFollow(@RequestBody @Valid PurchaseAddFollowRequest request) {
        purchaseService.quickOrderAddFollow(KsBeanUtil.convert(request, PurchaseRequest.class));
        return BaseResponse.SUCCESSFUL();
    }
}
