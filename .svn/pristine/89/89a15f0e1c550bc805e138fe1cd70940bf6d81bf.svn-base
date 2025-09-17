package com.wanmi.sbc.mq.producer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityAddListByActivityIdRequest;
import com.wanmi.sbc.elastic.bean.dto.customer.EsCustomerDetailDTO;
import com.wanmi.sbc.elastic.bean.dto.customer.EsEnterpriseInfoDTO;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ManagerBaseProducerService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 会员注册后，发送MQ消息
     * @param customerVO
     */
    public void sendMQForCustomerRegister(CustomerVO customerVO) {
        EsCustomerDetailDTO esCustomerDetailDTO = wrapperEsCustomerDetailDTO(customerVO);
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_SERVICE_CUSTOMER_REGISTER);
        mqSendDTO.setData(JSONObject.toJSONString(esCustomerDetailDTO));
        mqSendProvider.send(mqSendDTO);
    }


    /**
     * 根据优惠券活动ID集合，同步生成ES数据，发送MQ消息
     * @param activityIdList
     */
    public void sendMQForAddCouponActivity(List<String> activityIdList) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_COUPON_ADD_POINTS_COUPON);
        mqSendDTO.setData(JSONObject.toJSONString(new EsCouponActivityAddListByActivityIdRequest(activityIdList)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * ES积分商品上下架
     * @param goodsIds 商品spuId
     * @param addedFlag 上下架
     */
    public void sendMQForModifyPointsGoodsAddedFlag(List<String> goodsIds, Integer addedFlag){
        Map<String, Object> map = new HashMap<>();
        map.put("goodsIds", goodsIds);
        map.put("addedFlag", addedFlag);
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_POINTS_GOODS_MODIFY_ADDED_FLAG);
        mqSendDTO.setData(JSONObject.toJSONString(map));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 小程序订阅消息推送
     * @param messageRequest 小程序订阅消息入参
     */
    public void sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest messageRequest){
        MqSendDelayDTO mqSendDTO = new MqSendDelayDTO();
        mqSendDTO.setData(JSON.toJSONString(messageRequest));
        mqSendDTO.setTopic(ProducerTopic.SEND_MINI_PROGRAM_SUBSCRIBE_MSG);
        mqSendDTO.setDelayTime(6000L);
        mqSendProvider.sendDelay(mqSendDTO);
    }

    /**
     * 包装同步ES会员对象
     * @param customerVO
     * @return
     */
    public EsCustomerDetailDTO wrapperEsCustomerDetailDTO(CustomerVO customerVO){
        CustomerDetailVO customerDetail = customerVO.getCustomerDetail();
        EsCustomerDetailDTO esCustomerDetail = new EsCustomerDetailDTO();
        esCustomerDetail.setCustomerId( customerDetail.getCustomerId() );
        esCustomerDetail.setCustomerName( customerDetail.getCustomerName() );
        esCustomerDetail.setCustomerAccount( customerVO.getCustomerAccount() );
        esCustomerDetail.setProvinceId( customerDetail.getProvinceId() );
        esCustomerDetail.setCityId( customerDetail.getCityId() );
        esCustomerDetail.setAreaId( customerDetail.getAreaId() );
        esCustomerDetail.setStreetId( customerDetail.getStreetId() );
        esCustomerDetail.setCustomerAddress( customerDetail.getCustomerAddress() );
        esCustomerDetail.setContactName( customerDetail.getContactName() );
        esCustomerDetail.setCustomerLevelId( customerVO.getCustomerLevelId() );
        esCustomerDetail.setContactPhone( customerDetail.getContactPhone() );
        esCustomerDetail.setCheckState( customerVO.getCheckState() );
        esCustomerDetail.setCustomerStatus( customerDetail.getCustomerStatus() );
        esCustomerDetail.setCustomerType(customerVO.getCustomerType());
        esCustomerDetail.setEmployeeId( customerDetail.getEmployeeId() );
        esCustomerDetail.setIsDistributor( customerDetail.getIsDistributor() );
        esCustomerDetail.setRejectReason( customerDetail.getRejectReason() );
        esCustomerDetail.setForbidReason( customerDetail.getForbidReason() );
        esCustomerDetail.setEsStoreCustomerRelaList(Lists.newArrayList() );
        esCustomerDetail.setEnterpriseInfo(Objects.isNull(customerVO.getEnterpriseInfoVO()) ? null : KsBeanUtil.convert(customerVO.getEnterpriseInfoVO(), EsEnterpriseInfoDTO.class));
        esCustomerDetail.setEnterpriseCheckState( customerVO.getEnterpriseCheckState() );
        esCustomerDetail.setEnterpriseCheckReason( customerVO.getEnterpriseCheckReason() );
        esCustomerDetail.setCreateTime( customerDetail.getCreateTime());
        return esCustomerDetail;
    }

    /**
     * 创建清分账户
     * @param businessId
     */
    public void saveLedgerAccount(String businessId) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.LEDGER_ACCOUNT_ADD);
        mqSendDTO.setData(businessId);
        mqSendProvider.send(mqSendDTO);
    }
}
