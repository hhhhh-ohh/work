package com.wanmi.sbc.miniprogramsubscribe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreInfoByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreInfoResponse;
import com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord.MiniMsgCustomerRecordQueryProvider;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordListRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordListResponse;
import com.wanmi.sbc.empower.bean.vo.MiniMsgCustomerRecordVO;
import com.wanmi.sbc.message.api.provider.minimsgtempsetting.MiniMsgTempSettingQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingByNodeIdRequest;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingByIdResponse;
import com.wanmi.sbc.message.bean.vo.MiniMsgTemplateSettingVO;
import com.wanmi.sbc.mq.producer.ManagerBaseProducerService;
import com.wanmi.sbc.order.bean.enums.ReturnType;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MiniProgramSubscribeService {

    @Autowired
    private MiniMsgCustomerRecordQueryProvider miniMsgCustomerRecordQueryProvider;

    @Autowired
    private MiniMsgTempSettingQueryProvider miniMsgTempSettingQueryProvider;

    @Autowired
    private ManagerBaseProducerService managerBaseProducerService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    /**
     * 订单发货提醒
     * @param tradeId
     */
    @Async
    public void dealTradeDeliverMiniProgramSubscribeMsg(String tradeId, String customerId, String deliverNo,
                                                        String deliverTime, String expressName) {
        TriggerNodeType triggerNodeId = TriggerNodeType.ORDER_DELIVERY;
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTemplateSettingVO miniProgramSubscribeTemplateSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", tradeId);
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", deliverTime);
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", deliverNo);
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", expressName);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("number2", map1);
            map.put("date4", map2);
            map.put("character_string13", map3);
            map.put("thing14", map4);
            // 组装入参
            PlatformSendMiniMsgRequest messageRequest =
                    PlatformSendMiniMsgRequest.builder()
                            .triggerNodeId(triggerNodeId)
                            .customerRecordId(messageCustomerRecordVO.getId())
                            .toUsers(toUsers)
                            .templateId(miniProgramSubscribeTemplateSetting.getTemplateId())
                            .page(miniProgramSubscribeTemplateSetting.getToPage().concat(tradeId))
                            .data(map)
                            .build();
            managerBaseProducerService.sendMiniProgramSubscribeMessage(messageRequest);
        }
    }

    /**
     * 售后申请审核通知
     * @param returnOrderVO
     */
    @Async
    public void dealAuditMiniProgramSubscribeMsg(ReturnOrderVO returnOrderVO, Boolean auditFlag) {
        String customerId = returnOrderVO.getBuyer().getId();
        String skuName = returnOrderVO.getReturnItems().get(0).getSkuName();
        String returnType;

        if (ReturnType.RETURN == returnOrderVO.getReturnType()){
            returnType="退货";
        }else {
            returnType="退款";
        }
        String auditResult;
        if (auditFlag.equals(Boolean.TRUE)){
            auditResult = "审核通过";
        }else {
            auditResult = "审核不通过";
        }
        TriggerNodeType triggerNodeId = TriggerNodeType.AFTER_SALES_APPLICATION;
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTemplateSettingVO miniProgramSubscribeTemplateSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", returnOrderVO.getTid());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", skuName);
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", returnType);
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", auditResult);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("character_string1", map1);
            map.put("thing4", map2);
            map.put("thing6", map3);
            map.put("phrase2", map4);
            // 组装入参
            PlatformSendMiniMsgRequest messageRequest =
                    PlatformSendMiniMsgRequest.builder()
                            .triggerNodeId(triggerNodeId)
                            .customerRecordId(messageCustomerRecordVO.getId())
                            .toUsers(toUsers)
                            .templateId(miniProgramSubscribeTemplateSetting.getTemplateId())
                            .page(miniProgramSubscribeTemplateSetting.getToPage().concat(returnOrderVO.getId()))
                            .data(map)
                            .build();
            managerBaseProducerService.sendMiniProgramSubscribeMessage(messageRequest);
        }
    }

    /**
     * 退货寄回通知
     * @param returnOrderVO
     */
    @Async
    public void dealReturnDeliverMiniProgramSubscribeMsg(ReturnOrderVO returnOrderVO) {
        String customerId = returnOrderVO.getBuyer().getId();
        TriggerNodeType triggerNodeId = TriggerNodeType.RETURN_NOTICE;
        // 异步处理小程序订阅消息发送
        FindMiniProgramTemplateAndCustomerRecord findMiniProgramTemplateAndCustomerRecord =
                new FindMiniProgramTemplateAndCustomerRecord(customerId, triggerNodeId).invoke();
        // 获取模板配置
        MiniMsgTemplateSettingVO miniProgramSubscribeTemplateSetting =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeTemplateSetting();
        // 获取用户授权信息
        List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList =
                findMiniProgramTemplateAndCustomerRecord.getMiniProgramSubscribeMessageCustomerRecordVOList();
        if (CollectionUtils.isNotEmpty(miniMsgCustomerRecordVOList)){
            Long storeId = returnOrderVO.getCompany().getStoreId();
            StoreInfoResponse storeInfoResponse = storeQueryProvider.getStoreInfoById(new StoreInfoByIdRequest(storeId))
                    .getContext();

            String tips = miniProgramSubscribeTemplateSetting.getNewTips();
            if (StringUtils.isBlank(tips)) {
                tips = miniProgramSubscribeTemplateSetting.getTips();
            }

            List<String> addrIds = new ArrayList<>();
            if (Objects.nonNull(storeInfoResponse.getProvinceId())){
                addrIds.add(Objects.toString(storeInfoResponse.getProvinceId()));
            }
            if (Objects.nonNull(storeInfoResponse.getCityId())){
                addrIds.add(Objects.toString(storeInfoResponse.getCityId()));
            }
            if (Objects.nonNull(storeInfoResponse.getAreaId())){
                addrIds.add(Objects.toString(storeInfoResponse.getAreaId()));
            }
            if (Objects.nonNull(storeInfoResponse.getStreetId())){
                addrIds.add(Objects.toString(storeInfoResponse.getStreetId()));
            }

            //根据Id获取地址信息
            List<PlatformAddressVO> voList = platformAddressQueryProvider.list(PlatformAddressListRequest.builder()
                    .addrIdList(addrIds).build()).getContext().getPlatformAddressVOList();

            Map<String, String> addrMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(voList)) {
                addrMap = voList.stream().collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName));
            }
            String address = "";
            if (Objects.nonNull(storeInfoResponse.getProvinceId())){
                String provinceName = addrMap.get(Objects.toString(storeInfoResponse.getProvinceId()));
                address = address.concat(provinceName);
            }
            if (Objects.nonNull(storeInfoResponse.getCityId())){
                String cityName = addrMap.get(Objects.toString(storeInfoResponse.getCityId()));
                address = address.concat(cityName);
            }
            if (Objects.nonNull(storeInfoResponse.getAreaId())){
                String areaName = addrMap.get(Objects.toString(storeInfoResponse.getAreaId()));
                address = address.concat(areaName);
            }
            if (Objects.nonNull(storeInfoResponse.getStreetId())){
                String streetName = addrMap.get(Objects.toString(storeInfoResponse.getStreetId()));
                address = address.concat(streetName);
            }

            // 取一条发送消息
            MiniMsgCustomerRecordVO messageCustomerRecordVO =
                    miniMsgCustomerRecordVOList.get(0);
            List<String> toUsers = Lists.newArrayList();
            toUsers.add(messageCustomerRecordVO.getOpenId());
            Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map1.put("value", returnOrderVO.getTid());
            Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map2.put("value", address.concat(storeInfoResponse.getAddressDetail()));
            Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map3.put("value", storeInfoResponse.getContactPerson());
            Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map4.put("value", storeInfoResponse.getContactMobile());
            Map<String, Object> map5 = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map5.put("value", tips);
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
            map.put("character_string5", map1);
            map.put("thing2", map2);
            map.put("name3", map3);
            map.put("phone_number4", map4);
            map.put("thing6", map5);
            // 组装入参
            PlatformSendMiniMsgRequest messageRequest =
                    PlatformSendMiniMsgRequest.builder()
                            .triggerNodeId(triggerNodeId)
                            .customerRecordId(messageCustomerRecordVO.getId())
                            .toUsers(toUsers)
                            .templateId(miniProgramSubscribeTemplateSetting.getTemplateId())
                            .page(miniProgramSubscribeTemplateSetting.getToPage().concat(returnOrderVO.getId()))
                            .data(map)
                            .build();
            managerBaseProducerService.sendMiniProgramSubscribeMessage(messageRequest);
        }
    }

    /**
     * 退款成功通知
     * @param returnOrderVO
     */
    @Async
    public void dealReturnOnlineMiniProgramSubscribeMsg(ReturnOrderVO returnOrderVO) {
        // 组装入参
        PlatformSendMiniMsgRequest messageRequest =
                PlatformSendMiniMsgRequest.builder()
                        .triggerNodeId(TriggerNodeType.REFUND_SUCCESS).rid(returnOrderVO.getId())
                        .finishTime(DateUtil.format(returnOrderVO.getFinishTime(), DateUtil.FMT_TIME_1))
                        .actualReturnPrice(String.valueOf(returnOrderVO.getReturnPrice().getActualReturnPrice()))
                        .actualPoints(String.valueOf(returnOrderVO.getReturnPoints().getActualPoints()))
                        .customerId(returnOrderVO.getBuyer().getId()).build();
        managerBaseProducerService.sendMiniProgramSubscribeMessage(messageRequest);
    }

    private class FindMiniProgramTemplateAndCustomerRecord {
        private final String customerId;
        private final TriggerNodeType triggerNodeId;
        private MiniMsgTemplateSettingVO miniProgramSubscribeTemplateSetting;
        private List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList;

        public FindMiniProgramTemplateAndCustomerRecord(String customerId, TriggerNodeType triggerNodeId) {
            this.customerId = customerId;
            this.triggerNodeId = triggerNodeId;
        }

        public MiniMsgTemplateSettingVO getMiniProgramSubscribeTemplateSetting() {
            return miniProgramSubscribeTemplateSetting;
        }

        public List<MiniMsgCustomerRecordVO> getMiniProgramSubscribeMessageCustomerRecordVOList() {
            return miniMsgCustomerRecordVOList;
        }

        public FindMiniProgramTemplateAndCustomerRecord invoke() {
            MiniMsgTempSettingByIdResponse miniMsgTempSettingByIdResponse =
                    miniMsgTempSettingQueryProvider.findByTriggerNodeId(MiniMsgTempSettingByNodeIdRequest.builder()
                            .triggerNodeId(triggerNodeId).build()).getContext();
            miniProgramSubscribeTemplateSetting = miniMsgTempSettingByIdResponse.getMiniMsgTemplateSettingVO();
            MiniMsgCustomerRecordListResponse response =
                    miniMsgCustomerRecordQueryProvider.list(MiniMsgCustomerRecordListRequest.builder()
                            .customerId(customerId).triggerNodeId(triggerNodeId).sendFlag(Constants.ZERO).build()).getContext();
            miniMsgCustomerRecordVOList = response.getMiniMsgCustomerRecordVOList();
            return this;
        }
    }
}
