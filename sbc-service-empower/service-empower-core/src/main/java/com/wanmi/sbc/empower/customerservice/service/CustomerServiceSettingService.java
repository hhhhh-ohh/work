package com.wanmi.sbc.empower.customerservice.service;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingQueryRequest;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingItemVO;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;
import com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSetting;
import com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSettingItem;
import com.wanmi.sbc.empower.customerservice.repository.CustomerServiceSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 在线客服配置业务逻辑
 *
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Service("CustomerServiceSettingService")
public class CustomerServiceSettingService {
    @Autowired private CustomerServiceSettingRepository customerServiceSettingRepository;
    @Autowired private CustomerServiceSettingItemService customerServiceSettingItemService;

    /**
     * 修改在线客服配置
     *
     * @author 韩伟
     */
    @Transactional
    public CustomerServiceSetting modify(CustomerServiceSetting entity) {
        customerServiceSettingRepository.save(entity);
        //如果平台关闭企微客服，店铺企微客服默认关闭
        if(entity.getStoreId() == 0 && entity.getStatus() == DefaultFlag.NO && entity.getPlatformType() == CustomerServicePlatformType.WECHAT){
            customerServiceSettingRepository.closeWXForStore();
        }
        //如果平台关闭七鱼客服，店铺七鱼客服默认关闭
        if(entity.getStoreId() == 0 && entity.getStatus() == DefaultFlag.NO && entity.getPlatformType() == CustomerServicePlatformType.QIYU){
            customerServiceSettingRepository.closeQiYuForStore();
        }
        return entity;
    }

    /**
     * 单个删除在线客服配置
     *
     * @author 韩伟
     */
    @Transactional
    public void deleteById(CustomerServiceSetting entity) {
        customerServiceSettingRepository.save(entity);
    }

    /**
     * 单个查询在线客服配置
     *
     * @author 韩伟
     */
    public CustomerServiceSetting getOne(Long id) {
        return customerServiceSettingRepository
                .findById(id)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "在线客服配置不存在"));
    }


    /**
     * 分页查询在线客服配置
     *
     * @author 韩伟
     */
    public Page<CustomerServiceSetting> page(CustomerServiceSettingQueryRequest queryReq) {
        return customerServiceSettingRepository.findAll(
                CustomerServiceSettingWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询在线客服配置
     *
     * @author 韩伟
     */
    public List<CustomerServiceSetting> list(CustomerServiceSettingQueryRequest queryReq) {
        return customerServiceSettingRepository.findAll(
                CustomerServiceSettingWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author 韩伟
     */
    public CustomerServiceSettingVO wrapperVo(CustomerServiceSetting customerServiceSetting) {
        if (customerServiceSetting != null) {
            CustomerServiceSettingVO customerServiceSettingVO =
                    KsBeanUtil.convert(customerServiceSetting, CustomerServiceSettingVO.class);
            // 兼容字段赋值
            customerServiceSettingVO.setOnlineServiceId(
                    customerServiceSettingVO.getId().intValue());
            customerServiceSettingVO.setServerStatus(customerServiceSettingVO.getStatus());
            // 客服坐席赋值
            List<CustomerServiceSettingItem> customerServiceSettingItemList =
                    customerServiceSettingItemService.list(customerServiceSetting.getId());
            List<CustomerServiceSettingItemVO> customerServiceSettingItemVOList =
                    customerServiceSettingItemList.stream()
                            .map(
                                    customerServiceSettingItem ->
                                            customerServiceSettingItemService.wrapperVo(
                                                    customerServiceSettingItem))
                            .collect(Collectors.toList());
            customerServiceSettingVO.setCustomerServiceSettingItemVOList(
                    customerServiceSettingItemVOList);
            return customerServiceSettingVO;
        }
        return null;
    }

    /**
     * @description 初始化一条客服开关设置记录
     * @author hanwei
     * @date 2021/4/9 16:34
     * @param storeId, platformType
     * @return com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSetting
     */
    @Transactional
    public CustomerServiceSetting initByStoreId(
            Long storeId, CustomerServicePlatformType platformType) {
        Optional<CustomerServiceSetting> storeServiceSetting = customerServiceSettingRepository.findByStoreIdAndPlatformTypeAndDelFlag(storeId, platformType, DeleteFlag.NO);
        if(storeServiceSetting.isPresent()){
            return storeServiceSetting.get();
        }
        CustomerServiceSetting customerServiceSetting = new CustomerServiceSetting();
        customerServiceSetting.setStoreId(storeId);
        customerServiceSetting.setPlatformType(platformType);
        customerServiceSetting.setStatus(DefaultFlag.NO);
        customerServiceSetting.setEffectiveApp(DefaultFlag.NO);
        customerServiceSetting.setEffectiveMobile(DefaultFlag.NO);
        customerServiceSetting.setEffectivePc(DefaultFlag.NO);
        customerServiceSetting.setDelFlag(DeleteFlag.NO);
        customerServiceSetting.setCreateTime(LocalDateTime.now());
        customerServiceSetting.setServiceType(PlatformType.STORE);
        if(CustomerServicePlatformType.WECHAT.equals(platformType)){
            customerServiceSetting.setGroupStatus(DefaultFlag.NO);
        }
        return customerServiceSettingRepository.saveAndFlush(customerServiceSetting);
    }
}
