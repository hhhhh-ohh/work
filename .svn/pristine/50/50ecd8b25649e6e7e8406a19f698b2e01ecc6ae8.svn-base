package com.wanmi.sbc.vas.channel.goods.service;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsStatusGetResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO;

import java.util.List;

/**
 * @description 渠道商品服务
 * @author daiyitian
 * @date 2021/5/12 18:09
 */
public interface ChannelGoodsService {

    /**
     * 填充商品状态
     *
     * @description 填充状态
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param goodsInfoList 商品列表
     * @param platformAddress 本地地址
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelGoodsStatusGetResponse
     */
    ChannelGoodsStatusGetResponse fillGoodsStatus(
			List<ChannelGoodsInfoVO> goodsInfoList, PlatformAddress platformAddress);

    /**
     * 验证商品
     *
     * @description 验证商品
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param goodsInfoList 商品列表
     * @param platformAddress 本地地址
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse
     */
    ChannelGoodsVerifyResponse verifyGoods(
			List<ChannelGoodsInfoVO> goodsInfoList, PlatformAddress platformAddress);
}
