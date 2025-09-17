package com.wanmi.sbc.mq.producer;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailModifyRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.elastic.bean.dto.customer.EsCustomerDetailDTO;
import com.wanmi.sbc.elastic.bean.dto.customer.EsEnterpriseInfoDTO;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelSkuOffAddedSyncRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class WebBaseProducerService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 会员注册后，发送MQ消息
     * @param customerVO
     */
    public void sendMQForCustomerRegister(CustomerVO customerVO) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_SERVICE_CUSTOMER_REGISTER);
        mqSendDTO.setData(JSONObject.toJSONString(wrapperEsCustomerDetailDTO(customerVO)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 修改会员基本信息后，发送MQ消息
     * @param modifyRequest
     */
    public void sendMQForModifyBaseInfo(CustomerDetailModifyRequest modifyRequest) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_CUSTOMER_MODIFY_BASE_INFO);
        mqSendDTO.setData(JSONObject.toJSONString(wrapperEsCustomerDetailDTO(modifyRequest)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 修改会员基本信息后，发送MQ消息
     * @param customerId
     * @param customerAccount
     */
    public void sendMQForModifyCustomerAccount(String customerId,String customerAccount) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_CUSTOMER_MODIFY_CUSTOMER_ACCOUNT);
        mqSendDTO.setData(JSONObject.toJSONString(wrapperEsCustomerDetailDTO(customerId,customerAccount)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 第三方平台下架SKU商品
     * @param providerSkuId 供应商商品skuId
     * @return
     */
    public void sendThirdPlatformSkuOffAddedSync(List<String> providerSkuId){
        try {
            ChannelSkuOffAddedSyncRequest request = new ChannelSkuOffAddedSyncRequest();
            request.setProviderSkuId(providerSkuId);
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.THIRD_PLATFORM_SKU_OFF_ADDED_FLAG);
            mqSendDTO.setData(JSONObject.toJSONString(request));
            mqSendProvider.send(mqSendDTO);
        }catch (Exception e){
            log.error("第三方订单验证调用-下架SKU商品MQ异常", e);
        }
    }


    /**
     * 包装会员ID和会员账号
     * @param customerId
     * @param customerAccount
     * @return
     */
    public EsCustomerDetailDTO wrapperEsCustomerDetailDTO(String customerId,String customerAccount){
        EsCustomerDetailDTO esCustomerDetail = new EsCustomerDetailDTO();
        esCustomerDetail.setCustomerId(customerId);
        esCustomerDetail.setCustomerAccount(customerAccount);
        return esCustomerDetail;
    }


    /**
     * 包装会员基本信息
     * @param modifyRequest
     * @return
     */
    public EsCustomerDetailDTO wrapperEsCustomerDetailDTO(CustomerDetailModifyRequest modifyRequest){
        EsCustomerDetailDTO esCustomerDetail = new EsCustomerDetailDTO();
        esCustomerDetail.setAreaId(modifyRequest.getAreaId());
        esCustomerDetail.setCityId(modifyRequest.getCityId());
        esCustomerDetail.setContactName(modifyRequest.getContactName());
        esCustomerDetail.setContactPhone(modifyRequest.getContactPhone());
        esCustomerDetail.setCustomerAddress(modifyRequest.getCustomerAddress());
        esCustomerDetail.setCustomerId(modifyRequest.getCustomerId());
        esCustomerDetail.setCustomerName(modifyRequest.getCustomerName());
        esCustomerDetail.setProvinceId(modifyRequest.getProvinceId());
        esCustomerDetail.setStreetId(modifyRequest.getStreetId());
        return esCustomerDetail;
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
        esCustomerDetail.setPointsAvailable(customerVO.getPointsAvailable());
        esCustomerDetail.setEnterpriseInfo(Objects.isNull(customerVO.getEnterpriseInfoVO()) ? null : KsBeanUtil.convert(customerVO.getEnterpriseInfoVO(), EsEnterpriseInfoDTO.class));
        esCustomerDetail.setEnterpriseCheckState( customerVO.getEnterpriseCheckState() );
        esCustomerDetail.setEnterpriseCheckReason( customerVO.getEnterpriseCheckReason() );
        esCustomerDetail.setCreateTime( customerDetail.getCreateTime());
        esCustomerDetail.setLogOutStatus(Objects.isNull(customerVO.getLogOutStatus())? Constants.NUM_1L:(long)customerVO.getLogOutStatus().toValue());
        return esCustomerDetail;
    }
}
