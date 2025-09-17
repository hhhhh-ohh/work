package com.wanmi.sbc.empower.deliveryrecord.service;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DadaOrderStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressByIdRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressByIdResponse;
import com.wanmi.sbc.empower.api.constant.DeliveryStatusConstant;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DadaMessageRiderCancelRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaCallBackRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaCancelByIdRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaDeliverFeeQueryRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaFaultConfirmRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaQueryRequest;
import com.wanmi.sbc.empower.bean.vo.DadaDeliverFeeVO;
import com.wanmi.sbc.empower.bean.vo.DadaOrderDetailVO;
import com.wanmi.sbc.empower.bean.vo.DeliveryRecordDadaVO;
import com.wanmi.sbc.empower.deliveryrecord.model.root.DeliveryRecordDada;
import com.wanmi.sbc.empower.deliveryrecord.repository.DeliveryRecordDadaRepository;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaCancelRequest;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaOrderFaultConfirmRequest;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaOrderQueryRequest;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaOrderRequest;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaRiderCancelConfirmRequest;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.request.trade.TradeConfirmReceiveRequest;
import com.wanmi.sbc.order.api.request.trade.TradeUpdateStateRequest;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.DistributionState;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressByIdRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>达达配送记录业务逻辑</p>
 *
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Slf4j
@Service("DeliveryRecordDadaService")
public class DeliveryRecordDadaService {
    @Autowired
    private DeliveryRecordDadaRepository deliveryRecordDadaRepository;

    @Autowired
    private DadaApiService dadaApiService;

    @Autowired
    private RedisUtil redisService;


    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    /**
     * 新增达达配送记录
     *
     * @author dyt
     */
    @Transactional(rollbackFor = Exception.class)
    public DeliveryRecordDada add(TradeVO order, String createPerson, String shopNo, Operator operator) {
        CustomerDeliveryAddressByIdResponse response = customerDeliveryAddressQueryProvider.getById(CustomerDeliveryAddressByIdRequest.builder()
                .deliveryAddressId(order.getConsignee().getId())
                .build()).getContext();
        PlatformAddressVO platformAddressVO = platformAddressQueryProvider.getById(PlatformAddressByIdRequest.builder().id(String.valueOf(order.getConsignee().getCityId())).build()).getContext().getPlatformAddressVO();
        DeliveryRecordDada entity = new DeliveryRecordDada();
        entity.setStoreId(order.getSupplier().getStoreId());
        entity.setOrderNo(order.getId());
        entity.setCityName(platformAddressVO.getAddrName());
        entity.setReceiverName(response.getConsigneeName());
        entity.setReceiverPhone(response.getConsigneeNumber());
        entity.setReceiverAddress(order.getConsignee().getAddress());
        entity.setCargoPrice(order.getTradePrice().getGoodsPrice());
        entity.setReceiverLat(response.getLatitude());
        entity.setReceiverLng(response.getLongitude());
        entity.setIsPrepay(Constants.no);
        entity.setIsUseInsurance(Constants.no);
        entity.setCreatePerson(createPerson);
        entity.setUpdatePerson(createPerson);
        entity.setCargoWeight(order.getTradeItems().stream().filter(vo ->
                Objects.nonNull(vo.getGoodsWeight())).reduce(BigDecimal.ZERO, (x, y) ->
                x.add(y.getGoodsWeight().multiply(new BigDecimal(y.getNum()))), BigDecimal::add));
        entity.setCityCode(dadaApiService.getCityCodeByName(entity.getCityName()));
        return thirdUpdate(insert(entity),shopNo,operator);
    }

    /**
     * 新增一条
     *
     * @param entity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public DeliveryRecordDada insert(DeliveryRecordDada entity) {
        //取店铺达达绑定表
        entity.setCityCode(dadaApiService.getCityCodeByName(entity.getCityName()));
        entity.setDelFlag(DeleteFlag.NO);
        entity.setDeliveryStatus(DadaOrderStatus.START.getStatusId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        return deliveryRecordDadaRepository.save(entity);
    }

    /**
     * 更新一条
     *
     * @param entity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public DeliveryRecordDada thirdUpdate(DeliveryRecordDada entity, String shopNumber,Operator operator) {
        // 新增订单请求
        DadaOrderRequest request = new DadaOrderRequest();
        KsBeanUtil.copyPropertiesThird(entity, request);
        request.setIsFinishCodeNeeded(Constants.yes);
        request.setShopNo(shopNumber);
        DadaDeliverFeeVO vo = dadaApiService.addOrder(request);
        if (Objects.nonNull(vo)) {
            entity.setDistance(vo.getDistance());
            entity.setFee(vo.getFee());
            entity.setDeliverFee(vo.getDeliverFee());
            entity.setInsuranceFee(vo.getInsuranceFee());
            entity.setTipsFee(vo.getTips());
        }
        entity.setDeliveryStatus(DadaOrderStatus.WAIT_RECEIPT.getStatusId());
        deliveryRecordDadaRepository.save(entity);

        //设为已分配
        this.updateDistributionStateByCode(entity.getOrderNo(), DistributionState.DONE,operator,entity.getDeliveryId());
        return entity;
    }

    /**
     * 重发达达配送记录
     *
     * @author dyt
     */
    @Transactional(rollbackFor = Exception.class)
    public DeliveryRecordDada reAdd(DeliveryRecordDada dada, String shopNo,Operator operator) {
        DeliveryRecordDada oEntity = deliveryRecordDadaRepository.getOne(dada.getDeliveryId());
        if (Objects.isNull(oEntity)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //重发订单请求
        DadaOrderRequest orderRequest = new DadaOrderRequest();
        KsBeanUtil.copyPropertiesThird(oEntity, orderRequest);
        orderRequest.setShopNo(shopNo);
        orderRequest.setIsFinishCodeNeeded(Constants.yes);
        DadaDeliverFeeVO vo = dadaApiService.reAddOrder(orderRequest);
        if (Objects.nonNull(vo)) {
            oEntity.setDistance(vo.getDistance());
            oEntity.setFee(vo.getFee());
            oEntity.setDeliverFee(vo.getDeliverFee());
            oEntity.setInsuranceFee(vo.getInsuranceFee());
            oEntity.setTipsFee(vo.getTips());
        }
        oEntity.setDeliveryStatus(DadaOrderStatus.WAIT_RECEIPT.getStatusId());
        oEntity.setUpdatePerson(dada.getUpdatePerson());
        deliveryRecordDadaRepository.save(oEntity);

        //设为已分配
        this.updateDistributionStateByCode(oEntity.getOrderNo(), DistributionState.DONE,operator,oEntity.getDeliveryId());
        return oEntity;
    }

    /**
     * 取消达达配送记录
     *
     * @author dyt
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancel(DeliveryRecordDadaCancelByIdRequest request) {
        DeliveryRecordDada oEntity = this.getOne(request.getOrderCode());
        if (Objects.isNull(oEntity)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (DeliveryStatusConstant.CANCEL == oEntity.getDeliveryStatus()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999997);
        }
        if (DeliveryStatusConstant.WAIT_TAKE == oEntity.getDeliveryStatus()
                || DeliveryStatusConstant.DELIVERY == oEntity.getDeliveryStatus()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050168);
        }
        oEntity.setCancelReasonId(request.getCancelReasonId());
        oEntity.setCancelReason(request.getReason());
        oEntity.setDeliveryStatus(DadaOrderStatus.CANCEL.getStatusId());
        oEntity.setUpdatePerson(request.getUserId());
        deliveryRecordDadaRepository.save(oEntity);
        //取消
        DadaCancelRequest cancelRequest = new DadaCancelRequest();
        cancelRequest.setCancelReasonId(request.getCancelReasonId());
        cancelRequest.setCancelReason(request.getReason());
        cancelRequest.setOrderNo(oEntity.getOrderNo());
        //积累违约金，考虑反复重发->取消情况
        BigDecimal deductFee = dadaApiService.cancel(cancelRequest);
        oEntity.setFee(Objects.isNull(oEntity.getDeductFee()) ? deductFee : oEntity.getDeductFee().add(deductFee));
        deliveryRecordDadaRepository.save(oEntity);

        //设为未分配
        this.updateDistributionStateByCode(oEntity.getOrderNo(), DistributionState.INIT,request.getOperator(),oEntity.getDeliveryId());
    }

    /**
     * 取消达达配送记录
     *
     * @author dyt
     */
    @Transactional(rollbackFor = Exception.class)
    public void confirmFault(DeliveryRecordDadaFaultConfirmRequest request) {
        DeliveryRecordDada oEntity = this.getOne(request.getOrderCode());
        if (Objects.isNull(oEntity)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 确认完成异常
        DadaOrderFaultConfirmRequest confirmRequest = new DadaOrderFaultConfirmRequest();
        confirmRequest.setOrderNo(oEntity.getOrderNo());
        dadaApiService.confirmFault(confirmRequest);
        // 同步本地记录状态
        oEntity.setDeliveryStatus(DadaOrderStatus.FAULT_BACK_END.getStatusId());
        oEntity.setUpdatePerson(request.getUserId());
        deliveryRecordDadaRepository.save(oEntity);
        // 设为未分配、待发货
        TradeStateDTO orderState = TradeStateDTO.builder()
                .deliverStatus(DeliverStatus.NOT_YET_SHIPPED)
                .distributionState(DistributionState.INIT)
                .build();
        tradeProvider.updateTradeState(TradeUpdateStateRequest.builder()
                .tradeId(request.getOrderCode())
                .tradeStateDTO(orderState)
                .orderFinishCode("")
                .build());
    }

    /**
     * 修改达达配送记录
     *
     * @author dyt
     */
    @Transactional
    public void callBack(DeliveryRecordDadaCallBackRequest request) {
        DeliveryRecordDada dada = deliveryRecordDadaRepository.findByOrderNoAndDelFlag(request.getOrder_id(),
                DeleteFlag.NO);
        if (Objects.isNull(dada)) {
            return;
        }
        // 每次订单状态变化都会发生回调，如果出现订单状态回调顺序不一致，请根据回调参数中的时间戳进行判断
        if (Objects.nonNull(dada.getDadaUpdateTime()) && dada.getDadaUpdateTime() > request.getUpdate_time()) {
            return;
        }
        dada.setDadaUpdateTime(request.getUpdate_time());
        if (StringUtils.isNotEmpty(request.getCancel_reason())) {
            dada.setCancelReason(request.getCancel_reason());
        }
        if (Objects.nonNull(request.getCancel_from()) && request.getCancel_from() > 0) {
            dada.setCancelReasonId(request.getCancel_from());
        }
        dada.setDeliveryStatus(request.getOrder_status());
        dada.setDmName(request.getDm_name());
        dada.setDmMobile(request.getDm_mobile());
        if (DadaOrderStatus.FAIL.getStatusId().equals(dada.getDeliveryStatus())) {
            dada.setDelFlag(DeleteFlag.YES);
        }
        deliveryRecordDadaRepository.save(dada);

        // 扭转订单状态
        TradeStateDTO orderState = new TradeStateDTO();
        orderState.setPluginType(request.getPluginType());
        String orderFinishCode = null;
        // 已取消、过期、妥投完成改为待分配状态
        if (DadaOrderStatus.CANCEL.getStatusId().equals(dada.getDeliveryStatus())
                || DadaOrderStatus.FAULT_BACK_END.getStatusId().equals(dada.getDeliveryStatus())
                || DadaOrderStatus.OVERDUE.getStatusId().equals(dada.getDeliveryStatus())
                || DadaOrderStatus.FAIL.getStatusId().equals(dada.getDeliveryStatus())) {
            // 设为待分配、待发货状态
            orderState.setDistributionState(DistributionState.INIT);
            orderState.setDeliverStatus(DeliverStatus.NOT_YET_SHIPPED);
            orderState.setFlowState(FlowState.AUDIT);
        } else if (DadaOrderStatus.FAULT_BACK.getStatusId().equals(dada.getDeliveryStatus())) {
            // 妥投异常之物品返回中 设为已发货状态
            orderState.setDistributionState(DistributionState.UNTREAD);
            orderState.setDeliverStatus(DeliverStatus.SHIPPED);
            orderState.setFlowState(FlowState.AUDIT);
        } else if (DadaOrderStatus.DELIVERY.getStatusId().equals(dada.getDeliveryStatus())) {
            // 配送中 设为已发货状态
            orderState.setDeliverStatus(DeliverStatus.SHIPPED);
            orderState.setFlowState(FlowState.DELIVERED);
        } else if (DadaOrderStatus.FINISH.getStatusId().equals(request.getOrder_status())) {
            // 配送完成  设为已完成订单
            orderState.setFlowState(FlowState.COMPLETED);
            orderState.setDeliverStatus(DeliverStatus.SHIPPED);
            // 构建Operator对象
            Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("1").name("SYSTEM").platform
                    (Platform.BOSS).build();
            // 调用订单确认收货
            tradeProvider.confirmReceive(TradeConfirmReceiveRequest.builder()
                    .operator(operator).tid(request.getOrder_id()).build());

        } else if (DadaOrderStatus.WAIT_PICKUP.getStatusId().equals(dada.getDeliveryStatus())) {
            // 配送中 设为已发货状态
            orderState.setDeliverStatus(DeliverStatus.SHIPPED);
            orderState.setFlowState(FlowState.DELIVERED);
            // 查询订单状态
            try {
                DadaOrderQueryRequest queryRequest = new DadaOrderQueryRequest();
                queryRequest.setOrderNo(request.getOrder_id());
                DadaOrderDetailVO detailVO = dadaApiService.queryOrder(queryRequest);
                if(Objects.nonNull(detailVO)) {
                    orderFinishCode = detailVO.getOrderFinishCode();
                }
            } catch (Exception e) {
                log.error("DeliveryRecordDadaService callBack queryDadaOrder is fail, Error is ", e);
            }
        }
        tradeProvider.updateTradeState(TradeUpdateStateRequest.builder()
                .tradeId(request.getOrder_id())
                .tradeStateDTO(orderState)
                .orderFinishCode(orderFinishCode)
                .build());
    }

    /**
     * 更新分配状态
     *
     * @param orderCode 订单号
     * @param state     分配状态
     */
    private void updateDistributionStateByCode(String orderCode, DistributionState state,Operator operator,String deliveryId) {
        TradeStateDTO orderState = new TradeStateDTO();
        orderState.setDistributionState(state);
        tradeProvider.updateTradeState(TradeUpdateStateRequest.builder()
                        .deliveryId(deliveryId)
                .tradeId(orderCode)
                .tradeStateDTO(orderState)
                .operator(operator)
                .build());
    }

    /**
     * 查询运费
     *
     * @param request 请求
     * @return 运费
     */
    public DadaDeliverFeeVO queryDeliverFee(DeliveryRecordDadaDeliverFeeQueryRequest request) {
        if (StringUtils.isBlank(request.getOrderNo())) {
            request.setOrderNo(UUIDUtil.getUUID());
        }
        DadaDeliverFeeVO feeVO = redisService.getObj(CacheKeyConstant.DADA_DELIVER_FEE.concat(request.getOrderNo()),
                DadaDeliverFeeVO.class);
        if (Objects.nonNull(feeVO)) {
            return feeVO;
        }
        //获取地址
        String cityCode = dadaApiService.getCityCodeByName(request.getCityName());
        DadaOrderRequest orderRequest = new DadaOrderRequest();
        KsBeanUtil.copyPropertiesThird(request, orderRequest);
        orderRequest.setShopNo(request.getShopNo());
        orderRequest.setCityCode(cityCode);
        DadaDeliverFeeVO vo = dadaApiService.queryDeliverFee(orderRequest);
        vo.setOrderNo(request.getOrderNo());
        //缓存有效期半个小时
        redisService.setObj(CacheKeyConstant.DADA_DELIVER_FEE.concat(request.getOrderNo()), vo, 60 * 30);
        return vo;
    }

    /**
     * 单个查询达达配送记录
     *
     * @author dyt
     */
    public DeliveryRecordDada getOne(String orderCode) {
        return deliveryRecordDadaRepository.findByOrderNoAndDelFlag(orderCode, DeleteFlag.NO);
    }

    /**
     * 分页查询达达配送记录
     *
     * @author dyt
     */
    public Page<DeliveryRecordDada> page(DeliveryRecordDadaQueryRequest queryReq) {
        return deliveryRecordDadaRepository.findAll(
                DeliveryRecordDadaWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询达达配送记录
     *
     * @author dyt
     */
    public List<DeliveryRecordDada> list(DeliveryRecordDadaQueryRequest queryReq) {
        return deliveryRecordDadaRepository.findAll(DeliveryRecordDadaWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author dyt
     */
    public DeliveryRecordDadaVO wrapperVo(DeliveryRecordDada deliveryRecordDada) {
        if (deliveryRecordDada != null) {
            return KsBeanUtil.convert(deliveryRecordDada, DeliveryRecordDadaVO.class);
        }
        return null;
    }

    /**
     * 达达骑手取消取消
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void riderCancel(DadaMessageRiderCancelRequest request) {
        DeliveryRecordDada oEntity = this.getOne(request.getOrderId());
        if (Objects.isNull(oEntity)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (DeliveryStatusConstant.CANCEL == oEntity.getDeliveryStatus()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999997);
        }
        oEntity.setCancelReason(request.getCancelReason());
        oEntity.setDeliveryStatus(DadaOrderStatus.CANCEL.getStatusId());
        oEntity.setUpdatePerson("system");
        deliveryRecordDadaRepository.save(oEntity);
        // 设为未分配，操作人取不到默认为空
        this.updateDistributionStateByCode(oEntity.getOrderNo(), DistributionState.INIT, null ,oEntity.getDeliveryId());

        DadaRiderCancelConfirmRequest cancelRequest = new DadaRiderCancelConfirmRequest();
        cancelRequest.setOrderId(request.getOrderId());
        // 产品要求现阶段默认允许
        cancelRequest.setIsConfirm(1);
        // 骑手取消确认
        dadaApiService.riderCancelConfirm(cancelRequest);
    }

    /**
     * 验证签名
     *
     * @param request 参数
     * @return 签名字符串
     */
    private Boolean validSign(DeliveryRecordDadaCallBackRequest request) {
        List<String> params = new ArrayList<>();
        params.add(request.getClient_id());
        params.add(request.getOrder_id());
        params.add(Objects.toString(request.getUpdate_time(), StringUtils.EMPTY));
        String sign = MD5Util.md5Hex(params.stream().sorted().collect(Collectors.joining()), "UTF-8").toUpperCase();
        return sign.equals(request.getSignature().toUpperCase());
    }

}

