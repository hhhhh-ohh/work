package com.wanmi.sbc.elastic.api.provider.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.mqconsumer.EsMqConsumerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description mq消费者方法接口
 * @author  lvzhenwei
 * @date 2021/8/11 2:32 下午
 **/
@FeignClient(value = "${application.elastic.name}", contextId = "EsMqConsumerProvider")
public interface EsMqConsumerProvider {

    /**
     * @description 新增商品评价mq消费方法
     * @author  lvzhenwei
     * @date 2021/8/11 2:39 下午
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/add-goods-evaluate")
    BaseResponse addGoodsEvaluate(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 商家评价
     * @author  lvzhenwei
     * @date 2021/8/16 1:51 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/add-store-evaluate")
    BaseResponse addStoreEvaluate(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 会员注册成功，发送订单支付MQ消息，同步ES
     * @author  lvzhenwei
     * @date 2021/8/11 2:39 下午
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/customer-register")
    BaseResponse customerRegister(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 积分商品-修改上下架同步es
     * @author  lvzhenwei
     * @date 2021/8/11 4:29 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/points-goods-modify-added")
    BaseResponse pointsGoodsModifyAdded(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 新增积分兑换券，同步ES
     * @author  lvzhenwei
     * @date 2021/8/11 4:39 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/coupon-add-points-coupon")
    BaseResponse couponAddPointsCoupon(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 商品-更新店铺信息,同步es
     * @author  lvzhenwei
     * @date 2021/8/11 4:53 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/goods-modify-store-state")
    BaseResponse goodsModifyStoreState(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 记录操作日志
     * @author  lvzhenwei
     * @date 2021/8/11 5:05 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/operate-log-add")
    BaseResponse operateLogAdd(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 修改会员基本信息成功，发送订单支付MQ消息，同步ES
     * @author  lvzhenwei
     * @date 2021/8/12 10:40 上午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/customer-modify-base-info")
    BaseResponse customerModifyBaseInfo(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 修改会员账号成功，发送订单支付MQ消息，同步ES
     * @author  lvzhenwei
     * @date 2021/8/12 10:51 上午
     * @param esMqConsumerRequest 
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/modify-customer-account")
    BaseResponse modifyCustomerAccount(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description elastic模块-标品库-初始化
     * @author  lvzhenwei
     * @date 2021/8/12 1:39 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/es-standard-init")
    BaseResponse esStandardInit(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description elastic模块-商品-初始化
     * @author  lvzhenwei
     * @date 2021/8/12 1:54 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/es-goods-init")
    BaseResponse esGoodsInit(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description elastic模块-积分商品-增加销量
     * @author  lvzhenwei
     * @date 2021/8/12 3:30 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/points-goods-add-sales")
    BaseResponse pointsGoodsAddSales(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 更新会员是否分销员字段，发送订单支付MQ消息，同步ES
     * @author  lvzhenwei
     * @date 2021/8/12 3:45 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/modify-customer-is-distributor")
    BaseResponse modifyCustomerIsDistributor(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);


    /**
     * @description 新增会员等級，同步ES
     * @author  lvzhenwei
     * @date 2021/8/12 4:17 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/customer-level-aetail-add")
    BaseResponse customerLevelDetailAdd(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 订单开票数据新增同步es
     * @author  lvzhenwei
     * @date 2021/8/12 4:44 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/add-order-invoice")
    BaseResponse addOrderInvoice(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);

    /**
     * @description 同步订单状态到开票的es数据中
     * @author  lvzhenwei
     * @date 2021/8/12 4:57 下午
     * @param esMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/elastic/${application.elastic.version}/mq/consumer/update-flow-state-order-invoice")
    BaseResponse updateFlowStateOrderInvoice(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest);


}
