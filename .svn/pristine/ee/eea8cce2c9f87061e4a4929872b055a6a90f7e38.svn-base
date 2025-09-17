package com.wanmi.sbc.vas.channel.order;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderCompensateResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderSplitResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderVerifyResponse;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelConsigneeDTO;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelOrderDTO;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelOrderItemDTO;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description VOP订单服务
 * @author daiyitian
 * @date 2021/5/12 18:09
 */
public interface ChannelOrderService {

    /**
     * 验证订单
     *
     * @description 验证订单
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param trades 订单列表
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelOrderVerifyResponse
     */
    ChannelOrderVerifyResponse verifyOrder(List<ChannelOrderDTO> trades);

    /**
     * 拆分订单
     *
     * @description 拆分订单
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param trade 供应商订单信息 是供应商订单providerOrder
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelOrderSplitResponse
     */
    ChannelOrderSplitResponse splitOrder(ChannelOrderDTO trade);

    /**
     * @description 查询渠道运费
     * @author  wur
     * @date: 2021/9/24 16:07
     * @param channelGoodsInfoDTOList
     * @param address
     * @return
     **/
    BigDecimal queryFreight(List<ChannelGoodsInfoDTO> channelGoodsInfoDTOList, PlatformAddress address);

    /**
     * 补偿订单
     *
     * @description 补偿订单
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param trades 第三方平台订单列表
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelOrderCompensateResponse
     */
    ChannelOrderCompensateResponse compensateOrder(List<ChannelOrderDTO> trades);

    /**
     * 合并订单明细数据
     *
     * @param tradeItems 普通商品
     * @param gifts 赠品
     * @param thirdPlatformType 第三方平台类型
     * @return 新合并数据
     */
    default List<ChannelGoodsInfoVO> zipItem(
            List<ChannelOrderItemDTO> tradeItems,
            List<ChannelOrderItemDTO> gifts,
            List<ChannelOrderItemDTO> preferentialList,
            ThirdPlatformType thirdPlatformType) {
        List<ChannelGoodsInfoVO> items = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tradeItems)) {
            items.addAll(
                    tradeItems.stream()
                            .filter(
                                    i ->
                                            Objects.equals(
                                                    thirdPlatformType, i.getThirdPlatformType()))
                            .map(this::wrapperSku)
                            .collect(Collectors.toList()));
        }

        if (CollectionUtils.isNotEmpty(gifts)) {
            List<ChannelGoodsInfoVO> giftItems =
                    gifts.stream()
                            .filter(
                                    i ->
                                            Objects.equals(
                                                    thirdPlatformType, i.getThirdPlatformType()))
                            .map(this::wrapperSku)
                            .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(items)) {
                items =
                        IteratorUtils.zip(
                                items,
                                giftItems,
                                (a, b) ->
                                        a.getThirdPlatformSpuId().equals(b.getThirdPlatformSpuId())
                                                && a.getThirdPlatformSkuId()
                                                        .equals(b.getThirdPlatformSkuId()),
                                (a, b) -> {
                                    a.setBuyCount(a.getBuyCount() + b.getBuyCount());
                                });
            } else {
                items.addAll(giftItems);
            }
        }
        if (CollectionUtils.isNotEmpty(preferentialList)) {
            List<ChannelGoodsInfoVO> preferentials =
                    preferentialList.stream()
                            .filter(
                                    i ->
                                            Objects.equals(
                                                    thirdPlatformType, i.getThirdPlatformType()))
                            .map(this::wrapperSku)
                            .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(items)) {
                items =
                        IteratorUtils.zip(
                                items,
                                preferentials,
                                (a, b) ->
                                        a.getThirdPlatformSpuId().equals(b.getThirdPlatformSpuId())
                                                && a.getThirdPlatformSkuId()
                                                .equals(b.getThirdPlatformSkuId()),
                                (a, b) -> {
                                    a.setBuyCount(a.getBuyCount() + b.getBuyCount());
                                });
            } else {
                items.addAll(preferentials);
            }
        }
        return items;
    }

    /**
     * 根据订单获取地址
     *
     * @description 根据订单获取地址
     * @author daiyitian
     * @date 2021/5/23 10:11
     * @param trade 订单
     * @return com.wanmi.sbc.common.base.PlatformAddress 本地地址
     */
    default PlatformAddress getThirdAddressByTrade(ChannelOrderDTO trade) {
        ChannelConsigneeDTO consigneeDTO = trade.getConsignee();
        PlatformAddress platformAddress = new PlatformAddress();
        platformAddress.setProvinceId(Objects.toString(consigneeDTO.getProvinceId()));
        platformAddress.setCityId(Objects.toString(consigneeDTO.getCityId()));
        platformAddress.setAreaId(Objects.toString(consigneeDTO.getAreaId()));
        platformAddress.setStreetId(Objects.toString(consigneeDTO.getStreetId()));
        return platformAddress;
    }

    /**
     * 封装ChannelOrderItemDTO转换为ChannelGoodsInfoVO
     *
     * @description ChannelOrderItemDTO转换为ChannelGoodsInfoVO
     * @author daiyitian
     * @date 2021/6/1 13:48
     * @param dto ChannelOrderItemDTO实例
     * @return com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO
     */
    default ChannelGoodsInfoVO wrapperSku(ChannelOrderItemDTO dto) {
        ChannelGoodsInfoVO info = new ChannelGoodsInfoVO();
        info.setThirdPlatformType(dto.getThirdPlatformType());
        info.setThirdPlatformSkuId(dto.getThirdPlatformSkuId());
        info.setThirdPlatformSpuId(dto.getThirdPlatformSpuId());
        info.setProviderGoodsInfoId(dto.getProviderSkuId());
        info.setBuyCount(dto.getNum());
        // 先标记有效，无效的商品无需查询库存
        info.setVendibility(Constants.yes);
        return info;
    }

    /**
     * 标识错误-补偿专用结果
     *
     * @description 标识错误-补偿专用结果
     * @author daiyitian
     * @date 2021/6/1 17:05
     * @param thirdTrade 第三方平台订单信息
     * @param compensateResponse 补偿响应类
     */
    default void errorTrade(
            ChannelOrderDTO thirdTrade, ChannelOrderCompensateResponse compensateResponse) {
        compensateResponse.getPayErrThirdTradeId().add(thirdTrade.getId());
        compensateResponse.getPayErrProviderTradeIds().add(thirdTrade.getParentId());
    }

    /**
     * 标识待确认-补偿专用结果
     *
     * @description 标识待确认-补偿专用结果
     * @author daiyitian
     * @date 2021/6/1 17:05
     * @param thirdTrade 第三方平台订单信息
     * @param compensateResponse 补偿响应类
     */
    default void unconfirmedTrade(
            ChannelOrderDTO thirdTrade, ChannelOrderCompensateResponse compensateResponse) {
        compensateResponse.getUnconfirmedThirdTradeId().add(thirdTrade.getId());
        compensateResponse.getUnconfirmedProviderTradeIds().add(thirdTrade.getParentId());
    }
}
