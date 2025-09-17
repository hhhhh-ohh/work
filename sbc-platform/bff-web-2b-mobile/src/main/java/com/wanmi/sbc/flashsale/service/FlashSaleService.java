package com.wanmi.sbc.flashsale.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressByIdRequest;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByCompanyIdRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressByIdResponse;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByCompanyIdResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.flashsale.request.FlashSaleTradeCommitRequest;
import com.wanmi.sbc.flashsale.request.RushToBuyFlashSaleGoodsRequest;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingLevelPluginProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardTradeRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingLevelGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardTradeResponse;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.order.api.provider.trade.TradeItemQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.VerifyQueryProvider;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.trade.TradeGetGoodsResponse;
import com.wanmi.sbc.order.api.response.trade.TradeItemSnapshotByCustomerIdResponse;
import com.wanmi.sbc.order.bean.dto.TradeGoodsInfoPageDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.SupplierVO;
import com.wanmi.sbc.order.bean.vo.TradeItemGroupVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.request.TradeItemConfirmRequest;
import com.wanmi.sbc.order.request.TradeItemRequest;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressVerifyRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.Duration.between;

/**
 * @ClassName FlashSaleService
 * @Description 秒杀活动service
 * @Author lvzhenwei
 * @Date 2019/6/15 9:48
 **/
@Slf4j
@Service
public class FlashSaleService {

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired
    private MarketingLevelPluginProvider marketingLevelPluginProvider;

    @Autowired
    private TradeItemQueryProvider tradeItemQueryProvider;

    @Autowired
    private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private VerifyQueryProvider verifyQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    /**
     * 获取秒杀活动详情
     *
     * @param request
     * @return
     */
    public FlashSaleGoodsVO getFlashSaleGoodsInfo(RushToBuyFlashSaleGoodsRequest request) {
        return flashSaleGoodsQueryProvider.getById(FlashSaleGoodsByIdRequest.builder()
                .id(request.getFlashSaleGoodsId())
                .build())
                .getContext().getFlashSaleGoodsVO();
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 从Redis缓存中某个秒杀活动的商品信息
     * @Date 16:32 2019/6/15
     * @Param []
     **/
    public FlashSaleGoodsVO getFlashSaleGoodsInfoForRedis(RushToBuyFlashSaleGoodsRequest request) {
        String flashSaleGoodsInfoKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_KEY + request.getGoodsInfoId();
        //从redis缓存中获取对应的秒杀抢购商品信息
        FlashSaleGoodsVO flashSaleGoodsVO = redisService.getObj(flashSaleGoodsInfoKey, FlashSaleGoodsVO.class);
        if (Objects.isNull(flashSaleGoodsVO)) {
            RLock rLock = redissonClient.getFairLock(request.getGoodsInfoId());
            rLock.lock();
            try {
                //如果redis缓存中不存在秒杀抢购信息从数据获取，重新放入缓存中
                flashSaleGoodsVO = flashSaleGoodsQueryProvider.getById(FlashSaleGoodsByIdRequest.builder()
                        .id(request.getFlashSaleGoodsId())
                        .build())
                        .getContext().getFlashSaleGoodsVO();
                //1、判断抢购抢购商品是否存在
                if (Objects.isNull(flashSaleGoodsVO) || DeleteFlag.YES == flashSaleGoodsVO.getDelFlag()) {
                    //抛出不存在该秒杀活动的抢购商品异常信息
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080176);
                }
                LocalDateTime nowTime = LocalDateTime.now();
                Duration duration;
                // 限时购逻辑
                if (Objects.nonNull(flashSaleGoodsVO.getType()) && Constants.ONE == flashSaleGoodsVO.getType()){
                    //1、判断抢购抢购商品是否暂停
                    if (Constants.ONE == flashSaleGoodsVO.getStatus()) {
                        //抛出不存在该秒杀活动的抢购商品异常信息
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080176);
                    }
                    //2、判断该商品对应的场次是否存在并且该商品对应的抢购活动是否开始
                    if (nowTime.isBefore(flashSaleGoodsVO.getStartTime())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080172);
                    }
                    //3、判断该商品对应的场次是否存在并且该商品对应的抢购活动是否已结束
                    if (nowTime.isAfter(flashSaleGoodsVO.getEndTime())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080177);
                    }
                    duration = between(nowTime,  flashSaleGoodsVO.getEndTime());
                } else {
                    //2、判断该商品对应的场次是否存在并且该商品对应的抢购活动是否开始
                    if (nowTime.isBefore(flashSaleGoodsVO.getActivityFullTime())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080172);
                    }
                    //3、判断该商品对应的场次是否存在并且该商品对应的抢购活动是否已结束
                    if (nowTime.isAfter(flashSaleGoodsVO.getActivityFullTime().plusHours(Constants.FLASH_SALE_LAST_HOUR))) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080177);
                    }
                    duration = between(nowTime,  flashSaleGoodsVO.getActivityFullTime().plusHours(Constants.FLASH_SALE_LAST_HOUR));
                }
                long existSeconds = duration.getSeconds();
                if (existSeconds<=0){
                    existSeconds = Constants.FIVE;
                }
                redisService.setObj(flashSaleGoodsInfoKey, flashSaleGoodsVO, existSeconds);
                // 预热库存进redis
                String flashSaleStockKey =
                        RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY + flashSaleGoodsVO.getGoodsInfoId();
                // 库存key不存在则添加，否则不添加，防止中途改库存
                if (!redisService.hasKey(flashSaleStockKey)){
                    redisService.setString(flashSaleStockKey,
                            String.valueOf(flashSaleGoodsVO.getStock()-flashSaleGoodsVO.getSalesVolume()),
                            existSeconds);
                }
            }finally {
                rLock.unlock();
            }
        }
        return flashSaleGoodsVO;
    }

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @Author lvzhenwei
     * @Description 生成抢购订单快照
     * @Date 15:02 2019/6/17
     * @Param [request]
     **/
    public BaseResponse<TradeItemSnapshotByCustomerIdResponse> assembleSnapshot(RushToBuyFlashSaleGoodsRequest request) {
        //获取抢购商品信息
        FlashSaleGoodsVO flashSaleGoodsVO = getFlashSaleGoodsInfoForRedis(request);
        //设置抢购订单快照数据信息
        TradeItemRequest tradeItemRequest = new TradeItemRequest();
        tradeItemRequest.setNum(request.getFlashSaleGoodsNum());
        tradeItemRequest.setSkuId(flashSaleGoodsVO.getGoodsInfoId());
        tradeItemRequest.setPrice(flashSaleGoodsVO.getPrice());
        tradeItemRequest.setIsFlashSaleGoods(Boolean.TRUE);
        tradeItemRequest.setFlashSaleGoodsId(flashSaleGoodsVO.getId());

        List<TradeItemRequest> tradeItemConfirmRequests = new ArrayList<>();
        tradeItemConfirmRequests.add(tradeItemRequest);

        List<TradeMarketingDTO> tradeMarketingList = new ArrayList<>();
        TradeItemConfirmRequest confirmRequest = new TradeItemConfirmRequest();
        confirmRequest.setTradeItems(tradeItemConfirmRequests);
        confirmRequest.setTradeMarketingList(tradeMarketingList);
        confirmRequest.setForceConfirm(false);
        String customerId = request.getCustomerId();
        List<TradeItemDTO> tradeItems = confirmRequest.getTradeItems().stream().map(
                o -> TradeItemDTO.builder().skuId(o.getSkuId()).num(o.getNum()).price(o.getPrice())
                        .isFlashSaleGoods(o.getIsFlashSaleGoods()).flashSaleGoodsId(o.getFlashSaleGoodsId()).build()
        ).collect(Collectors.toList());
        List<String> skuIds =
                confirmRequest.getTradeItems().stream().map(TradeItemRequest::getSkuId).collect(Collectors.toList());
        //验证用户
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();
        GoodsInfoResponse response = getGoodsResponse(skuIds, customer);
        //商品验证
        List<TradeItemVO> tradeItemVOList =verifyQueryProvider.verifyGoods(new VerifyGoodsRequest(tradeItems, Collections.emptyList(),
                KsBeanUtil.convert(response, TradeGoodsInfoPageDTO.class), flashSaleGoodsVO.getStoreId(), Boolean.TRUE, null)).getContext().getTradeItems();
        tradeItems = KsBeanUtil.convertList(tradeItemVOList, TradeItemDTO.class);
        return tradeItemQueryProvider.assembleSnapshot(TradeItemSnapshotRequest.builder().customerId(customerId).tradeItems
                (tradeItems)
                .tradeMarketingList(confirmRequest.getTradeMarketingList())
                .skuList(KsBeanUtil.convertList(response.getGoodsInfos(), GoodsInfoDTO.class))
                .snapshotType(Constants.FLASH_SALE_GOODS_ORDER_TYPE)
                .terminalToken(request.getTerminalToken()).build());
    }

    /**
     * 获取订单商品详情,包含区间价，会员级别价
     */
    private GoodsInfoResponse getGoodsResponse(List<String> skuIds, CustomerVO customer) {
        TradeGetGoodsResponse response =
                tradeQueryProvider.getGoods(TradeGetGoodsRequest.builder().skuIds(skuIds).build()).getContext();
        //计算区间价
        GoodsIntervalPriceByCustomerIdResponse priceResponse = goodsIntervalPriceService.getGoodsIntervalPriceVOList
                (response.getGoodsInfos(), customer.getCustomerId());
        response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
        response.setGoodsInfos(priceResponse.getGoodsInfoVOList());
        //获取客户的等级
        if (StringUtils.isNotBlank(customer.getCustomerId())) {
            //计算会员价
            response.setGoodsInfos(
                    marketingLevelPluginProvider.goodsListFilter(MarketingLevelGoodsListFilterRequest.builder()
                            .goodsInfos(KsBeanUtil.convert(response.getGoodsInfos(), GoodsInfoDTO.class))
                            .customerId(customer.getCustomerId()).build())
                            .getContext().getGoodsInfoVOList());
        }
        return GoodsInfoResponse.builder().goodsInfos(response.getGoodsInfos())
                .goodses(response.getGoodses())
                .goodsIntervalPrices(response.getGoodsIntervalPrices())
                .build();
    }

    /**
     * 组装秒杀入参数据
     */
    public TradeCommitRequest buildTradeCommitRequest(@RequestBody @Valid FlashSaleTradeCommitRequest request) {
        Operator operator = commonUtil.getOperator();
        request.setCustomerId(operator.getUserId());
        //第一步，秒杀活动校验
        FlashSaleGoodsVO flashSaleGoodsVO = verifyFlashSale(request);
        //第二步，组装订单入参
        TradeItemRequest tradeItemRequest = new TradeItemRequest();
        tradeItemRequest.setNum(request.getFlashSaleGoodsNum());
        tradeItemRequest.setSkuId(flashSaleGoodsVO.getGoodsInfoId());
        tradeItemRequest.setPrice(flashSaleGoodsVO.getPrice());
        tradeItemRequest.setIsFlashSaleGoods(Boolean.TRUE);
        tradeItemRequest.setFlashSaleGoodsId(flashSaleGoodsVO.getId());

        List<TradeItemRequest> tradeItemConfirmRequests = new ArrayList<>();
        tradeItemConfirmRequests.add(tradeItemRequest);

        List<TradeItemVO> tradeItems = tradeItemConfirmRequests.stream().map(
                o -> TradeItemVO.builder().skuId(o.getSkuId()).num(o.getNum()).price(o.getPrice())
                        .isFlashSaleGoods(o.getIsFlashSaleGoods()).flashSaleGoodsId(o.getFlashSaleGoodsId()).build()
        ).collect(Collectors.toList());
        //获取商品所属商家，店铺信息
        StoreVO store =
                storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder().storeId(flashSaleGoodsVO.getStoreId())
                        .build())
                        .getContext().getStoreVO();
        SupplierVO supplier = SupplierVO.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .isSelf(store.getCompanyType() == BoolFlag.NO)
                .supplierCode(store.getCompanyInfo().getCompanyCode())
                .supplierId(store.getCompanyInfo().getCompanyInfoId())
                .supplierName(store.getCompanyInfo().getSupplierName())
                .freightTemplateType(store.getFreightTemplateType())
                .build();
        TradeItemGroupVO tradeItemGroupVO = new TradeItemGroupVO();
        tradeItemGroupVO.setTradeItems(tradeItems);
        tradeItemGroupVO.setSupplier(supplier);
        tradeItemGroupVO.setTradeMarketingList(new ArrayList<>());
        tradeItemGroupVO.setSnapshotType(Constants.FLASH_SALE_GOODS_ORDER_TYPE);
        List<TradeItemGroupVO> itemGroups = new ArrayList<>();
        itemGroups.add(tradeItemGroupVO);
        TradeCommitRequest tradeCommitRequest = new TradeCommitRequest();
        tradeCommitRequest.setTradeItemGroups(itemGroups);
        tradeCommitRequest.setOperator(operator);
        tradeCommitRequest.setTerminalToken(operator.getTerminalToken());
        //是否开启第三方平台
        boolean isOpen = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
        tradeCommitRequest.setIsOpen(isOpen);
        tradeCommitRequest.setIsFlashSaleGoods(true);
        tradeCommitRequest.setConsigneeId(request.getConsigneeId());
        CustomerDeliveryAddressRequest addressRequest = new CustomerDeliveryAddressRequest();
        addressRequest.setCustomerId(operator.getUserId());
        addressRequest.setDeliveryAddressId(request.getConsigneeId());
        CustomerDeliveryAddressResponse address = customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(addressRequest).getContext();
        if (Objects.nonNull(address)) {
            PlatformAddressListRequest platformAddressListRequest =
                    PlatformAddressListRequest.builder()
                            .addrIdList(
                                    Lists.newArrayList(
                                            String.valueOf(address.getProvinceId()),
                                            String.valueOf(address.getCityId()),
                                            String.valueOf(address.getAreaId()),
                                            String.valueOf(address.getStreetId())))
                            .delFlag(DeleteFlag.NO)
                            .build();
            List<PlatformAddressVO> platformAddressVOList =
                    platformAddressQueryProvider
                            .list(platformAddressListRequest)
                            .getContext()
                            .getPlatformAddressVOList();
            String consigneeAddress = "";
            PlatformAddressVO addressVO = platformAddressVOList.stream()
                    .filter(a -> StringUtils.isNotBlank(a.getAddrName()))
                    .findFirst().orElse(null);
            // 考虑收货详情地址是否存在省份名称，如果不含就加上
            if(Objects.nonNull(addressVO) && !address.getDeliveryAddress().contains(addressVO.getAddrName())){
                consigneeAddress = platformAddressVOList.parallelStream()
                        .map(PlatformAddressVO::getAddrName)
                        .collect(Collectors.joining());
            }
            consigneeAddress = consigneeAddress.concat(address.getDeliveryAddress());
            //拼接收货地址楼层号
            if (Objects.nonNull(addressVO) && StringUtils.isNotBlank(address.getHouseNum())
                    && !consigneeAddress.contains(address.getHouseNum())) {
                consigneeAddress = consigneeAddress.concat(address.getHouseNum());
            }
            tradeCommitRequest.setConsigneeAddress(consigneeAddress);
        } else {
            tradeCommitRequest.setConsigneeAddress(request.getConsigneeAddress());
        }
        tradeCommitRequest.setStoreCommitInfoList(request.getStoreCommitInfoList());
        tradeCommitRequest.setCustomerCardNo(request.getCustomerCardNo());
        tradeCommitRequest.setCustomerCardName(request.getCustomerCardName());
        tradeCommitRequest.setOrderSource(request.getOrderSource());
        tradeCommitRequest.setSceneGroup(request.getSceneGroup());
        tradeCommitRequest.setIsChannelsFlag(request.getIsChannelsFlag());
        tradeCommitRequest.setGiftCardTradeCommitVOList(request.getGiftCardTradeCommitVOList());
        tradeCommitRequest.setPickUpInfos(request.getPickUpInfos());
        return tradeCommitRequest;
    }

    /**
     * 秒杀订单提交前验证
     */
    public FlashSaleGoodsVO verifyFlashSale(@RequestBody @Valid FlashSaleTradeCommitRequest request) {
        //抢购秒杀商品对应限制条件判断
        Integer rushToBuyNum = request.getFlashSaleGoodsNum();
        //从redis缓存中获取对应的秒杀抢购商品信息
        String flashSaleGoodsInfoKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_KEY + request.getGoodsInfoId();
        //从redis缓存中获取对应的秒杀抢购商品信息
        FlashSaleGoodsVO flashSaleGoodsVO = redisService.getObj(flashSaleGoodsInfoKey, FlashSaleGoodsVO.class);
        //1、查询商品信息是否存在
        GoodsInfoByIdResponse response = goodsInfoQueryProvider.getById(
                GoodsInfoByIdRequest.builder().storeId(flashSaleGoodsVO.getStoreId()).goodsInfoId(flashSaleGoodsVO.getGoodsInfoId()).build()).getContext();
        if (Objects.isNull(response)) {
            //抛出不存在该秒杀活动的抢购商品异常信息
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080176);
        }
        //2、判断店铺是否关店或者账号禁用
        StoreByIdResponse storeByIdResponse = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(flashSaleGoodsVO.getStoreId()).build()).getContext();
        EmployeeByCompanyIdResponse employeeByCompanyIdResponse = employeeQueryProvider.getByCompanyId(EmployeeByCompanyIdRequest.builder()
                .companyInfoId(storeByIdResponse.getStoreVO().getCompanyInfo().getCompanyInfoId()).build()).getContext();
        if (storeByIdResponse.getStoreVO().getStoreState() == StoreState.CLOSED || employeeByCompanyIdResponse.getAccountState() == AccountState.DISABLE) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080178);
        }
        //3、校验传入活动id是否正确
        if (!flashSaleGoodsVO.getId().equals(request.getFlashSaleGoodsId())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080176);
        }
        //4、判断抢购抢购商品是否存在
        if (Objects.isNull(flashSaleGoodsVO) || DeleteFlag.YES == flashSaleGoodsVO.getDelFlag()) {
            //抛出不存在该秒杀活动的抢购商品异常信息
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080176);
        }
        if (Objects.isNull(flashSaleGoodsVO.getType()) || Constants.ZERO==flashSaleGoodsVO.getType()){
            //5、判断该商品对应的场次是否存在并且该商品对应的抢购活动是否
            if (LocalDateTime.now().isBefore(flashSaleGoodsVO.getActivityFullTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080172);
            }
            //6、判断该商品对应的场次是否存在并且该商品对应的抢购活动是否已结束
            if (LocalDateTime.now().isAfter(flashSaleGoodsVO.getActivityFullTime().plusHours(Constants.FLASH_SALE_LAST_HOUR))) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080177);
            }
        }else {
            //5、判断该商品对应的场次是否存在并且该商品对应的抢购活动是否
            if (LocalDateTime.now().isBefore(flashSaleGoodsVO.getStartTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080172);
            }
            //6、判断该商品对应的场次是否存在并且该商品对应的抢购活动是否已结束
            if (LocalDateTime.now().isAfter(flashSaleGoodsVO.getEndTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080177);
            }
        }
        //7、抢购商品数量是否大于该上限数量
        if (rushToBuyNum > flashSaleGoodsVO.getStock()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080175);
        }
        //8、判断抢购商品是否小于起购数
        if (rushToBuyNum < flashSaleGoodsVO.getMinNum()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080174);
        }
        //9、判断剩余库存是否小于起购数
        String flashSaleStockKey =
                RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY + request.getGoodsInfoId();
        int remainStock = Integer.parseInt(redisService.getString(flashSaleStockKey));
        if (remainStock < flashSaleGoodsVO.getMinNum()){
            //抛出该秒杀活动的抢购商品库存不足异常信息
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080179);
        }
        //10、判断该用户是否已经超过该商品抢购限制数量
        // 商品秒杀抢购已抢购商品数量对应key前缀
        String havePanicBuyingKey = RedisKeyConstant.FLASH_SALE_GOODS_HAVE_BUYING_KEY + request.getCustomerId() + flashSaleGoodsVO.getId();
        if (Objects.nonNull(redisService.getString(havePanicBuyingKey))) {
            rushToBuyNum = rushToBuyNum + Integer.parseInt(redisService.getString(havePanicBuyingKey));
        }
        if (rushToBuyNum > flashSaleGoodsVO.getMaxNum()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080173);
        }
        //11、校验地址
        this.checkAddress(request, response.getGoodsType());
        //12、校验是否需要完善地址信息
        if (GoodsType.REAL_GOODS.toValue() == response.getGoodsType()) {
            CustomerDeliveryAddressByIdResponse address =
                    customerDeliveryAddressQueryProvider.getById(new CustomerDeliveryAddressByIdRequest(request.getConsigneeId())).getContext();
            if (address != null) {
                if (!request.getCustomerId().equals(address.getCustomerId())) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050149);
                }
                PlatformAddressVerifyRequest platformAddressVerifyRequest = new PlatformAddressVerifyRequest();
                if (Objects.nonNull(address.getProvinceId())) {
                    platformAddressVerifyRequest.setProvinceId(String.valueOf(address.getProvinceId()));
                }
                if (Objects.nonNull(address.getCityId())) {
                    platformAddressVerifyRequest.setCityId(String.valueOf(address.getCityId()));
                }
                if (Objects.nonNull(address.getAreaId())) {
                    platformAddressVerifyRequest.setAreaId(String.valueOf(address.getAreaId()));
                }
                if (Objects.nonNull(address.getStreetId())) {
                    platformAddressVerifyRequest.setStreetId(String.valueOf(address.getStreetId()));
                }
                if (Boolean.TRUE.equals(platformAddressQueryProvider.verifyAddress(platformAddressVerifyRequest).getContext())) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030129);
                }
            }
        }
        return flashSaleGoodsVO;
    }

    public void checkAddress(FlashSaleTradeCommitRequest request, Integer goodsType) {
        if (GoodsType.REAL_GOODS.toValue() == goodsType) {
            //实物商品校验地址
            if (StringUtils.isBlank(request.getConsigneeId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (StringUtils.isBlank(request.getConsigneeAddress())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } else {
            //虚拟订单或者卡券订单取地址
            CustomerDeliveryAddressRequest addressRequest = new CustomerDeliveryAddressRequest();
            addressRequest.setCustomerId(commonUtil.getOperatorId());
            CustomerDeliveryAddressResponse addressResponse = customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(addressRequest).getContext();
            //如果有，就设置，没有则空
            if (Objects.nonNull(addressResponse)) {
                request.setConsigneeId(addressResponse.getDeliveryAddressId());
                PlatformAddressListRequest addressListRequest = PlatformAddressListRequest.builder()
                        .addrIdList(Lists.newArrayList(String.valueOf(addressResponse.getProvinceId()),
                                String.valueOf(addressResponse.getCityId()),
                                String.valueOf(addressResponse.getAreaId()),
                                String.valueOf(addressResponse.getStreetId())))
                        .delFlag(DeleteFlag.NO)
                        .build();
                List<PlatformAddressVO> platformAddressVOList = platformAddressQueryProvider.list(addressListRequest)
                        .getContext().getPlatformAddressVOList();
                String consigneeAddress = platformAddressVOList.parallelStream().map(PlatformAddressVO::getAddrName).collect(Collectors.joining());
                consigneeAddress = consigneeAddress.concat(addressResponse.getDeliveryAddress());
                request.setConsigneeAddress(consigneeAddress);
            }
            request.getStoreCommitInfoList().forEach(storeCommitInfoDTO -> {
                if(storeCommitInfoDTO.getInvoiceType() != -1) {
                    // 如果不是否单独的开票收货地址，则参数错误，虚拟订单或者卡券订单必须是单独的开票地址
                    if (!Boolean.TRUE.equals(storeCommitInfoDTO.isSpecialInvoiceAddress())) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050156);
                    }
                    // 发票的收货地址ID,如果需要开票,则必传
                    if (StringUtils.isBlank(storeCommitInfoDTO.getInvoiceAddressId())) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050156);
                    }
                    // 收货地址详细信息（包含省市区），如果需要开票,则必传
                    if (StringUtils.isBlank(storeCommitInfoDTO.getInvoiceAddressDetail())) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050156);
                    }
                }
            });

        }
    }

    /**
     * @description   处理用户可用礼品卡数据
     * @author  wur
     * @date: 2022/12/15 19:29
     * @param tradeItemVOList
     * @param customerId
     * @return
     **/
    public Long dealGiftCard(List<TradeItemVO> tradeItemVOList, String customerId) {
        if (CollectionUtils.isEmpty(tradeItemVOList)) {
            return 0L;
        }
        // 封装快照中商品信息
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        tradeItemVOList.forEach(tradeItemVO -> {
            GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
            goodsInfoVO.setGoodsInfoId(tradeItemVO.getSkuId());
            goodsInfoVO.setStoreId(tradeItemVO.getStoreId());
            goodsInfoVO.setCateId(tradeItemVO.getCateId());
            goodsInfoVO.setBrandId(tradeItemVO.getBrand());
            goodsInfoVOList.add(goodsInfoVO);
        });

        UserGiftCardTradeRequest giftCardTradeRequest = new UserGiftCardTradeRequest();
        giftCardTradeRequest.setCustomerId(customerId);
        giftCardTradeRequest.setGoodsInfoVOList(goodsInfoVOList);
        UserGiftCardTradeResponse response = userGiftCardProvider.tradeUserGiftCard(giftCardTradeRequest).getContext();
        return response.getValidNum();
    }
}
