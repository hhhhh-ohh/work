package com.wanmi.sbc.empower.provider.impl.deliveryrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressByIdRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressByIdResponse;
import com.wanmi.sbc.empower.api.provider.deliveryrecord.DeliveryRecordDadaQueryProvider;
import com.wanmi.sbc.empower.api.request.deliveryrecord.*;
import com.wanmi.sbc.empower.api.response.deliveryrecord.*;
import com.wanmi.sbc.empower.bean.vo.DadaOrderDetailVO;
import com.wanmi.sbc.empower.bean.vo.DeliveryRecordDadaVO;
import com.wanmi.sbc.empower.deliveryrecord.model.root.DeliveryRecordDada;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaOrderQueryRequest;
import com.wanmi.sbc.empower.deliveryrecord.service.DadaApiService;
import com.wanmi.sbc.empower.deliveryrecord.service.DeliveryRecordDadaService;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>达达配送记录查询服务接口实现</p>
 *
 * @author zhangwenchang
 */
@RestController
@Validated
public class DeliveryRecordDadaQueryController implements DeliveryRecordDadaQueryProvider {
    @Autowired
    private DeliveryRecordDadaService deliveryRecordDadaService;

    @Autowired
    private DadaApiService dadaApiService;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Override
    public BaseResponse<DeliveryRecordDadaPageResponse> page(@RequestBody @Valid DeliveryRecordDadaPageRequest request) {
        DeliveryRecordDadaQueryRequest queryReq = KsBeanUtil.convert(request, DeliveryRecordDadaQueryRequest.class);
        Page<DeliveryRecordDada> deliveryRecordDadaPage = deliveryRecordDadaService.page(queryReq);
        Page<DeliveryRecordDadaVO> newPage =
                deliveryRecordDadaPage.map(entity -> deliveryRecordDadaService.wrapperVo(entity));
        MicroServicePage<DeliveryRecordDadaVO> microPage = new MicroServicePage<>(newPage, request.getPageable());
        DeliveryRecordDadaPageResponse finalRes = new DeliveryRecordDadaPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<DeliveryRecordDadaListResponse> list(@RequestBody @Valid DeliveryRecordDadaListRequest request) {
        DeliveryRecordDadaQueryRequest queryReq = KsBeanUtil.convert(request, DeliveryRecordDadaQueryRequest.class);
        List<DeliveryRecordDada> deliveryRecordDadaList = deliveryRecordDadaService.list(queryReq);
        List<DeliveryRecordDadaVO> newList =
                deliveryRecordDadaList.stream().map(entity -> deliveryRecordDadaService.wrapperVo(entity)).collect(Collectors.toList());
        return BaseResponse.success(new DeliveryRecordDadaListResponse(newList));
    }

    @Override
    public BaseResponse<DeliveryRecordDadaByIdResponse> getById(@RequestBody @Valid DeliveryRecordDadaByIdRequest request) {
        TradeVO order = tradeQueryProvider.getOrderById(TradeGetByIdRequest.builder().tid(request.getOrderCode()).build()).getContext().getTradeVO();
        if (Objects.isNull(order) || Objects.isNull(order.getBuyer())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DeliveryRecordDada deliveryRecordDada = deliveryRecordDadaService.getOne(request.getOrderCode());
        return BaseResponse.success(new DeliveryRecordDadaByIdResponse(deliveryRecordDadaService.wrapperVo(deliveryRecordDada)));
    }

    @Override
    public BaseResponse<DeliveryRecordDadaDeliverFeeQueryResponse> queryDeliverFee(@RequestBody @Valid
                                                                                           DeliveryRecordDadaDeliverFeeQueryRequest
                                                                                           request) {
        return BaseResponse.success(new DeliveryRecordDadaDeliverFeeQueryResponse(deliveryRecordDadaService.queryDeliverFee(request)));
    }

    @Override
    public BaseResponse<DeliveryRecordDadaReasonListResponse> reasonList(@RequestBody @Valid
                                                                                 DeliveryRecordDadaReasonListRequest
                                                                                 request) {
        return BaseResponse.success(new DeliveryRecordDadaReasonListResponse(dadaApiService.reasonList()));
    }

    @Override
    public BaseResponse<DeliveryRecordDadaCityListResponse> cityList(@RequestBody @Valid
                                                                             DeliveryRecordDadaCityListRequest request) {
        return BaseResponse.success(new DeliveryRecordDadaCityListResponse(dadaApiService.cityList()));
    }

    @Override
    public BaseResponse<DeliveryRecordDadaProgressQueryResponse> queryProgress(@RequestBody @Valid
                                                                                       DeliveryRecordDadaProgressQueryRequest request) {
        TradeVO order = tradeQueryProvider.getOrderById(TradeGetByIdRequest.builder().tid(request.getOrderCode()).build()).getContext().getTradeVO();
        if (Objects.isNull(order) || Objects.isNull(order.getBuyer())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DadaOrderQueryRequest queryRequest = new DadaOrderQueryRequest();
        queryRequest.setOrderNo(request.getOrderCode());
        DadaOrderDetailVO detailVO = dadaApiService.queryOrder(queryRequest);
        //收货地址详情
        CustomerDeliveryAddressByIdResponse customerDeliveryAddress =
                customerDeliveryAddressQueryProvider.getById(CustomerDeliveryAddressByIdRequest.builder().deliveryAddressId(order.getConsignee().getId()).build()).getContext();
        return BaseResponse.success(DeliveryRecordDadaProgressQueryResponse.builder()
                .customerLat(Objects.nonNull(customerDeliveryAddress.getLatitude()) ?
                        customerDeliveryAddress.getLatitude() : null)
                .customerLng(Objects.nonNull(customerDeliveryAddress.getLongitude()) ?
                        customerDeliveryAddress.getLongitude() : null)
                .transporterName(detailVO.getTransporterName())
                .transporterPhone(detailVO.getTransporterPhone())
                .transporterLat(detailVO.getTransporterLat())
                .transporterLng(detailVO.getTransporterLng())
                .distance(detailVO.getDistance())
                .orderFinishCode(detailVO.getOrderFinishCode()).build());
    }
}

