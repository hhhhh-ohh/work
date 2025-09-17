package com.wanmi.sbc.goods.wechatvideosku.service;

import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuQueryRequest;
import com.wanmi.sbc.goods.bean.enums.WechatShelveStatus;

import com.wanmi.sbc.goods.wechatvideosku.model.root.WechatSku;
import com.wanmi.sbc.goods.wechatvideosku.repository.WechatSkuRepository;
import com.wanmi.sbc.goods.wechatvideosku.service.WechatSkuWhereCriteriaBuilder;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformGoodsProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.goods.SellPlatformGoodsBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhanggaolei
 * @className WechatSkuProxyService
 * @description
 * @date 2022/10/31 16:07
 */
@Service
public class WechatSkuProxyService {
    @Autowired
    WechatSkuRepository wechatSkuRepository;

    @Autowired SellPlatformGoodsProvider sellPlatformGoodsProvider;

    /**
     * 列表查询微信视频号带货商品
     *
     * @author
     */
    public List<WechatSku> list(WechatSkuQueryRequest queryReq) {
        return wechatSkuRepository.findAll(
                WechatSkuWhereCriteriaBuilder.build(queryReq), queryReq.getSort());
    }

    /**
     * 更新上下架状态
     *
     * @param goodsIds
     * @param wechatShelveStatus
     */
    @Transactional
    public void updateWecahtShelveStatus(
            List<String> goodsIds, WechatShelveStatus wechatShelveStatus) {
        wechatSkuRepository.updateAddedByGoodsId(goodsIds, wechatShelveStatus);
        if (wechatShelveStatus.equals(WechatShelveStatus.UN_SHELVE)) {
            sellPlatformGoodsProvider.delistingGoods(new SellPlatformGoodsBaseRequest(goodsIds));
        } else {
            sellPlatformGoodsProvider.listingGoods(new SellPlatformGoodsBaseRequest(goodsIds));
        }
    }
}
