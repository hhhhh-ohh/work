package com.wanmi.sbc.purchase;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsPriceAssistProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoFillGoodsStatusRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseProvider;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseQueryProvider;
import com.wanmi.sbc.order.api.request.purchase.*;
import com.wanmi.sbc.order.api.response.purchase.*;
import com.wanmi.sbc.order.bean.dto.PurchaseMergeDTO;
import com.wanmi.sbc.purchase.request.FrontCartInfoRequest;
import com.wanmi.sbc.purchase.request.PurchaseGoodsQueryRequest;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 采购单Controller
 * Created by daiyitian on 17/4/12.
 */
@Tag(name = "PurchaseBaseController", description = "采购单服务API")
@RestController
@RequestMapping("/site")
@Validated
public class PurchaseBaseController {

    @Autowired
    private PurchaseQueryProvider purchaseQueryProvider;

    @Autowired
    private PurchaseProvider purchaseProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired
    private GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private GoodsPriceAssistProvider goodsPriceAssistProvider;

    @Autowired
    private DistributionCacheService distributionCacheService;

    @Autowired
    private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired
    private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Operation(summary = "获取店铺下，是否有优惠券营销，展示优惠券标签")
    @RequestMapping(value = "/getStoreCouponExist", method = RequestMethod.GET)
    public BaseResponse<PurchaseGetStoreCouponExistResponse> getStoreCouponExist() {
        return purchaseQueryProvider.getStoreCouponExist(PurchaseGetStoreCouponExistRequest.builder()
                .inviteeId(commonUtil.getPurchaseInviteeId())
                .customer(commonUtil.getCustomer())
                .pluginType(PluginType.NORMAL)
                .build());
    }


    /**
     * 未登录时,根据参数获取迷你采购单信息
     *
     * @return
     */
    @Operation(summary = "未登录时,根据参数获取迷你采购单信息")
    @RequestMapping(value = "/front/miniPurchases", method = RequestMethod.POST)
    public BaseResponse<MiniPurchaseResponse> miniFrontInfo(@RequestBody @Valid PurchaseFrontMiniRequest request) {
        request.setInviteeId(commonUtil.getPurchaseInviteeId());
        request.setTerminalSource(commonUtil.getTerminal());
        MiniPurchaseResponse response = purchaseQueryProvider.miniListFront(request).getContext();
        List<GoodsInfoVO> goodsInfos = goodsInfoProvider.fillGoodsStatus(GoodsInfoFillGoodsStatusRequest.builder()
                .goodsInfos(KsBeanUtil.convertList(response.getGoodsList(), GoodsInfoDTO.class)).build()).getContext().getGoodsInfos();
        response.getGoodsList().forEach(goodsInfo -> {
            goodsInfos.forEach(info -> {
                if (info.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())) {
                    goodsInfo.setGoodsStatus(info.getGoodsStatus());
                }
            });
        });
        return BaseResponse.success(response);
    }

    /**
     * 获取迷你采购单
     *
     * @return
     */
    @Operation(summary = "获取迷你采购单")
    @RequestMapping(value = "/miniPurchases", method = RequestMethod.POST)
    public BaseResponse<PurchaseMiniListResponse> miniInfo() {

        CustomerVO customer = commonUtil.getCustomer();
        PurchaseMiniListRequest purchaseMiniListRequest = new PurchaseMiniListRequest();
        purchaseMiniListRequest.setCustomerId(customer.getCustomerId());

        purchaseMiniListRequest.setInviteeId(commonUtil.getPurchaseInviteeId());
        purchaseMiniListRequest.setCustomer(KsBeanUtil.convert(customer, CustomerDTO.class));
        purchaseMiniListRequest.setTerminalSource(commonUtil.getTerminal());

        //需要叠加访问端Pc\app不体现分销业务
        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        if (Objects.equals(ChannelType.PC_MALL, commonUtil.getDistributeChannel().getChannelType()) || DefaultFlag.NO.equals(openFlag)) {
            purchaseMiniListRequest.setPcAndNoOpenFlag(Boolean.TRUE);
        }
        TerminalSource terminal = commonUtil.getTerminal();

        if(terminal == TerminalSource.PC) {
            purchaseMiniListRequest.setPluginTypeFlag(BoolFlag.YES);
        }

        PurchaseMiniListResponse purchaseMiniListResponse =
                purchaseQueryProvider.minilist(purchaseMiniListRequest).getContext();
        List<GoodsInfoVO> goodsInfos = goodsInfoProvider.fillGoodsStatus(GoodsInfoFillGoodsStatusRequest.builder()
                .goodsInfos(KsBeanUtil.convertList(purchaseMiniListResponse.getGoodsList(), GoodsInfoDTO.class)).build()).getContext().getGoodsInfos();
        purchaseMiniListResponse.getGoodsList().forEach(goodsInfo -> {
            goodsInfos.forEach(info -> {
                if (info.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())) {
                    goodsInfo.setGoodsStatus(info.getGoodsStatus());
                }
            });
        });
        return BaseResponse.success(purchaseMiniListResponse);
    }

    /**
     * 获取采购单商品数量
     *
     * @return
     */
    @Operation(summary = "获取采购单商品数量")
    @RequestMapping(value = "/countGoods", method = RequestMethod.GET)
    public BaseResponse<Integer> countGoods() {
        PurchaseCountGoodsRequest purchaseCountGoodsRequest = new PurchaseCountGoodsRequest();
        purchaseCountGoodsRequest.setCustomerId(commonUtil.getOperatorId());
        purchaseCountGoodsRequest.setInviteeId(commonUtil.getPurchaseInviteeId());

        PurchaseCountGoodsResponse purchaseCountGoodsResponse =
                purchaseQueryProvider.countGoods(purchaseCountGoodsRequest).getContext();
        return BaseResponse.success(purchaseCountGoodsResponse.getTotal());
    }

    /**
     * 新增采购单
     *
     * @param request 数据
     * @return 结果
     */
    @Operation(summary = "新增采购单")
    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public BaseResponse add(@RequestBody @Valid PurchaseSaveRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        if (StringUtils.isBlank(request.getCustomerId()) || StringUtils.isBlank(request.getGoodsInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //校验是否可以加入购物车
       // purchaseQueryProvider.checkAdd(request);
        request.setInviteeId(commonUtil.getPurchaseInviteeId());
        request.setTerminalSource(commonUtil.getTerminal());
        purchaseProvider.save(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量新增采购单
     *
     * @param request
     * @return
     */
    @Operation(summary = "批量新增采购单")
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    public BaseResponse batchAdd(@RequestBody PurchaseBatchSaveRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        if (StringUtils.isBlank(request.getCustomerId()) || CollectionUtils.isEmpty(request.getGoodsInfos())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<String> goodsInfoIds = request.getGoodsInfos().stream().map(GoodsInfoDTO::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setInviteeId(commonUtil.getPurchaseInviteeId());
        request.setTerminalSource(commonUtil.getTerminal());
        purchaseProvider.batchSave(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description    批量新增采购单  支持部分容错
     * @author  wur
     * @date: 2022/10/22 13:13
     * @param request
     * @return
     **/
    @Operation(summary = "批量新增采购单 - 支持部分容错")
    @RequestMapping(value = "/batchAddNew", method = RequestMethod.POST)
    public BaseResponse batchAddNew(@RequestBody PurchaseBatchSaveRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        if (StringUtils.isBlank(request.getCustomerId()) || CollectionUtils.isEmpty(request.getGoodsInfos())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        for (GoodsInfoDTO goodsInfo : request.getGoodsInfos()) {
            if (Objects.isNull(goodsInfo) || StringUtils.isBlank(goodsInfo.getGoodsInfoId()) || Objects.isNull(goodsInfo.getBuyCount())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        request.setInviteeId(commonUtil.getPurchaseInviteeId());
        request.setTerminalSource(commonUtil.getTerminal());
        return purchaseProvider.batchSaveNew(request);
    }

    /**
     * 调整数量
     *
     * @param request 数据
     * @return 结果
     */
    @Operation(summary = "调整数量")
    @RequestMapping(value = "/purchase", method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody @Valid PurchaseUpdateNumRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        if (StringUtils.isBlank(request.getCustomerId()) || StringUtils.isEmpty(request.getGoodsInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        request.setInviteeId(commonUtil.getPurchaseInviteeId());
        request.setTerminalSource(commonUtil.getTerminal());
        purchaseProvider.updateNum(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除采购单
     *
     * @param request 数据
     * @return 结果
     */
    @Operation(summary = "删除采购单")
    @RequestMapping(value = "/purchase", method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody PurchaseDeleteRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds()) || StringUtils.isBlank(request.getCustomerId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        request.setInviteeId(commonUtil.getPurchaseInviteeId());
        return purchaseProvider.delete(request);
    }

    /**
     * 清除失效商品
     *
     * @return
     */
    @MultiSubmit
    @Operation(summary = "清除失效商品")
    @RequestMapping(value = "/clearLoseGoods/{storeId}", method = RequestMethod.DELETE)
    public BaseResponse clearLoseGoods(@PathVariable Long storeId) {
        PurchaseClearLoseGoodsRequest purchaseClearLoseGoodsRequest = new PurchaseClearLoseGoodsRequest();
        purchaseClearLoseGoodsRequest.setUserId(commonUtil.getOperatorId());
        purchaseClearLoseGoodsRequest.setCustomerVO(commonUtil.getCustomer());
        purchaseClearLoseGoodsRequest.setStoreId(storeId == -1 ? null : storeId);
        DistributeChannel channel = commonUtil.getDistributeChannel();
        channel.setInviteeId(commonUtil.getPurchaseInviteeId());
        purchaseClearLoseGoodsRequest.setDistributeChannel(channel);
        purchaseProvider.clearLoseGoods(purchaseClearLoseGoodsRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 采购单商品移入收藏夹
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "采购单商品移入收藏夹")
    @RequestMapping(value = "/addFollow", method = RequestMethod.PUT)
    public BaseResponse addFollow(@RequestBody PurchaseAddFollowRequest queryRequest) {
        queryRequest.setCustomerId(commonUtil.getOperatorId());

        queryRequest.setInviteeId(commonUtil.getPurchaseInviteeId());
        purchaseProvider.addFollow(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 快速下单商品移入收藏夹
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "快速下单商品移入收藏夹")
    @RequestMapping(value = "/quickOrderAddFollow", method = RequestMethod.PUT)
    public BaseResponse quickOrderAddFollow(@RequestBody PurchaseAddFollowRequest queryRequest) {
        queryRequest.setCustomerId(commonUtil.getOperatorId());
        purchaseProvider.quickOrderAddFollow(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 合并登录前后采购单
     *
     * @param request
     * @return
     */
    @Operation(summary = "合并登录前后采购单")
    @RequestMapping(value = "/mergePurchase", method = RequestMethod.POST)
    public BaseResponse mergePurchase(@RequestBody @Valid PurchaseMergeRequest request) {
        if (CollectionUtils.isEmpty(request.getPurchaseMergeDTOList())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //如果超出50个商品 则只保留前50个商品
        if (request.getPurchaseMergeDTOList().size() > Constants.PURCHASE_MAX_SIZE) {
            request.setPurchaseMergeDTOList(request.getPurchaseMergeDTOList().subList(0, 50));
        }
        List<String> goodsInfoIds = request.getPurchaseMergeDTOList().stream().map(PurchaseMergeDTO::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setCustomer(KsBeanUtil.convert(commonUtil.getCustomer(), CustomerDTO.class));
        request.setInviteeId(commonUtil.getPurchaseInviteeId());
        request.setTerminalSource(commonUtil.getTerminal());
        purchaseProvider.mergePurchase(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 未登录时,计算采购单中参加同种营销的商品列表/总额/优惠
     *
     * @param marketingId
     * @return
     */
    @Operation(summary = "未登录时,计算采购单中参加同种营销的商品列表/总额/优惠")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/{marketingId}/calcMarketingByMarketingIdFront", method = RequestMethod.POST)
    public BaseResponse<PurchaseCalcMarketingResponse> calcMarketingByMarketingIdFront(
            @PathVariable Long marketingId, @RequestBody @Valid PurchaseFrontRequest queryRequest) {
        PurchaseCalcMarketingRequest request = new PurchaseCalcMarketingRequest();
        request.setMarketingId(marketingId);
        request.setFrontRequest(queryRequest);
        request.setGoodsInfoIds(queryRequest.getGoodsInfoIds());
        request.setIsPurchase(Boolean.FALSE);
        return purchaseProvider.calcMarketingByMarketingId(request);
    }

    /**
     * 计算采购单中参加同种营销的商品列表/总额/优惠
     *
     * @param marketingId
     * @return
     */
    @Operation(summary = "计算采购单中参加同种营销的商品列表/总额/优惠")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/{marketingId}/calcMarketingByMarketingId", method = RequestMethod.GET)
    public BaseResponse<PurchaseCalcMarketingResponse> calcMarketingByMarketingId(@PathVariable Long marketingId) {
        //获取会员
        CustomerVO customer = commonUtil.getCustomer();
        PurchaseCalcMarketingRequest purchaseCalcMarketingRequest = new PurchaseCalcMarketingRequest();
        purchaseCalcMarketingRequest.setCustomer(KsBeanUtil.convert(customer, CustomerDTO.class));
        purchaseCalcMarketingRequest.setGoodsInfoIds(null);
        purchaseCalcMarketingRequest.setIsPurchase(Boolean.FALSE);
        purchaseCalcMarketingRequest.setMarketingId(marketingId);
        return BaseResponse.success(purchaseProvider.calcMarketingByMarketingId(purchaseCalcMarketingRequest).getContext());
    }

    /**
     * 修改商品选择的营销
     *
     * @param goodsInfoId
     * @param marketingId
     * @return
     */
    @Operation(summary = "修改商品选择的营销")
    @Parameters({
            @Parameter(
                    name = "goodsInfoId", description = "sku Id", required = true),
            @Parameter(
                    name = "marketingId", description = "营销Id", required = true)
    })
    @RequestMapping(value = "/goodsMarketing/{goodsInfoId}/{marketingId}", method = RequestMethod.PUT)
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse modifyGoodsMarketing(@PathVariable String goodsInfoId, @PathVariable Long marketingId) {
        if (goodsInfoId == null || marketingId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        MarketingVO marketingVO = marketingQueryProvider.getById(MarketingGetByIdRequest.buildRequest(marketingId))
                .getContext()
                .getMarketingVO();
        if (Objects.isNull(marketingVO)){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        }

        //获取会员
        CustomerVO customer = commonUtil.getCustomer();
        PurchaseModifyGoodsMarketingRequest purchaseModifyGoodsMarketingRequest =
                new PurchaseModifyGoodsMarketingRequest();
        purchaseModifyGoodsMarketingRequest.setCustomer(KsBeanUtil.convert(customer, CustomerDTO.class));
        purchaseModifyGoodsMarketingRequest.setGoodsInfoId(goodsInfoId);
        purchaseModifyGoodsMarketingRequest.setMarketingId(marketingId);
        purchaseProvider.modifyGoodsMarketing(purchaseModifyGoodsMarketingRequest);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 获取采购单所有商品使用的营销
     *
     * @return
     */
    @Operation(summary = "获取采购单所有商品使用的营销")
    @RequestMapping(value = "/goodsMarketing", method = RequestMethod.GET)
    public BaseResponse<List<GoodsMarketingVO>> queryGoodsMarketingList() {
        PurchaseQueryGoodsMarketingListRequest purchaseQueryGoodsMarketingListRequest =
                new PurchaseQueryGoodsMarketingListRequest();
        purchaseQueryGoodsMarketingListRequest.setCustomerId(commonUtil.getOperatorId());
        PurchaseQueryGoodsMarketingListResponse purchaseQueryGoodsMarketingListResponse =
                purchaseQueryProvider.queryGoodsMarketingList(purchaseQueryGoodsMarketingListRequest).getContext();
        return BaseResponse.success(purchaseQueryGoodsMarketingListResponse.getGoodsMarketingList());
    }


    /**
     * 获取购物车信息
     */
    @Operation(summary = "获取购物车信息")
    @RequestMapping(value = "/cartInfo", method = RequestMethod.POST)
    public BaseResponse<GoodsCartListResponse> cartInfo(@RequestBody PurchaseListRequest request) {
        return purchaseProvider.getCartList(PurchaseInfoRequest.builder()
                .customer(commonUtil.getCustomer())
                .areaId(request.getAreaId())
                .address(request.getAddress())
                .inviteeId(commonUtil.getPurchaseInviteeId())
                .terminalSource(commonUtil.getTerminal()).build());
    }

    /**
     * 获取购物车信息 （开放访问）
     */
    @Operation(summary = "获取购物车信息 （开放访问）")
    @RequestMapping(value = "/front/cartInfo", method = RequestMethod.POST)
    public BaseResponse<GoodsCartListResponse> cartInfo(@RequestBody FrontCartInfoRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoList())) {
            return BaseResponse.SUCCESSFUL();
        }
        GoodsCartListResponse goodsCartListResponse = purchaseProvider.getCartList(PurchaseInfoRequest.builder()
                .goodsInfoList(request.getGoodsInfoList())
                .inviteeId(commonUtil.getPurchaseInviteeId())
                .terminalSource(commonUtil.getTerminal()).build()).getContext();
        if (Objects.nonNull(goodsCartListResponse) && CollectionUtils.isNotEmpty(goodsCartListResponse.getGoodsInfos())) {
            goodsCartListResponse.getGoodsInfos().forEach(goodsInfo->{
                for (CartGoodsInfoRequest goodsInfoVO : request.getGoodsInfoList()) {
                    if (goodsInfoVO.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())) {
                        goodsInfo.setBuyCount(goodsInfoVO.getBuyCount());
                        continue;
                    }
                }
            });
        }
        return BaseResponse.success(goodsCartListResponse);
    }

    /**
     * 购物车替换 用于在购物车切换规格 （开放访问）
     * @param request
     * @return
     */
    @Operation(summary = "购物车替换获取购物车信息")
    @RequestMapping(value = "/replacePurchase", method = RequestMethod.POST)
    public BaseResponse replacePurchase(@RequestBody PurchaseReplaceRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        if (StringUtils.isBlank(request.getCustomerId())
                || CollectionUtils.isEmpty(request.getGoodsInfos())
                || StringUtils.isBlank(request.getDeleteGoodsInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<String> goodsInfoIds = request.getGoodsInfos().stream().map(GoodsInfoDTO::getGoodsInfoId)
                .filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setInviteeId(commonUtil.getPurchaseInviteeId());
        request.setTerminalSource(commonUtil.getTerminal());
        return purchaseProvider.replacePurchase(request);
    }

    /**
     * @description 购物车商品默认选择
     * @Author qiyuanzhao
     * @Date 2022/5/26 16:02
     * @param
     * @return
     **/
    @Operation(summary = "购物车商品默认选择")
    @RequestMapping(value = "/goodsInfo/selection", method = RequestMethod.POST)
    public BaseResponse goodsInfoSelection(@RequestBody @Valid RedisRequest request){

        if (CollectionUtils.isEmpty(request.getValues())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        long currentTime = LocalDateTime.now().toInstant(ZoneOffset.ofHours(Constants.EIGHT)).toEpochMilli();

        try {
            request.getValues().forEach(value->redisTemplate.boundZSetOps(CacheKeyConstant.CUSTOMER_GOODSINFO_SELECTION_KEY.concat(request.getKey())).add(value, currentTime));
            //设置key过期时间，每次添加购物车都会刷新过期时间
            redisTemplate.boundZSetOps(CacheKeyConstant.CUSTOMER_GOODSINFO_SELECTION_KEY.concat(request.getKey())).expire(60 * 60 * 24, TimeUnit.SECONDS);
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }

        return BaseResponse.SUCCESSFUL();

    }

    /**
     * @description 购物车商品选择移除
     * @Author qiyuanzhao
     * @Date 2022/5/26 16:02
     * @param
     * @return
     **/
    @Operation(summary = "购物车商品选择移除")
    @RequestMapping(value = "/goodsInfo/selection", method = RequestMethod.DELETE)
    public BaseResponse goodsInfoSelectionDelete(@RequestBody @Valid RedisRequest request){

        if (CollectionUtils.isEmpty(request.getValues())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        try {
            request.getValues().forEach(value-> redisTemplate.boundZSetOps(CacheKeyConstant.CUSTOMER_GOODSINFO_SELECTION_KEY.concat(request.getKey())).remove(value));
            //设置key过期时间，每次更新购物车都会刷新过期时间
            redisTemplate.boundZSetOps(CacheKeyConstant.CUSTOMER_GOODSINFO_SELECTION_KEY.concat(request.getKey())).expire(60 * 60 * 24, TimeUnit.SECONDS);
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }

        return BaseResponse.SUCCESSFUL();

    }

    /**
     * @description 查询购物车商品默认选择的商品
     * @Author qiyuanzhao
     * @Date 2022/5/26 16:02
     * @param
     * @return
     **/
    @Operation(summary = "查询redis中数据")
    @RequestMapping(value = "/goodsInfo/selection/{key}", method = RequestMethod.GET)
    public BaseResponse<PurchaseGoodsInfoSelectionResponse> findGoodsInfoSelection(@PathVariable("key") String key){

        long startTime = LocalDateTime.now().minusYears(Constants.ONE).toInstant(ZoneOffset.ofHours(Constants.EIGHT)).toEpochMilli();
        long endTime = LocalDateTime.now().minusHours(Constants.NUM_24).toInstant(ZoneOffset.ofHours(Constants.EIGHT)).toEpochMilli();

        Set<Object> range;
        try {
            //移除当前key超时的元素
            redisTemplate.boundZSetOps(CacheKeyConstant.CUSTOMER_GOODSINFO_SELECTION_KEY.concat(key)).removeRangeByScore(startTime,endTime);
            //获取
            range = redisTemplate.boundZSetOps(CacheKeyConstant.CUSTOMER_GOODSINFO_SELECTION_KEY.concat(key)).range(Constants.ZERO, Constants.MINUS_ONE);
        } catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }

        return BaseResponse.success(PurchaseGoodsInfoSelectionResponse.builder().goodsInfoIds(range).build());
    }

    @Operation(summary = "获取购物车商品信息 - 方便凑单页使用")
    @RequestMapping(value = "/purchases/goodsInfo", method = RequestMethod.POST)
    public BaseResponse<PurchaseQueryResponse> purchasesGoodsInfo(@RequestBody @Valid PurchaseGoodsQueryRequest request){
        PurchaseQueryRequest queryRequest =
                PurchaseQueryRequest.builder()
                        .customerId(commonUtil.getOperatorId())
                        .goodsInfoIds(request.getGoodsInfoIds())
                        .inviteeId(request.getInviteeId())
                        .build();
        return purchaseQueryProvider.query(queryRequest);
    }

}
