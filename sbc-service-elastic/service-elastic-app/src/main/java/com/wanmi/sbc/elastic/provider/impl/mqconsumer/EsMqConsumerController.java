package com.wanmi.sbc.elastic.provider.impl.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.mqconsumer.EsMqConsumerProvider;
import com.wanmi.sbc.elastic.api.request.mqconsumer.EsMqConsumerRequest;
import com.wanmi.sbc.elastic.mqconsumer.EsMqConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className EsMqConsumerController
 * @description mq消费者接口实现
 * @date 2021/8/11 2:33 下午
 **/
@RestController
public class EsMqConsumerController implements EsMqConsumerProvider {

    @Autowired
    private EsMqConsumerService mqConsumerService;

    @Override
    public BaseResponse addGoodsEvaluate(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.addGoodsEvaluate(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse addStoreEvaluate(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.addStoreEvaluate(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse customerRegister(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.customerRegister(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse pointsGoodsModifyAdded(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.pointsGoodsModifyAdded(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse couponAddPointsCoupon(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.couponAddPointsCoupon(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse goodsModifyStoreState(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.goodsModifyStoreState(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse operateLogAdd(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.operateLogAdd(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse customerModifyBaseInfo(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.customerModifyBaseInfo(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyCustomerAccount(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.modifyCustomerAccount(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse esStandardInit(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.esStandardInit(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse esGoodsInit(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.esGoodsInit(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse pointsGoodsAddSales(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.pointsGoodsAddSales(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyCustomerIsDistributor(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.modifyCustomerIsDistributor(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse customerLevelDetailAdd(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.customerLevelDetailAdd(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse addOrderInvoice(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.addOrderInvoice(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateFlowStateOrderInvoice(@RequestBody @Valid EsMqConsumerRequest esMqConsumerRequest) {
        mqConsumerService.updateFlowStateOrderInvoice(esMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }
}
