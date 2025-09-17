package com.wanmi.sbc.vas.provider.impl.channel.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsStatusGetRequest;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsVerifyRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsStatusGetResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO;
import com.wanmi.sbc.vas.channel.goods.service.ChannelGoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @description 渠道商品服务实现
 * @author daiyitian
 * @date 2021/5/21 21:45
 */
@RestController
public class ChannelGoodsController implements ChannelGoodsProvider {
    @Autowired private List<ChannelGoodsService> channelGoodsServiceList;

    @Override
    public BaseResponse<ChannelGoodsStatusGetResponse> getGoodsStatus(
            @RequestBody @Valid ChannelGoodsStatusGetRequest request) {
        List<ChannelGoodsInfoVO> skuList =
                KsBeanUtil.convert(request.getGoodsInfoList(), ChannelGoodsInfoVO.class);
        ChannelGoodsStatusGetResponse response = new ChannelGoodsStatusGetResponse();
        if(CollectionUtils.isEmpty(channelGoodsServiceList)){
            return BaseResponse.success(response);
        }
        channelGoodsServiceList.forEach(
                i -> {
                    ChannelGoodsStatusGetResponse tmpResponse =
                            i.fillGoodsStatus(skuList, request.getAddress());
                    response.getOffAddedSkuId().addAll(tmpResponse.getOffAddedSkuId());
                });
        response.setGoodsInfoList(skuList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<ChannelGoodsVerifyResponse> verifyGoods(
            @RequestBody @Valid ChannelGoodsVerifyRequest request) {
        List<ChannelGoodsInfoVO> skuList =
                KsBeanUtil.convert(request.getGoodsInfoList(), ChannelGoodsInfoVO.class);
        ChannelGoodsVerifyResponse response = new ChannelGoodsVerifyResponse();
        if(CollectionUtils.isEmpty(channelGoodsServiceList)){
            return BaseResponse.success(response);
        }
        channelGoodsServiceList.forEach(
                i -> {
                    ChannelGoodsVerifyResponse tmpResponse =
                            i.verifyGoods(skuList, request.getAddress());
                    response.getOffAddedSkuId().addAll(tmpResponse.getOffAddedSkuId());
                    if (tmpResponse.getInvalidGoods()) {
                        response.setInvalidGoods(tmpResponse.getInvalidGoods());
                    }

                    if (tmpResponse.getNoAuthGoods()) {
                        response.setNoAuthGoods(tmpResponse.getNoAuthGoods());
                    }

                    if (tmpResponse.getNoOutStockGoods()) {
                        response.setNoOutStockGoods(tmpResponse.getNoOutStockGoods());
                    }
                });
        response.setGoodsInfoList(skuList);
        return BaseResponse.success(response);
    }
}
