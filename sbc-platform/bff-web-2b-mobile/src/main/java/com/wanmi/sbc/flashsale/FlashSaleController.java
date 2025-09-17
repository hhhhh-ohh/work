package com.wanmi.sbc.flashsale;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmitWithToken;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.flashsale.request.FlashSaleTradeCommitRequest;
import com.wanmi.sbc.flashsale.request.RushToBuyFlashSaleGoodsRequest;
import com.wanmi.sbc.flashsale.service.FlashSaleService;
import com.wanmi.sbc.goods.api.provider.flashsaleactivity.FlashSaleActivityQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalecate.FlashSaleCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsaleactivity.FlashSaleActivityListRequest;
import com.wanmi.sbc.goods.api.request.flashsalecate.FlashSaleCateListRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsPageRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.response.flashsaleactivity.FlashSaleActivityListResponse;
import com.wanmi.sbc.goods.api.response.flashsalecate.FlashSaleCateListResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsPageResponse;
import com.wanmi.sbc.goods.bean.vo.FlashSaleActivityVO;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.request.market.MarketingGoodsForXsiteRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingLevelType;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.api.request.trade.TradeQueryPurchaseInfoRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemGroupDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.api.response.trade.TradeConfirmResponse;
import com.wanmi.sbc.setting.api.provider.flashsalesetting.FlashSaleSettingQueryProvider;
import com.wanmi.sbc.setting.api.response.flashsalesetting.FlashSaleSettingResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Tag(name =  "秒杀首页API", description =  "FlashSaleController")
@RestController
@Validated
@RequestMapping(value = "/flashsale")
public class FlashSaleController {

    @Autowired
    private FlashSaleCateQueryProvider flashSaleCateQueryProvider;

    @Autowired
    private FlashSaleActivityQueryProvider flashSaleActivityQueryProvider;

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    public static final String COMPLETED = "已结束";

    public static final String BEGIN = "已开抢";

    public static final String SALE = "抢购中";

    public static final String ABOUT_TO_START = "即将开始";

    public static final String NEXT_DAY_NOTICE = "次日预告";

    @Autowired
    private FlashSaleService flashSaleService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private FlashSaleSettingQueryProvider flashSaleSettingQueryProvider;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private RedisUtil redisService;

    @Operation(summary = "查询场次信息")
    @GetMapping(value = "/sceneList")
    public BaseResponse<FlashSaleActivityListResponse> sceneList() {
        //当前时间前后24小时 有商品参与的场次
        FlashSaleActivityListRequest listReq = new FlashSaleActivityListRequest();
        listReq.setFullTimeBegin(LocalDateTime.now().minusDays(1));
        listReq.setFullTimeEnd(LocalDateTime.now().plusDays(1));
        List<FlashSaleActivityVO> activityVOList = flashSaleActivityQueryProvider.list(listReq).getContext()
                .getFlashSaleActivityVOList();
        if (CollectionUtils.isEmpty(activityVOList)) {
            return BaseResponse.success(FlashSaleActivityListResponse.builder().flashSaleActivityVOList(activityVOList).build());
        }
        //获取离当前时间最近的时间 为默认选中场次
        LocalDateTime nowTime = LocalDateTime.parse(DateUtil.nowHourTime(), DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_6));
        //默认第一个为最近
        long minDiff = Duration.between(nowTime, activityVOList.get(0).getActivityFullTime()).toMillis();
        minDiff = Math.abs(minDiff);
        String activityDate = activityVOList.get(0).getActivityDate();
        String activityTime = activityVOList.get(0).getActivityTime();
        for (FlashSaleActivityVO activity : activityVOList) {
            activity.setStatus(getActivityStatus(activity.getActivityDate(), activity.getActivityFullTime()));
            long timeDiff = Duration.between(nowTime, activity.getActivityFullTime()).toMillis();
            timeDiff = Math.abs(timeDiff);
            if (timeDiff < minDiff) {
                minDiff = timeDiff;
                activityDate = activity.getActivityDate();
                activityTime = activity.getActivityTime();
            }
        }

        return BaseResponse.success(FlashSaleActivityListResponse.builder().flashSaleActivityVOList(activityVOList)
                .recentDate(activityDate).recentTime(activityTime).build());
    }

    private String getActivityStatus(String date, LocalDateTime fullTime) {
        if (date.equals(DateUtil.nowDate())) {
            if (fullTime.isAfter(LocalDateTime.now())) {
                return ABOUT_TO_START;
            }
            if ((fullTime.isEqual(LocalDateTime.now()) ||
                    fullTime.isBefore(LocalDateTime.now())) &&
                    (fullTime.isAfter(LocalDateTime.now().minusHours(Constants.FLASH_SALE_LAST_HOUR)) ||
                            fullTime.isEqual(LocalDateTime.now().minusHours(Constants.FLASH_SALE_LAST_HOUR)))) {
                //距离当前时间不足一小时的场次即为*正在抢购
                if (fullTime.isAfter((LocalDateTime.now().minusHours(1)))) {
                    return SALE;
                }
                return BEGIN;
            }
        } else if (date.equals(DateUtil.tomorrowDate())) {
            return NEXT_DAY_NOTICE;
        }
        return COMPLETED;
    }

    @Operation(summary = "查询秒杀分类信息")
    @GetMapping(value = "/cateList")
    public BaseResponse<FlashSaleCateListResponse> cateList() {
        FlashSaleCateListRequest listReq = new FlashSaleCateListRequest();
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("sort", "asc");
        return flashSaleCateQueryProvider.list(listReq);
    }

    /**
     * 当前场次有库存的秒杀商品
     *
     * @return
     */
    @Operation(summary = "查询秒杀商品")
    @PostMapping("/goodsList")
    public BaseResponse<FlashSaleGoodsPageResponse> goodsList(@RequestBody @Valid FlashSaleGoodsPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        this.fillSort(pageReq);
        System.out.println("activityDate" + pageReq.getActivityDate() + "activityTime" + pageReq.getActivityTime() + "cateId" + pageReq.getCateId());
        pageReq.setVendibility(Constants.yes);
        return flashSaleGoodsQueryProvider.page(pageReq);
    }

    /**
     * 当前场次有库存的秒杀商品
     *
     * @return
     */
    @Operation(summary = "查询秒杀商品")
    @PostMapping("/xsite/goodsList")
    public BaseResponse<FlashSaleGoodsPageResponse> goodsListForXsite(@RequestBody @Valid FlashSaleGoodsPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        this.fillSort(pageReq);
        log.info("FlashSaleController => goodsListForXsite  params is : activityDate:{},activityTime:{},cateId :{} ",
                pageReq.getActivityDate(), pageReq.getActivityTime(), pageReq.getCateId());

        pageReq.setVendibility(Constants.yes);
        // 判断返回值是否为空
        FlashSaleGoodsPageResponse flashSaleGoodsPageRes = flashSaleGoodsQueryProvider.page(pageReq).getContext();
        if (Objects.nonNull(flashSaleGoodsPageRes)
                && CollectionUtils.isNotEmpty(flashSaleGoodsPageRes.getFlashSaleGoodsVOPage().getContent())) {

            List<FlashSaleGoodsVO> flashSaleGoodsVOS = flashSaleGoodsPageRes.getFlashSaleGoodsVOPage().getContent();
            List<String> ids = flashSaleGoodsVOS.stream().map(FlashSaleGoodsVO::getGoodsInfoId).collect(Collectors.toList());

            MarketingLevelType marketingLevelType = MarketingLevelType.FLASH_SALE;
            if(Objects.nonNull(pageReq.getType())
                    && pageReq.getType().equals(Constants.ONE)){
                marketingLevelType = MarketingLevelType.FLASH_PROMOTION;
            }

            MarketingGoodsForXsiteRequest marketingRequest = MarketingGoodsForXsiteRequest.builder()
                    .marketingLevelType(marketingLevelType)
                    .goodsInfoIds(ids).build();
            List<String> goodsInfoIds = marketingQueryProvider.queryForXsite(marketingRequest).getContext().getGoodsInfoIds();
            flashSaleGoodsVOS = flashSaleGoodsVOS.stream().filter(vo -> goodsInfoIds.contains(vo.getGoodsInfoId())).collect(Collectors.toList());

            // 返回值塞回
            flashSaleGoodsPageRes.getFlashSaleGoodsVOPage().setContent(flashSaleGoodsVOS);
        }
        return BaseResponse.success(flashSaleGoodsPageRes);
    }

    /**
     * @Author xufeng
     * @Description 立刻抢购秒杀商品提交订单
     * @Date 9:42 2021/7/8
     * @Param [request]
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @Operation(summary = "立刻抢购秒杀商品提交订单")
    @RequestMapping(value = "/flashSaleGoodsCommit", method = RequestMethod.POST)
    @MultiSubmitWithToken
    public BaseResponse<List<TradeCommitResultVO>> flashSaleGoodsCommit(@RequestBody @Valid FlashSaleTradeCommitRequest request) {
        TradeCommitRequest tradeCommitRequest = flashSaleService.buildTradeCommitRequest(request);
        //第三步，提交订单
        RLock rLock = redissonClient.getFairLock(commonUtil.getOperatorId());
        rLock.lock();
        List<TradeCommitResultVO> successResults;
        try {
            successResults = tradeProvider.commitFlashSale(tradeCommitRequest).getContext().getTradeCommitResults();
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.success(successResults);
    }

    /**
     * @Author lvzhenwei
     * @Description 获取秒杀活动详情
     * @Date 10:58 2019/7/1
     * @Param [request]
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO>
     **/
    @Operation(summary = "获取秒杀活动详情")
    @PostMapping("/getFlashSaleInfo")
    public BaseResponse<FlashSaleGoodsVO> getFlashSaleInfo(@RequestBody @Valid RushToBuyFlashSaleGoodsRequest request) {
        return BaseResponse.success(flashSaleService.getFlashSaleGoodsInfo(request));
    }

    /**
     * 获取秒杀轮播海报
     *
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "获取秒杀轮播海报")
    @GetMapping("/get-setting")
    public BaseResponse<FlashSaleSettingResponse> getSetting() {
        return flashSaleSettingQueryProvider.get();
    }

    /**
     * 查询结果排序
     * 0:销量倒序
     * 1:好评数倒序
     * 2:评论率倒序
     * 3:排序号倒序
     */
    private void fillSort(FlashSaleGoodsPageRequest pageReq) {
        if (Objects.nonNull(pageReq.getSortFlag())) {
            switch (pageReq.getSortFlag()) {
                case 0:
                    pageReq.putSort("goods.allSalesNum", SortType.DESC.toValue());
                    pageReq.putSort("createTime", SortType.DESC.toValue());
                    break;
                case 1:
                    pageReq.putSort("goods.goodsFavorableCommentNum", SortType.DESC.toValue());
                    pageReq.putSort("createTime", SortType.DESC.toValue());
                    break;
                case 2:
                    pageReq.putSort("goods.feedbackRate", SortType.DESC.toValue());
                    pageReq.putSort("createTime", SortType.DESC.toValue());
                    break;
                case 3:
                    pageReq.putSort("goods.sortNo", SortType.DESC.toValue());
                    pageReq.putSort("createTime", SortType.DESC.toValue());
                    break;
                default:
                    pageReq.putSort("salesVolume", SortType.DESC.toValue());
                    pageReq.putSort("createTime", SortType.DESC.toValue());
                    pageReq.putSort("id", "desc");
                    break;
            }
        } else {
            pageReq.putSort("salesVolume", SortType.DESC.toValue());
            pageReq.putSort("createTime", SortType.DESC.toValue());
            pageReq.putSort("id", "desc");
        }
    }

    /**
     * @Author xufeng
     * @Description 秒杀详情页获取提交订单数据
     * @Date 16:42 2021/7/13
     * @Param [request]
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @Operation(summary = "立刻抢购秒杀商品")
    @PostMapping("/rushToBuyFlashSaleGoods")
    @MultiSubmitWithToken
    public BaseResponse<TradeConfirmResponse> rushToBuyFlashSaleGoods(@RequestBody @Valid RushToBuyFlashSaleGoodsRequest request) {
        String customerId = commonUtil.getOperatorId();
        request.setCustomerId(customerId);
        request.setTerminalToken(commonUtil.getTerminalToken());
        TradeItemSnapshotVO tradeItemSnapshotVO = flashSaleService.assembleSnapshot(request).getContext().getTradeItemSnapshotVO();
        TradeConfirmResponse confirmResponse = new TradeConfirmResponse();

        List<TradeItemGroupVO> tradeItemGroups = tradeItemSnapshotVO.getItemGroups();
        List<TradeConfirmItemVO> items = new ArrayList<>();
        Map<Long, StoreVO> storeMap = storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest
                (tradeItemGroups.stream().map(g -> g
                        .getSupplier().getStoreId())
                        .collect(Collectors.toList()))).getContext().getStoreVOList().stream().collect(Collectors
                .toMap(StoreVO::getStoreId, Function.identity()));
        TradeItemGroupVO tradeItemGroupVO = tradeItemGroups.parallelStream().findFirst().orElseGet(null);
        OrderTagVO orderTag =  Objects.isNull(tradeItemGroupVO) ? new OrderTagVO() : tradeItemGroupVO.getOrderTag();
        List<TradeItemVO> allTradeItemList = new ArrayList<>();

        //判断剩余库存是否小于起购数
        String flashSaleStockKey =
                RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY + request.getGoodsInfoId();
        int remainStock = Integer.parseInt(redisService.getString(flashSaleStockKey));
        if (remainStock < request.getFlashSaleGoodsNum()){
            //抛出该秒抢购商品库存不足异常信息
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080179);
        }

        tradeItemGroups.forEach(
                g -> {
                    g.getSupplier().setFreightTemplateType(storeMap.get(g.getSupplier().getStoreId())
                            .getFreightTemplateType());
                    List<TradeItemVO> tradeItems = g.getTradeItems();
                    allTradeItemList.addAll(tradeItems);
                    //抢购商品价格回设
                    if (StringUtils.isNotBlank(g.getSnapshotType()) && (g.getSnapshotType().equals(Constants.FLASH_SALE_GOODS_ORDER_TYPE))) {
                        tradeItems.forEach(tradeItemVO -> {
                            g.getTradeItems().forEach(tradeItem -> {
                                if (tradeItem.getSkuId().equals(tradeItemVO.getSkuId())) {
                                    tradeItemVO.setPrice(tradeItem.getPrice());
                                }
                            });
                        });
                        if (CollectionUtils.isNotEmpty(tradeItems)) {
                            Integer postage =
                                    flashSaleGoodsQueryProvider.getById(new FlashSaleGoodsByIdRequest(tradeItems.get(0).getFlashSaleGoodsId())).getContext().getFlashSaleGoodsVO().getPostage();
                            confirmResponse.setFlashFreeDelivery(postage.equals(NumberUtils.INTEGER_ONE) ?
                                    Boolean.TRUE : Boolean.FALSE);
                        }

                    }
                    g.setTradeItems(tradeItems);
                    items.add(tradeQueryProvider.queryPurchaseInfo(TradeQueryPurchaseInfoRequest.builder()
                            .tradeItemGroupDTO(KsBeanUtil.convert(g, TradeItemGroupDTO.class))
                            .tradeItemList(Lists.newArrayList()).build()).getContext().getTradeConfirmItemVO());
                }
        );
        confirmResponse.setOrderTagVO(orderTag);
        confirmResponse.setTradeConfirmItems(items);
        confirmResponse.setPurchaseBuy(tradeItemSnapshotVO.getPurchaseBuy());
        //处理可用礼品卡数据
        confirmResponse.setGiftCardNum(flashSaleService.dealGiftCard(allTradeItemList, customerId));
        return BaseResponse.success(confirmResponse);
    }

    /**
     * 查询限时购列表
     *
     * @return
     */
    @Operation(summary = "查询限时购列表")
    @PostMapping("/promotion/goodsList")
    public BaseResponse<FlashSaleGoodsPageResponse> promotionGoodsList(@RequestBody @Valid FlashSaleGoodsPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        if (Objects.isNull(pageReq.getPromotionStatus())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (Constants.ZERO!=pageReq.getPromotionStatus() && Constants.ONE!=pageReq.getPromotionStatus()){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (Constants.ZERO==pageReq.getPromotionStatus()){
            pageReq.putSort("endTime", SortType.ASC.toValue());
        }else {
            pageReq.putSort("startTime", SortType.ASC.toValue());
        }
        pageReq.setType(Constants.ONE);
        pageReq.setVendibility(Constants.yes);
        return flashSaleGoodsQueryProvider.page(pageReq);
    }

    /**
     * 获取限时购轮播海报
     *
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "获取限时购轮播海报")
    @GetMapping("/get-promotion-setting")
    public BaseResponse<FlashSaleSettingResponse> getPromotion() {
        return flashSaleSettingQueryProvider.getPromotion();
    }

}
