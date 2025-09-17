package com.wanmi.sbc.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.giftcard.request.UserGiftCardRequest;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.*;
import com.wanmi.sbc.order.api.provider.trade.TradeItemQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeItemSnapshotByCustomerIdRequest;
import com.wanmi.sbc.order.bean.vo.TradeItemSnapshotVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lvzhenwei
 * @className UserGiftCardController
 * @description 用户礼品卡业务处理
 * @date 2022/12/10 2:23 下午
 **/
@Slf4j
@Tag(name =  "用户礼品卡业务API", description =  "UserGiftCardController")
@RestController
@Validated
@RequestMapping(value = "/userGiftCard")
public class UserGiftCardController {

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    @Autowired private CommonUtil commonUtil;

    @Autowired private RedissonClient redissonClient;

    @Autowired private TradeItemQueryProvider tradeItemQueryProvider;

    @Autowired protected GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private GiftCardDetailQueryProvider giftCardDetailQueryProvider;

    @Operation(summary = "校验会员兑换礼品卡")
    @RequestMapping(value = "/checkExchangeGiftCard", method = RequestMethod.POST)
    public BaseResponse<UserGiftCardInfoResponse>  checkExchangeGiftCard(@RequestBody @Valid ExchangeGiftCardRequest request) {
        String lock = CacheKeyConstant.MARKETING_GIFT_CARD_NO_LOCK+request.getGiftCardNo();
        RLock rLock = redissonClient.getFairLock(lock);
        rLock.lock();
        try {
            request.setCustomerId(commonUtil.getOperatorId());
            return userGiftCardProvider.checkExchangeGiftCard(request);
        }catch (Exception e){
            throw e;
        } finally {
            rLock.unlock();
        }
    }

    @Operation(summary = "会员兑换礼品卡")
    @RequestMapping(value = "/exchangeGiftCard", method = RequestMethod.POST)
    public BaseResponse<UserGiftCardInfoResponse>  exchangeGiftCard(@RequestBody @Valid ExchangeGiftCardRequest request) {
        String lock = CacheKeyConstant.MARKETING_GIFT_CARD_NO_LOCK+request.getGiftCardNo();
        RLock rLock = redissonClient.getFairLock(lock);
        rLock.lock();
        try {
            request.setCustomerId(commonUtil.getOperatorId());
            return userGiftCardProvider.exchangeGiftCard(request);
        }catch (Exception e){
            throw e;
        } finally {
            rLock.unlock();
        }
    }

    @Operation(summary = "查询会员礼品卡详情")
    @RequestMapping(value = "/getUserGiftCardDetail", method = RequestMethod.POST)
    public BaseResponse<UserGiftCardInfoResponse> getUserGiftCardDetail(@RequestBody @Valid UserGiftCardDetailQueryRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        return userGiftCardProvider.getUserGiftCardDetail(request);
    }

    @Operation(summary = "会员激活礼品卡")
    @RequestMapping(value = "/activateGiftCard", method = RequestMethod.POST)
    public BaseResponse activateGiftCard(@RequestBody @Valid ActivateGiftCardRequest request) {
        String lock = CacheKeyConstant.MARKETING_GIFT_CARD_NO_LOCK+request.getGiftCardNo();
        RLock rLock = redissonClient.getFairLock(lock);
        rLock.lock();
        try {
            request.setCustomerId(commonUtil.getOperatorId());
            userGiftCardProvider.activateGiftCard(request);
        }catch (Exception e){
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description   用户礼品卡分页查询
     * @author  wur
     * @date: 2022/12/12 16:13
     * @param request
     * @return
     **/
    @Operation(summary = "用户礼品卡分页查询")
    @RequestMapping(value = "/getUserGiftCardPage", method = RequestMethod.POST)
    public BaseResponse<UserGiftCardInfoPageResponse> getUserGiftCardPage(@RequestBody @Valid UserGiftCardQueryRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        request.putSort("acquireTime", "desc");
        request.putSort("userGiftCardId", "desc");
        return userGiftCardProvider.getUserGiftCardInfoPage(request);
    }

    /**
     * @description   用户可用礼品卡数量
     * @author  wur
     * @date: 2022/12/12 16:13
     * @param request
     * @return
     **/
    @Operation(summary = "用户礼品卡数量")
    @PostMapping(value = "/getUserGiftCardNum")
    public BaseResponse<UserGiftCardInfoNumResponse> getUserGiftCardNum(@RequestBody @Valid UserGiftCardQueryRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        return userGiftCardProvider.getUserGiftCardInfoNum(request);
    }

    @Operation(summary = " 确认订单页礼品卡信息查询")
    @PostMapping(value = "/getTradeUserGiftCard")
    public BaseResponse<UserGiftCardTradeResponse> getTradeUserGiftCard(@RequestBody @Valid UserGiftCardRequest request) {
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        if(Objects.nonNull(request) && CollectionUtils.isNotEmpty(request.getSkuIdList())) {
            goodsInfoVOList = goodsInfoQueryProvider.getGoodsInfoByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(request.getSkuIdList()).build()).getContext().getGoodsInfos();
        } else {
            // 获取用户订单快照
            TradeItemSnapshotVO tradeItemSnapshotVO = tradeItemQueryProvider.listByTerminalTokenWithout(TradeItemSnapshotByCustomerIdRequest
                    .builder().terminalToken(commonUtil.getTerminalToken()).build()).getContext().getTradeItemSnapshotVO();
            if ( Objects.isNull(tradeItemSnapshotVO)) {
                return BaseResponse.success(UserGiftCardTradeResponse.builder().build());
            }
            // 封装快照中商品信息
            List<GoodsInfoVO> finalGoodsInfoVOList = goodsInfoVOList;
            tradeItemSnapshotVO.getItemGroups().forEach(tradeItemGroupVO -> {
                tradeItemGroupVO.getTradeItems().forEach(tradeItemVO -> {
                    GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                    goodsInfoVO.setGoodsInfoId(tradeItemVO.getSkuId());
                    goodsInfoVO.setStoreId(tradeItemVO.getStoreId());
                    goodsInfoVO.setCateId(tradeItemVO.getCateId());
                    goodsInfoVO.setBrandId(tradeItemVO.getBrand());
                    finalGoodsInfoVOList.add(goodsInfoVO);
                });
                if (CollectionUtils.isNotEmpty(tradeItemGroupVO.getPreferentialTradeItems())) {
                    tradeItemGroupVO.getPreferentialTradeItems().forEach(tradeItemVO -> {
                        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                        goodsInfoVO.setGoodsInfoId(tradeItemVO.getSkuId());
                        goodsInfoVO.setStoreId(tradeItemVO.getStoreId());
                        goodsInfoVO.setCateId(tradeItemVO.getCateId());
                        goodsInfoVO.setBrandId(tradeItemVO.getBrand());
                        finalGoodsInfoVOList.add(goodsInfoVO);
                    });
                }
            });

        }
        UserGiftCardTradeRequest tradeRequest = new UserGiftCardTradeRequest();
        tradeRequest.setCustomerId(commonUtil.getOperatorId());
        tradeRequest.setGoodsInfoVOList(goodsInfoVOList);
        return userGiftCardProvider.tradeUserGiftCard(tradeRequest);
    }

    /**
     * @description 验证礼品卡使用预估金额
     * @author  wur
     * @date: 2022/12/13 15:07
     * @param request
     * @return
     **/
    @Operation(summary = " 确认订单页-验证礼品卡使用预估金额  验证失败返回错误码：K-080034")
    @PostMapping(value = "/checkUserGiftCard")
    public BaseResponse checkUserGiftCard(@RequestBody @Valid UserGiftCardUseCheckRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        userGiftCardProvider.checkUserGiftCard(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "未登录查询会员礼品卡详情")
    @RequestMapping(value = "/getGiftCardDetailUnLogin", method = RequestMethod.POST)
    public BaseResponse<GiftCardDetailByIdResponse> getGiftCardDetailUnLogin(@RequestBody @Valid GiftCardDetailByIdRequest request) {
        return giftCardDetailQueryProvider.getById(request);
    }
}
