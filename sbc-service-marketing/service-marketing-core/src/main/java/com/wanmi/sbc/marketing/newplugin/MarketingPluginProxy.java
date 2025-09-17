package com.wanmi.sbc.marketing.newplugin;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.common.MarketingResponseProcess;
import com.wanmi.sbc.marketing.util.mapper.MarketingGoodsInfoMapper;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zhanggaolei
 * @className MarketingPluginProxy
 * @description
 * @date 2021/5/19 10:48
 */
@Slf4j
@Service
@Scope("prototype")
public class MarketingPluginProxy implements MarketingPlugin {
    @Autowired MarketingPluginBaseService marketingPluginBaseService;
    @Autowired MarketingGoodsInfoMapper marketingGoodsInfoMapper;

    private MarketingPluginInterface plugin;
    private MarketingPluginType marketingPluginType;

    @Override
    public Boolean goodsDetail(GoodsInfoPluginRequest request) {
        Boolean flag = Boolean.FALSE;
        MarketingGoods marketingGoods =
                marketingGoodsInfoMapper.goodsInfoPluginRequestToMarketingGoods(
                        request.getGoodsInfoPluginRequest());

        // 校验商品是不是包含对应的营销活动，如果不包含直接return
        if (!marketingPluginBaseService.checkBaseRequest(
                marketingGoods, request.getCustomerId(), marketingPluginType, true, request.getStoreId())) {
            return false;
        }
        log.info(
                "goodsInfoId:{},plugin:{}",
                request.getGoodsInfoPluginRequest().getGoodsInfoId(),
                marketingPluginType.name());
        GoodsInfoDetailPluginResponse response = null;

        //付费会员
        if (MarketingPluginType.PAYING_MEMBER == marketingPluginType) {
            response = plugin.goodsDetail(request);
            return this.replacePayMemberLabelForDetail(response);
        }
        // 如果是满返，需组装boss端信息
        if (MarketingPluginType.RETURN == marketingPluginType) {
            // 兼容预约 预售等营销
            if (!MarketingContext.isNotNullMultiMarketingMapKey(
                    request.getGoodsInfoPluginRequest().getGoodsInfoId()) && Objects.nonNull(request.getCustomerId())) {
                if (MapUtils.isEmpty(MarketingContext.getBaseRequest().getMultiTypeMarketingMap())){
                    Map<String, List<MarketingResponse>> multiTypeMap =
                            marketingPluginBaseService.getMultiTypeMarketingMap(Collections.singletonList(marketingGoods), request.getStoreId());
                    if (MapUtils.isNotEmpty(multiTypeMap)){
                        MarketingContext.getBaseRequest().setMultiTypeMarketingMap(multiTypeMap);
                    }
                }
            }
            List<MarketingPluginLabelVO> marketingLabels = Lists.newArrayList();
            GoodsInfoDetailPluginResponse responseSupplier = plugin.goodsDetail(request);
            if (Objects.nonNull(responseSupplier)) {
                marketingLabels.addAll(responseSupplier.getMarketingLabels());
                response = responseSupplier;
            }
            //这里需拷贝入参，防止查询更改了storeId
            GoodsInfoPluginRequest platformRequest = KsBeanUtil.convert(request,GoodsInfoPluginRequest.class);
            if (Objects.nonNull(platformRequest)){
                platformRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
            }
            GoodsInfoDetailPluginResponse responsePlatform = plugin.goodsDetail(platformRequest);
            if (Objects.nonNull(responsePlatform)) {
                marketingLabels.addAll(responsePlatform.getMarketingLabels());
                response = responsePlatform;
            }
            if (Objects.nonNull(response)){
                response.setMarketingLabels(marketingLabels);
            }
        }else{
            response = plugin.goodsDetail(request);
        }
        // 注意如果营销活动不存在必须返回null
        if (Objects.nonNull(response)) {
            if (MarketingPluginType.BUY_CYCLE != marketingPluginType) {
                MarketingResponseProcess.setGoodsDetailResponseNotNUll(response);
            }
            // 此处需要注意，为了满足普通商品列表的拼团需求而进行的过滤，
            // 也就是说普通商品详情拼团的营销活动不生效，需要顺序的往下判断，
            // 如果后续拼团的商品详情跟普通商品详情合并则删除自处判断
//            if (!marketingPluginType.equals(MarketingPluginType.GROUPON)) {
//                flag = true;
//            }else if( "PC".equals(request.getTerminalSource())){
//                flag = true;
//            }
            flag = true;
        }
        return flag;
    }

    @Override
    public Boolean goodsList(GoodsListPluginRequest request) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean check(GoodsInfoPluginRequest request) {
        Boolean flag = Boolean.FALSE;
        MarketingGoods marketingGoods =
                marketingGoodsInfoMapper.goodsInfoPluginRequestToMarketingGoods(
                        request.getGoodsInfoPluginRequest());

        // 校验商品是不是包含对应的营销活动，如果不包含直接return
        if (!marketingPluginBaseService.checkBaseRequest(
                marketingGoods, request.getCustomerId(), marketingPluginType, true)) {
            log.info(
                    "goodsInfo:{},plugin:{} exists",
                    request.getGoodsInfoPluginRequest().getGoodsInfoId(),
                    marketingPluginType.name());
            return false;
        } else {
            log.info(
                    "goodsInfo:{},plugin:{} exists",
                    request.getGoodsInfoPluginRequest().getGoodsInfoId(),
                    marketingPluginType.name());
        }

        if (MarketingPluginType.PAYING_MEMBER == marketingPluginType) {
            //付费会员 比较会员等级价
            MarketingPluginSimpleLabelVO response = plugin.check(request);
            flag = replacePayMemberLabel(response);
        } else if (MarketingPluginType.RETURN == marketingPluginType) {
            // 如果是满返，需组装boss端信息
            MarketingPluginSimpleLabelVO response = plugin.check(request);
            // 注意如果营销活动不存在必须返回null
            if (Objects.nonNull(response)) {
                MarketingResponseProcess.setMarketingLabel(response);
                flag = true;
            }
            //这里需拷贝入参，防止列表查询更改了storeId
            GoodsInfoPluginRequest platformRequest = KsBeanUtil.convert(request,GoodsInfoPluginRequest.class);
            if (Objects.nonNull(platformRequest)){
                platformRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
            }
            response = plugin.check(platformRequest);
            // 注意如果营销活动不存在必须返回null
            if (Objects.nonNull(response)) {
                MarketingResponseProcess.setMarketingLabel(response);
                flag = true;
            }
        } else {
            MarketingPluginSimpleLabelVO response = plugin.check(request);
            // 注意如果营销活动不存在必须返回null
            if (Objects.nonNull(response)) {
                if (MarketingPluginType.BUY_CYCLE != marketingPluginType) {
                    MarketingResponseProcess.setMarketingLabel(response);
                }
                flag = true;
            }
        }

        return flag;
    }

    @Override
    public Boolean cartMarketing(GoodsInfoPluginRequest request) {
        Boolean flag = Boolean.FALSE;
        MarketingGoods marketingGoods =
                marketingGoodsInfoMapper.goodsInfoPluginRequestToMarketingGoods(
                        request.getGoodsInfoPluginRequest());

        // 校验商品是不是包含对应的营销活动，如果不包含直接return
        if (!marketingPluginBaseService.checkBaseRequest(
                marketingGoods,
                request.getCustomerId(),
                marketingPluginType,
                true)) {
            log.debug(
                    "goodsInfo:{},plugin:{} not exists",
                    request.getGoodsInfoPluginRequest().getGoodsInfoId(),
                    marketingPluginType.name());
            return false;
        }

        MarketingPluginSimpleLabelVO response = plugin.cartMarketing(request);

        if (MarketingPluginType.PAYING_MEMBER == marketingPluginType) {
            return replacePayMemberLabelForTrade(response);
        }
        // 注意如果营销活动不存在必须返回null
        if (Objects.nonNull(response)) {
            if (MarketingPluginType.BUY_CYCLE != marketingPluginType) {
                MarketingResponseProcess.setMarketingLabel(response);
            }
            flag = true;
        }
        return flag;
    }

    @Override
    public Boolean tradeMarketing(GoodsInfoPluginRequest request) {
        Boolean flag = Boolean.FALSE;
        MarketingGoods marketingGoods =
                marketingGoodsInfoMapper.goodsInfoPluginRequestToMarketingGoods(
                        request.getGoodsInfoPluginRequest());

        // 校验商品是不是包含对应的营销活动，如果不包含直接return
        if (!marketingPluginBaseService.checkBaseRequest(
                marketingGoods,
                request.getCustomerId(),
                marketingPluginType,
                true)) {
            log.debug(
                    "goodsInfo:{},plugin:{} not exists",
                    request.getGoodsInfoPluginRequest().getGoodsInfoId(),
                    marketingPluginType.name());
            return false;
        }

        MarketingPluginSimpleLabelVO response = plugin.tradeMarketing(request);

        if (MarketingPluginType.PAYING_MEMBER == marketingPluginType) {
            return replacePayMemberLabelForTrade(response);
        }


        // 注意如果营销活动不存在必须返回null
        if (Objects.nonNull(response)) {
            if (MarketingPluginType.BUY_CYCLE != marketingPluginType) {
                MarketingResponseProcess.setMarketingLabel(response);
            }
            flag = true;
        }
        return flag;
    }

    protected void setPlugin(MarketingPluginInterface marketingPlugin) {
        this.plugin = marketingPlugin;
    }

    protected void setMarketingPluginType(MarketingPluginType marketingPluginType) {
        this.marketingPluginType = marketingPluginType;
    }

    private Boolean replacePayMemberLabel(MarketingPluginSimpleLabelVO response) {
        Boolean flag = Boolean.FALSE;
        if (response != null) {
            PayingMemberLevelVO payingMemberLevel = MarketingContext.getBaseRequest().getPayingMemberLevel();
            MarketingPluginSimpleLabelVO marketingLabel = MarketingResponseProcess.getMarketingLabel(MarketingPluginType.CUSTOMER_LEVEL);
            if (marketingLabel == null || !Boolean.TRUE.equals(payingMemberLevel.getOwnFlag())) {
                //没有会员等级价
                MarketingResponseProcess.setMarketingLabel(response);
                flag = Boolean.TRUE;
            } else if (response.getPluginPrice().compareTo(marketingLabel.getPluginPrice()) < 1) {
                if (Boolean.TRUE.equals(payingMemberLevel.getOwnFlag())) {
                    MarketingResponseProcess.removeMarketingLabel(MarketingPluginType.CUSTOMER_LEVEL);
                }
                MarketingResponseProcess.setMarketingLabel(response);
                flag = Boolean.TRUE;
            }
        }
        return flag;
    }

    private Boolean replacePayMemberLabelForTrade(MarketingPluginSimpleLabelVO response) {
        Boolean flag = Boolean.FALSE;
        if (response != null) {
            Boolean ownFlag = MarketingContext.getBaseRequest().getPayingMemberLevel().getOwnFlag();
            if (Boolean.TRUE.equals(ownFlag)) {
                MarketingPluginSimpleLabelVO marketingLabel = MarketingResponseProcess.getMarketingLabel(MarketingPluginType.CUSTOMER_LEVEL);
                if (marketingLabel == null) {
                    //没有会员等级价
                    MarketingResponseProcess.setMarketingLabel(response);
                    flag = Boolean.TRUE;
                } else if (response.getPluginPrice().compareTo(marketingLabel.getPluginPrice()) < 1) {
                    MarketingResponseProcess.removeMarketingLabel(MarketingPluginType.CUSTOMER_LEVEL);
                    MarketingResponseProcess.setMarketingLabel(response);
                    flag = Boolean.TRUE;
                }
            }
        }
        return flag;
    }

    private Boolean replacePayMemberLabelForDetail(GoodsInfoDetailPluginResponse response) {
        Boolean flag = Boolean.FALSE;
        if (response != null) {
            PayingMemberLevelVO payingMemberLevel = MarketingContext.getBaseRequest().getPayingMemberLevel();
            MarketingPluginLabelVO payMemberLabel = response.getMarketingLabels().get(0);
            GoodsInfoDetailPluginResponse oldResponse = MarketingContext.getResponse();
            MarketingPluginLabelVO levelLabel = null;
            //付费会员价低比会员等级价/市场价就设置标签
            if (oldResponse != null && CollectionUtils.isNotEmpty(oldResponse.getMarketingLabels())) {
                Optional<MarketingPluginLabelVO> optional =
                        oldResponse.getMarketingLabels().stream()
                                .filter(
                                        label ->
                                                MarketingPluginType.CUSTOMER_LEVEL.getId()
                                                        == label.getMarketingType())
                                .findFirst();
                if (optional.isPresent()) {
                    levelLabel = optional.get();
                }
            }

            if (levelLabel == null || !Boolean.TRUE.equals(payingMemberLevel.getOwnFlag())) {
                //无粉丝价或普通会员
                MarketingResponseProcess.setGoodsDetailResponseNotNUll(response);
                flag = true;
            } else {
                if (payMemberLabel.getPluginPrice().compareTo(levelLabel.getPluginPrice()) < 1) {
                    if (Boolean.TRUE.equals(payingMemberLevel.getOwnFlag())) {
                        MarketingResponseProcess.setGoodsDetailResponseNotNUll(response, MarketingPluginType.CUSTOMER_LEVEL);
                        return true;
                    }
                    MarketingResponseProcess.setGoodsDetailResponseNotNUll(response);
                    flag = true;
                }
            }
        }
        return flag;
    }

}
