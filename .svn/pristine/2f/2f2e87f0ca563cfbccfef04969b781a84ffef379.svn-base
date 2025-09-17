package com.wanmi.sbc.job.jdvop.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.channel.vop.order.VopOrderProvider;
import com.wanmi.sbc.empower.api.request.channel.vop.order.VopQueryOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.channel.vop.order.VopQueryOrderTrackRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.message.VopMessageResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.order.VopQueryOrderDetailResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.order.VopQueryOrderTrackResponse;
import com.wanmi.sbc.empower.bean.vo.channel.order.VopWaybillCode;
import com.wanmi.sbc.message.api.provider.vopmessage.VopLogProvider;
import com.wanmi.sbc.message.api.request.vopmessage.VopLogAddRequest;
import com.wanmi.sbc.message.bean.enums.VopLogType;
import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeProvider;
import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeProvider;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeByThirdPlatformOrderIdRequest;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeUpdateRequest;
import com.wanmi.sbc.order.api.request.trade.TradeDeliverRequest;
import com.wanmi.sbc.order.api.response.trade.TradeDeliverResponse;
import com.wanmi.sbc.order.bean.dto.ShippingItemDTO;
import com.wanmi.sbc.order.bean.dto.ThirdPlatformTradeDTO;
import com.wanmi.sbc.order.bean.dto.TradeDeliverDTO;
import com.wanmi.sbc.order.bean.dto.TradeDeliverRequestDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.ShipperType;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.setting.api.provider.expresscompany.ExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyPageRequest;
import com.wanmi.sbc.setting.api.response.expresscompany.ExpressCompanyPageResponse;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author hanwei
 * @className JdVopDeliveryMessageHandler
 * @description 处理京东VOP发货消息
 * @date 2021/6/2 11:00
 **/
@Slf4j
@Component
public class JdVopDeliveryMessageHandler implements JdVopMessageHandler {

    //是否返回订单的配送信息。0不返回配送信息。1，返回配送信息。只支持最近2个月的配送信息查询。
    private static final int RETURN_WAY_BILL_CODE = 1;

    @Autowired
    private VopOrderProvider vopOrderProvider;

    @Autowired
    private ExpressCompanyQueryProvider expressCompanyQueryProvider;

    @Autowired
    private ThirdPlatformTradeQueryProvider thirdPlatformTradeQueryProvider;

    @Autowired
    private ThirdPlatformTradeProvider thirdPlatformTradeProvider;

    @Autowired
    private ProviderTradeProvider providerTradeProvider;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private VopLogProvider vopLogProvider;

    /**
     * 是否记录vop日志，true是记录, false是关闭
     * @return
     */
    @Value("${vopLogFlag}")
    private boolean VOP_LOG_FLAG = true;

    /**
     * 要处理的京东VOP消息类型
     * 1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更
     *
     * @return
     */
    @Override
    public Integer getVopMessageType() {
        return 12;
    }

    /**
     * @param messageList
     * @return java.util.List<java.lang.String>
     * @description 消息处理
     * @author hanwei
     * @date 2021/6/2 10:26
     **/
    @Override
    public List<String> handleMessage(List<VopMessageResponse> messageList) {
        List<String> successList = new ArrayList<>();
        messageList.forEach(message -> {
            try {
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Twelve).content("配送单生成-VOP响应信息-".concat(JSON.toJSONString(message))).build());
                }
                JSONObject jsonObject = JSON.parseObject(message.getResult());
                // 如果是不存在子单的话 就是父订单编号  子订单的话就是子订单编号
                String jdOrderId = jsonObject.getString("orderId");
                String thirdPlatformOrderId = jdOrderId;

                //查询京东订单配送信息
                VopQueryOrderTrackResponse vopQueryOrderTrackResponse = vopOrderProvider.queryOrderTrack(
                        VopQueryOrderTrackRequest.builder().jdOrderId(jdOrderId).waybillCode(RETURN_WAY_BILL_CODE).build()
                ).getContext();
                // 获取订单发货记录
                List<VopWaybillCode> waybillCodes = vopQueryOrderTrackResponse.getWaybillCode();
                VopWaybillCode waybillCode = waybillCodes.get(0);
                // 存在父单的话
                if (!Long.valueOf(0).equals(waybillCode.getParentId())) {
                    thirdPlatformOrderId = String.valueOf(waybillCodes.get(0).getParentId());
                }

                // 获取第三方订单信息
                ThirdPlatformTradeVO thirdPlatformTradeVO =
                        thirdPlatformTradeQueryProvider.queryByThirdPlatformOrderId(
                                ThirdPlatformTradeByThirdPlatformOrderIdRequest.builder().thirdPlatformOrderId(thirdPlatformOrderId)
                                        .thirdPlatformType(ThirdPlatformType.VOP).build()).getContext().getThirdPlatformTradeVO();
                if (thirdPlatformTradeVO == null) {
                    log.error("订单不存在，第三方订单id：{}", jdOrderId);
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Twelve).majorId(jdOrderId).content("配送单生成-订单不存在").build());
                    }
                    XxlJobHelper.log("订单不存在，第三方订单id：{}", jdOrderId);
                    return;
                } else {
                    log.info("订单存在，订单id：{}，第三方订单id：{}", thirdPlatformTradeVO.getParentId(), jdOrderId);
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Twelve).majorId(jdOrderId).content("配送单生成-".concat(JSON.toJSONString(thirdPlatformTradeVO))).build());
                    }
                    XxlJobHelper.log("订单存在，订单id：{}，第三方订单id：{}", thirdPlatformTradeVO.getParentId(), jdOrderId);
                }

                // 获取物流公司信息
                ExpressCompanyVO expressCompanyVO = null;
                ExpressCompanyPageResponse expressCompanyPageResponse = expressCompanyQueryProvider.page(
                        ExpressCompanyPageRequest.builder().expressName(waybillCode.getCarrier()).build()
                ).getContext();
                if (expressCompanyPageResponse == null || expressCompanyPageResponse.getExpressCompanyVOPage() == null
                        || !expressCompanyPageResponse.getExpressCompanyVOPage().hasContent()) {
                    expressCompanyPageResponse = expressCompanyQueryProvider.page(
                            ExpressCompanyPageRequest.builder().expressCode("JD").build()
                    ).getContext();
                }
                if (expressCompanyPageResponse != null && expressCompanyPageResponse.getExpressCompanyVOPage().hasContent()) {
                    expressCompanyVO = expressCompanyPageResponse.getExpressCompanyVOPage().getContent().get(0);
                } else {
                    log.error("未获取到物流公司信息：{}", JSON.toJSONString(waybillCode));
                    if (VOP_LOG_FLAG){
                        vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Twelve).majorId(jdOrderId).content("配送单生成-未获取到物流公司信息：".concat(JSON.toJSONString(waybillCode))).build());
                    }
                    expressCompanyVO = new ExpressCompanyVO();
                    expressCompanyVO.setExpressCode("JD");
                    expressCompanyVO.setExpressName(waybillCode.getCarrier());
                }

                TradeDeliverRequestDTO tradeDeliverRequestDTO = new TradeDeliverRequestDTO();
                tradeDeliverRequestDTO.setDeliverTime(DateUtil.getDate(LocalDateTime.now()));
                tradeDeliverRequestDTO.setDeliverNo(waybillCode.getDeliveryOrderId());
                if (Objects.nonNull(expressCompanyVO) && Objects.nonNull(expressCompanyVO.getExpressCompanyId())) {
                    tradeDeliverRequestDTO.setDeliverId(expressCompanyVO.getExpressCompanyId().toString());
                }
                VopQueryOrderDetailResponse vopQueryOrderDetailResponse = vopOrderProvider.queryOrderDetail(
                        VopQueryOrderDetailRequest.builder().jdOrderId(Long.valueOf(jdOrderId)).build()).getContext();
                List<ShippingItemDTO> shippingItems = new ArrayList<>();
                vopQueryOrderDetailResponse.toSkuList().forEach(vopSuborderSkuVO ->
                        thirdPlatformTradeVO.getTradeItems().forEach(tradeItem -> {
                            if (tradeItem.getThirdPlatformSkuId().equals(String.valueOf(vopSuborderSkuVO.getSkuId()))) {
                                ShippingItemDTO shippingItem = new ShippingItemDTO();
                                shippingItem.setSkuId(tradeItem.getSkuId());
                                shippingItem.setSkuNo(tradeItem.getSkuNo());
                                shippingItem.setItemNum(tradeItem.getNum());
                                shippingItem.setUnit(tradeItem.getUnit());
                                shippingItem.setSpuId(tradeItem.getSpuId());
                                shippingItem.setItemName(tradeItem.getSkuName());
                                shippingItem.setSpecDetails(tradeItem.getSpecDetails());
                                shippingItem.setProviderSkuNo(tradeItem.getProviderSkuNo());
                                shippingItems.add(shippingItem);
                                // 同步已发货数量、发货状态
                                tradeItem.setDeliveredNum(tradeItem.getNum());
                                tradeItem.setDeliverStatus(DeliverStatus.SHIPPED);
                            }
                        })
                );
                tradeDeliverRequestDTO.setShippingItemList(shippingItems);

                //处理赠品逻辑
                if (CollectionUtils.isNotEmpty(thirdPlatformTradeVO.getGifts())) {
                    List<ShippingItemDTO> giftItemList = new ArrayList<>();
                    vopQueryOrderDetailResponse.toSkuList().forEach(vopSuborderSkuVO ->
                            thirdPlatformTradeVO.getGifts().forEach(tradeItem -> {
                                if (tradeItem.getThirdPlatformSkuId().equals(String.valueOf(vopSuborderSkuVO.getSkuId()))) {
                                    ShippingItemDTO shippingItem = new ShippingItemDTO();
                                    shippingItem.setSkuId(tradeItem.getSkuId());
                                    shippingItem.setSkuNo(tradeItem.getSkuNo());
                                    shippingItem.setItemNum(tradeItem.getNum());
                                    shippingItem.setUnit(tradeItem.getUnit());
                                    shippingItem.setSpuId(tradeItem.getSpuId());
                                    shippingItem.setItemName(tradeItem.getSkuName());
                                    shippingItem.setSpecDetails(tradeItem.getSpecDetails());
                                    shippingItem.setProviderSkuNo(tradeItem.getProviderSkuNo());
                                    giftItemList.add(shippingItem);
                                    // 同步已发货数量、发货状态
                                    tradeItem.setDeliveredNum(tradeItem.getNum());
                                    tradeItem.setDeliverStatus(DeliverStatus.SHIPPED);
                                }
                            })
                    );
                    tradeDeliverRequestDTO.setGiftItemList(giftItemList);
                }

                //处理加价购逻辑
                if (CollectionUtils.isNotEmpty(thirdPlatformTradeVO.getPreferential())) {
                    List<ShippingItemDTO> itemList = new ArrayList<>();
                    vopQueryOrderDetailResponse.toSkuList().forEach(vopSuborderSkuVO ->
                            thirdPlatformTradeVO.getPreferential().forEach(tradeItem -> {
                                if (tradeItem.getThirdPlatformSkuId().equals(String.valueOf(vopSuborderSkuVO.getSkuId()))) {
                                    ShippingItemDTO shippingItem = new ShippingItemDTO();
                                    shippingItem.setSkuId(tradeItem.getSkuId());
                                    shippingItem.setSkuNo(tradeItem.getSkuNo());
                                    shippingItem.setItemNum(tradeItem.getNum());
                                    shippingItem.setUnit(tradeItem.getUnit());
                                    shippingItem.setSpuId(tradeItem.getSpuId());
                                    shippingItem.setItemName(tradeItem.getSkuName());
                                    shippingItem.setSpecDetails(tradeItem.getSpecDetails());
                                    shippingItem.setProviderSkuNo(tradeItem.getProviderSkuNo());
                                    itemList.add(shippingItem);
                                    // 同步已发货数量、发货状态
                                    tradeItem.setDeliveredNum(tradeItem.getNum());
                                    tradeItem.setDeliverStatus(DeliverStatus.SHIPPED);
                                }
                            })
                    );
                    tradeDeliverRequestDTO.setPreferentialItemList(itemList);
                }

                //发货操作人
                Operator operator = new Operator();
                operator.setPlatform(Platform.THIRD);
                operator.setName("京东系统");

                TradeDeliverVO tradeDeliver = tradeDeliverRequestDTO.toTradeDevlier(expressCompanyVO);
                tradeDeliver.setShipperType(ShipperType.PROVIDER);
                tradeDeliver.getLogistics().setThirdPlatformOrderId(thirdPlatformTradeVO.getThirdPlatformOrderIds().get(0));
                tradeDeliver.getLogistics().setOutOrderId(waybillCode.getOrderId().toString());
                tradeDeliver.setThirdPlatformType(ThirdPlatformType.VOP);
                TradeDeliverRequest deliverRequest = TradeDeliverRequest.builder()
                        .tradeDeliver(KsBeanUtil.convert(tradeDeliver, TradeDeliverDTO.class))
                        .tid(thirdPlatformTradeVO.getParentId())
                        .operator(operator)
                        .build();
                // 供应商发货
                TradeDeliverResponse tradeDeliverResponse = providerTradeProvider.providerDeliver(deliverRequest).getContext();
                if (tradeDeliverResponse != null && StringUtils.isNotBlank(tradeDeliverResponse.getDeliverId())) {
                    successList.add(message.getId());
                }

                //处理第三方订单
                LogisticsVO logistics = LogisticsVO.builder()
                        .logisticCompanyName(expressCompanyVO.getExpressName())
                        .logisticNo(waybillCode.getDeliveryOrderId())
                        .logisticStandardCode(expressCompanyVO.getExpressCode())
                        // 设置京东订单号、用户id
                        .thirdPlatformOrderId(thirdPlatformTradeVO.getThirdPlatformOrderIds().get(0))
                        .outOrderId(waybillCode.getOrderId().toString())
                        .buyerId(thirdPlatformTradeVO.getBuyer().getId())
                        .build();
                TradeDeliverVO tradeDeliverVO = TradeDeliverVO.builder()
                        .tradeId(thirdPlatformTradeVO.getId())
                        .deliverId(tradeDeliverResponse == null ? generatorService.generate("TD") : tradeDeliverResponse.getDeliverId())
                        .deliverTime(LocalDateTime.now())
                        .thirdPlatformType(ThirdPlatformType.VOP)
                        .logistics(logistics)
                        .shippingItems(KsBeanUtil.convertList(tradeDeliverRequestDTO.getShippingItemList(), ShippingItemVO.class))
                        .shipperType(ShipperType.PROVIDER)
                        .status(DeliverStatus.SHIPPED)
                        .providerName(thirdPlatformTradeVO.getSupplierName())
                        .build();
                thirdPlatformTradeVO.getTradeDelivers().add(tradeDeliverVO);
                // 判断本次发货后，是否还有部分发货或未发货的商品，来设置订单发货状态
                Long partShippedNum = thirdPlatformTradeVO.getTradeItems().stream()
                        .filter(tradeItem -> (tradeItem.getDeliverStatus() == null
                                || DeliverStatus.PART_SHIPPED.equals(tradeItem.getDeliverStatus())
                                || DeliverStatus.NOT_YET_SHIPPED.equals(tradeItem.getDeliverStatus()))
                        ).count();
                String detail = String.format("订单[%s]已%s,操作人：%s", thirdPlatformTradeVO.getId(), "发货", operator.getName());
                if (partShippedNum.intValue() != 0) {
                    thirdPlatformTradeVO.getTradeState().setFlowState(FlowState.DELIVERED_PART);
                    thirdPlatformTradeVO.getTradeState().setDeliverStatus(DeliverStatus.PART_SHIPPED);
                    detail = String.format("订单[%s]已%s,操作人：%s", thirdPlatformTradeVO.getId(), "部分发货", operator.getName());
                } else {
                    thirdPlatformTradeVO.getTradeState().setFlowState(FlowState.DELIVERED);
                    thirdPlatformTradeVO.getTradeState().setDeliverStatus(DeliverStatus.SHIPPED);
                }
                // 添加日志
                TradeEventLogVO tradeEventLogVO = TradeEventLogVO
                        .builder()
                        .operator(operator)
                        .eventType("同步京东发货")
                        .eventTime(LocalDateTime.now())
                        .eventDetail(detail)
                        .build();
                thirdPlatformTradeVO.getTradeEventLogs().add(tradeEventLogVO);
                ThirdPlatformTradeUpdateRequest thirdPlatformTradeUpdateRequest = new ThirdPlatformTradeUpdateRequest();
                thirdPlatformTradeUpdateRequest.setTrade(KsBeanUtil.convert(thirdPlatformTradeVO, ThirdPlatformTradeDTO.class));
                thirdPlatformTradeProvider.update(thirdPlatformTradeUpdateRequest);
                log.info("订单发货成功，订单id：{}，第三方订单id：{}", thirdPlatformTradeVO.getParentId(), jdOrderId);
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Twelve).majorId(jdOrderId).content("配送单生成-订单发货成功：".concat(JSON.toJSONString(thirdPlatformTradeVO))).build());
                }
                XxlJobHelper.log("订单发货成功，订单id：{}，第三方订单id：{}", thirdPlatformTradeVO.getParentId(), jdOrderId);
            } catch (Exception e) {
                log.error("处理京东推送消息[12:生成配送单]出现异常，消息内容为：{}", JSON.toJSONString(message), e);
                if (VOP_LOG_FLAG){
                    vopLogProvider.add(VopLogAddRequest.builder().vopLogType(VopLogType.Twelve).content("配送单生成-异常：".concat(JSON.toJSONString(message))).build());
                }
                XxlJobHelper.log("处理京东推送消息[12:生成配送单]出现异常，消息内容为：{}", JSON.toJSONString(message));
                XxlJobHelper.log(e);
            }
        });
        return successList;
    }
}