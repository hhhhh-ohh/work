package com.wanmi.sbc.empower.provider.impl.deliveryrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.deliveryrecord.DeliveryRecordDadaProvider;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DadaMessageRiderCancelRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaAddRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaCallBackRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaCancelByIdRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaFaultConfirmRequest;
import com.wanmi.sbc.empower.api.response.deliveryrecord.DeliveryRecordDadaAddResponse;
import com.wanmi.sbc.empower.bean.vo.DeliveryRecordDadaAddVO;
import com.wanmi.sbc.empower.deliveryrecord.model.root.DeliveryRecordDada;
import com.wanmi.sbc.empower.deliveryrecord.service.DeliveryRecordDadaService;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>达达配送记录保存服务接口实现</p>
 *
 * @author zhangwenchang
 */
@RestController
@Validated
public class DeliveryRecordDadaController implements DeliveryRecordDadaProvider {
    @Autowired
    private DeliveryRecordDadaService deliveryRecordDadaService;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Override
    public BaseResponse<DeliveryRecordDadaAddResponse> add(@RequestBody @Valid DeliveryRecordDadaAddRequest request) {
        DeliveryRecordDada dada = deliveryRecordDadaService.getOne(request.getOrderNo());
        DeliveryRecordDadaAddVO dadaVO = new DeliveryRecordDadaAddVO();
        TradeVO order = tradeQueryProvider.getOrderById(TradeGetByIdRequest.builder().tid(request.getOrderNo()).build()).getContext().getTradeVO();
        //新增
        if (Objects.isNull(dada)) {
            if (Objects.isNull(order)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            dada = deliveryRecordDadaService.add(order, request.getUserId(),request.getShopNo(),request.getOperator());
            KsBeanUtil.copyPropertiesThird(dada, dadaVO);
            return BaseResponse.success(new DeliveryRecordDadaAddResponse(dadaVO));
        }
        if(Constants.FIVE == dada.getDeliveryStatus() || BigDecimal.TEN.intValue() == dada.getDeliveryStatus()) {
            //重发
            dada.setStoreId(request.getStoreId());
            dada.setUpdatePerson(request.getUserId());
            dada = deliveryRecordDadaService.reAdd(dada, request.getShopNo(),request.getOperator());
            KsBeanUtil.copyPropertiesThird(dada, dadaVO);
            return BaseResponse.success(new DeliveryRecordDadaAddResponse(dadaVO));
        }
        throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
    }

    @Override
    public BaseResponse callBack(@RequestBody @Valid DeliveryRecordDadaCallBackRequest request) {
        deliveryRecordDadaService.callBack(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse cancelById(@RequestBody @Valid DeliveryRecordDadaCancelByIdRequest request) {
        deliveryRecordDadaService.cancel(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse faultConfirm(@RequestBody @Valid DeliveryRecordDadaFaultConfirmRequest request) {
        deliveryRecordDadaService.confirmFault(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse riderCancel(DadaMessageRiderCancelRequest request) {
        deliveryRecordDadaService.riderCancel(request);
        return BaseResponse.SUCCESSFUL();
    }
}
