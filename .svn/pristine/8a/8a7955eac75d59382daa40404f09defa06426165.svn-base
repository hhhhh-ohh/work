package com.wanmi.sbc.goods.provider.impl.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.mqconsumer.GoodsMqConsumerProvider;
import com.wanmi.sbc.goods.api.request.mqconsumer.GoodsMqConsumerRequest;
import com.wanmi.sbc.goods.mqconsumer.GoodsMqConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className GoodsMqConsumerController
 * @description mq消费者发放实现类
 * @date 2021/8/11 3:48 下午
 **/
@RestController
public class GoodsMqConsumerController implements GoodsMqConsumerProvider {

    @Autowired
    private GoodsMqConsumerService goodsMqConsumerService;

    @Override
    public BaseResponse batchAddGoods(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.batchAddGoods(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchGoodsStockUpdate(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.batchGoodsStockUpdate(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse dealFlashSaleRecord(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.dealFlashSaleRecord(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateAlreadyGrouponNum(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.updateAlreadyGrouponNum(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateGrouponOrderPayStatistics(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.updateGrouponOrderPayStatistics(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse addGoodsInfoStock(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.addGoodsInfoStock(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse subGoodsInfoStock(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.subGoodsInfoStock(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse replaceGoodsInfoStock(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.replaceGoodsInfoStock(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse goodsPriceAdjust(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.goodsPriceAdjust(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse goodsStoreNameModify(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.goodsStoreNameModify(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse buildGoodsInfoVendibility(@Valid GoodsMqConsumerRequest request) {
        goodsMqConsumerService.buildGoodsInfoVendibility(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }
}
