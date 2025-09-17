package com.wanmi.sbc.goods.api.provider.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.mqconsumer.GoodsMqConsumerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description mq消费者处理接口
 * @author  lvzhenwei
 * @date 2021/8/11 3:42 下午
 **/
@FeignClient(value = "${application.goods.name}", contextId = "GoodsMqConsumerProvider")
public interface GoodsMqConsumerProvider {

    /**
     * @description 批量导入-商品-初始化
     * @author  lvzhenwei
     * @date 2021/8/11 3:46 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/delete-by-customer-id")
    BaseResponse batchAddGoods(@RequestBody @Valid GoodsMqConsumerRequest request);

    /**
     * @description 批量导入-商品-库存修改
     * @author  zgl
     * @date 2023/3/15 3:46 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/batch-goods-stock-update")
    BaseResponse batchGoodsStockUpdate(@RequestBody @Valid GoodsMqConsumerRequest request);

    /**
     * @description 秒杀订单--异步处理销量和个人购买记录
     * @author  lvzhenwei
     * @date 2021/8/13 1:44 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/deal-flash-sale-record")
    BaseResponse dealFlashSaleRecord(@RequestBody @Valid GoodsMqConsumerRequest request);

    /**
     * @description  更新已成团人数
     * @author  lvzhenwei
     * @date 2021/8/17 2:04 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/update-already-groupon-num")
    BaseResponse updateAlreadyGrouponNum(@RequestBody @Valid GoodsMqConsumerRequest request);

    /**
     * @description 更新商品销售量、订单量、交易额
     * @author  lvzhenwei
     * @date 2021/8/17 2:12 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/update-groupon-order-pay-statistics")
    BaseResponse updateGrouponOrderPayStatistics(@RequestBody @Valid GoodsMqConsumerRequest request);

    /**
     * @description 增加商品库存
     * @author  lvzhenwei
     * @date 2021/8/18 10:47 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/add-goods-info-stock")
    BaseResponse addGoodsInfoStock(@RequestBody @Valid GoodsMqConsumerRequest request);

    /**
     * @description 扣减商品库存
     * @author  lvzhenwei
     * @date 2021/8/18 10:59 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/sub-goods-info-stock")
    BaseResponse subGoodsInfoStock(@RequestBody @Valid GoodsMqConsumerRequest request);

    /**
     * @description 替换商品库存
     * @author  lvzhenwei
     * @date 2021/8/18 10:59 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/replace-goods-info-stock")
    BaseResponse replaceGoodsInfoStock(@RequestBody @Valid GoodsMqConsumerRequest request);
    
    /**
     * @description 商品批量改价
     * @author malianfeng 
     * @date 2021/9/9 13:39
     * @param request 请求
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/price-adjust")
    BaseResponse goodsPriceAdjust(@RequestBody @Valid GoodsMqConsumerRequest request);

    /**
     * @description 商品-更新店铺信息时，更新商品goods中对应店铺名称，同步es
     * @author chenli
     * @date 2022/11/26 13:39
     * @param request 请求
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/goods/${application.goods.version}/mq/consumer/store-name-modify")
    BaseResponse goodsStoreNameModify(@RequestBody @Valid GoodsMqConsumerRequest request);

    /**
     * 根据供应商商品ID批量处理代销商品可售性
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/mq/consumer/build-goodsInfo-vendibility")
    BaseResponse buildGoodsInfoVendibility(@RequestBody @Valid GoodsMqConsumerRequest request);

}
